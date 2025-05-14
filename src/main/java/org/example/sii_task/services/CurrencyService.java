package org.example.sii_task.services;

import org.example.sii_task.errorHandling.NbpException;
import org.example.sii_task.models.currency.CurrencyRateResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Service
public class CurrencyService {
    private static final MathContext mc = new MathContext(2, RoundingMode.HALF_DOWN);


    private final RestTemplate restTemplate;

    public CurrencyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BigDecimal getRateToPln(String currencyCode) {
        String NBP_API_URL = "https://api.nbp.pl/api/exchangerates/rates/A/";
        String url = UriComponentsBuilder.fromHttpUrl(NBP_API_URL + currencyCode)
                .queryParam("format", "json")
                .toUriString();

        try {
            CurrencyRateResponse response = restTemplate.getForObject(url, CurrencyRateResponse.class);
            if (response != null && response.getRates() != null && !response.getRates().isEmpty()) {
                double rate = response.getRates().getFirst().getMid();
                return BigDecimal.valueOf(rate);
            }
        } catch (Exception e) {
            throw new NbpException("Cannot convert currency. External API error.");
        }
        return null;
    }

    // Converts first to PLN, then to target currency. Caused by API I used (see ReadMe)
    public BigDecimal convertCurrency(String fromCurrency, String toCurrency, BigDecimal amount) {
        BigDecimal fromCurrencyToPln = getRateToPln(fromCurrency);
        BigDecimal toCurrencyToPln = getRateToPln(toCurrency);

        if (fromCurrencyToPln == null || toCurrencyToPln == null) {
            throw new NbpException("Unable to fetch exchange rates.");
        }
        BigDecimal amountInPln = amount.multiply(fromCurrencyToPln);
        return amountInPln.divide(toCurrencyToPln, mc);
    }

    public BigDecimal getRateFromPLN(String fromCurrency, BigDecimal amount) {
        BigDecimal fromCurrencyToPln = getRateToPln(fromCurrency);
        if (fromCurrencyToPln == null) {
            throw new NbpException("Unable to fetch exchange rates.");
        }
        return amount.divide(fromCurrencyToPln, mc);
    }
}