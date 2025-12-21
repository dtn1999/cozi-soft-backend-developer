package com.cozi.soft.listing.core.model.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ListingAddressRequest {
    @NotBlank
    private String country;
    @NotBlank
    private String state;
    @NotBlank
    private String city;
    private String street;
    private String zipCode;
}
