package com.cozi.soft.listing.api;

import com.cozi.soft.listing.core.ports.in.ListingManagement;
import com.cozisoft.ream.api.ListingsApi;
import com.cozisoft.ream.model.CreateSiteRequestDto;
import com.cozisoft.ream.model.PatchSiteRequestDto;
import com.cozisoft.ream.model.SiteCollectionDto;
import com.cozisoft.ream.model.SiteDto;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class ListingManagementApi implements ListingsApi {
    private final ListingManagement listingManagement;

    @Override
    public ResponseEntity<SiteDto> createSite(CreateSiteRequestDto createSiteRequestDto) {
        return Try.success(createSiteRequestDto)
                .map(OrganizationMapper.INSTANCE::map)
                .flatMap(this.listingManagement::createSite)
                .map(OrganizationMapper.INSTANCE::map)
                .map(siteDto -> ResponseEntity.status(HttpStatus.CREATED).body(siteDto))
                .get();
    }

    @Override
    public ResponseEntity<Void> deleteSiteById(Long siteId) {
        this.listingManagement.deleteSiteById(siteId)
                .get();
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<SiteDto> getSiteById(Long siteId) {
        return this.listingManagement.findSiteById(siteId)
                .map(OrganizationMapper.INSTANCE::map)
                .map(ResponseEntity::ok)
                .get();
    }

    @Override
    public ResponseEntity<SiteCollectionDto> listSites() {
        return this.listingManagement.listSites()
                .map(OrganizationMapper.INSTANCE::mapSitesCollection)
                .map(ResponseEntity::ok)
                .get();
    }

    @Override
    public ResponseEntity<SiteDto> patchSiteById(Long siteId, PatchSiteRequestDto patchSiteRequestDto) {
        return Try.success(patchSiteRequestDto)
                .map(OrganizationMapper.INSTANCE::map)
                .flatMap(site -> this.listingManagement.patchSiteById(siteId, site))
                .map(OrganizationMapper.INSTANCE::map)
                .map(ResponseEntity::ok)
                .get();
    }
}
