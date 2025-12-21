package com.cozi.soft.listing.core.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@Table(name = "site", schema = "listing_service")
public class Site extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "parcel_area")
    private BigDecimal parcelArea;
    @Column(name = "total_site_area")
    private BigDecimal totalSiteArea;
    @Column(name = "number_of_parcels")
    private Integer numberOfParcels;
    @Column(name = "price_per_parcel")
    private Long pricePerParcel;
    @Column(name = "monthly_taxes_fee")
    private Long parcelMonthlyTaxesFee;
    @Column(name = "annual_interest_rate")
    private BigDecimal annualInterestRate;
    @Column(name = "contract_processing_fee", nullable = false)
    private Long contractProcessingFee;
    @Column(name = "default_loan_term_in_months", nullable = false)
    private Integer defaultLoanTermInMonths;
    @Column(name = "down_payment_per_parcel")
    private Long downPaymentPerParcel;
    @Column(name = "monthly_payment_per_parcel")
    private Long monthlyPaymentPerParcel;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private SiteState state;

    // Foreign entities
    @Column(name = "organization_id", nullable = false)
    private Long organizationId;

    @JoinColumn(name = "cover_image_id")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private ListingMedia coverImage;

    @JoinColumn(name = "address_id")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private ListingAddress address;

    @Builder.Default
    @JoinTable(
            name = "site_media",
            joinColumns = @JoinColumn(name = "site_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "media_id", referencedColumnName = "id")
    )
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ListingMedia> medias = new HashSet<>();

    @Builder.Default
    @JoinTable(
            name = "site_media",
            joinColumns = @JoinColumn(name = "site_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "media_id", referencedColumnName = "id")
    )
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ListingMedia> documents = new HashSet<>();

    public void updateMedias(Collection<ListingMedia> medias) {
        this.medias.clear();
        this.medias.addAll(medias);
    }

    public void updateDocuments(Collection<ListingMedia> documents) {
        this.documents.clear();
        this.documents.addAll(documents);
    }
}
