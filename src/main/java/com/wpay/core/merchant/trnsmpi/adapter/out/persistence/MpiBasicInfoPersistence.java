package com.wpay.core.merchant.trnsmpi.adapter.out.persistence;

import com.wpay.common.global.annotation.PersistenceAdapter;
import com.wpay.core.merchant.trnsmpi.application.port.out.dto.MpiBasicInfoMapper;
import com.wpay.core.merchant.trnsmpi.application.port.out.persistence.MpiBasicInfoPersistencePort;
import com.wpay.core.merchant.trnsmpi.application.port.out.persistence.MpiBasicInfoPersistenceVersion;
import com.wpay.core.merchant.trnsmpi.domain.ActivityMpiBasicInfo;
import com.wpay.core.merchant.trnsmpi.domain.RecodeMpiBasicInfoTrns;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;

import javax.persistence.EntityNotFoundException;

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
    public void recodeActivitiesRun(@NonNull RecodeMpiBasicInfoTrns recodeMpiBasicInfoTrns) {
        final String wtid = recodeMpiBasicInfoTrns.getWtid();
        final String mid = recodeMpiBasicInfoTrns.getJnoffcId();
        log.info("[{}][{}] MPI_TRNS SAVE 시작.\n[{}]", mid, wtid, recodeMpiBasicInfoTrns.toString());

        final MpiTrnsJpaEntity mpiTrnsJpaEntity = new MpiTrnsJpaEntity();
        BeanUtils.copyProperties(recodeMpiBasicInfoTrns, mpiTrnsJpaEntity);
        log.info("[{}][{}] Set Entity => [{}]", mid, wtid, mpiTrnsJpaEntity.toString());

        MpiTrnsJpaEntity resultEntity = this.mpiTrnsRepository.save(mpiTrnsJpaEntity);
        log.info("[{}][{}] MPI_TRNS SAVE 완료: [{}]", mid, wtid, resultEntity.toString());
    }
}
