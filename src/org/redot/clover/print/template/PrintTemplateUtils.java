package org.redot.clover.print.template;


/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 11-8-23
 * Time: 下午2:44
 * To change this template use File | Settings | File Templates.
 */
public class PrintTemplateUtils {

    public static char[] INIT = new char[]{0x1b, 0x40};
    public static char[] GP = new char[]{0x1d, 0x50, 0x00, 0x00};

    public static char[] BLANK_LINE = new char[]{0x1b, 0x64, 0x30};
    public static char[] ALIGN_LEFT = new char[]{0x1b, 0x61, 0};
    public static char[] ALIGN_CENTER = new char[]{0x1b, 0x61, 1};
    public static char[] ALIGN_RIGHT = new char[]{0x1b, 0x61, 2};

    public static char[] LINE_SPACE_DEF = new char[]{0x1b, 0x33, 80};
    public static char[] LINE_SPACE = new char[]{0x1b, 0x32};

    public static char[] FONT_HEIGHT_YES= new char[]{0x1d, 0x21, 0x01};
    public static char[] FONT_HEIGHT_NO= new char[]{0x1d, 0x21, 0x00};
    public static char[] FONT_WIDTH_YES= new char[]{0x1d, 0x21, 0x10};
    public static char[] FONT_WIDTH_NO= new char[]{0x1d, 0x21, 0x00};
    public static char[] FONT_LARGE_YES= new char[]{0x1b, 0x21, 0x30, 0x1c, 0x57, 0x01};
    public static char[] FONT_LARGE_NO= new char[]{0x1b, 0x21, 0x00, 0x1c, 0x57, 0x00};
    public static char[] FONT_UNDERLINE_YES = new char[]{0x1b, 0x2d, 0x02};
    public static char[] FONT_UNDERLINE_NO = new char[]{0x1b, 0x2d, 0x00};
    public static char[] FONT_BOLD_YES = new char[]{0x1b, 0x45, 0x01};
    public static char[] FONT_BOLD_NO = new char[]{0x1b, 0x45, 0x00};


    public static char[] CUT= new char[]{0x1d, 0x56, 66, 60};

    //出单后叫声 第三个参数 次数 第四个参数 间隔 单位 50毫秒
    public static char[] VOICE= new char[]{0x1b, 0x42, 0x03, 0x01};

    //警告叫声 第三个参数 次数 第四个参数 间隔 单位 50毫秒  第五个参数 是否亮灯
    public static char[] ALARM= new char[]{0x1b, 0x43, 0x05, 0x02, 0x03};

    public static char[] MONEY_BOX= new char[]{0x1b, 0x70, 0x00, 50, 30};

    public static String FILL_SPACE(String str, int len){
        return  FILL_SPACE(str, len, 0);
    }

    public static String FILL_SPACE(String str, int len, int align){
        if(len == 0){
            return str;
        }
        int _len = str.getBytes().length;

        len = (int)(len * 2);
        if(len - _len > 0){
//            int x = (int)((len- _len)/1.5);
            int x = len-_len;
            if(align == 1){
                for(int i=0;i<x; i++){
                    if(i%2 == 0){
                        str = str + " ";
                    }
                    else{
                        str = " " + str;
                    }
                }
            }
            else if(align == 2){
                for(int i=0;i<x; i++){
                    str = " " + str;
                }
            }
            else{
                for(int i=0;i<x; i++){
                    str += " ";
                }
            }
        }
        else{
            str = str.substring(0, str.length() - (int)((_len - len) /2));
        }
        return str;
    }

}
