package com.cozi.soft.listing;

import com.cozi.soft.listing.config.ListingDomainConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import({ListingDomainConfiguration.class, ServiceModuleTestConfig.class})
class ListingApplicationTests {

    @Test
    void contextLoads() {
    }

}
