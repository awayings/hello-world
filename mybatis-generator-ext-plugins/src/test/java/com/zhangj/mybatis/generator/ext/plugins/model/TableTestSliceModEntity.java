package com.zhangj.mybatis.generator.ext.plugins.model;

import java.util.Date;
import lombok.Data;

@Data
public class TableTestSliceModEntity {
    private Long id;

    private Long sliceModId;

    private Long jacksonId1;

    private String jacksonId2;

    private Date jacksonTime;

    private Integer couldSumCol;

    private Long version;
}