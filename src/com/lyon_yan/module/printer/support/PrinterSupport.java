//package com.lyon_yan.module.printer.support;
//
//public class PrinterSupport {
//	/**
//	 * @param PRINT_DATA
//	 *            小票主要数据
//	 * @param GS_INFO
//	 *            小票附带信息
//	 * @param CAIDAN_SN
//	 *            小票号码
//	 */
//	public void print(List<Map<String, Object>> PRINT_DATA,Map<String, String> GS_INFO, String CAIDAN_SN) {
//		if (PRINT_DATA != null && PRINT_DATA.size() > 0) {
//		try {
//			// 条码打印指令
//			byte[] PRINT_CODE = new byte[9];
//			PRINT_CODE[0] = 0x1d;
//			PRINT_CODE[1] = 0x68;
//			PRINT_CODE[2] = 120;
//			PRINT_CODE[3] = 0x1d;
//			PRINT_CODE[4] = 0x48;
//			PRINT_CODE[5] = 0x10;
//			PRINT_CODE[6] = 0x1d;
//			PRINT_CODE[7] = 0x6B;
//			PRINT_CODE[8] = 0x02;
//
//			// 清除字体放大指令
//			byte[] FD_FONT = new byte[3];
//			FD_FONT[0] = 0x1c;
//			FD_FONT[1] = 0x21;
//			FD_FONT[2] = 4;
//			
//			// 字体加粗指令
//			byte[] FONT_B = new byte[3];
//			FONT_B[0] = 27;
//			FONT_B[1] = 33;
//			FONT_B[2] = 8;
//			
//			// 字体纵向放大一倍
//			byte[] CLEAR_FONT = new byte[3];
//			CLEAR_FONT[0] = 0x1c;
//			CLEAR_FONT[1] = 0x21;
//			CLEAR_FONT[2] = 0;
//			
//			// 计算合计金额
//			int price = 0;
//			
//			// 初始化打印机
//			socketOut.write(27);
//			socketOut.write(64);
//			socketOut.write(FD_FONT);// 字体放大
//			socketOut.write(FONT_B);// 字体加粗
//			socketOut.write(10);
//			writer.write("  " + GS_INFO.get("GS_Name") + " \r\n");
//			writer.flush();// 关键,很重要,不然指令一次性输出,后面指令覆盖前面指令,导致取消放大指令无效
//
//			socketOut.write(CLEAR_FONT);
//			socketOut.write(10);
//			writer.write("NO: " + CAIDAN_SN + " \r\n");
//			writer.write("------------------------------\r\n");
//			writer.write("买家姓名: " + GS_INFO.get("GS_user_name") + "\r\n");
//			writer.write("地址: " + GS_INFO.get("GS_address") + "\r\n");
//			writer.write("联系电话: " + GS_INFO.get("GS_tel") + " \r\n");
//			writer.write("付款方式: " + GS_INFO.get("GS_pay_type") + "\r\n");
//			writer.write("------------------------------\r\n");
//			writer.write(Fix_String_Lenth(1,Colum_Name[0], 4)
//					+ Fix_String_Lenth(0,Colum_Name[1], 14)
//					+ Fix_String_Lenth(1,Colum_Name[2], 4)
//					+ Fix_String_Lenth(1,Colum_Name[3], 6) + "\r\n");
//			for (int i = 0; i < PRINT_DATA.size() - 1; i++) {
//				writer.write(Fix_String_Lenth(1,i + 1 + "", 2)
//						+ Fix_String_Lenth(0,PRINT_DATA.get(i).get("cai_name").toString(), 14)
//						+ Fix_String_Lenth(1,PRINT_DATA.get(i).get("cai_num").toString(), 4)
//						+ Fix_String_Lenth(1,PRINT_DATA.get(i).get("cai_price").toString(), 6) + "\r\n");
//				price += Double.parseDouble(PRINT_DATA.get(i).get("cai_price").toString());
//			}
//			
//			// 打印二维码
//			Bitmap bmp = (Bitmap)PRINT_DATA.get(PRINT_DATA.size() - 1).get("erweima");
//			
//            byte[] data = new byte[] { 0x1B, 0x33, 0x00 };
//            socketOut.write(data);
//            data[0] = (byte)0x00;
//            data[1] = (byte)0x00;
//            data[2] = (byte)0x00;    //重置参数
//            
//            int pixelColor;
//            
//            // ESC * m nL nH 点阵图
//            byte[] escBmp = new byte[] { 0x1B, 0x2A, 0x00, 0x00, 0x00 };
//            
//            escBmp[2] = (byte)0x21;
//            
//            //nL, nH  
//            escBmp[3] = (byte)(bmp.getWidth() % 256);
//            escBmp[4] = (byte)(bmp.getWidth() / 256);
//            
//            // 每行进行打印
//            for (int i = 0; i < bmp.getHeight()  / 24 + 1; i++){
//            	socketOut.write(escBmp);
//            	
//                for (int j = 0; j < bmp.getWidth(); j++){
//                    for (int k = 0; k < 24; k++){
//                        if (((i * 24) + k) < bmp.getHeight()){
//                            pixelColor = bmp.getPixel(j, (i * 24) + k);
//                            if (pixelColor != -1){
//                            	data[k / 8] += (byte)(128 >> (k % 8));
//                            }
//                        }
//                    }
//                    
//                    socketOut.write(data);
//                    // 重置参数
//                    data[0] = (byte)0x00;
//                    data[1] = (byte)0x00;
//                    data[2] = (byte)0x00;
//                }
//                //换行
//                byte[] byte_send1 = new byte[2];
//                byte_send1[0] = 0x0d;
//                byte_send1[1] = 0x0a;
//                socketOut.write(byte_send1);
//            }
//            
//            //换行
//            byte[] byte_send2 = new byte[2];
//            byte_send2[0] = 0x0d;
//            byte_send2[1] = 0x0a;
//            //发送测试信息 
//            socketOut.write(byte_send2);
//            
//			writer.write("------------------------------\r\n");
//			writer.write("本单共" + (PRINT_DATA.size() - 1) + "件商品,合计费用:" + price + "元\r\n");
//			writer.write("------------------------------\r\n");
//			writer.write("  谢 谢 惠 顾\r\n");
//			// 下面指令为打印完成后自动走纸
//			writer.write(27);
//			writer.write(100);
//			writer.write(4);
//			writer.write(10);
//			writer.close();
//			socketOut.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	}
//}
