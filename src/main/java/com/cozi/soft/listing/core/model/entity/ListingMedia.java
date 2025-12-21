package com.cozi.soft.listing.core.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@Table(name = "media")
public class ListingMedia extends GenericMedia {

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "ownership", nullable = false)
    private MediaOwnership ownership = MediaOwnership.CUSTOMER;

    public ListingMedia(String url, MediaType type) {
        super();
        setUrl(url);
        setType(type);
    }

}
