package org.example.sii_task.models.currency;

import java.math.BigDecimal;
import java.util.List;

public class CurrencyRateResponse {
    private List<Rate> rates;

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

    public static class Rate {
        private String no;
        private String effectiveDate;
        private double mid;

        public double getMid() {
            return mid;
        }

        public void setMid(double mid) {
            this.mid = mid;
        }
    }
}
