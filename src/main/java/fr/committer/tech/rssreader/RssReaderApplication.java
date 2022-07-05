package fr.committer.tech.rssreader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RssReaderApplication {

    public static void main(String[] args) {
        SpringApplication.run(RssReaderApplication.class, args);
    }

}
