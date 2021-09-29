package com.zy.cs.loader;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 自定义ClassLoader
 * 从JarFile中加载class
 *
 * @author Zhanying
 * @version 10.0
 * Created by Zhanying on 2021/9/28.
 */
public class JarFileClassLoader extends ClassLoader {
    private final Map<String, JarFile> indexMap;//这个应该是一个全局索引

    public JarFileClassLoader(ClassLoader parent, Map<String, JarFile> indexMap) {
        super(parent);
        this.indexMap = indexMap;
    }

    public JarFileClassLoader(Map<String, JarFile> indexMap) {
        super();
        this.indexMap = indexMap;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        JarEntry result;
        String transEntryName = name.replaceAll("\\.", "/") + ".class";
        if (indexMap.containsKey(name)) {
            JarFile jarFile = indexMap.get(name);
            result = jarFile.getJarEntry(transEntryName);
            if (result != null) {
                try (BufferedInputStream bufferedInputStream = new BufferedInputStream(jarFile.getInputStream(result))) {
                    byte[] classBytes = toByteArray(bufferedInputStream);
                    return defineClass(name, classBytes, 0, classBytes.length );
                } catch (IOException e) {
                    System.out.println(name + " find error!");
                    e.printStackTrace();
                }
            }
        }
        return super.findClass(name);
    }

    private static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024*4];
        int n;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }
}
