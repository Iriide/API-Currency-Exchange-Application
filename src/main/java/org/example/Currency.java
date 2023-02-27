package org.example;

import java.net.MalformedURLException;
import java.net.URL;

public class Currency {
    private String code = "";
    private URL quarry = null;
    private Double amount = 0.0;
    private Double mid = 1.0;


    public Currency() {}
    public void setCode(String code) {
        this.code = code;
    }
    public void setQuarry(String quarry) throws MalformedURLException {
        this.quarry = new URL(quarry);;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public void setMid(Double mid) {
        this.mid = mid;
    }
    public String getCode() {
        return this.code;
    }
    public URL getQuarry() {
        return this.quarry;
    }
    public Double getAmount() {
        return this.amount;
    }
    public Double getMid() {
        return this.mid;
    }
}
