package com.zhangj.mybatis.generator.ext.plugins.base;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BaseMapper<T, E> {
    long countByFilter(E example);

    int deleteByFilter(E example);

    int insert(T record);

    int insertSelective(T record);

    List<T> selectByFilter(E example);

    int updateByFilterSelective(@Param("record") T record, @Param("example") E example);

    int updateByFilter(@Param("record") T record, @Param("example") E example);

    void batchInsert(@Param("items") List<T> items);
}