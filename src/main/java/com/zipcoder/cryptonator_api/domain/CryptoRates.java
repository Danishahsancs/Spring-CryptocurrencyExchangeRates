package com.zipcoder.cryptonator_api.domain;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


/**
 * Created by leon on 1/22/18.
 */

@Entity
@Table(name = "crypto_rates")
public class CryptoRates {

    @Id
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull
    @Column
    private double price;

    @NotNull
    @Column(name = "day_volume")
    private double dayVolume;

    @NotNull
    @Column(name = "day_change")
    private double dayChange;

    @NotNull
    @Column(name = "last_checked_time")
    private String lastCheckedTime;

    public CryptoRates() {
    }

    public CryptoRates(String name, double price, double dayVolume, double dayChange, String lastCheckedTime) {
        this.name = name;
        this.price = price;
        this.dayVolume = dayVolume;
        this.dayChange = dayChange;
        this.lastCheckedTime = lastCheckedTime;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDayVolume(double dayVolume) {
        this.dayVolume = dayVolume;
    }

    public double getDayVolume() {
        return dayVolume;
    }

    public void setDayChange(double dayChange) {
        this.dayChange = dayChange;
    }

    public double getDayChange() {
        return dayChange;
    }

    public void setLastCheckedTime(String lastCheckedTime) {
        this.lastCheckedTime = lastCheckedTime;
    }

    public String getLastCheckedTime() {
        return lastCheckedTime;
    }
}
