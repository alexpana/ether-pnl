package net.alexpana.etherpnl.entity;

import lombok.Getter;

import java.util.Map;

@Getter
public class CurrencyExchangeMap {
    private String base;
    private String date;
    private Map<String, Double> rates;
}
