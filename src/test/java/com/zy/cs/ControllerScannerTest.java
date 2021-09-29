package com.zy.cs;

import com.zy.cs.strategy.RouteStrategy;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Zhanying
 * @version 10.0
 * Created by Zhanying on 2021/9/29.
 */
public class ControllerScannerTest {

    @Test
    public void cScan() {
        ControllerScanner.cScan("D:\\二开学习\\本地开发BIjar\\lib", "com.fr.web", "/file", "GET", RouteStrategy.parse(2));
    }
}