package com.zhangj.mybatis.demo;

import org.mybatis.generator.api.ShellRunner;

/**
 * Created by zhangjing on 2017/8/27.
 */
public class Main
{
    public static void main(String[] args)
    {
        String config = Main.class.getClassLoader()
            .getResource("generatorConfig.xml").getFile();
        String[] arg = { "-configfile", config, "-overwrite" };
        System.setProperty("user.dir", "/Users/jason/projects/github/hello-world/generator-sample/mybatis-demo");
        ShellRunner.main(arg);
    }
}
