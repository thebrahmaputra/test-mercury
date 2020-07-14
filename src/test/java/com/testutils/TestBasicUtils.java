package com.testutils;

import org.openqa.selenium.WebDriver;
import org.testng.*;

public class TestBasicUtils {
    public static void step(int num, String desc){
        Reporter.log("Step" +num+ " : " +desc);
    }
}
