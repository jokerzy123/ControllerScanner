package com.zy.cs.jar;


import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Zhanying
 * @version 10.0
 * Created by Zhanying on 2021/9/28.
 */
public class JarUtils {
    public static List<JarEntry> selectJarEntries(JarFile jarFile, Predicate<JarEntry> predicate) {
        Enumeration<JarEntry> enumeration = jarFile.entries();
        List<JarEntry> jarEntries = new ArrayList<>();
        JarEntry jarEntry;
        while (enumeration.hasMoreElements()) {
            jarEntry = enumeration.nextElement();
            if (predicate.test(jarEntry)) {
                jarEntries.add(jarEntry);
            }
        }
        return jarEntries;
    }

    /**
     * 根据包名检索符合条件的class
     *
     * @param jarFile
     * @param pkg
     * @return
     */
    public static List<JarEntry> selectClassJarEntriesByPkg(JarFile jarFile, String pkg) {
        if (StringUtils.isEmpty(pkg)) {
            return selectJarEntries(jarFile, jarEntry -> true);
        }
        return selectJarEntries(jarFile, jarEntry -> jarEntry.getName().endsWith(".class") && jarEntry.getName().replaceAll("[\\\\/]", ".").startsWith(pkg));
    }
}
