package com.zy.cs.loader;

import java.io.*;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;

/**
 * 其实这个里边也是遍历jar
 *
 * @author Zhanying
 * @version 10.0
 * Created by Zhanying on 2021/9/28.
 */
public class JarFileClassLoader extends ClassLoader {
    private static final Map<String, JarFile> jarFileMap = new ConcurrentHashMap<>();

    public JarFileClassLoader(ClassLoader parent) {
        super(parent);
    }

    public JarFileClassLoader() {
        super();
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        JarEntry result;
        String transferName = name.replaceAll("\\.", Matcher.quoteReplacement(File.separator)) + ".class";
        //System.out.println(transferName);
        for (JarFile jarFile : jarFileMap.values()) {
            result = selectJarEntry(jarFile, jarEntry -> {
                String entryName = jarEntry.getName();
                return entryName.replaceAll("[\\\\/]", Matcher.quoteReplacement(File.separator)).equals(transferName);
            });
            if (result != null) {
                try (BufferedInputStream bufferedInputStream = new BufferedInputStream(jarFile.getInputStream(result))) {
                    byte[] classBytes = toByteArray(bufferedInputStream);
                    return defineClass(name, classBytes, 0, classBytes.length );
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return super.findClass(name);
    }

    /**
     * 从一个jar包中条件检索
     *
     * @param jarFile
     * @return
     */
    private JarEntry selectJarEntry(JarFile jarFile, Predicate<JarEntry> jarEntryFilter) {
        JarEntry jarEntry;
        // 从jarFile中检索
        Enumeration<JarEntry> entryEnumeration = jarFile.entries();
        while (entryEnumeration.hasMoreElements()) {
            jarEntry = entryEnumeration.nextElement();
            if (jarEntryFilter.test(jarEntry)) {
                return jarEntry;
            }
        }
        return null;
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024*4];
        int n;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    public static void registerJarFile(JarFile jarFile) {
        jarFileMap.putIfAbsent(jarFile.getName(), jarFile);
    }

    public static void registerJarFile(String jarFilePath) throws IOException {
        JarFile jarFile = new JarFile(jarFilePath);
        registerJarFile(jarFile);
    }
}
