package com.wpay.core.merchant.adapter.out.persistence;

import com.wpay.core.merchant.adapter.out.dto.MpiBasicInfoMapper;
import com.wpay.core.merchant.adapter.out.persistence.entity.MpiTrns;
import com.wpay.core.merchant.adapter.out.persistence.repository.MpiTrnsRepository;
import com.wpay.core.merchant.application.port.out.LoadMpiBasicInfoPort;
import com.wpay.core.merchant.application.port.out.SaveMpiBasicInfoPort;
import com.wpay.core.merchant.domain.ActivityMpiBasicInfo;
import com.wpay.core.merchant.global.annotation.PersistenceAdapter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@PersistenceAdapter
@RequiredArgsConstructor
public class MpiBasicInfoPersistenceAdapter  implements LoadMpiBasicInfoPort, SaveMpiBasicInfoPort {

    private final MpiTrnsRepository mpiTrnsRepository;

    @Override
    public MpiBasicInfoMapper readActivities(@NonNull ActivityMpiBasicInfo activityMpiBasicInfo) {

        return null;
    }

    @Override
    public void saveActivities(MpiTrns mpiTrns) {
        this.mpiTrnsRepository.save(mpiTrns);
    }
}
