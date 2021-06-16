package com.example.wghtest.Level1;

import com.example.wghtest.other.FnFileEvent;
import com.example.wghtest.other.FnFileEncrypt;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.select.Elements;

import java.awt.Desktop;
import java.net.URL;
import java.util.Properties;

import javax.mail.*;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;


public class FnMailEvent {
    private static String __strUserName;
    private static String __strPassword;
    private static String __strHost = "imap.gmail.com";

    private static String __strENMailRegister = "Activate your WowGoHealth account";
    private static String __strCHMailRegister = "我顧健康會員註冊驗證信";
    private static String __strENMailForgetPwd = "WowGoHealth Information (Forgot Password)";
    private static String __strCHMailForgetPwd = "我顧健康系統通知（忘記密碼)";

    private static String __strFileName = "MailAct";
    private static String __strFilePath;

    public FnMailEvent() throws Exception {
        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath =  fnFileEvent.getPath(__strFileName);
        FnFileEncrypt fnFileEncrypt = new FnFileEncrypt();
        byte[] bytes = fnFileEncrypt.loadFileContent(__strFilePath);
        String[] strContent = fnFileEncrypt.Decryptor(bytes).split("\r\n");
        __strUserName = strContent[0];
        __strPassword = strContent[1];

        //System.out.println(__strUserName+"   "+__strPassword);
    }

    public void getVerification() throws Exception {
        //使用imap並設定port
        Properties properties = System.getProperties();
        properties.setProperty("mail.store.protocol","imaps");
        properties.put("mail.imap.port","993");

        //連接imap
        Session session = Session.getDefaultInstance(properties,null);
        Store store = session.getStore("imaps");
        store.connect(__strHost,__strUserName,__strPassword);

        //抓取收信匣信件
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_WRITE);

        //System.out.println("Total Message:" + folder.getMessageCount());
        //System.out.println("Unread Message:" + folder.getUnreadMessageCount());

        //搜尋未讀的信件
        FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN),false);
        Message messages[] = folder.search(ft);

        //尋找讀取信件中的認證信
        String hrefVerification = "";

        for (Message message : messages){
            if (message.getSubject().equals(__strCHMailRegister) || message.getSubject().equals(__strENMailRegister)){
                //取得內容HTML
                Multipart multipart = (Multipart) message.getContent();
                BodyPart bodyPart = multipart.getBodyPart(0);
                //System.out.println(bodyPart.getContent().toString());
                //尋找認證的超連結
                Document doc = Jsoup.parse(bodyPart.getContent().toString());
                Elements href = doc.select("p").select("a[href]");
                //取得超連結的文字部分

                String pickVerification = "";
                for (int i=0; i<href.size(); i++){
                    if (href.get(i).text().contains("[ 點此進行認證 ]") || href.get(i).text().contains("[ Click here for confirmation ]")){
                        pickVerification = href.get(i).toString();
                    }
                }
                //切割取得超連結
                //System.out.println(pickVerification);
                String[] strAry = pickVerification.split("\"");
                hrefVerification = strAry[1];
                //System.out.println(hrefVerification);
            }
        }


        //開始超連結
        try {
            Desktop desktop = Desktop.getDesktop();
            URL url = new URL(hrefVerification);
            desktop.browse(url.toURI());
            //關閉瀏覽器
            Thread.sleep(30000);
            Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
        } catch (Exception eNoFindMail){
            eNoFindMail.printStackTrace();
            System.out.println("can't not find the verification of mail");
        }


    }

    public boolean isExistForgetPwdMail() throws Exception {
        //使用imap並設定port
        Properties properties = System.getProperties();
        properties.setProperty("mail.store.protocol","imaps");
        properties.put("mail.imap.port","993");

        //連接imap
        Session session = Session.getDefaultInstance(properties,null);
        Store store = session.getStore("imaps");
        store.connect(__strHost,__strUserName,__strPassword);

        //抓取收信匣信件
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_WRITE);

        //搜尋未讀的信件
        FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN),false);
        Message messages[] = folder.search(ft);

        //尋找是否有忘記密碼的郵件
        boolean isFindMail = false;
        for (Message message : messages) {
            if (message.getSubject().equals(__strCHMailForgetPwd) || message.getSubject().equals(__strENMailForgetPwd)) {
                isFindMail = true;
                //找到後更改為已讀
                message.setFlag(Flags.Flag.SEEN, true);
            }
        }

        //回傳結果
        return isFindMail;

    }


}

