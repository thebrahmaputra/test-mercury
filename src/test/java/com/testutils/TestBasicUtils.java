package com.testutils;

import org.testng.*;

public class TestBasicUtils {

    static int stepNum=0;
    public static void step(int num, String desc){
        if (num>1){
            Assert.assertEquals(num, ++stepNum);
        }else if (num==1){
            stepNum=1;
        }else if (num<=0){
            Assert.assertEquals(num, stepNum);
        }

        Reporter.log("Step" +num+ " : " +desc);
    }

    public static void logDetails(String details){Reporter.log(details);}
}
