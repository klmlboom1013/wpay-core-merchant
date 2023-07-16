package com.wpay.core.merchant.adapter.out.persistence;

import com.wpay.core.merchant.application.port.out.dto.MpiBasicInfoMapper;
import com.wpay.core.merchant.application.port.out.persistence.MpiBasicInfoPersistencePort;
import com.wpay.core.merchant.domain.ActivityMpiTrns;
import com.wpay.core.merchant.global.annotation.PersistenceAdapter;
import com.wpay.core.merchant.global.common.Functions;
import com.wpay.core.merchant.application.port.in.usecase.MpiBasicInfoVersion;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.persistence.EntityNotFoundException;
import java.util.Date;

@Log4j2
@PersistenceAdapter
@RequiredArgsConstructor
class MpiBasicInfoPersistence implements MpiBasicInfoPersistencePort {

    private final MpiTrnsRepository mpiTrnsRepository;

    @Override public MpiBasicInfoVersion getVersionCode() { return MpiBasicInfoVersion.v1; }

    @Override
    public MpiBasicInfoMapper loadActivitiesRun(@NonNull ActivityMpiTrns activityMpiTrns) {
        final String wtid = activityMpiTrns.getMpiTrnsId().getWtid();
        final String mid = activityMpiTrns.getMid();
        final Long srlno = this.mpiTrnsRepository.getMpiTrnsByWtid(wtid);
        log.info("[{}][{}] MPI_TRNS SRLNO 조회 완료 [wtid:{}][srlno:{}]", mid, wtid, wtid, srlno);

        final MpiTrnsJpaEntity mpiTrnsJpaEntity = this.mpiTrnsRepository
                .findById(new MpiTrnsJpaEntity.MpiTrnsId(wtid, srlno))
                .orElseThrow(() -> new EntityNotFoundException("MpiTrns 조회 결과가 없습니다."));
        log.info("[{}][{}] MPI_TRNS 조회 결과 : {}", mid, wtid, mpiTrnsJpaEntity);

        return MpiBasicInfoMapper.builder()
                .wtid(activityMpiTrns.getMpiTrnsId().getWtid())
                .mid(activityMpiTrns.getMid())
                .message(mpiTrnsJpaEntity.getRspsGrmConts())
                .build();
    }

    @Override
    public boolean recodeActivitiesRun(@NonNull ActivityMpiTrns activityMpiTrns) {
        final String wtid = activityMpiTrns.getMpiTrnsId().getWtid();
        final String mid = activityMpiTrns.getMid();

        final String[] timestamp = Functions.getTimestampMilliSecond.apply(new Date()).split(" ");
        final String yyyymmdd = timestamp[0].trim().replaceAll("-", "");
        final String hhmmss = timestamp[1].split("\\.")[0].trim().replaceAll(":","");

        final MpiTrnsJpaEntity mpiTrnsJpaEntity = MpiTrnsJpaEntity.builder()
                .wtid(activityMpiTrns.getMpiTrnsId().getWtid())
                .srlno(activityMpiTrns.getMpiTrnsId().getSrlno())
                .connUrl(activityMpiTrns.getActivitySendMpi().getConnUrl())
                .jnoffcId(activityMpiTrns.getMid())
                .idcDvdCd(activityMpiTrns.getServerName())
                .jobDvdCd(activityMpiTrns.getJobCode().getCode())
                .payRsltCd(activityMpiTrns.getActivitySendMpi().getPayRsltCd())
                .rspsGrmConts(activityMpiTrns.getActivitySendMpi().getRspsGrmConts())
                .otransWtid(activityMpiTrns.getMpiTrnsId().getWtid())
                .regiDt(yyyymmdd)
                .regiTm(hhmmss)
                .chngDt(yyyymmdd)
                .chngTm(hhmmss)
                .build();
        log.info("[{}][{}] Entity Save Set {}", mid, wtid, mpiTrnsJpaEntity);
        final MpiTrnsJpaEntity saveResult = this.mpiTrnsRepository.save(mpiTrnsJpaEntity);

        final boolean result = (saveResult.getWtid().equals(mpiTrnsJpaEntity.getWtid()) && saveResult.getSrlno().equals(mpiTrnsJpaEntity.getSrlno()));

        if(result) log.info("[{}][{}] Entity Save Success.", mid, wtid);
        else log.warn("[{}][{}] Entity Save Fail.", mid, wtid);

        return result;
    }


}
