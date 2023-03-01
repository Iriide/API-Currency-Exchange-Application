package main.java.org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.*;

public class APIRequest {
    /**
     * Class for API request, parsing JSON and calculating currency exchange
     * @param host - API host URL
     * @param input_currency - list of input currencies, used to store currencies from user input
     * @param inline - String used to store JSON response from API
     * @param currencyList - CurrencyList object used to store currency codes and table IDs
     */
    static String host = "http://api.nbp.pl/api/exchangerates/rates/";
    List<Currency> input_currency = new ArrayList<>();
    String inline;
    static CurrencyList currencyList;

    public APIRequest() {
        currencyList = new CurrencyList();
    }

    private  void getInput(String cur1, String cur2, Double amnt1) {
        /*
         * Method for getting user input and creating Currency objects
         * @param cur1 - String with currency code and currency name
         * @param cur2 - String with currency code and currency name
         * @param amnt1 - Double with amount of currency
         */
        Currency currency1 = new Currency();
        currency1.setCode(cur1.split(" ")[0]);
        currency1.setAmount(amnt1);
        Currency currency2 = new Currency();
        currency2.setCode(cur2.split(" ")[0]);

        input_currency.add(currency1);
        input_currency.add(currency2);
    }
    private  void createQuarry(Currency currency) throws MalformedURLException {
        /*
         * Method for creating API request URL
         * @param currency - Currency object
         */
        if(Objects.equals(currency.getCode(), "PLN")) {
            return;
        }
        String quarry = String.format("%s%s/%s/?format=json", host, currencyList.getTableId(currency.getCode()), currency.getCode().toLowerCase());
        currency.setQuarry(quarry);
    }

    private  void connect(Currency currency) throws IOException {
        /*
         * Method for connecting to API and getting JSON response
         * @param currency - Currency object
         */
        if(currency.getQuarry() == null) {
            return;
        }
        HttpURLConnection conn = (HttpURLConnection) currency.getQuarry().openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        this.inline = "";
        int responseCode = conn.getResponseCode();
        if(responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        }
        else {
            Scanner scanner = new Scanner(currency.getQuarry().openStream());
            while(scanner.hasNext()) {
                this.inline += scanner.nextLine();
            }
            scanner.close();
        }
        conn.disconnect();
    }

    private  void parse(Currency currency) throws ParseException {
        /*
         * Method for parsing JSON response
         * @param currency - Currency object
         */
        JSONParser parse = new JSONParser();
        JSONObject data_obj = (JSONObject) parse.parse(this.inline);
        JSONArray rates = (JSONArray) data_obj.get("rates");

        for (Object o : rates) {
            JSONObject rate = (JSONObject) o;
            Double mid = (Double) rate.get("mid");
            currency.setMid(mid);
        }

    }

    private  Double calculateExchangeRate(){
        return input_currency.get(0).getMid() / input_currency.get(1).getMid() * input_currency.get(0).getAmount();
    }

    private  String printResult(){
        return String.format("1.00 %s = %.2f %s%n", input_currency.get(0).getCode(), input_currency.get(0).getMid() / input_currency.get(1).getMid(), input_currency.get(1).getCode());
    }

    public String[] calculate(String cur1, String cur2, Double amnt1) throws Exception {
        /*
         * Method for calculating currency exchange
         * @param cur1 - String with currency code and currency name
         * @param cur2 - String with currency code and currency name
         * @param amnt1 - Double with amount of currency
         * @return String[] with calculated exchange rate and exchange rate
         */
        getInput(cur1, cur2, amnt1);
        for(Currency currency : input_currency) {
            if(Objects.equals(currency.getCode(), "PLN")) {
                continue;
            }
            createQuarry(currency);
            connect(currency);
            parse(currency);
        }
        String[] result = new String[]{String.format("%.2f%n", calculateExchangeRate()), printResult()};
        input_currency.clear();
        return result;
    }
}
