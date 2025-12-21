package com.cozi.soft.listing;

import com.cozi.soft.listing.config.ListingDomainConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ListingDomainConfiguration.class)
public class ListingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ListingApplication.class, args);
    }

}
