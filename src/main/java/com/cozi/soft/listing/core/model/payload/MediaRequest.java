package com.cozi.soft.listing.core.model.payload;

import com.cozi.soft.listing.core.model.entity.MediaType;
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
public class MediaRequest {
    @NotBlank
    private String url;
    @NotBlank
    private MediaType type;
}
