package com.example.wghtest.Level2.Logout;

import com.example.wghtest.Level2.WDFnL2TitleMenuTabbar;

import org.openqa.selenium.By;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.adDriver;
import static com.example.wghtest.WGHTestBase.tmoutLoading;

public class WDFnL2Logout {
    private int __intHeight,__intWidth;

    public static String __strLogoutID = "tw.com.wgh3h:id/tvLogout";
    public static String __strLogoutXpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[2]/android.widget.RelativeLayout/android.support.v4.view.ViewPager/android.widget.ScrollView/android.widget.LinearLayout/android.widget.LinearLayout[3]/android.widget.LinearLayout/android.widget.ImageView";
    private static String __strButton1ID = "android:id/button1";
    private static String __strButton2ID = "android:id/button2";

    TouchAction touchAction = new TouchAction(adDriver);

    public WDFnL2Logout(){
        this.__intWidth =  adDriver.manage().window().getSize().width;
        this.__intHeight =  adDriver.manage().window().getSize().height;
    }

    public void logout() throws InterruptedException {
        new WDFnL2TitleMenuTabbar().clickTitlePersonal();
        Thread.sleep(5000);
        touchAction.press(PointOption.point(__intWidth/2,__intHeight*4/5)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).moveTo(PointOption.point(__intWidth/2,__intHeight/5)).release().perform();
        Thread.sleep(5000);

        adDriver.findElement(By.id(__strLogoutID)).click();

        adDriver.findElement(By.id(__strButton1ID)).click();
    }

    public void logoutByCancel() throws InterruptedException {

        new WDFnL2TitleMenuTabbar().clickTitlePersonal();
        Thread.sleep(8000);

        touchAction.press(PointOption.point(__intWidth/2,__intHeight*4/5)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).moveTo(PointOption.point(__intWidth/2,__intHeight/5)).release().perform();
        adDriver.findElement(By.xpath(__strLogoutXpath)).click();

        adDriver.findElement(By.id(__strButton2ID)).click();
    }
}
