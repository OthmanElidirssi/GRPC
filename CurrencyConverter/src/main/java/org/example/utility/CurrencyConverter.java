package org.example.utility;

import java.util.HashMap;
import java.util.Map;

public class CurrencyConverter {
    public Map<String, Map<String, Double>> getExchangeRates() {
        return exchangeRates;
    }

    private Map<String, Map<String, Double>> exchangeRates;

    public CurrencyConverter() {
        exchangeRates = new HashMap<>();
        this.addConversionRate("usd", "eur", 0.93 );
        this.addConversionRate("eur", "usd", 1.07);
        this.addConversionRate("usd", "gbp", 0.75);
        this.addConversionRate("gbp", "usd", 1.33);
        this.addConversionRate("eur", "gbp", 0.85);
        this.addConversionRate("gbp", "eur", 1.18);
    }

    // Add a conversion rate between two currencies (case-insensitive)
    public void addConversionRate(String fromCurrency, String toCurrency, double rate) {
        fromCurrency = fromCurrency.toUpperCase();
        toCurrency = toCurrency.toUpperCase();

        exchangeRates.computeIfAbsent(fromCurrency, k -> new HashMap<>()).put(toCurrency, rate);
    }

    // Convert an amount from one currency to another (case-insensitive)
    public double convert(double amount, String fromCurrency, String toCurrency) {
        fromCurrency = fromCurrency.toUpperCase();
        toCurrency = toCurrency.toUpperCase();

        if (fromCurrency.equals(toCurrency)) {
            return amount;
        }

        Map<String, Double> fromRates = exchangeRates.get(fromCurrency);
        if (fromRates == null) {
            throw new IllegalArgumentException("Invalid fromCurrency");
        }

        Double conversionRate = fromRates.get(toCurrency);
        if (conversionRate == null) {
            throw new IllegalArgumentException("Invalid toCurrency");
        }

        return amount * conversionRate;
    }
}