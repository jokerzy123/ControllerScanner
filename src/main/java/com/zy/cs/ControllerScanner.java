package com.zy.cs;

import com.fr.third.springframework.stereotype.Controller;
import com.fr.third.springframework.web.bind.annotation.RequestMapping;
import com.zy.cs.jar.JarUtils;
import com.zy.cs.loader.JarFileClassLoader;
import com.zy.cs.strategy.RouteStrategy;
import org.apache.commons.lang.StringUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Zhanying
 * @version 10.0
 * Created by Zhanying on 2021/9/29.
 */
public class ControllerScanner {

    public static void cScan(String libPath, String pkg, String routePattern, String type) {
        cScan(libPath, pkg, routePattern, type, RouteStrategy.CONTAIN);//默认是包含策略
    }
    public static void cScan(String libPath, String pkg, String routePattern, String type, RouteStrategy routeStrategy) {
        //loadVT4FR();
        try {
            Set<String> cNames = new HashSet<>();
            Map<String, JarFile> indexMap = new HashMap<>();
            Stream<Path> pathStream = Files.list(Paths.get(libPath));
            JarFile jarFile;
            for (Path path : pathStream.collect(Collectors.toList())) {
                if (StringUtils.endsWith(path.toString(), ".jar")) {// lib文件夹下的jar包扫描，建立索引
                    jarFile = new JarFile(path.toFile());
                    JarFile finalJarFile = jarFile;
                    JarUtils.selectClassJarEntries(jarFile).forEach(jarEntry -> {
                        String cName = jarEntry.getName().replaceAll("[\\\\/]", "\\.");
                        cName = cName.substring(0, cName.lastIndexOf(".class"));
                        indexMap.putIfAbsent(cName, finalJarFile);
                        if (cName.startsWith(pkg)) { // 包路径甄别
                            cNames.add(cName); // 主要是为了找到所有class文件的引用路径
                        }
                    });
                }
            }
            ClassLoader jarFileClassLoader = new JarFileClassLoader(Thread.currentThread().getContextClassLoader(), indexMap); // 如果已经引用了，这里实际上没必要
            Class<? extends Annotation> anClass = Controller.class;
            AtomicInteger atomicInteger = new AtomicInteger(0);
            List<String> result = new ArrayList<>();
            Class<?> clz;
            for (String cName : cNames) {
                try {
                    clz = Class.forName(cName, false, jarFileClassLoader);
                    Controller controller = clz.getAnnotation(Controller.class);
                    if (controller != null) {//clz.isAnnotationPresent(anClass)
                        String url = "";
                        String[] rType = new String[0];
                        RequestMapping requestMapping = clz.getAnnotation(RequestMapping.class);
                        if (requestMapping != null) {
                            String[] value = requestMapping.value();
                            if (value.length > 0) {
                                url = value[0];
                            }
                            rType = Arrays.stream(requestMapping.method()).map(Enum::toString).distinct().toArray(String[]::new);
                        }
                        // 遍历方法
                        for (Method method : clz.getDeclaredMethods()) {
                            String mUrl = url;
                            String[] mType;
                            RequestMapping requestMapping1 = method.getAnnotation(RequestMapping.class);
                            if (requestMapping1 != null) {
                                String[] value = requestMapping1.value();
                                if (value.length > 0) {
                                    mUrl += value[0];
                                }
                                mType = Arrays.stream(requestMapping1.method()).map(Enum::toString).distinct().toArray(String[]::new);
                                mType = concat(rType, mType);
                                // 这里就可以判断方法了  TODO 这里route应该支持策略，contain endWith 正则
                                if (routeStrategy.getRoutePredicate().test(mUrl, routePattern) && Arrays.stream(mType).anyMatch(s -> StringUtils.equalsIgnoreCase(s, type))) {
                                    String s = String.format("%s#%s\t%s\t%s", clz.getName(), method.getName(), mUrl, String.join(",", mType));
                                    result.add(s);
                                    atomicInteger.incrementAndGet();
                                }
                            }
                        }
                    }
                } catch (Throwable e) {
                    System.out.println(cName + " read error!");
                }
            }
            System.out.println("一共找到" + atomicInteger.get() + "个");
            System.out.println(String.join("\n", result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    @Deprecated
    private static void loadVT4FR() {
        try {
            Class<?> clz = Class.forName("com.fr.license.function.VT4FR", false, Thread.currentThread().getContextClassLoader());
            System.out.println(clz.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
