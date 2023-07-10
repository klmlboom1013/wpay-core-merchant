package com.wpay.core.merchant.adapter.out.persistence.entity.pk;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class MpiTrnsPrimaryKey implements Serializable {
    private String wtid;
    private String srlno;
}
