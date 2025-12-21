package com.cozi.soft.listing.core;

import com.cozi.soft.listing.core.model.entity.*;
import com.cozi.soft.listing.core.model.exception.ListingNotFoundException;
import com.cozi.soft.listing.core.model.payload.CreateSiteRequest;
import com.cozi.soft.listing.core.model.payload.ListingAddressRequest;
import com.cozi.soft.listing.core.model.payload.MediaRequest;
import com.cozi.soft.listing.core.model.payload.PatchSiteRequest;
import com.cozi.soft.listing.core.ports.in.ListingManagement;
import com.cozi.soft.listing.core.ports.out.SiteRepository;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class ListingManager implements ListingManagement {
    private final SiteRepository siteRepository;

    @Override
    public Try<Site> createSite(CreateSiteRequest request) {
        return this.createNewSite(request)
                .map(this.siteRepository::save);
    }

    @Override
    public Try<Site> findSiteById(Long siteId) {
        return Try.success(siteId)
                .map(this.siteRepository::findById)
                .filter(Optional::isPresent)
                .recoverWith(NoSuchElementException.class, e -> Try.failure(new ListingNotFoundException(e)))
                .map(Optional::get);
    }

    @Override
    public Try<EntityCollection<Site>> listSites() {
        return Try.of(this.siteRepository::findAll)
                .map(EntityCollection::from);
    }

    @Override
    public Try<Site> patchSiteById(Long siteId, PatchSiteRequest request) {
        return this.findSiteById(siteId)
                .map(site -> this.updateSite(site, request))
                .map(this.siteRepository::save);
    }

    @Override
    public Try<Site> deleteSiteById(Long siteId) {
        return this.findSiteById(siteId)
                .map(site -> {
                    site.setDeleted(true);
                    return site.setState(SiteState.ARCHIVED);
                })
                .map(this.siteRepository::save);
    }

    private ListingAddress createListingAddress(ListingAddressRequest newAddress) {
        return ListingAddress.builder()
                .country(newAddress.getCountry())
                .city(newAddress.getCity())
                .state(newAddress.getState())
                .street(newAddress.getStreet())
                .country(newAddress.getCountry())
                .build();
    }

    private Try<Site> createNewSite(CreateSiteRequest request) {
        return Try.of(Site::new)
                .andThen(site -> {
                    Optional.ofNullable(request)
                            .map(CreateSiteRequest::getName)
                            .ifPresent(site::setName);
                    Optional.ofNullable(request)
                            .map(CreateSiteRequest::getOrganizationId)
                            .ifPresent(site::setOrganizationId);
                    Optional.ofNullable(request)
                            .map(CreateSiteRequest::getTotalSiteArea)
                            .ifPresent(site::setTotalSiteArea);
                    Optional.ofNullable(request)
                            .map(CreateSiteRequest::getParcelArea)
                            .ifPresent(site::setParcelArea);
                    Optional.ofNullable(request)
                            .map(CreateSiteRequest::getNumberOfParcels)
                            .ifPresent(site::setNumberOfParcels);
                    site.setState(SiteState.DRAFT);
                });
    }

    private Site updateSite(Site site, PatchSiteRequest request) {
        Optional.ofNullable(request)
                .map(PatchSiteRequest::getMonthlyTaxesFee)
                .ifPresent(site::setParcelMonthlyTaxesFee);
        Optional.ofNullable(request)
                .map(PatchSiteRequest::getContractProcessingFee)
                .ifPresent(site::setContractProcessingFee);
        Optional.ofNullable(request)
                .map(PatchSiteRequest::getDefaultLoanTermInMonths)
                .ifPresent(site::setDefaultLoanTermInMonths);
        Optional.ofNullable(request)
                .map(PatchSiteRequest::getName)
                .ifPresent(site::setName);
        Optional.ofNullable(request)
                .map(PatchSiteRequest::getDescription)
                .ifPresent(site::setDescription);
        Optional.ofNullable(request)
                .map(PatchSiteRequest::getParcelArea)
                .ifPresent(site::setParcelArea);
        Optional.ofNullable(request)
                .map(PatchSiteRequest::getPricePerParcel)
                .ifPresent(site::setPricePerParcel);
        Optional.ofNullable(request)
                .map(PatchSiteRequest::getTotalSiteArea)
                .ifPresent(site::setTotalSiteArea);
        Optional.ofNullable(request)
                .map(PatchSiteRequest::getNumberOfParcels)
                .ifPresent(site::setNumberOfParcels);
        Optional.ofNullable(request)
                .map(PatchSiteRequest::getAnnualInterestRate)
                .ifPresent(site::setAnnualInterestRate);
        Optional.ofNullable(request)
                .map(PatchSiteRequest::getDownPaymentPerParcel)
                .ifPresent(site::setDownPaymentPerParcel);
        Optional.ofNullable(request)
                .map(PatchSiteRequest::getAddress)
                .map(this::createListingAddress)
                .map(address -> {
                    address.setId(address.getId());
                    return address;
                })
                .ifPresent(site::setAddress);
        Optional.ofNullable(request)
                .map(PatchSiteRequest::getCoverImage)
                .map(this::createListingMedia)
                .ifPresent(site::setCoverImage);
        Optional.ofNullable(request)
                .map(PatchSiteRequest::getMedias)
                .map(this::createListingMedia)
                .ifPresent(site::updateMedias);
        Optional.ofNullable(request)
                .map(PatchSiteRequest::getDocuments)
                .map(this::createListingMedia)
                .ifPresent(site::updateDocuments);
        return site;
    }

    private Set<ListingMedia> createListingMedia(Set<MediaRequest> medias) {
        return medias.stream()
                .map(this::createListingMedia)
                .collect(Collectors.<ListingMedia>toSet());
    }

    private ListingMedia createListingMedia(MediaRequest media) {
        return new ListingMedia(media.getUrl(), media.getType());
    }

}
