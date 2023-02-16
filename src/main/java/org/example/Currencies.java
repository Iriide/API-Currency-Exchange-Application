package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Currencies {
    private final List<String> codeList = new ArrayList<String>();
    BufferedReader reader;
    String delimiter = ",";

    Currencies(){
        try {
            this.readCurrencyFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readCurrencyFile() throws IOException {
        reader = new BufferedReader(new FileReader("src\\resources\\list-one.csv"));

        String line = reader.readLine();
        while (true) {
                line = reader.readLine();
                if (line == null) break;
                String[] fields = line.split(delimiter);
                if(fields.length < 3) continue;
                String code = fields[2];
                codeList.add(code);
        }
        reader.close();
        codeList.sort(String::compareTo);
    }

    boolean isCurrencyCode(String code) {
        return codeList.contains(code);
    }
}
