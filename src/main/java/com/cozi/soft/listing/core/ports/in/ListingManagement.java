package com.cozi.soft.listing.core.ports.in;

import com.cozi.soft.listing.core.model.entity.EntityCollection;
import com.cozi.soft.listing.core.model.entity.Site;
import com.cozi.soft.listing.core.model.payload.CreateSiteRequest;
import com.cozi.soft.listing.core.model.payload.PatchSiteRequest;
import io.vavr.control.Try;
import org.springframework.validation.annotation.Validated;

public interface ListingManagement {

    Try<Site> createSite(@Validated CreateSiteRequest request);

    Try<Site> patchSiteById(Long siteId, @Validated PatchSiteRequest request);

    Try<Site> findSiteById(Long siteId);

    Try<EntityCollection<Site>> listSites();

    Try<Site> deleteSiteById(Long siteId);
}
