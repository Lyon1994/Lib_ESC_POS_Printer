package org.print.support;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

/** 
 * Created by haoguibao on 16/2/18. 
 * Description : 封装Pos机打印工具类 
 * Revision : 
 */  
public class Pos {  
    //定义编码方式  
    private static String encoding = null;  
  
    private Socket sock = null;  
    // 通过socket流进行读写  
    private OutputStream socketOut = null;  
    private OutputStreamWriter writer = null;  
  
    /** 
     * 初始化Pos实例 
     * 
     * @param ip 打印机IP 
     * @param port  打印机端口号 
     * @param encoding  编码 
     * @throws IOException 
     */  
    public Pos(String ip, int port, String encoding) throws IOException {  
        sock = new Socket(ip, port);  
        socketOut = new DataOutputStream(sock.getOutputStream());  
        this.encoding = encoding;  
        writer = new OutputStreamWriter(socketOut, encoding);  
    }  
  
    /** 
     * 关闭IO流和Socket 
     * 
     * @throws IOException 
     */  
    protected void closeIOAndSocket() throws IOException {  
        writer.close();  
        socketOut.close();  
        sock.close();  
    }  
  
    /** 
     * 打印二维码 
     * 
     * @param qrData 二维码的内容 
     * @throws IOException 
     */  
    protected void qrCode(String qrData) throws IOException {  
        int moduleSize = 8;  
        int length = qrData.getBytes(encoding).length;  
  
        //打印二维码矩阵  
        writer.write(0x1D);// init  
        writer.write("(k");// adjust height of barcode  
        writer.write(length + 3); // pl  
        writer.write(0); // ph  
        writer.write(49); // cn  
        writer.write(80); // fn  
        writer.write(48); //  
        writer.write(qrData);  
  
        writer.write(0x1D);  
        writer.write("(k");  
        writer.write(3);  
        writer.write(0);  
        writer.write(49);  
        writer.write(69);  
        writer.write(48);  
  
        writer.write(0x1D);  
        writer.write("(k");  
        writer.write(3);  
        writer.write(0);  
        writer.write(49);  
        writer.write(67);  
        writer.write(moduleSize);  
  
        writer.write(0x1D);  
        writer.write("(k");  
        writer.write(3); // pl  
        writer.write(0); // ph  
        writer.write(49); // cn  
        writer.write(81); // fn  
        writer.write(48); // m  
  
        writer.flush();  
  
    }  
  
    /** 
     * 进纸并全部切割 
     * 
     * @return 
     * @throws IOException 
     */  
    protected void feedAndCut() throws IOException {  
        writer.write(0x1D);  
        writer.write(86);  
        writer.write(65);  
        //        writer.write(0);  
        //切纸前走纸多少  
        writer.write(100);  
        writer.flush();  
  
        //另外一种切纸的方式  
        //        byte[] bytes = {29, 86, 0};  
        //        socketOut.write(bytes);  
    }  
  
    /** 
     * 打印换行 
     * 
     * @return length 需要打印的空行数 
     * @throws IOException 
     */  
    protected void printLine(int lineNum) throws IOException {  
        for (int i = 0; i < lineNum; i++) {  
            writer.write("\n");  
        }  
        writer.flush();  
    }  
  
    /** 
     * 打印换行(只换一行) 
     * 
     * @throws IOException 
     */  
    protected void printLine() throws IOException {  
        writer.write("\n");  
        writer.flush();  
    }  
  
    /** 
     * 打印空白(一个Tab的位置，约4个汉字) 
     * 
     * @param length 需要打印空白的长度, 
     * @throws IOException 
     */  
    protected void printTabSpace(int length) throws IOException {  
        for (int i = 0; i < length; i++) {  
            writer.write("\t");  
        }  
        writer.flush();  
    }  
  
    /** 
     * 打印空白（一个汉字的位置） 
     * 
     * @param length 需要打印空白的长度, 
     * @throws IOException 
     */  
    protected void printWordSpace(int length) throws IOException {  
        for (int i = 0; i < length; i++) {  
            writer.write("  ");  
        }  
        writer.flush();  
    }  
  
    /** 
     * 打印位置调整 
     * 
     * @param position 打印位置  0：居左(默认) 1：居中 2：居右 
     * @throws IOException 
     */  
    protected void printLocation(int position) throws IOException {  
        writer.write(0x1B);  
        writer.write(97);  
        writer.write(position);  
        writer.flush();  
    }  
  
    /** 
     * 绝对打印位置 
     * 
     * @throws IOException 
     */  
    protected void printLocation(int light, int weight) throws IOException {  
        writer.write(0x1B);  
        writer.write(0x24);  
        writer.write(light);  
        writer.write(weight);  
        writer.flush();  
    }  
  
    /** 
     * 打印文字 
     * 
     * @param text 
     * @throws IOException 
     */  
    protected void printText(String text) throws IOException {  
        String s = text;  
        byte[] content = s.getBytes("gbk");  
        socketOut.write(content);  
        socketOut.flush();  
    }  
  
    /** 
     * 新起一行，打印文字 
     * 
     * @param text 
     * @throws IOException 
     */  
    protected void printTextNewLine(String text) throws IOException {  
        //换行  
        writer.write("\n");  
        writer.flush();  
  
        String s = text;  
        byte[] content = s.getBytes("gbk");  
        socketOut.write(content);  
        socketOut.flush();  
    }  
  
    /** 
     * 初始化打印机 
     * 
     * @throws IOException 
     */  
    protected void initPos() throws IOException {  
        writer.write(0x1B);  
        writer.write(0x40);  
        writer.flush();  
    }  
  
    /** 
     * 加粗 
     * 
     * @param flag false为不加粗 
     * @return 
     * @throws IOException 
     */  
    protected void bold(boolean flag) throws IOException {  
        if (flag) {  
            //常规粗细  
            writer.write(0x1B);  
            writer.write(69);  
            writer.write(0xF);  
            writer.flush();  
        } else {  
            //加粗  
            writer.write(0x1B);  
            writer.write(69);  
            writer.write(0);  
            writer.flush();  
        }  
    }  
}  