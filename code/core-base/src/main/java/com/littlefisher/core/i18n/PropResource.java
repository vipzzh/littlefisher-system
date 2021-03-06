package com.littlefisher.core.i18n;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.littlefisher.core.i18n.utils.I18nConstants;
import com.littlefisher.core.utils.LittleFisherLogger;
import com.littlefisher.core.utils.StringUtil;

/**
 * Description: 资源加载
 *
 * Created on 2017年1月3日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public final class PropResource {

    /** logger */
    private static LittleFisherLogger logger = LittleFisherLogger.getLogger(PropResource.class);

    private static final String WINDOWS = "windows";

    private static final String UNIX = "unix";

    /**
     * 缓存资源文件
     * 第一层Map是根据语言进行分类，第二层Map根据Code查找Value
     */
    private static Map<String, Map<String, String>> resourceMap = Maps.newConcurrentMap();

    static {
        init();
    }

    /**
     * 单例构造函数
     */
    private PropResource() {
    }

    /**
     * Description: 第一次加载时初始化资源文件
     */
    private static void init() {
        getResourceValueList(findFileNameSuffix());
    }

    /**
     * Description: 遍历SYSTEM_HOME/resource目录下所有文件名，筛选出一共有几种语言
     */
    private static List<File> findFileNameSuffix() {
        String littleFisherHome = initHomePath();
        String resourceDir = littleFisherHome + File.separator + I18nConstants.I18N_RESOURCE_PATH;
        List<File> allFileList = Lists.newArrayList();
        getAllFileName(resourceDir, allFileList);
        for (File file : allFileList) {
            String name = file.getName()
                    .substring(0, file.getName().length() - I18nConstants.PROPERTIES_LENGTH);
            int index = name.lastIndexOf(".");
            String local = name.substring(index + 1, name.length());
            resourceMap.put(local, Maps.newConcurrentMap());
        }
        return allFileList;
    }

    /**
     * Description: 获取所有文件名
     *
     * @param path 根目录路径
     * @param list 所有文件名
     */
    private static void getAllFileName(String path, List<File> list) {
        File dir = new File(path);
        File[] files = dir.listFiles();
        if (ArrayUtils.isEmpty(files)) {
            return;
        }
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    getAllFileName(file.getPath(), list);
                } else {
                    if (file.getName().endsWith(".properties")) {
                        list.add(file);
                    }
                }
            }
        }
    }

    /**
     * Description: 查询SYSTEM_HOME目录地址
     *
     * @return String
     */
    private static String initHomePath() {
        Properties p = System.getProperties();
        String littlefisherHome = p.getProperty(I18nConstants.SYSTEM_HOME);
        if (StringUtil.isNotEmpty(littlefisherHome)) {
            return littlefisherHome;
        } else {
            String os = System.getProperties().getProperty("os.name").toLowerCase();
            try {
                InputStream inStream;
                if (os.contains(WINDOWS)) {
                    inStream = Runtime.getRuntime().exec("cmd   /C   set").getInputStream();
                } else if (os.contains(UNIX)) {
                    inStream = Runtime.getRuntime().exec("env").getInputStream();
                } else {
                    inStream = Runtime.getRuntime().exec("env").getInputStream();
                }
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(inStream, Charsets.ISO_8859_1));
                String line;
                while ((line = br.readLine()) != null) {

                    if (line.contains(I18nConstants.SYSTEM_HOME)) {
                        int idx = line.indexOf('=');
                        return line.substring(idx + 1).trim();
                    }

                }
            } catch (IOException e) {
                logger.error("env loader error", e);
            }
        }

        return null;
    }

    /**
     * Description: 遍历SYSTEM_HOME/resource目录下所有文件内容，i18n信息加入缓存
     *
     * @param allFileList resource目录下所有的资源文件
     */
    private static void getResourceValueList(List<File> allFileList) {
        Set<String> keys = resourceMap.keySet();
        for (String key : keys) {
            Map<String, String> valueMap = Maps.newConcurrentMap();
            for (File file : allFileList) {
                if (file.getName().contains("." + key + ".")) {
                    readFile(file, valueMap);
                }
            }
            resourceMap.put(key, valueMap);
        }
    }

    /**
     * Description: 读取File数据
     */
    private static void readFile(File file, Map<String, String> valueMap) {
        try {
            List<String> lineList = Files.readLines(file, Charsets.UTF_8);
            for (String line : lineList) {
                int idx = line.indexOf('=');
                String key = line.substring(0, idx).trim();
                String value = line.substring(idx + 1).trim();
                valueMap.put(key, value);
            }
        } catch (IOException e) {
            logger.error("IOException", e);
        }
    }

    /**
     * Description: 根据方言和编码查询对应值
     *
     * 如果所用方言不是en，但是却根据key查不到value时，会默认使用en再查询一下
     *
     * @param dialect 方言
     * @param key 编码
     * @return String 值
     */
    public static String getValue(String dialect, String key) {
        String value = resourceMap.get(dialect).get(key);
        if (!I18nConstants.DIALECT_EN.equals(dialect) && StringUtil.isBlank(value)) {
            return resourceMap.get(I18nConstants.DIALECT_EN).get(key);
        } else {
            return value;
        }
    }
}
