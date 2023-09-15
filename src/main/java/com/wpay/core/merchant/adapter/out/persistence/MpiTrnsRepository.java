package com.wpay.core.merchant.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MpiTrnsRepository extends JpaRepository<com.wpay.core.merchant.adapter.out.persistence.MpiTrnsJpaEntity, com.wpay.core.merchant.adapter.out.persistence.MpiTrnsJpaEntity.MpiTrnsId> {
    @Query("select max(a.srlno) from MpiTrnsJpaEntity a where a.wtid = :wtid")
    Long getMpiTrnsByWtid(@Param("wtid") String wtid);

    @Query("select COUNT(*) from MpiTrnsJpaEntity a where a.wtid = :wtid and a.jobDvdCd = :jobDvdCd")
    Integer getCountByWtidAndJobDvdCd(@Param("wtid") String wtid, @Param("jobDvdCd") String jobDvdCd);
}
