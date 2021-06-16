package com.example.wghtest;

import com.example.wghtest.other.FnFileEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;


public class WGHTestBase {
    public static AndroidDriver adDriver;
    protected static DesiredCapabilities _dcCap;
    private static AndroidElement __bgLoginPage;
    public static int tmoutLoading = 15;

    public static int appStartX,appStartY;
    public static int appSizeX,appSizeY;

    public static String strLoginMainPageID = "tw.com.wgh3h:id/rlLoginMain";
    protected static String _strURL ="http://127.0.0.1:4723/wd/hub";

    FnFileEvent fnFileEvent = new FnFileEvent();
    private String __strFileName;
    private String __strFilePath;

    public WGHTestBase() throws IOException {


        fnFileEvent.loadPath();

        this.setCapForAVD8();
        this.adDriver = new AndroidDriver<>(new URL(_strURL), _dcCap);
        adDriver.manage().timeouts().implicitlyWait(tmoutLoading, TimeUnit.SECONDS);

        this.__bgLoginPage = (AndroidElement) adDriver.findElement(By.id(strLoginMainPageID));
        this.appStartX = __bgLoginPage.getLocation().getX();
        this.appStartY = __bgLoginPage.getLocation().getY();
        this.appSizeX = __bgLoginPage.getSize().getWidth();
        this.appSizeY = __bgLoginPage.getSize().getHeight();
    }

    public void setCapForAVD6() throws IOException {
        this._dcCap = new DesiredCapabilities();
        __strFileName = "AVD6";
        __strFilePath = fnFileEvent.getPath(__strFileName);
        ArrayList arlsCapSetting = fnFileEvent.getContent(__strFilePath);
       for(int i=0; i<arlsCapSetting.size(); i+=2){
           _dcCap.setCapability(arlsCapSetting.get(i).toString(),arlsCapSetting.get(i+1).toString());
       }
    }

    public void setCapForAVD8() throws IOException {
        this._dcCap = new DesiredCapabilities();
        __strFileName = "AVD8";
        __strFilePath = fnFileEvent.getPath(__strFileName);
        ArrayList arlsCapSetting = fnFileEvent.getContent(__strFilePath);
        for(int i=0; i<arlsCapSetting.size(); i+=2){
            _dcCap.setCapability(arlsCapSetting.get(i).toString(),arlsCapSetting.get(i+1).toString());
        }
    }

    public void setCapForOPPO_A77() throws IOException {
        this._dcCap = new DesiredCapabilities();
        __strFileName = "OPPO";
        __strFilePath = fnFileEvent.getPath(__strFileName);
        ArrayList arlsCapSetting = fnFileEvent.getContent(__strFilePath);
        for (int i = 0; i < arlsCapSetting.size(); i += 2) {
            _dcCap.setCapability(arlsCapSetting.get(i).toString(), arlsCapSetting.get(i + 1).toString());
        }
    }

    public void setCapForVivo_X20() throws IOException {
        this._dcCap = new DesiredCapabilities();
        __strFileName = "X20";
        __strFilePath = fnFileEvent.getPath(__strFileName);
        ArrayList arlsCapSetting = fnFileEvent.getContent(__strFilePath);
        for (int i = 0; i < arlsCapSetting.size(); i += 2) {
            _dcCap.setCapability(arlsCapSetting.get(i).toString(), arlsCapSetting.get(i + 1).toString());
        }
    }

    public void setCapForNote_5A() throws IOException {
        this._dcCap = new DesiredCapabilities();
        __strFileName = "note5A";
        __strFilePath = fnFileEvent.getPath(__strFileName);
        ArrayList arlsCapSetting = fnFileEvent.getContent(__strFilePath);
        for (int i = 0; i < arlsCapSetting.size(); i += 2) {
            _dcCap.setCapability(arlsCapSetting.get(i).toString(), arlsCapSetting.get(i + 1).toString());
        }
    }
}