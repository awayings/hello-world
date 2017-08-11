package com.zhangj.research.utils;

import com.sun.tools.javac.util.ArrayUtils;

import javax.swing.*;
import java.util.Collections;
import java.util.Random;
import java.util.function.Consumer;

/**
 * Created by zhangjing on 2017/8/11.
 */
public class PerformanceUtil
{
    public static long elapsed(String prefix, Runnable runnable)
    {
        long st = System.nanoTime();
        runnable.run();
        long elapsed = System.nanoTime() - st;
        System.out.println( prefix + "Elapsed:" + elapsed + " ns, " + ((double)elapsed/1000 /1000) + " ms");
        return elapsed;
    }

    public static long elapsed(Runnable runnable)
    {
        return elapsed("", runnable);
    }

    public static void main(String[] args)
    {
        elapsed(()->{
            for (Integer i = 0; i < Integer.valueOf(1000); i++)
            {
                Thread a = new Thread();
            }
        });

    }
}