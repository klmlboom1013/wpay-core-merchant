package com.wpay.core.merchant.adapter.out.persistence;


import com.wpay.core.merchant.adapter.out.persistence.entity.MpiTrns;
import com.wpay.core.merchant.adapter.out.persistence.entity.pk.MpiTrnsPrimaryKey;
import com.wpay.core.merchant.adapter.out.persistence.repository.MpiTrnsRepository;
import com.wpay.core.merchant.application.port.out.LoadMpiBasicInfoPort;
import com.wpay.core.merchant.application.port.out.SaveMpiBasicInfoPort;
import com.wpay.core.merchant.domain.ActivityMpiBasicInfo;
import com.wpay.core.merchant.domain.MpiBasicInfoMapper;
import com.wpay.core.merchant.global.annotation.PersistenceAdapter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.persistence.EntityNotFoundException;

@Log4j2
@PersistenceAdapter
@RequiredArgsConstructor
public class MpiBasicInfoPersistenceAdapter  implements LoadMpiBasicInfoPort, SaveMpiBasicInfoPort {

    private final MpiTrnsRepository mpiTrnsRepository;

    @Override
    public MpiBasicInfoMapper readActivities(@NonNull ActivityMpiBasicInfo activityMpiBasicInfo) {
        final String wtid = activityMpiBasicInfo.getMpiTrnsId().getWtid();
        final Long srlno = this.mpiTrnsRepository.getMpiTrnsByWtid(wtid);
        log.info("MPI_TRNS SRLNO 조회 완료 [wtid:{}][srlno:{}]", wtid, srlno);

        final MpiTrns mpiTrns = this.mpiTrnsRepository
                .findById(new MpiTrnsPrimaryKey(wtid, srlno))
                .orElseThrow(EntityNotFoundException::new);

        return MpiBasicInfoMapper.builder()
                .wtid(activityMpiBasicInfo.getMpiTrnsId().getWtid())
                .message(mpiTrns.getRspsGrmConts())
                .build();
    }

    @Override
    public void saveActivities(MpiTrns mpiTrns) {
        this.mpiTrnsRepository.save(mpiTrns);
    }


}
