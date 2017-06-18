package net.alexpana.etherpnl.service;

import lombok.Data;
import lombok.Getter;
import net.alexpana.etherpnl.entity.CurrencyExchangeMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class CurrencyExchangeService {

    @Getter
    private volatile CurrencyExchangeMap exchange;

    private final String url;

    private RestTemplate restTemplate = new RestTemplate();

    public CurrencyExchangeService(@Value("${service.exchange.url}") String url) {
        this.url = url;
    }

    public ExchangeRate getExchangeRate(String from, String to) {
        from = from.toUpperCase();
        to = to.toUpperCase();

        if (Objects.equals(exchange.getBase(), from) && Objects.equals(exchange.getBase(), to)) {
            return new ExchangeRate(to, from, 1.0);
        }

        if (Objects.equals(exchange.getBase(), to)) {
            return new ExchangeRate(from, to, 1.0 / exchange.getRates().get(from));
        }

        if (Objects.equals(exchange.getBase(), from)) {
            return new ExchangeRate(from, to, exchange.getRates().get(to));
        }

        return new ExchangeRate(from, to, exchange.getRates().get(to) / exchange.getRates().get(from));
    }

    @Scheduled(fixedDelay = 3000)
    public void poll() {
        ResponseEntity<CurrencyExchangeMap> forEntity = restTemplate.getForEntity(url, CurrencyExchangeMap.class);
        if (forEntity.getStatusCode().value() == 200) {
            exchange = forEntity.getBody();
        }
    }

    @Data
    public static final class ExchangeRate {
        private final String from;
        private final String to;
        private final Double rate;
    }
}
