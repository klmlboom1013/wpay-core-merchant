package com.wpay.core.merchant.adapter.out.persistence.repository;

import com.wpay.core.merchant.adapter.out.persistence.entity.MpiTrns;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MpiTrnsRepository extends JpaRepository<MpiTrns, MpiTrns.MpiTrnsPrimaryKey> {

    List<MpiTrns> findAllByWtid(String wtid);
}
