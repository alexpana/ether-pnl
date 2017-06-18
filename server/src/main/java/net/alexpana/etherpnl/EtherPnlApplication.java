package net.alexpana.etherpnl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class EtherPnlApplication {
    public static void main(String[] args) {
        SpringApplication.run(EtherPnlApplication.class, args);
    }
}
