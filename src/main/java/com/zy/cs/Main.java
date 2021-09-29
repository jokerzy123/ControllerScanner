package com.zy.cs;

import org.apache.commons.lang.StringUtils;
import java.util.Scanner;

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
            ControllerScanner.cScan(libPath, pkg, route, type);
            System.out.println("是否继续？y/n");
            String goon = scanner.nextLine();
            if (!StringUtils.equalsIgnoreCase(goon, "y")) {
                break;
            }
        }
    }


}
