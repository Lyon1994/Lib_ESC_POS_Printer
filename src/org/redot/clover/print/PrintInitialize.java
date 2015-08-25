package org.redot.clover.print;

public class PrintInitialize {

   final static String init = new String(new byte[]{27, 64});
    final static String gp = new String(new byte[]{29, 80, 0,0});

    final static String blod_No = new String(new byte[]{27, 69, 0});
    final static String blod_Yes = new String(new byte[]{27, 69, 1});
    //final static String Font_Wieght = "BBBB";
//    final static String Font_Height_No = new String(new byte[]{29, 33, 0});
    final static String Font_Height_No = new String(new byte[]{0x1b, 0x21, 0x30, 0x1c, 0x57, 0x00});
    final static String Font_Height_Yes = new String(new byte[]{0x1b, 0x21, 0x30, 0x1c, 0x57, 0x01});
//    final static String Font_Height_Yes = new String(new byte[]{29, 33, 1});
    final static String Center_Left = new String(new byte[]{27, 97, 0});
    final static String Center_Center = new String(new byte[]{27, 97, 1});
    final static String Center_Right = new String(new byte[]{27, 97, 2});
    final static String CUT = new String(new byte[]{29, 86, 66,60});

    //出单后叫声 第三个参数 次数 第四个参数 间隔 单位 50毫秒
    final static String Voice = new String(new byte[]{27, 66, 3,1});

    //警告叫声 第三个参数 次数 第四个参数 间隔 单位 50毫秒  第五个参数 是否亮灯
    final static String Alarm = new String(new byte[]{27, 67, 20,20,3});

    final static String MONEY_BOX = new String(new byte[]{27,112,0,50,30});

    static String previousBlod = "";
    static String previousXlocationField = "";
    static String previousXlocationValue = "";
    static String previousFontHeight = "";
    static String previousCenter = "";
}