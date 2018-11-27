package com.company.microservice.repository;

import com.company.microservice.domain.ModePopularity;
import com.company.microservice.entity.RegionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ModeRepositoryCustom {

    Page<ModePopularity> getModePopularityByRegion(RegionEntity region, Pageable pageable);

}
