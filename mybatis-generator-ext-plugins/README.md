# 一组实用的MBP插件
旨在结局数据库少些sql的问题。
* example生成(重命名)
* comments优化
* mapper文件精简
* entity支持lombok
* batchInsert方法生成。
* 重复生成

设计:

* java client
    * baseClass Generate
    * comments
    * batchInsert
    * example limit, offset
    * example rename
* mapper
    * comments
    * separate manual/generated
    * batchInsert
    * example 增加分页 startIndex, pageSize
    * example rename
* entity
    * lombok
    * rename


用法

### 【实体-XML-CLIENT】DatabaseCommentGenerator
配置文件中增加如下配置。 注意: xml文件当中去除了`@MBP generated` 注释之后, MBP讲无法做合并的去重判断。故默认为xml文件当中的
注释不去除。


```
<commentGenerator type="com.zhangj.mybatis.plugins.DatabaseCommentGenerator"/>
```

### 【实体】LombokAnnotationPlugin
去除`getter/setter`方法, 使用lombok annotation。

```
<plugin type="com.zhangj.mybatis.generator.ext.plugins.LombokAnnotationPlugin"/>
```

### 【实体】ModelNamePlugin
自定义实体名称。增加以`Entity`结尾

```
<plugin type="com.zhangj.mybatis.generator.ext.plugins.ModelNamePlugin"/>
```

### 【实体-XML-CLIENT】BatchInsertPlugin
为所有方法生成生成批量插入的方法。

```
<plugin type="com.zhangj.mybatis.generator.ext.plugins.BatchInsertPlugin"/>
```

### 【XML-CLIENT】PaginationExamplePlugin
给`selectByExample`方法增加limit参数

```
<plugin type="com.zhangj.mybatis.generator.ext.plugins.PaginationExamplePlugin"/>
```

生成结果示例

```
        TableTestSliceMonthExample example = new TableTestSliceMonthExample();
        example.page(1,   // pageStartIndex
                     2    // pageSize
         ).createCriteria().andIdGreaterThan(0l);
        System.out.println(mapper.selectByExample(example));
```

### 【XML-CLIENT】ExampleRenamePlugin
Example类, `selectByExample`, `updateByExample`等方法更名。 `Example` -> `Filter`

```
<plugin type="com.zhangj.mybatis.generator.ext.plugins.ExampleRenamePlugin"/>
```

生成结果示例

```
        TableTestSliceMonthFilter filter = new TableTestSliceMonthFilter();
        filter.page(1,2).createCriteria().andIdGreaterThan(0l);
        System.out.println(mapper.selectByFilter(filter));
```

### 【XML-CLIENT】MinMaxPlugin
根据`table`的配置增加`min{fieldId}ByExample`, `max{fieldId}ByExample`等方法。如需得到改名
插件作用。需讲此插件声明在改名插件之后。不支持`BaseMapperPlugin`插件。

配置

```
<table tableName="TABLE_TEST_SLICE_MONTH" domainObjectName="TableTestSliceMonth">
            <property name="maxColumns" value="JACKSON_ID1,JACKSON_ID2"/>
            <property name="minColumns" value="JACKSON_ID1,JACKSON_ID2"/>
</table>
```

生成结果示例

```
        TableTestSliceMonthFilter filter = new TableTestSliceMonthFilter();
        filter.createCriteria().andIdGreaterThan(0l);
        System.out.println(mapper.minJacksonId1ByFilter(filter));
```

### 【XML-CLIENT】OptimisticLockAutoIncreasePlugin
生成为乐观锁字段增加赋值操作。`insert`时赋值为0, `updateByxxx`时自动加1. 目前不支持BaseMapperPlugin插件

```
        <table tableName="TABLE_TEST_SLICE_MONTH" domainObjectName="TableTestSliceMonth">
            <property name="optimisticLockColumn" value="VERSION"/>
            <generatedKey column="ID" sqlStatement="MySql" identity="true" />
        </table>
```

### 【XML-CLIENT】BaseMapperPlugin
生成mapper的基类`BaseMapper<T, E, PK extends Serializable>`和sql mapper的xml文件`manual/TableTestSliceModMapper.xml`
人工手写的sql可以放入子类mapper和`manual`目录下的sql mapper xml文件当中。

配置
```
<plugin type="com.zhangj.mybatis.generator.ext.plugins.BaseMapperPlugin">
    <!-- 指定BaseMapper的包名, 不指定时默认为生成java mapper同级的base包内。 -->
    <property name="baseMapperPackage" value="com.zhangj.mybatis.generator.ext.plugins.base"/>
</plugin>
```

生成结果示例

```
目录结构
java
  - mapper
    - base
        - BaseMapper
    TableTestSliceMonthMapper
  - resources
    - mapping
        - manual
            - TableTestSliceMonthMapper.xml
        - TableTestSliceMonthMapper.xml
```

