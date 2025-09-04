package com.zipcoder.cryptonator_api.domain;

public class CoinDataFromApi {
    private double usd;
    private double usd_24h_vol;
    private double usd_24h_change;

    public void setUsd(double usd) {
        this.usd = usd;
    }
    public double getUsd() {
        return usd;
    }
    public void setUsd_24h_change(double usd_24h_change) {
        this.usd_24h_change = usd_24h_change;
    }
    public double getUsd_24h_change() {
        return usd_24h_change;
    }
    public void setUsd_24h_vol(double usd_24h_vol) {
        this.usd_24h_vol = usd_24h_vol;
    }
    public double getUsd_24h_vol() {
        return usd_24h_vol;
    }
}
