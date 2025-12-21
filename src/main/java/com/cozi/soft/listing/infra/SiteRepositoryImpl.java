package com.cozi.soft.listing.infra;

import com.cozi.soft.listing.core.model.entity.Site;
import com.cozi.soft.listing.core.model.exception.ListingNotFoundException;
import com.cozi.soft.listing.core.ports.out.SiteRepository;
import com.cozi.soft.listing.infra.jpa.JpaSiteRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class SiteRepositoryImpl implements SiteRepository {
    private final JpaSiteRepository repository;

    public SiteRepositoryImpl(JpaSiteRepository repository) {
        this.repository = repository;
    }

    @Override
    public Site save(Site site) {
        return this.repository.save(site);
    }

    @Override
    public Optional<Site> findById(Long siteId) {
        return this.repository.findById(siteId);
    }

    @Override
    public void deleteById(Long siteId, boolean hardDelete) {
        Site site = this.findById(siteId)
                .orElseThrow(() -> new ListingNotFoundException("No listing found with id: " + siteId));
        if (hardDelete) {
            this.repository.delete(site);
        } else {
            site.setDeleted(true);
            this.save(site);
        }
    }

    @Override
    public Set<Site> findAll() {
        return new HashSet<>(this.repository.findAll());
    }
}
