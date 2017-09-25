package com.zhangj.mybatis.generator.ext.plugins;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.XmlConstants;
import org.mybatis.generator.codegen.mybatis3.javamapper.JavaMapperGenerator;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.NullProgressCallback;

/**
 * Created by zhangjing on 2017/9/18.
 */
public class BaseMapperPlugin extends PluginAdapter
{

    private static final String DOT = ".";
    private static ConcurrentHashMap<String, Boolean> concurrentRun = new ConcurrentHashMap<>();
    private String baseMapperPackage = null;
    private String baseMapperType = "BaseMapper<T, E>";
    private String originalMapperNameSpace = null;
    private String originalJavaMapperType;

    @Override
    public boolean validate(List<String> warnings)
    {
        if (!properties.containsKey("baseMapperPackage"))
        {
            System.out.println("没有指定基类mapper的位置, 将使用默认位置。");
        }else {
            baseMapperPackage = properties.getProperty("baseMapperPackage");
        }

        return true;
    }

    /**
     * 插件初始化获取原来package值。
     *
     * @param introspectedTable
     */
    public void initialized(IntrospectedTable introspectedTable)
    {
        super.initialized(introspectedTable);

        // 人工手写所使用的mapper文件与生成的mapper文件拥有相同的namespace。
        originalMapperNameSpace = introspectedTable.getMyBatis3SqlMapNamespace();
        originalJavaMapperType = introspectedTable.getMyBatis3JavaMapperType();
    }

    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {
        return null;
    }


    protected String constructBaseMapperName(String theOriginalType) {

        String newType = constructBaseMapperPackage(theOriginalType) + DOT + baseMapperType;
        System.out.println(MessageFormat.format("replace type [{0}][{1}]", theOriginalType, newType));
        return newType;
    }

    protected String constructBaseMapperPackage(String originalPackage)
    {
        String newPackage;
        if (baseMapperPackage == null || "".equals(baseMapperPackage))
        {
            int lastDot = originalPackage.lastIndexOf(DOT);
            newPackage = originalPackage.substring(0, lastDot) + DOT + "base";

        }else{
            newPackage = baseMapperPackage;
        }
        return newPackage;
    }


    /**
     * 生成包含表定义方法的基础类。
     *
     * @param introspectedTable
     * @return
     */
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(
        IntrospectedTable introspectedTable) {

        JavaMapperGenerator abstractGenerator = new JavaMapperGenerator();
        abstractGenerator.setContext(context);
        abstractGenerator.setIntrospectedTable(introspectedTable);
        abstractGenerator.setProgressCallback(new NullProgressCallback());
        abstractGenerator.setWarnings(new ArrayList<>());

        introspectedTable.setMyBatis3JavaMapperType(constructBaseMapperName(introspectedTable.getMyBatis3JavaMapperType()));
        introspectedTable.setExampleType("E");
        introspectedTable.setBaseRecordType("T");

        // 插件的clientGenerated会在getCompilationUnits当中调用, 增加参数区分不同的文件生成。
        concurrentRun.put(introspectedTable.getMyBatis3JavaMapperType(), true);

        List<GeneratedJavaFile> answer = new ArrayList<>();
        for (CompilationUnit compilationUnit : abstractGenerator.getCompilationUnits()) {
            GeneratedJavaFile gjf = new GeneratedJavaFile(compilationUnit,
                context.getJavaClientGeneratorConfiguration()
                    .getTargetProject(),
                context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                context.getJavaFormatter());
            answer.add(gjf);
        }

        concurrentRun.remove(introspectedTable.getMyBatis3JavaMapperType());

        return answer;
    }

    public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles() {
        return null;
    }

    /**
     * 生成人工书写sql的mapper文件。
     *
     * @param introspectedTable
     * @return
     */
    public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(
        IntrospectedTable introspectedTable) {

        Document document = new Document(
            XmlConstants.MYBATIS3_MAPPER_PUBLIC_ID,
            XmlConstants.MYBATIS3_MAPPER_SYSTEM_ID);

        XmlElement answer = new XmlElement("mapper"); //$NON-NLS-1$
        String namespace = originalMapperNameSpace;
        answer.addAttribute(new Attribute("namespace", //$NON-NLS-1$
            namespace));
        document.setRootElement(answer);

        GeneratedXmlFile gxf = new GeneratedXmlFile(document,
            introspectedTable.getMyBatis3XmlMapperFileName(), // 文件名相同
            introspectedTable.getMyBatis3XmlMapperPackage() + ".manual",  // 增加generated属性
            context.getSqlMapGeneratorConfiguration().getTargetProject(),
            true, context.getXmlFormatter());

        List<GeneratedXmlFile> answers = new ArrayList<GeneratedXmlFile>(1);
        answers.add(gxf);

        return answers;
    }

    /**
     * 生成到base_mapper当中
     *
     */
    @Override
    public boolean clientGenerated(Interface interfaze,
        TopLevelClass topLevelClass,
        IntrospectedTable introspectedTable) {


        if (concurrentRun.containsKey(introspectedTable.getMyBatis3JavaMapperType()))
        {
            // 生成BaseMapper
            return true;
        }else
        {
            // 生成表对应的Mapper子类
            FullyQualifiedJavaType fqjt =
                new FullyQualifiedJavaType("BaseMapper<" + introspectedTable.getBaseRecordType() + "," + introspectedTable.getExampleType() + ">");

            FullyQualifiedJavaType imp = new FullyQualifiedJavaType(constructBaseMapperPackage(originalJavaMapperType) + DOT + "BaseMapper");

            interfaze.addSuperInterface(fqjt);

            interfaze.addImportedType(imp);

            interfaze.getMethods().clear();
            interfaze.getAnnotations().clear();

            interfaze.addAnnotation("@Repository");
            interfaze.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Repository"));
        }

        return true;
    }

}
