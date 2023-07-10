package com.wpay.core.merchant.adapter.out.persistence.repository;

import com.wpay.core.merchant.adapter.out.persistence.entity.MpiTrns;
import com.wpay.core.merchant.adapter.out.persistence.entity.pk.MpiTrnsPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MpiTrnsRepository extends JpaRepository<MpiTrns, MpiTrnsPrimaryKey> {

//    List<MpiTrns> findAllByWtid(String wtid);
}
