package com.company.microservice.repository;

import com.company.microservice.domain.ModePopularity;
import com.company.microservice.entity.CountryEntity;
import com.company.microservice.entity.ModeEntity;
import com.company.microservice.entity.PlayerEntity;
import com.company.microservice.entity.RegionEntity;
import com.company.microservice.entity.SessionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.List;

public class ModeRepositoryImpl implements ModeRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Page<ModePopularity> getModePopularityByRegion(RegionEntity regionEntity, Pageable pageable) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ModePopularity> query = builder.createQuery(ModePopularity.class);

        Root<PlayerEntity> player = query.from(PlayerEntity.class);
        Join<PlayerEntity, SessionEntity> sessionJoin = player.join("session", JoinType.LEFT);
        Join<PlayerEntity, CountryEntity> countryJoin = player.join("country", JoinType.LEFT);
        Join<SessionEntity, ModeEntity> modeJoin = sessionJoin.join("mode", JoinType.LEFT);
        Join<CountryEntity, RegionEntity> regionJoin = countryJoin.join("region", JoinType.LEFT);

        Expression mode = modeJoin.get("name");
        Expression popularity = builder.count(player.get("id"));

        query.select(builder.construct(ModePopularity.class, mode, popularity));
        query.where(builder.equal(regionJoin, regionEntity));
        query.groupBy(modeJoin.get("name"));
        query.orderBy(builder.desc(popularity));

        TypedQuery<ModePopularity> typeQuery = entityManager.createQuery(query);

        final List<ModePopularity> popularModes = typeQuery.getResultList();

        return new PageImpl<>(popularModes, pageable, popularModes.size());
    }
}
