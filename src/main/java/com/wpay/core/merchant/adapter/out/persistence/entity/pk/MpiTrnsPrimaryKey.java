package com.wpay.core.merchant.adapter.out.persistence.entity.pk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class MpiTrnsPrimaryKey implements Serializable {
    private String wtid;
    private Long srlno;
}
