package com.zhangj.mybatis.generator.ext.plugins;

import java.text.MessageFormat;
import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;

/**
 * Created by chengyang
 */
public class SumSelectivePlugin extends PluginAdapter
{
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType paramType = new FullyQualifiedJavaType(introspectedTable.getExampleType());
        Method method = new Method();
        method.setName("sum" + PluginUtils.BY_EXAMPLE);
        method.setVisibility(JavaVisibility.PUBLIC);
        method.addParameter(new Parameter(paramType, PluginUtils.Example));
        method.setReturnType(LONG_TYPE);
        interfaze.addMethod(method);
        System.out.println("-----------------" + interfaze.getType().getShortName() + " add method sumByExample.");
        return true;
    }

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
        PluginUtils.addProperty(SUM_COL_FIELD, STRING_TYPE, topLevelClass, this.getContext(), tableName);

        List<IntrospectedColumn> columns = introspectedTable.getAllColumns();
        for (IntrospectedColumn column : columns) {
            String field = column.getJavaProperty();
            String mn = "sum" + field.substring(0, 1).toUpperCase() + field.substring(1, field.length());
            Method method = new Method();
            method.setName(mn);
            method.setVisibility(JavaVisibility.PUBLIC);
            method.setReturnType(topLevelClass.getType());
            method.addBodyLine("this." + SUM_COL_FIELD + "=\"" + column.getActualColumnName() + "\";");
            method.addBodyLine("return this;");
            //PluginUtils.addDoc(this.getContext(), method, tableName);
            topLevelClass.addMethod(method);
        }

        return true;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        String paramType = introspectedTable.getExampleType();
        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
        if(!tableName.contains("_${tableNameSuffix}") && SliceTablePlugin.needPartition(introspectedTable)) {
            tableName = tableName + "_${tableNameSuffix}";
        }
        String xml = MessageFormat.format(template, paramType, tableName, PluginUtils.BY_EXAMPLE, introspectedTable.getExampleWhereClauseId());

        document.getRootElement().getElements().add(new TextElement(xml));

        return true;
    }

    private final static String SUM_COL_FIELD = "sumCol";
    private final static FullyQualifiedJavaType LONG_TYPE = new FullyQualifiedJavaType("java.lang.Long");
    private final static FullyQualifiedJavaType STRING_TYPE = FullyQualifiedJavaType.getStringInstance();

    private static final String template = "" +
            "<select id=\"sum{2}\" parameterType=\"{0}\" resultType=\"long\" >\n" +
            "    select sum('${'" + SUM_COL_FIELD + "'}') from {1}\n" +
            "    <if test=\"_parameter != null\" >\n" +
            "      <include refid=\"{3}\" />\n" +
            "    </if>\n" +
            "  </select>";

}
