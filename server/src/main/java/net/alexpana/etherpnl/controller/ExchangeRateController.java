package net.alexpana.etherpnl.controller;

import net.alexpana.etherpnl.service.CurrencyExchangeService;
import net.alexpana.etherpnl.service.KrakenTradesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/0/")
public class ExchangeRateController {

    private KrakenTradesService krakenTradesService;

    private CurrencyExchangeService currencyExchangeService;

    public ExchangeRateController(KrakenTradesService krakenTradesService, CurrencyExchangeService currencyExchangeService) {
        this.krakenTradesService = krakenTradesService;
        this.currencyExchangeService = currencyExchangeService;
    }

    @RequestMapping("/exchange/{from}/{to}")
    @ResponseBody
    public CurrencyExchangeService.ExchangeRate exchange(@PathVariable("from") String from, @PathVariable("to") String to) {
        Double ethToEur = krakenTradesService.getLastPrice();

        if ("ETH".equals(from.toUpperCase())) {
            Double ethToRequested = ethToEur * currencyExchangeService.getExchangeRate("EUR", to).getRate();
            return new CurrencyExchangeService.ExchangeRate(from, to, ethToRequested);
        }

        if ("ETH".equals(to.toUpperCase())) {
            Double ethToRequested = ethToEur * currencyExchangeService.getExchangeRate("EUR", from).getRate();
            return new CurrencyExchangeService.ExchangeRate(from, to, 1.0 / ethToRequested);
        }

        return currencyExchangeService.getExchangeRate(from, to);
    }
}
