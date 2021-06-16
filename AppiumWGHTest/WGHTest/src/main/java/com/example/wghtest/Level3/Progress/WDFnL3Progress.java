package com.example.wghtest.Level3.Progress;

import java.util.ArrayList;
import java.util.Set;

import io.appium.java_client.android.AndroidElement;

import static com.example.wghtest.WGHTestBase.adDriver;

public class WDFnL3Progress {

    protected AndroidElement _btnAwards,__btnInfo;

    private String[] __strAwardsID = {"awards","1","2","3"};
    private String __strInfoID = "info";
    private String __strAwardsDescriptionClass = "android.view.View";


    public void contextNative(){
        Set context = adDriver.getContextHandles();
        adDriver.context((String) context.toArray()[0]);
    }

    public void contextWebview(){
        Set context = adDriver.getContextHandles();
        adDriver.context((String) context.toArray()[1]);
    }

    public void clickAwards(){
        for (int i=0; i<__strAwardsID.length; i++){
            try {
                _btnAwards = (AndroidElement) adDriver.findElementByAccessibilityId(__strAwardsID[i]);
                break;
            }catch (Exception eNoFindElement){
            }
        }
        _btnAwards.click();
    }

    public void clickInfo(){
        __btnInfo = (AndroidElement) adDriver.findElementByAccessibilityId(__strInfoID);
        __btnInfo.click();
    }

    public ArrayList getDescription(){
        ArrayList<AndroidElement> alView = (ArrayList<AndroidElement>) adDriver.findElementsByClassName(__strAwardsDescriptionClass);
        ArrayList alContent = new ArrayList();
        for (int i = 0; i<alView.size(); i++){
            String strContent = alView.get(i).getAttribute("name");
            if (!(strContent.equals("") || strContent.equals("\n"))){
                alContent.add(strContent);
            }
        }
        return alContent;
    }
}
