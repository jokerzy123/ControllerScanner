package com.zy.cs;

import com.zy.cs.jar.JarUtils;
import org.apache.commons.lang.StringUtils;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;

/**
 * 入口类
 * 根据搜索条件，扫描指定的的文件目录下的controller类
 *
 * @author Zhanying
 * @version 10.0
 * Created by Zhanying on 2021/9/28.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("请输入待扫描lib文件夹路径");
            String libPath = scanner.nextLine();
            System.out.println("请输入检索包路径");
            String pkg = scanner.nextLine();
            System.out.println("请输入相对路由");
            String route = scanner.nextLine();
            System.out.println("请输入请求类型");
            String type = scanner.nextLine();
            System.out.println(libPath);
            System.out.println(pkg);
            System.out.println(route);
            System.out.println(type);
            //这里进行核心的工作
            //Controller controller
            //遍历文件
            try {
                Map<JarFile, List<JarEntry>> map = new HashMap<>();

                List<JarEntry> jarEntryList = new ArrayList<>();
                Stream<Path> pathStream = Files.list(Paths.get(libPath));
                for (Path path : pathStream.collect(Collectors.toList())) {
//                    URL url = path.toUri().toURL();
//                    if (StringUtils.equals("jar", url.getProtocol())) {
//
//                    }
                    if (StringUtils.endsWith(path.toString(), ".jar")) {
                        jarEntryList.addAll(JarUtils.selectClassJarEntriesByPkg(new JarFile(path.toFile()), pkg));
                    }
                }
                System.out.println(jarEntryList.stream().map(ZipEntry::getName).collect(Collectors.joining("\n")));
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("是否继续？y/n");
            String goon = scanner.nextLine();
            if (!StringUtils.equalsIgnoreCase(goon, "y")) {
                break;
            }
        }
    }
}
