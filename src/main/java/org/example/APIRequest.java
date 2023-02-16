package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.*;

public class APIRequest {
    static String host = "http://api.nbp.pl/api/exchangerates/rates/a";
    static List<Currency> input_currency = new ArrayList<>();
    String inline;
    static Currencies currencies = new Currencies();

    public APIRequest() {
        try {
            this.main(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void getInput() {
        System.out.println("Enter currency codes separated by comma:");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] input_ = input.split(",");

        for(String currency : input_) {
            if(!currencies.isCurrencyCode(currency.toUpperCase())) {
                System.out.println("Invalid currency code: " + currency);
                System.exit(1);
            }
        }

        for(String currency : input_) {
            Currency currency_ = new Currency();
            currency_.setCode(currency.toUpperCase());
            input_currency.add(currency_);
        }

        System.out.println("Enter the amount of money to convert:");
        input_currency.get(0).setAmount(Double.parseDouble(scanner.nextLine()));
        scanner.close();
    }
    public void createQuarry(Currency currency) throws MalformedURLException {
        if(Objects.equals(currency.getCode(), "PLN")) {
            return;
        }
        String quarry = String.format("%s/%s/?format=json", host, currency.getCode().toLowerCase());
        currency.setQuarry(quarry);
    }

    public void connect(Currency currency) throws IOException {
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

    public void parse(Currency currency) throws ParseException {
        JSONParser parse = new JSONParser();
        JSONObject data_obj = (JSONObject) parse.parse(this.inline);
        JSONArray rates = (JSONArray) data_obj.get("rates");

        for (Object o : rates) {
            JSONObject rate = (JSONObject) o;
            Double mid = (Double) rate.get("mid");
            currency.setMid(mid);
        }

    }

    public void calculateExchangeRate(){
        Double rate = input_currency.get(0).getMid() / input_currency.get(1).getMid();
        input_currency.get(1).setAmount(input_currency.get(0).getAmount() * rate);
    }

    public void printResult(){
        for(Currency currency : input_currency) {
            System.out.printf("%s: %.2f%n", currency.getCode(), currency.getAmount());
        }

        System.out.printf("1.00 %s = %.2f %s%n", input_currency.get(0).getCode(), input_currency.get(0).getMid() / input_currency.get(1).getMid(), input_currency.get(1).getCode());
    }

    public void main(String[] args) throws Exception {
        getInput();
        for(Currency currency : input_currency) {
            if(Objects.equals(currency.getCode(), "PLN")) {
                continue;
            }
            createQuarry(currency);
            connect(currency);
            parse(currency);
        }
        calculateExchangeRate();
        printResult();
    }


}
