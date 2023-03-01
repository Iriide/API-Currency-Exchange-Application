package main.java.org.example;

import java.net.MalformedURLException;
import java.net.URL;

public class Currency {
    /*
     * Class for storing currency data,it only contains the getters and setters
     * @param code - String with currency code
     * @param quarry - URL with API request URL
     * @param amount - Double with amount of currency
     * @param mid - Double with currency exchange rate
     */
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
