package com.wpay.core.merchant.trnsmpi.adapter.out.persistence;

import com.wpay.common.global.annotation.PersistenceAdapter;
import com.wpay.common.global.functions.DateFunctions;
import com.wpay.core.merchant.trnsmpi.application.port.out.dto.MpiBasicInfoMapper;
import com.wpay.core.merchant.trnsmpi.application.port.out.persistence.MpiBasicInfoPersistencePort;
import com.wpay.core.merchant.trnsmpi.application.port.out.persistence.MpiBasicInfoPersistenceVersion;
import com.wpay.core.merchant.trnsmpi.domain.ActivityMpiBasicInfo;
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
    public MpiBasicInfoMapper loadActivitiesRun(@NonNull ActivityMpiBasicInfo activityMpiBasicInfo) {
        final String wtid = activityMpiBasicInfo.getMpiTrnsId().getWtid();
        final String mid = activityMpiBasicInfo.getMid();
        final Long srlno = this.mpiTrnsRepository.getMpiTrnsByWtid(wtid);
        log.info("[{}][{}] MPI_TRNS SRLNO 조회 완료 [wtid:{}][srlno:{}]", mid, wtid, wtid, srlno);

        final MpiTrnsJpaEntity mpiTrnsJpaEntity = this.mpiTrnsRepository
                .findById(new MpiTrnsJpaEntity.MpiTrnsId(wtid, srlno))
                .orElseThrow(() -> new EntityNotFoundException("MpiTrns 조회 결과가 없습니다."));
        log.info("[{}][{}] MPI_TRNS 조회 결과 : {}", mid, wtid, mpiTrnsJpaEntity);

        return MpiBasicInfoMapper.builder()
                .wtid(activityMpiBasicInfo.getMpiTrnsId().getWtid())
                .mid(activityMpiBasicInfo.getMid())
                .message(mpiTrnsJpaEntity.getRspsGrmConts())
                .build();
    }

    @Override
    public void recodeActivitiesRun(@NonNull ActivityMpiBasicInfo activityMpiBasicInfo) {
        final String wtid = activityMpiBasicInfo.getMpiTrnsId().getWtid();
        final String mid = activityMpiBasicInfo.getMid();

        final String[] datetime = DateFunctions.getDateAndTime.apply(new Date());

        final MpiTrnsJpaEntity mpiTrnsJpaEntity = MpiTrnsJpaEntity.builder()
                .wtid(activityMpiBasicInfo.getMpiTrnsId().getWtid())
                .srlno(activityMpiBasicInfo.getMpiTrnsId().getSrlno())
                .connUrl(activityMpiBasicInfo.getActivitySendMpi().getConnUrl())
                .jnoffcId(activityMpiBasicInfo.getMid())
                .idcDvdCd(activityMpiBasicInfo.getServerName())
                .jobDvdCd(activityMpiBasicInfo.getJobCodes().getCode())
                .payRsltCd(activityMpiBasicInfo.getActivitySendMpi().getPayRsltCd())
                .rspsGrmConts(activityMpiBasicInfo.getActivitySendMpi().getRspsGrmConts())
                .otransWtid(activityMpiBasicInfo.getMpiTrnsId().getWtid())
                .regiDt(datetime[0])
                .regiTm(datetime[1])
                .chngDt(datetime[0])
                .chngTm(datetime[1])
                .build();
        log.info("[{}][{}] Entity Save Set {}", mid, wtid, mpiTrnsJpaEntity);

        this.mpiTrnsRepository.save(mpiTrnsJpaEntity);
    }
}
