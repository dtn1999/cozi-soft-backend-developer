/*
 *  Copyright (c) CoziSoft 2025 - All rights reserved.
 */

package com.cozi.soft.listing.config;


import com.cozi.soft.listing.core.ListingManager;
import com.cozi.soft.listing.core.ports.in.ListingManagement;
import com.cozi.soft.listing.core.ports.out.SiteRepository;
import com.cozi.soft.listing.infra.SiteRepositoryImpl;
import com.cozi.soft.listing.infra.jpa.JpaSiteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.cozi.soft.*")
public class ListingDomainConfiguration {

    @Bean
    public ObjectMapper createObjectMapper() {
        return new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Bean
    public ListingManagement listingManagement(SiteRepository siteRepository) {
        return new ListingManager(siteRepository);
    }

    @Bean
    public SiteRepository siteRepository(JpaSiteRepository jpaSiteRepository) {
        return new SiteRepositoryImpl(jpaSiteRepository);
    }
}
