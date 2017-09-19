package com.zhangj.mybatis.generator.ext.plugins;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

/**
 * Created by zhangjing on 2017/9/12.
 */
public class ExampleRenamePlugin extends PluginAdapter
{
    private String replaceExample(String oldStr)
    {
        if (oldStr != null)
        {
            return oldStr.replace("Example", "Filter");
        }
        if (oldStr.equals("example"))
        {
            return "filter";
        }
        return null;
    }
    
    @Override
    public void initialized(IntrospectedTable introspectedTable)
    {
        String oldType = introspectedTable.getExampleType();

        introspectedTable.setExampleType(replaceExample(oldType));
        introspectedTable.setCountByExampleStatementId(replaceExample(introspectedTable.getCountByExampleStatementId()));
        introspectedTable.setSelectByExampleStatementId(replaceExample(introspectedTable.getSelectByExampleStatementId()));
        introspectedTable.setUpdateByExampleSelectiveStatementId(replaceExample(introspectedTable.getUpdateByExampleSelectiveStatementId()));
        introspectedTable.setUpdateByExampleStatementId(replaceExample(introspectedTable.getUpdateByExampleStatementId()));
        introspectedTable.setDeleteByExampleStatementId(replaceExample(introspectedTable.getDeleteByExampleStatementId()));
        introspectedTable.setSelectByExampleWithBLOBsStatementId(replaceExample(introspectedTable.getSelectByExampleWithBLOBsStatementId()));
        introspectedTable.setUpdateByExampleWithBLOBsStatementId(replaceExample(introspectedTable.getUpdateByExampleWithBLOBsStatementId()));
        introspectedTable.setExampleWhereClauseId(replaceExample(introspectedTable.getExampleWhereClauseId()));
        introspectedTable.setMyBatis3UpdateByExampleWhereClauseId(replaceExample(introspectedTable.getMyBatis3UpdateByExampleWhereClauseId()));
        PluginUtils.BY_EXAMPLE = replaceExample(PluginUtils.BY_EXAMPLE);
        PluginUtils.EXAMPLE = replaceExample(PluginUtils.EXAMPLE);
        PluginUtils.Example = replaceExample(PluginUtils.Example);
    }
    
    @Override
    public boolean validate(List<String> warnings)
    {
        return true;
    }
}
