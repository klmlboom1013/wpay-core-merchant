package com.wpay.core.merchant.trnsmpi.adapter.out.persistence;

import com.wpay.common.global.annotation.PersistenceAdapter;
import com.wpay.common.global.common.Functions;
import com.wpay.core.merchant.trnsmpi.application.port.out.dto.MpiBasicInfoMapper;
import com.wpay.core.merchant.trnsmpi.application.port.out.persistence.MpiBasicInfoPersistencePort;
import com.wpay.core.merchant.trnsmpi.application.port.out.persistence.MpiBasicInfoPersistenceVersion;
import com.wpay.core.merchant.trnsmpi.domain.ActivityMpiTrns;
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

    @Override public MpiBasicInfoPersistenceVersion getVersionCode() { return MpiBasicInfoPersistenceVersion.v1; }

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
    public void recodeActivitiesRun(@NonNull ActivityMpiTrns activityMpiTrns) {
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
                .jobDvdCd(activityMpiTrns.getJobCodes().getCode())
                .payRsltCd(activityMpiTrns.getActivitySendMpi().getPayRsltCd())
                .rspsGrmConts(activityMpiTrns.getActivitySendMpi().getRspsGrmConts())
                .otransWtid(activityMpiTrns.getMpiTrnsId().getWtid())
                .regiDt(yyyymmdd)
                .regiTm(hhmmss)
                .chngDt(yyyymmdd)
                .chngTm(hhmmss)
                .build();
        log.info("[{}][{}] Entity Save Set {}", mid, wtid, mpiTrnsJpaEntity);

        this.mpiTrnsRepository.save(mpiTrnsJpaEntity);
    }
}