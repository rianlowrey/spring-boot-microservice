package com.company.microservice.repository.specification;

import com.company.microservice.domain.Player;
import com.company.microservice.entity.PlayerEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class PlayerSpecificationFactory {

    public static Specification<PlayerEntity> playerWithName(Player player) {
        return (Root<PlayerEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            return builder.equal(root.get("name"), player.getName());
        };
    }

}
