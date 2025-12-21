package com.cozi.soft.listing.core.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
public class EntityCollection<D> {
    private int page;
    private long limit;
    private long offset;
    private long total;
    private Set<D> data;

    public static <D> EntityCollection<D> from(Collection<D> data) {
        return EntityCollection.<D>builder()
                .data(new HashSet<>(data))
                .page(1)
                .limit(data.size())
                .offset(0)
                .total(data.size())
                .build();
    }

    public static <D> EntityCollection<D> empty() {
        return EntityCollection.<D>builder()
                .data(Collections.emptySet())
                .page(0)
                .limit(0)
                .offset(0)
                .total(0)
                .build();
    }
}
