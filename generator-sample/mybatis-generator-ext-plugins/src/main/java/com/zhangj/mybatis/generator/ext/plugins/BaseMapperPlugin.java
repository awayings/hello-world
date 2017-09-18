package com.zhangj.mybatis.generator.ext.plugins;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.XmlConstants;
import org.mybatis.generator.codegen.mybatis3.javamapper.JavaMapperGenerator;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.internal.NullProgressCallback;

/**
 * Created by zhangjing on 2017/9/18.
 */
public class BaseMapperPlugin extends PluginAdapter
{
    
    @Override
    public boolean validate(List<String> warnings)
    {
        return true;
    }


    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {
        return null;
    }

    private static final String DOT = ".";
    private static ConcurrentHashMap<String, Boolean> concurrentRun = new ConcurrentHashMap<>();

    public String addSubPackageToFullName(String theOriginalType, String subPackage) {
        String originalType;
        String newType;

        if (theOriginalType != null) {
            originalType = theOriginalType;
            int lastDot = originalType.lastIndexOf(DOT);
            newType = originalType.substring(0, lastDot)  + DOT + subPackage + DOT + "BaseMapper<T, E, PK extends Serializable>";
            System.out.println(MessageFormat.format("replace type [{0}][{1}]", originalType, newType));
            return newType;
        }

        return theOriginalType;
    }

    public String getPackage(String originalType, String subPackage)
    {
        if (originalType != null)
        {
            int lastDot = originalType.lastIndexOf(DOT);
            if (lastDot <= 0) return "";

            return originalType.substring(0, lastDot) + DOT + subPackage;
        }
        return originalType;
    }


    /**
     * 生成有内容的基础类。
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

        String originalType = introspectedTable.getMyBatis3JavaMapperType();
        introspectedTable.setMyBatis3JavaMapperType(addSubPackageToFullName(introspectedTable.getMyBatis3JavaMapperType(), "base"));

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

        // 恢复类型, 以便生成generated的sql mapper的xml文件。
        introspectedTable.setMyBatis3JavaMapperType(originalType);

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
        String namespace = introspectedTable.getMyBatis3SqlMapNamespace();
        answer.addAttribute(new Attribute("namespace", //$NON-NLS-1$
            namespace));
        document.setRootElement(answer);

        GeneratedXmlFile gxf = new GeneratedXmlFile(document,
            introspectedTable.getMyBatis3XmlMapperFileName(), // 文件名相同
            introspectedTable.getMyBatis3XmlMapperPackage() + ".manual",  // 增加generated属性
            context.getSqlMapGeneratorConfiguration().getTargetProject(),
            false, context.getXmlFormatter());

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

            FullyQualifiedJavaType imp = new FullyQualifiedJavaType("java.io.Serializable");
            interfaze.addImportedType(imp);
            return true;
        }else
        {
            // 生成表对应的Mapper子类

            /**
             * 主键默认采用java.lang.Integer
             */
            FullyQualifiedJavaType fqjt =
                new FullyQualifiedJavaType("BaseMapper<" + introspectedTable.getBaseRecordType() + "," + introspectedTable.getExampleType() + "," + "java.lang.Integer" + ">");

            FullyQualifiedJavaType imp = new FullyQualifiedJavaType(getPackage(introspectedTable.getMyBatis3JavaMapperType(), "base") + DOT + "BaseMapper");

            /**
             * 添加 extends MybatisBaseMapper
             */
            interfaze.addSuperInterface(fqjt);

            /**
             * 添加import my.mabatis.example.base.MybatisBaseMapper;
             */
            interfaze.addImportedType(imp);
            /**
             * 方法不需要
             */
            interfaze.getMethods().clear();
            interfaze.getAnnotations().clear();
        }

        return true;
    }

}
