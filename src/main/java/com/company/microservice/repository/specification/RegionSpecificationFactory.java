package com.company.microservice.repository.specification;

import com.company.microservice.domain.Region;
import com.company.microservice.entity.RegionEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class RegionSpecificationFactory {

    public static Specification<RegionEntity> regionByCode(Region region) {
        return regionByCode(region.getCode());
    }

    public static Specification<RegionEntity> regionByCode(String code) {
        return (Root<RegionEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            return builder.equal(root.get("code"), code);
        };
    }
}
