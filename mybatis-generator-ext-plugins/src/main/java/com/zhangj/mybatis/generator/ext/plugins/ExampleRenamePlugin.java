package com.zhangj.mybatis.generator.ext.plugins;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

/**
 * Created by zhangjing on 2017/9/12.
 */
public class ExampleRenamePlugin extends PluginAdapter
{
    public static final String CLASS_SEARCH_PROPERTY = "classMethodSearchString";
    public static final String CLASS_REPLACE_PROPERTY = "classMethodReplaceString";
    public static final String PARAM_SEARCH_PROPERTY = "parameterSearchString";
    public static final String PARAM_REPLACE_PROPERTY = "parameterReplaceString";

    public String CLASS_NAME = "Example";
    public String PARAM_NAME = "example";
    public String CLASS_REPLACE_NAME = "Filter";
    public String PARAM_REPLACE_NAME = "filter";

    private String replaceExample(String oldStr)
    {
        if (oldStr != null)
        {
            return oldStr.replace(CLASS_NAME, CLASS_REPLACE_NAME);
        }
        if (oldStr.equals(PARAM_NAME))
        {
            return PARAM_REPLACE_NAME;
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
        CLASS_NAME = properties.getProperty(CLASS_SEARCH_PROPERTY, CLASS_NAME);
        PARAM_NAME = properties.getProperty(PARAM_SEARCH_PROPERTY, PARAM_NAME);
        CLASS_REPLACE_NAME = properties.getProperty(CLASS_REPLACE_PROPERTY, CLASS_REPLACE_NAME);
        PARAM_REPLACE_NAME = properties.getProperty(PARAM_REPLACE_PROPERTY, PARAM_REPLACE_NAME);

        return true;
    }
}
