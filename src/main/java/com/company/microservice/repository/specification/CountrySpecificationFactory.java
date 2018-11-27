package com.company.microservice.repository.specification;

import com.company.microservice.domain.Country;
import com.company.microservice.entity.CountryEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class CountrySpecificationFactory {

    public static Specification<CountryEntity> countryByAlpha2(Country country) {
        return countryByAlpha2(country.getAlpha2());
    }

    public static Specification<CountryEntity> countryByAlpha2(String alpha2) {
        return (Root<CountryEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            return builder.equal(root.get("alpha2"), alpha2);
        };
    }

}
