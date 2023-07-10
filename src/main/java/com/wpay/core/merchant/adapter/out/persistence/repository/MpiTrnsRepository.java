package com.wpay.core.merchant.adapter.out.persistence.repository;

import com.wpay.core.merchant.adapter.out.persistence.entity.MpiTrns;
import com.wpay.core.merchant.adapter.out.persistence.entity.pk.MpiTrnsPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MpiTrnsRepository extends JpaRepository<MpiTrns, MpiTrnsPrimaryKey> {
    @Query("select max(a.srlno) from MpiTrns a where a.wtid = :wtid")
    Long getMpiTrnsByWtid(@Param("wtid") String wtid);
}
