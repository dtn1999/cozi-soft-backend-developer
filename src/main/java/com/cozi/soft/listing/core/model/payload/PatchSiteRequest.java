package com.cozi.soft.listing.core.model.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PatchSiteRequest {
    private String name;
    private BigDecimal parcelArea;
    private BigDecimal totalSiteArea;
    private Integer  numberOfParcels;
    private Long pricePerParcel;
    private Long monthlyTaxesFee;
    private BigDecimal annualInterestRate;
    private Long contractProcessingFee;
    private Integer defaultLoanTermInMonths;
    private Long downPaymentPerParcel;
    private String description;
    private MediaRequest coverImage;
    private Set<MediaRequest> medias;
    private Set<MediaRequest> documents;
    private ListingAddressRequest address;
}
