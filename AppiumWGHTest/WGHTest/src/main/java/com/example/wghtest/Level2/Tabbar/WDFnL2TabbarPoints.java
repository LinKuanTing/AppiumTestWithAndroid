package com.example.wghtest.Level2.Tabbar;

import org.openqa.selenium.By;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;


import static com.example.wghtest.WGHTestBase.adDriver;

public class WDFnL2TabbarPoints {
    protected AndroidElement _btnTermsOfUse,_btnEarned,_btnUsed,_btnDeadline;

    private static String __strFirstRecord = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[2]/android.widget.RelativeLayout/android.support.v4.view.ViewPager/android.widget.LinearLayout/android.widget.ScrollView/android.widget.LinearLayout/android.widget.ListView/android.widget.LinearLayout[1]/android.widget.LinearLayout[1]";

    private static String __strEarnedListID = "tw.com.wgh3h:id/reward_list";

    private static String __strWowPointsID = "tw.com.wgh3h:id/tv_point_value";
    private static String __strEarnedPointsID = "tw.com.wgh3h:id/tvEarned";
    private static String __strUsedPointsID = "tw.com.wgh3h:id/tvUsed";
    private static String __strMoreID = "tw.com.wgh3h:id/foot_tv";
    private static String __strNoDataID = "tw.com.wgh3h:id/noData_tv";

    private static String __strTermsOfUseID = "tw.com.wgh3h:id/tv_use_terms";
    private static String __strPointsOptionsID = "tw.com.wgh3h:id/lin2";
    private static String __strEarnedID = "tw.com.wgh3h:id/earnedBtn";
    private static String __strUsedID = "tw.com.wgh3h:id/usedBtn";
    private static String __strDeadlineID = "tw.com.wgh3h:id/deadlineBtn";

    TouchAction touchAction = new TouchAction(adDriver);

    public WDFnL2TabbarPoints(){
        this._btnTermsOfUse = (AndroidElement) adDriver.findElement(By.id(__strTermsOfUseID));
        this._btnEarned = (AndroidElement) adDriver.findElement(By.id(__strEarnedID));
        this._btnUsed = (AndroidElement) adDriver.findElement(By.id(__strUsedID));
        this._btnDeadline = (AndroidElement)adDriver.findElement(By.id(__strDeadlineID));
    }

    public int getWowPoints(){
        return Integer.parseInt(adDriver.findElement(By.id(__strWowPointsID)).getText());
    }

    public int getEarnedPoints(){
        return Integer.parseInt(adDriver.findElement(By.id(__strEarnedPointsID)).getText());
    }

    public int getUsedPoints(){
        return Integer.parseInt(adDriver.findElement(By.id(__strUsedPointsID)).getText());
    }


    public void clickTerms(){
        _btnTermsOfUse.click();
    }

    public void clickEarned(){
        _btnEarned.click();
    }

    public void clickUsed(){
        _btnUsed.click();
    }

    public void clickDeadline(){
        _btnDeadline.click();
    }


    public int getEarnedTotalPoints() throws InterruptedException {
        int intTotalPoints = 0;
        ArrayList alEarnedRecord = new ArrayList();


        while(true){
            try {
                //更多資料按鈕  點擊
                adDriver.findElement(By.id(__strMoreID)).click();
            }catch (Exception eNoFindElement){}


            List<AndroidElement> listEarnedItem = adDriver.findElements(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/titleTV\"]"));
            List<AndroidElement> listEarnedPoints = adDriver.findElements(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/pointsTV\"]"));
            List<AndroidElement> listEarnedDate = adDriver.findElements(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/dateTV\"]"));
            List<AndroidElement> listEarnedLine = adDriver.findElements(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/points_line\"]"));
            //判斷是滑動是否滑完全
            while(true){
                int pointX = adDriver.findElementById(__strEarnedListID).getLocation().getX() + adDriver.findElementById(__strEarnedListID).getSize().getWidth()/2;
                int pointY = adDriver.findElementById(__strEarnedListID).getLocation().getY() + adDriver.findElementById(__strEarnedListID).getSize().getHeight()/2;
                if (listEarnedLine.get(0).getLocation().getY() < listEarnedPoints.get(0).getLocation().getY()){
                    touchAction.press(PointOption.point(pointX,pointY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).moveTo(PointOption.point(pointX,pointY-50)).release().perform();
                    listEarnedPoints = adDriver.findElements(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/pointsTV\"]"));
                    listEarnedLine = adDriver.findElements(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/points_line\"]"));
                }else { break; }
            }


            //開始抓取點數資料
            listEarnedItem = adDriver.findElements(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/titleTV\"]"));
            listEarnedPoints = adDriver.findElements(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/pointsTV\"]"));
            listEarnedDate = adDriver.findElements(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/dateTV\"]"));
            listEarnedLine = adDriver.findElements(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/points_line\"]"));
            int visibleNum = listEarnedLine.size();

            //儲存點數資料
            for (int i=0; i<visibleNum; i++){
                String strRecordData;

                strRecordData = listEarnedItem.get(i).getText() +"/"+ listEarnedDate.get(i).getText() +"/"+ listEarnedPoints.get(i).getText();

                //先存入第一筆資料
                if (alEarnedRecord.size() <= visibleNum){
                    alEarnedRecord.add(strRecordData);
                }


                //判斷將被儲存的資料是否已存在
                for (int j = 0; i<alEarnedRecord.size(); j++){
                    if (alEarnedRecord.get(j).equals(strRecordData)){
                        break;
                    }
                    if (j == alEarnedRecord.size()-1){
                        alEarnedRecord.add(strRecordData);
                    }
                }

            }

            //下滑距離計算
            int startX = listEarnedLine.get(visibleNum-1).getLocation().getX()+listEarnedLine.get(visibleNum-1).getSize().getWidth()/2;
            int startY = listEarnedLine.get(visibleNum-1).getLocation().getY()+listEarnedLine.get(visibleNum-1).getSize().getHeight()/2;
            int endX = adDriver.findElementById(__strEarnedListID).getLocation().getX()+adDriver.findElementById(__strEarnedListID).getSize().getWidth()/2;
            int endY = adDriver.findElementById(__strEarnedListID).getLocation().getY();

            touchAction.press(PointOption.point(startX,startY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).moveTo(PointOption.point(endX,endY-20)).release().perform();

            String strSourcePage = adDriver.getPageSource();
            String strCheckLoadFinish;
            if (strSourcePage.contains(__strMoreID)){
                int x = adDriver.findElement(By.id(__strMoreID)).getLocation().getX();
                int y = adDriver.findElement(By.id(__strMoreID)).getLocation().getY();
                adDriver.findElement(By.id(__strMoreID)).click();

                while(true){
                    listEarnedItem = adDriver.findElements(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/titleTV\"]"));
                    listEarnedDate = adDriver.findElements(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/dateTV\"]"));
                    int pointItem = listEarnedItem.get(0).getLocation().getY();
                    int pointDate = listEarnedDate.get(0).getLocation().getY();

                    if (pointItem > pointDate){
                        touchAction.press(PointOption.point(500,1300)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(5))).moveTo(PointOption.point(500,1240)).release().perform();
                    } else {
                        break;
                    }
                }

                //check click Btn_more if load finish
                strCheckLoadFinish = adDriver.getPageSource();
                Thread.sleep(3000);
                if (strCheckLoadFinish.equals(adDriver.getPageSource())){
                    touchAction.tap(PointOption.point(x,y)).release().perform();
                }
            }

            else if (strSourcePage.contains(__strNoDataID)){

                //可能遇到 no more data時 直接跳出迴圈
                //但可能有幾筆資料沒計算到就跳出
                listEarnedItem = adDriver.findElements(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/titleTV\"]"));
                listEarnedPoints = adDriver.findElements(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/pointsTV\"]"));
                listEarnedDate = adDriver.findElements(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/dateTV\"]"));
                for (int i=0; i<visibleNum; i++){
                    String strRecordData;
                    try {
                        strRecordData = listEarnedItem.get(i).getText() + "/" +listEarnedDate.get(i).getText() + "/" +listEarnedPoints.get(i).getText();
                    }catch (Exception e){
                        continue;
                    }

                    if (alEarnedRecord.size() <= 3){
                        alEarnedRecord.add(strRecordData);
                        continue;
                    }

                    //判斷重複
                    for (int j = 0; i<alEarnedRecord.size(); j++){
                        if (alEarnedRecord.get(j).toString().contains(listEarnedDate.get(i).getText())){
                            break;
                        }
                        if (j == alEarnedRecord.size()-1){
                            alEarnedRecord.add(strRecordData);
                        }
                    }

                }

                break;
            }

            //System.out.println(alEarnedRecord);

        }


        for (int i=0; i<alEarnedRecord.size(); i++){
            String[] strEardedRecord = alEarnedRecord.get(i).toString().split("/");
            int idxPoint = strEardedRecord.length-1;
            intTotalPoints += Integer.parseInt(strEardedRecord[idxPoint]);
            //System.out.println(intTotalPoint + " , " +strEardedRecord[idxPoint]);
        }


        for (int i=0;i<alEarnedRecord.size();i++){
            System.out.println(alEarnedRecord.get(i));
        }

        return intTotalPoints;

    }


    public int willremove() throws InterruptedException {
        int intTotalPoint = 0;
        ArrayList alEarnedRecord = new ArrayList();

        int endX = adDriver.findElement(By.id(__strPointsOptionsID)).getLocation().getX()+adDriver.findElement(By.id(__strPointsOptionsID)).getSize().getWidth()/2;
        int endY = adDriver.findElement(By.id(__strPointsOptionsID)).getLocation().getY()+adDriver.findElement(By.id(__strPointsOptionsID)).getSize().getHeight();

        while (true){
            try {
                //若一開始有更多資料按鈕  點擊
                adDriver.findElement(By.id(__strMoreID)).click();
            }catch (Exception eNoFindElement){ }


            //判斷是滑動是否滑完全
            List<AndroidElement> listEarnedLine = adDriver.findElements(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/points_line\"]"));
            int visibleNum = listEarnedLine.size();
            while(visibleNum == listEarnedLine.size()+1){
                touchAction.press(PointOption.point(endX,endY+90)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).moveTo(PointOption.point(endX,endY)).release().perform();
                listEarnedLine = adDriver.findElements(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/points_line\"]"));
                visibleNum = listEarnedLine.size();
            }

            List<AndroidElement> listEarnedItem = adDriver.findElements(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/titleTV\"]"));
            List<AndroidElement> listEarnedPoints = adDriver.findElements(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/pointsTV\"]"));
            List<AndroidElement> listEarnedDate = adDriver.findElements(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/dateTV\"]"));

/*
            for (int i=0; i<listEarnedItem.size(); i++){
                System.out.print(listEarnedItem.get(i).getText()+",");
            }
            System.out.println();
            for (int i=0; i<listEarnedDate.size(); i++){
                System.out.print(listEarnedDate.get(i).getText()+",");
            }
            System.out.println();
            for (int i=0; i<listEarnedPoints.size(); i++){
                System.out.print(listEarnedPoints.get(i).getText()+",");
            }
            System.out.println();
*/


            //將記錄存到List
            for (int i=0; i<visibleNum; i++){
                String strRecordData;

                //System.out.println(alEarnedRecord);
                if (alEarnedRecord.size()<=1){
                    strRecordData = listEarnedItem.get(i).getText() + "/" +listEarnedDate.get(i).getText() + "/" +listEarnedPoints.get(i).getText();
                }else {
                    strRecordData = listEarnedItem.get(i).getText() + "/" +listEarnedDate.get(i).getText() + "/" +listEarnedPoints.get(i+1).getText();
                }

                //3 為可見數
                if (alEarnedRecord.size() < visibleNum){
                    alEarnedRecord.add(strRecordData);
                    continue;
                }

                for (int j = 0; i<alEarnedRecord.size(); j++){
                    if (alEarnedRecord.get(j).equals(strRecordData)){
                        break;
                    }
                    if (j == alEarnedRecord.size()-1){
                        alEarnedRecord.add(strRecordData);
                    }

                }
            }
            int startX = endX;
            int startY = listEarnedLine.get(visibleNum-1).getLocation().getY();
            touchAction.press(PointOption.point(startX,startY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(5))).moveTo(PointOption.point(endX,endY)).release().perform();

            String strSourcePage = adDriver.getPageSource();
            String strCheckLoadFinish;
            if (strSourcePage.contains(__strMoreID)){
                int x = adDriver.findElement(By.id(__strMoreID)).getLocation().getX();
                int y = adDriver.findElement(By.id(__strMoreID)).getLocation().getY();
                adDriver.findElement(By.id(__strMoreID)).click();

                while(true){
                    listEarnedItem = adDriver.findElements(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/titleTV\"]"));
                    listEarnedDate = adDriver.findElements(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/dateTV\"]"));
                    int pointItem = listEarnedItem.get(0).getLocation().getY();
                    int pointDate = listEarnedDate.get(0).getLocation().getY();

                    if (pointItem > pointDate){
                        touchAction.press(PointOption.point(500,1300)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(5))).moveTo(PointOption.point(500,1240)).release().perform();
                    } else {
                        break;
                    }
                }

                //check click Btn_more if load finish
                strCheckLoadFinish = adDriver.getPageSource();
                Thread.sleep(3000);
                if (strCheckLoadFinish.equals(adDriver.getPageSource())){
                    touchAction.tap(PointOption.point(x,y)).release().perform();
                }
            }

            else if (strSourcePage.contains(__strNoDataID)){

                //可能遇到 no more data時 直接跳出迴圈
                //但可能有幾筆資料沒計算到就跳出
                listEarnedItem = adDriver.findElements(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/titleTV\"]"));
                listEarnedPoints = adDriver.findElements(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/pointsTV\"]"));
                listEarnedDate = adDriver.findElements(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/dateTV\"]"));
                for (int i=0; i<visibleNum; i++){
                    String strRecordData;
                    try {
                        strRecordData = listEarnedItem.get(i).getText() + "/" +listEarnedDate.get(i).getText() + "/" +listEarnedPoints.get(i).getText();
                    }catch (Exception e){
                        continue;
                    }

                    if (alEarnedRecord.size() <= 3){
                        alEarnedRecord.add(strRecordData);
                        continue;
                    }

                    for (int j = 0; i<alEarnedRecord.size(); j++){
                        if (alEarnedRecord.get(j).toString().contains(listEarnedDate.get(i).getText())){
                            break;
                        }
                        if (j == alEarnedRecord.size()-1){
                            alEarnedRecord.add(strRecordData);
                        }
                    }
                }

                break;
            }

            //System.out.println(alEarnedRecord);

        }


        for (int i=0; i<alEarnedRecord.size(); i++){
            String[] strEardedRecord = alEarnedRecord.get(i).toString().split("/");
            int idxPoint = strEardedRecord.length-1;
            intTotalPoint += Integer.parseInt(strEardedRecord[idxPoint]);
            //System.out.println(intTotalPoint + " , " +strEardedRecord[idxPoint]);
        }

/*
        for (int i=0;i<alEarnedRecord.size();i++){
            System.out.println(alEarnedRecord.get(i));
        }
*/
        return intTotalPoint;
    }


    public int getUsedTotalPoints(){
        int intTotalPoints = 0;

        try {
            List<AndroidElement> listUsedPoints = adDriver.findElements(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/pointsTV\"]"));
            for (int i=0; i<listUsedPoints.size(); i++){
                intTotalPoints += Integer.parseInt(listUsedPoints.get(i).getText());
            }
        }catch (Exception eNoFindElement){
            //沒有使用紀錄時
        }

        return intTotalPoints;

    }

    public int getDeadlineTotalPoints(){
        List<AndroidElement> listDeadlinePoints = adDriver.findElements(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/deadlinePointsTV\"]"));
        int intTotalPoints = 0;
        for (int i=0; i<listDeadlinePoints.size(); i++){
            intTotalPoints += Integer.parseInt(listDeadlinePoints.get(i).getText());
        }
        return intTotalPoints;
    }
}
