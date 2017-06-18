package net.alexpana.etherpnl.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class KrakenTrades {

    private List<String> error;

    private Trades result;

    public static final class Trades {

        @JsonProperty("XETHZEUR")
        public List<Trade> xethzeur;
    }

    @Data
    public static final class Trade {
        private final Double value;
        private final Double volume;
        private final Date time;

        @JsonCreator
        public Trade(List<Object> array) {
            value = Double.valueOf(array.get(0).toString());
            volume = Double.valueOf(array.get(1).toString());
            time = new Date(Math.round(Double.valueOf(array.get(2).toString()) * 1000));
        }
    }
}
