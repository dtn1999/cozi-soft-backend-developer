package com.cozi.soft.listing.api;

import com.cozi.soft.listing.core.model.entity.*;
import com.cozi.soft.listing.core.model.payload.CreateSiteRequest;
import com.cozi.soft.listing.core.model.payload.MediaRequest;
import com.cozi.soft.listing.core.model.payload.PatchSiteRequest;
import com.cozisoft.ream.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper(uses = {CommonMapper.class})
public interface OrganizationMapper {
    OrganizationMapper INSTANCE = Mappers.getMapper(OrganizationMapper.class);

    /***********************************************************
     *  Listing related
     ***********************************************************/
    SiteDto map(Site site);

    ListingAddressDto map(ListingAddress address);

    ListingMediaDto map(GenericMedia media);

    CreateSiteRequest map(CreateSiteRequestDto dto);

    MediaRequest map(MediaRequestDto dto);

    PatchSiteRequest map(PatchSiteRequestDto dto);

    MediaType map(ListingMediaTypeDto type);

    SiteCollectionDto mapSitesCollection(EntityCollection<Site> siteEntityCollection);

}
