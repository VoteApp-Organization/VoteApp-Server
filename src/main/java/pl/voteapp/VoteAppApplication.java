package pl.voteapp;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VoteAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(VoteAppApplication.class, args);
    }

    @Bean
    InitializingBean loadInitialData() {
        return () -> {
            //Utils.createExampleData();
        };
    }
}
