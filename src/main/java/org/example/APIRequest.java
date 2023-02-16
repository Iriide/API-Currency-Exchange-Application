package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class APIRequest {
    /*
    *   This class is responsible for connecting to the API and parsing the response.
     */
    static String host = "http://api.nbp.pl/api/exchangerates/rates/a";
    static String[] input_currency = new String[]{};
    static Double amount = 0.0;
    Map<String, Double> mid = new HashMap<String, Double>();
    String inline = "";
    static Currencies currencies = new Currencies();

    public APIRequest() {
        try {
            this.main(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void getInput() {
        // input currency codes to the class variable
        System.out.println("Enter currency codes separated by comma:");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        input_currency = input.split(",");
        System.out.println("Enter the amount of money to convert:");
        Double amount = Double.parseDouble(scanner.nextLine());
        scanner.close();
    }
    public String createQuarry(int index) {
        for(String currency : input_currency) {
            if(!currencies.isCurrencyCode(currency.toUpperCase())) {
                System.out.println("Invalid currency code: " + currency);
                System.exit(1);
            }
        }

        String quarry = String.format("%s/%s/?format=json", host, this.input_currency[index].toLowerCase());
        System.out.println(quarry);
        return quarry;
    }

    public void connect(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        this.inline = "";
        int responseCode = conn.getResponseCode();
        if(responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        }
        else {
            Scanner scanner = new Scanner(url.openStream());
            while(scanner.hasNext()) {
                this.inline += scanner.nextLine();
            }
            scanner.close();
        }
        conn.disconnect();
    }

    public void parse() throws ParseException {
        JSONParser parse = new JSONParser();
        JSONObject data_obj = (JSONObject) parse.parse(this.inline);
        JSONArray rates = (JSONArray) data_obj.get("rates");
        for(int i = 0; i < rates.size(); i++) {
            JSONObject rate = (JSONObject) rates.get(i);
            Double mid = (Double) rate.get("mid");
            String code = (String) data_obj.get("code");
            this.mid.put(code, mid);
        }
    }

    public void main(String[] args) throws Exception {
        getInput();
        URL url_from = new URL(createQuarry(0));
        URL url_to = new URL(createQuarry(1));
        connect(url_from);
        parse();
        connect(url_to);
        parse();
    }


}
