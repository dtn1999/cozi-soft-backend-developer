package com.cozi.soft.listing.infra.jpa;

import com.cozi.soft.listing.core.model.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaSiteRepository extends JpaRepository<Site, Long> {

}
