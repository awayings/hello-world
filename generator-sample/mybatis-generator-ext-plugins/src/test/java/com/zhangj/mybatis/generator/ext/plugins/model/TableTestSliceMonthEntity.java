package com.zhangj.mybatis.generator.ext.plugins.model;

import java.util.Date;
import lombok.Data;

@Data
public class TableTestSliceMonthEntity {
    private Long id;

    private Date sliceMonthId;

    private Long jacksonId1;

    private Long version;

    private String jacksonId2;

    private Date jacksonTime;

    private Integer couldSumCol;
}