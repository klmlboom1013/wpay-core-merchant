package com.wpay.core.merchant.adapter.out.persistence;

import com.wpay.core.merchant.application.port.out.persistence.persistenceMpiTrnsPort;
import com.wpay.core.merchant.domain.ActivityMpiBasicInfo;
import com.wpay.core.merchant.domain.MpiBasicInfo;
import com.wpay.core.merchant.global.annotation.PersistenceAdapter;
import com.wpay.core.merchant.global.enums.JobCode;
import com.wpay.core.merchant.global.enums.VersionCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.persistence.EntityNotFoundException;

@Log4j2
@PersistenceAdapter
@RequiredArgsConstructor
public class MpiBasicInfoPersistenceAdapter  implements persistenceMpiTrnsPort {

    private final MpiTrnsRepository mpiTrnsRepository;

    @Override public JobCode getJobCode() { return JobCode.SendMpiBasicInfo; }

    @Override public VersionCode getVersionCode() { return VersionCode.v1; }

    @Override
    public MpiBasicInfo searchMpiTrnMpiBasicInfo(@NonNull ActivityMpiBasicInfo activityMpiBasicInfo) {
        final String wtid = activityMpiBasicInfo.getMpiTrnsId().getWtid();
        final Long srlno = this.mpiTrnsRepository.getMpiTrnsByWtid(wtid);
        log.info("MPI_TRNS SRLNO 조회 완료 [wtid:{}][srlno:{}]", wtid, srlno);

        final MpiTrnsJpaEntity mpiTrnsJpaEntity = this.mpiTrnsRepository
                .findById(new MpiTrnsJpaEntity.MpiTrnsId(wtid, srlno))
                .orElseThrow(() -> new EntityNotFoundException("MpiTrns 조회 결과가 없습니다."));

        return MpiBasicInfo.builder()
                .wtid(activityMpiBasicInfo.getMpiTrnsId().getWtid())
                .message(mpiTrnsJpaEntity.getRspsGrmConts())
                .build();
    }

    @Override
    public void saveMpiTrnMpiBasicInfo(MpiTrnsJpaEntity mpiTrnsJpaEntity) {
        this.mpiTrnsRepository.save(mpiTrnsJpaEntity);
    }


}
