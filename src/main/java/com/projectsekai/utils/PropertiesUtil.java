/*
 * Copyright (C), 2015-9999, 南京紫金数据信息技术有限公司
 * Author: 孙夕银
 * Date: 2017年11月20日
 * Description:
 */
package com.projectsekai.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 读取properties配置文件工具类
 *
 * @ClassName: PropertiesTool
 * @Description: TODO
 * @author: wumeng
 * @date: 2017年8月17日 上午11:01:45
 */

/**
 * 读取配置文件Properties
 *
 * @author xl
 */
public class PropertiesUtil {

    private PropertiesUtil() {
    }

    private static class SingletonHolder {
        private final static PropertiesUtil instance = new PropertiesUtil();
    }

    public static PropertiesUtil getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 读取key字段，配置文件在classes根路径下xx.properties，在子路径下xx/xx.properties
     *
     * @param file
     * @param key
     * @return
     */
    public String read(String key) {
        InputStream in = getClass().getClassLoader().getResourceAsStream("generaldata.properties");
        BufferedReader bf = new BufferedReader(new InputStreamReader(in));
        Properties prop = new Properties();
        String value = "";
        try {
            prop.load(bf);
            value = prop.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

}