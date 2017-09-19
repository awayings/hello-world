package com.zhangj.mybatis.generator.ext.plugins.base;

import com.zhangj.mybatis.generator.ext.plugins.model.TableTestSliceMonthEntity;
import com.zhangj.mybatis.generator.ext.plugins.model.TableTestSliceMonthFilter;
import java.io.Serializable;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BaseMapper<T, E, PK extends Serializable> {
    long countByFilter(TableTestSliceMonthFilter example);

    int deleteByFilter(TableTestSliceMonthFilter example);

    int insert(TableTestSliceMonthEntity record);

    int insertSelective(TableTestSliceMonthEntity record);

    List<TableTestSliceMonthEntity> selectByFilter(TableTestSliceMonthFilter example);

    int updateByFilterSelective(@Param("record") TableTestSliceMonthEntity record, @Param("example") TableTestSliceMonthFilter example);

    int updateByFilter(@Param("record") TableTestSliceMonthEntity record, @Param("example") TableTestSliceMonthFilter example);

    Long sumByFilter(TableTestSliceMonthFilter example);

    void batchInsert(@Param("items") List<TableTestSliceMonthEntity> items);

    Long minJacksonId1ByFilter(TableTestSliceMonthFilter example);

    String minJacksonId2ByFilter(TableTestSliceMonthFilter example);

    Long maxJacksonId1ByFilter(TableTestSliceMonthFilter example);

    String maxJacksonId2ByFilter(TableTestSliceMonthFilter example);
}