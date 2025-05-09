package org.example.sii_task.services;

import org.example.sii_task.models.currency.CurrencyRateResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class CurrencyService {

    private final String NBP_API_URL = "https://api.nbp.pl/api/exchangerates/rates/A/";


    private final RestTemplate restTemplate;

    public CurrencyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public double getRateToPln(String currencyCode) {
        String url = UriComponentsBuilder.fromHttpUrl(NBP_API_URL + currencyCode)
                .queryParam("format", "json")
                .toUriString();

        try {
            CurrencyRateResponse response = restTemplate.getForObject(url, CurrencyRateResponse.class);
            if (response != null && response.getRates() != null && !response.getRates().isEmpty()) {
                return response.getRates().getFirst().getMid();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    // Converts first to PLN, then to target currency. Caused by API I used (see ReadMe)
    public double convertCurrency(String fromCurrency, String toCurrency, double amount) {
        double fromCurrencyToPln = getRateToPln(fromCurrency);
        double toCurrencyToPln = getRateToPln(toCurrency);

        if (fromCurrencyToPln == 0.0 || toCurrencyToPln == 0.0) {
            throw new RuntimeException("Unable to fetch exchange rates.");
        }
        double amountInPln = amount * fromCurrencyToPln;
        return amountInPln / toCurrencyToPln;
    }

    public double getRateFromPLN(String fromCurrency, double amount) {
        double fromCurrencyToPln = getRateToPln(fromCurrency);
        if (fromCurrencyToPln == 0.0) {
            throw new RuntimeException("Unable to fetch exchange rates.");
        }
        System.out.println("FROM CURRENCY TO PLN:" + fromCurrencyToPln);
        return amount/fromCurrencyToPln;
    }
}