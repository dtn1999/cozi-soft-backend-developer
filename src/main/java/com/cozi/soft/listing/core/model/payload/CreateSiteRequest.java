package com.cozi.soft.listing.core.model.payload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CreateSiteRequest {
    private Long organizationId;

    @NotBlank
    private String name;
    @Min(1)
    @NotNull
    private BigDecimal parcelArea;
    @Min(1)
    @NotNull
    private BigDecimal totalSiteArea;
    @Min(1)
    @NotNull
    private Integer numberOfParcels;
}
