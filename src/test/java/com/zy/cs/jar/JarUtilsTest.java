package com.zy.cs.jar;

import org.junit.Test;

import java.io.IOException;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;

import static org.junit.Assert.*;

/**
 * @author Zhanying
 * @version 10.0
 * Created by Zhanying on 2021/9/28.
 */
public class JarUtilsTest {

    @Test
    public void selectClassJarEntriesByPkg() {
        try {
            System.out.println(JarUtils.selectClassJarEntriesByPkg(new JarFile("D:\\二开学习\\本地开发BIjar\\lib - persist 20201214\\fine-bi-adapter-5.1.jar"), "com.finebi.web.action.v5").stream().map(ZipEntry::getName).collect(Collectors.joining("\n")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}