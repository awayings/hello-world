package com.zhangj.mybatis.generator.ext.plugins;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

import java.util.List;

public class ModelNamePlugin extends PluginAdapter
{
    public ModelNamePlugin() {
    }

    public boolean validate(List<String> arg0) {
        return true;
    }

    public void initialized(IntrospectedTable introspectedTable) {
        introspectedTable.setBaseRecordType(introspectedTable.getBaseRecordType() + "Entity");
        super.initialized(introspectedTable);
    }
}