package com.zipcoder.cryptonator_api.services;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.zipcoder.cryptonator_api.domain.CoinDataFromApi;
import com.zipcoder.cryptonator_api.domain.CryptoRates;
import com.zipcoder.cryptonator_api.repositories.CryptoRatesRepository;

/**
 * Created by leon on 1/22/18.
 */
@Service
public class CryptoRatesService {

    @Autowired
    private CryptoRatesRepository repo;

    @Autowired
    private RestTemplate restTemplate;

    private final List<String> cryptoSymbols = List.of("ethereum", "bitcoin", "litecoin");

    @Scheduled(fixedRate = 60_000, initialDelay = 10_000)///300_000 equal 5 mins
    public void refreshCryptoRates(){

        cryptoSymbols.forEach(symbol -> {
            try {
                fetchAndSaveCoinDataFromApi(symbol);
                System.out.println("Updated: " + symbol + " at " + LocalDateTime.now());
            } catch (Exception e) {
                System.err.println("Failed to update " + symbol + ": " + e.getMessage());
            }
        });
    }

    @Transactional
    public CryptoRates fetchAndSaveCoinDataFromApi(String cryptoName) {
        String url = "https://api.coingecko.com/api/v3/simple/price?ids=" + cryptoName
                + "&vs_currencies=usd&include_24hr_change=true&include_24hr_vol=true";

        ResponseEntity<Map<String, CoinDataFromApi>> response = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<Map<String, CoinDataFromApi>>() {
                });

        CoinDataFromApi data = response.getBody().get(cryptoName);

        if (data == null) {
            throw new RuntimeException("No data found from API for " + cryptoName);
        }

        CryptoRates crypto = repo.findByName(cryptoName).orElse(new CryptoRates());
        crypto.setName(cryptoName);
        crypto.setPrice(data.getUsd());
        crypto.setDayChange(data.getUsd_24h_change());
        crypto.setDayVolume(data.getUsd_24h_vol());
        crypto.setLastCheckedTime(java.time.LocalDateTime.now().toString());

        return repo.save(crypto);
    }

    public List<CryptoRates> getAllCryptoRates() {
        return repo.findAll();
    }

    public Optional<CryptoRates> findByName(String name) {
        return repo.findByName(name);
    }

    public CryptoRates saveCryptoRates(CryptoRates crypto) {
        return repo.save(crypto);
    }

    public boolean delete(String name) {
        return repo.findByName(name)
                .map(crypto -> {
                    repo.delete(crypto);
                    return true;
                })
                .orElse(false);
    }

    public Optional<CryptoRates> update(String name, CryptoRates crypto) {
        return repo.findByName(name).map(existing -> {
            existing.setPrice(crypto.getPrice());
            existing.setDayChange(crypto.getDayChange());
            existing.setDayVolume(crypto.getDayVolume());
            existing.setLastCheckedTime(crypto.getLastCheckedTime());
            return repo.save(existing);
        });
    }

}
