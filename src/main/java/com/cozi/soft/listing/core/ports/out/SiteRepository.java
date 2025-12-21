package com.cozi.soft.listing.core.ports.out;

import com.cozi.soft.listing.core.model.entity.Site;
import io.vavr.control.Try;

import java.util.Optional;
import java.util.Set;

public interface SiteRepository {
    Site save(Site site);
    Optional<Site> findById(Long siteId);
    void deleteById(Long siteId, boolean hardDelete);
    Set<Site> findAll();
}
