package com.zhangj.research.utils;

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
        System.out.println(prefix + "Elapsed:" + elapsed + " ns, " + ((double)elapsed / 1000 / 1000) + " ms");
        return elapsed;
    }
    
    public static long elapsed(Runnable runnable)
    {
        return elapsed("", runnable);
    }
    
    public static void main(String[] args)
    {
        elapsed(() -> {
            for (Integer i = 0; i < Integer.valueOf(1); i++)
            {
                Thread a = new Thread();
            }
        });
        
    }
}
