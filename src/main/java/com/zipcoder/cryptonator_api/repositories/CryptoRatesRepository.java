package com.zipcoder.cryptonator_api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zipcoder.cryptonator_api.domain.CryptoRates;

/**
 * Created by leon on 1/22/18.
 */
public interface CryptoRatesRepository extends JpaRepository<CryptoRates,String> {

    Optional<CryptoRates>findByName(String name);
}
