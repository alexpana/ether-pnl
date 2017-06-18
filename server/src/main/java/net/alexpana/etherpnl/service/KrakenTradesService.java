package net.alexpana.etherpnl.service;

import net.alexpana.etherpnl.entity.KrakenTrades;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class KrakenTradesService {

    private volatile KrakenTrades trades;

    private final String krakenTradesUrl;

    private RestTemplate restTemplate = new RestTemplate();

    public KrakenTradesService(@Value("${service.kraken.tradesUrl}") String krakenTradesUrl) {
        this.krakenTradesUrl = krakenTradesUrl;
    }

    public Double getLastPrice() {
        List<KrakenTrades.Trade> trades = this.trades.getResult().xethzeur;
        return trades.get(trades.size() - 1).getValue();
    }

    @Scheduled(fixedDelay = 3000)
    public void poll() {
        ResponseEntity<KrakenTrades> forEntity = restTemplate.getForEntity(krakenTradesUrl, KrakenTrades.class);
        if (forEntity.getStatusCode().value() == 200) {
            trades = forEntity.getBody();
        }
    }
}
