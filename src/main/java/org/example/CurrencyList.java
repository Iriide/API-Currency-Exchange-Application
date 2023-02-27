package org.example;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.*;

public class CurrencyList {
    private static final Map<String, String> codeList = new HashMap<>();
    private static final ObservableList<String> codeAndName = FXCollections.observableArrayList();
    String url;

    CurrencyList() {
        try {
            url = "http://api.nbp.pl/api/exchangerates/tables/";
            codeAndName.add("PLN - złoty polski");
            readCurrencyList("a");
            readCurrencyList("b");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void readCurrencyList(String tableid) throws Exception {
        URL url_ = new URL(String.format("%s/%s/?format=json", url, tableid));
        HttpURLConnection conn = (HttpURLConnection) url_.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        String inline = "";
        int responseCode = conn.getResponseCode();
        if(responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        }
        else {
            Scanner scanner = new Scanner(url_.openStream());
            while(scanner.hasNext()) {
                inline += scanner.nextLine();
            }
            scanner.close();
        }
        conn.disconnect();

        JSONParser parse = new JSONParser();
        JSONArray data_obj = (JSONArray) parse.parse(inline);
        JSONObject table = (JSONObject) data_obj.get(0);
        JSONArray rates = (JSONArray) table.get("rates");

        for (Object o : rates) {
            JSONObject rate = (JSONObject) o;
            String code = (String) rate.get("code");
            String name = (String) rate.get("currency");
            codeList.put(code, tableid);
            codeAndName.add(String.format("%s - %s", code, name));
        }
        codeAndName.sort(Comparator.naturalOrder());
    }

    boolean isCurrencyCode(String code) {
        return codeList.containsKey(code);
    }
    String getTableId(String code) {
        return codeList.get(code);
    }
    static ObservableList<String> getCodeAndName() {
        return codeAndName;
    }
}
