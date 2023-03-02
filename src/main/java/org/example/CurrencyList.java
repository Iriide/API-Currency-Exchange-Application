package main.java.org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class CurrencyList {
    /*
     * Class for storing currency codes and table IDs
     * @param codeList - HashMap used to store currency codes and table IDs
     * @param codeAndName - ObservableList used to store currency codes and names
     * @param url - String used to store API URL
     */
    private static final Map<String, String> codeList = new HashMap<>();
    private static final ObservableList<String> codeAndName = FXCollections.observableArrayList();
    String url;

    CurrencyList() {
        try {
            url = "http://api.nbp.pl/api/exchangerates/tables/";
            // Adding currencies from both tables
            readCurrencyList("a");
            readCurrencyList("b");
            // We need to add PLN to the list because it's not in the API response
            codeAndName.add(0, "PLN - złoty polski");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void readCurrencyList(String tableid) throws Exception {
        // Method for reading currency codes and names from API response
        // They will be udes to generate lists of avaiable at the moment currencies so we don't have to hardcode them
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
        // Sorting the list so it is displayed in alphabetical order
        codeAndName.sort(Comparator.naturalOrder());
    }
    String getTableId(String code) {
        return codeList.get(code);
    }
    static ObservableList<String> getCodeAndName() {
        return codeAndName;
    }
}
