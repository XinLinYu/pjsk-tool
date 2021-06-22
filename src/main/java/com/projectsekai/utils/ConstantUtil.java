package com.projectsekai.utils;

import java.util.concurrent.ExecutorService;

/**
 * @author XinlindeYu
 * @version 1.0
 * @ClassName ConstantUtil
 * @description
 * @date 2021/6/21 0021 下午 4:14
 **/
public class ConstantUtil {
    /**
     * 线程池,全局共用
     **/
    public static ExecutorService executor = java.util.concurrent.Executors.newFixedThreadPool(5);
}
