package org.lyon_yan.android.utils.esc_pos;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * ESC/POS 打印指令集
 * 
 * @author Lyon_Yan <br/>
 *         <b>time</b>: 2015年9月1日 下午12:58:46
 */
public class EscPosSupport {
	/**
	 * 打印机命令
	 * 
	 * @author Lyon_Yan <br/>
	 *         <b>time</b>: 2015年9月1日 下午1:27:29
	 */
	public static final class COMMAND {
		public static final char[] INIT = new char[] { 0x1b, 0x40 };
		public static final char[] GP = new char[] { 0x1d, 0x50, 0x00, 0x00 };

		public static final char[] BLANK_LINE = new char[] { 0x1b, 0x64, 0x30 };
		public static final char[] ALIGN_LEFT = new char[] { 0x1b, 0x61, 0 };
		public static final char[] ALIGN_CENTER = new char[] { 0x1b, 0x61, 1 };
		public static final char[] ALIGN_RIGHT = new char[] { 0x1b, 0x61, 2 };

		public static final char[] LINE_SPACE_DEF = new char[] { 0x1b, 0x33, 80 };
		public static final char[] LINE_SPACE = new char[] { 0x1b, 0x32 };

		public static final char[] FONT_HEIGHT_YES = new char[] { 0x1d, 0x21,
				0x01 };
		public static final char[] FONT_HEIGHT_NO = new char[] { 0x1d, 0x21,
				0x00 };
		public static final char[] FONT_WIDTH_YES = new char[] { 0x1d, 0x21,
				0x10 };
		public static final char[] FONT_WIDTH_NO = new char[] { 0x1d, 0x21,
				0x00 };
		public static final char[] FONT_LARGE_YES = new char[] { 0x1b, 0x21,
				0x30, 0x1c, 0x57, 0x01 };
		public static final char[] FONT_LARGE_NO = new char[] { 0x1b, 0x21,
				0x00, 0x1c, 0x57, 0x00 };
		public static final char[] FONT_UNDERLINE_YES = new char[] { 0x1b,
				0x2d, 0x02 };
		public static final char[] FONT_UNDERLINE_NO = new char[] { 0x1b, 0x2d,
				0x00 };
		public static final char[] FONT_BOLD_YES = new char[] { 0x1b, 0x45,
				0x01 };
		public static final char[] FONT_BOLD_NO = new char[] { 0x1b, 0x45, 0x00 };

		public static final char[] CUT = new char[] { 0x1d, 0x56, 66, 60 };

		// 出单后叫声 第三个参数 次数 第四个参数 间隔 单位 50毫秒
		public static final char[] VOICE = new char[] { 0x1b, 0x42, 0x03, 0x01 };

		// 警告叫声 第三个参数 次数 第四个参数 间隔 单位 50毫秒 第五个参数 是否亮灯
		public static final char[] ALARM = new char[] { 0x1b, 0x43, 0x05, 0x02,
				0x03 };

		public static final char[] MONEY_BOX = new char[] { 0x1b, 0x70, 0x00,
				50, 30 };

		// ESC * m nL nH 点阵图
		/**
		 * 获取打印的数据
		 * 
		 * @author Lyon_Yan <br/>
		 *         <b>time</b>: 2015年9月1日 下午1:39:26
		 * @param nL
		 * @param nH
		 * @return
		 */
		public static final byte[] IMAGE_BITMAP(int width) {
			return new byte[] { 0x1B, 0x2A, 0x21, (byte) (width % 256),
					(byte) (width / 256) };
		}
	}

	private PrintWriter socketWriter = null;
	private DataInputStream socketReader = null;
	private Socket client = new Socket();
	private String host = "192.168.1.52";
	private int port = 9100;
	private String charset = "GBK";
	private int timeout = 1000;

	public EscPosSupport(String host, int port, int timeout) throws IOException {
		super();
		this.host = host;
		this.port = port;
		this.timeout = timeout;
		init();
	}

	public EscPosSupport(String host, int port, String charset, int timeout)
			throws IOException {
		super();
		this.host = host;
		this.port = port;
		this.charset = charset;
		this.timeout = timeout;
		init();
	}

	public boolean clear() {
		try {
			flush();
			// clear
			socketWriter.write(0x10);
			socketWriter.write(0x14);
			socketWriter.write(0x08);
			socketWriter.write(8);
			socketWriter.write(1);
			socketWriter.write(3);
			socketWriter.write(20);
			socketWriter.write(1);
			socketWriter.write(6);
			socketWriter.write(28);
			flush();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public void destory() {
		try {
			reset();
			// clear();
			socketWriter.close();
			socketReader.close();
			client.getOutputStream().close();
			client.getOutputStream().close();
			client.close();
			socketWriter = null;
			socketReader = null;
			client = null;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 刷新数据流
	 * 
	 * @author Lyon_Yan <br/>
	 *         <b>time</b>: 2015年9月1日 下午2:17:01
	 * @return
	 */
	public boolean flush() {
		// TODO Auto-generated method stub
		try {
			socketWriter.flush();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public String getCharset() {
		return charset;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public int getTimeout() {
		return timeout;
	}

	public void init() throws IOException {
		// TODO Auto-generated method stub
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					destory();
					client.connect(new InetSocketAddress(host, port), timeout);// 创建一个socket
					socketWriter = new PrintWriter(new OutputStreamWriter(
							client.getOutputStream(), charset));// 创建输入输出数据流
					socketReader = new DataInputStream(client.getInputStream());
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				super.run();
			}
		}.start();
	}

	/**
	 * 
	 * @author Lyon_Yan <br/>
	 *         <b>time</b>: 2015年9月1日 下午12:58:37
	 * @param bitmap
	 */
	public void printBitmap(Bitmap bitmap) {
		try {
			// 打印二维码
			byte[] data = new byte[] { 0x1B, 0x33, 0x00 };
			reset();
			socketWriter.write(COMMAND.ALIGN_CENTER);
			socketWriter.flush();
			client.getOutputStream().write(data);
			data[0] = (byte) 0x00;
			data[1] = (byte) 0x00;
			data[2] = (byte) 0x00; // 重置参数
			int pixelColor;
			byte[] escBmp = COMMAND.IMAGE_BITMAP(bitmap.getWidth());
			final int line_height = 24;
			// 每行进行打印
			for (int i = 0; i < bitmap.getHeight() / line_height + 1; i++) {
				client.getOutputStream().write(escBmp);
				for (int j = 0; j < bitmap.getWidth(); j++) {
					for (int k = 0; k < line_height; k++) {
						if (((i * line_height) + k) < bitmap.getHeight()) {
							pixelColor = bitmap.getPixel(j, (i * line_height)
									+ k);
							/**
							 * 当pixelColor小于0时为黑色区域
							 */
							if (pixelColor < 0) {
								/**
								 * 取k值 的高位上的数据
								 */
								data[k / 8] += (byte) (128 >> (k % 8));
							}
						}
					}
					client.getOutputStream().write(data);
					// 重置参数
					data[0] = (byte) 0x00;
					data[1] = (byte) 0x00;
					data[2] = (byte) 0x00;
				}
				// 换行
				byte[] byte_send1 = new byte[2];
				byte_send1[0] = 0x0d;
				byte_send1[1] = 0x0a;
				client.getOutputStream().write(byte_send1);
			}
			client.getOutputStream().flush();
			reset();
			client.getOutputStream().flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 写入字符串并换行
	 * 
	 * @author Lyon_Yan <br/>
	 *         <b>time</b>: 2015年9月1日 下午2:20:09
	 * @param str
	 * @return
	 */
	public boolean println(String str) {
		try {
			socketWriter.println(str);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 打印条形码
	 * 
	 * @author Lyon_Yan <br/>
	 *         <b>time</b>: 2015年9月1日 下午2:47:59
	 * @param value
	 *            EAN13的数据源必须是13位的字符
	 * @return
	 */
	public boolean printOneCodeByEAN13(String value) {
		try {
			// 打印条形码
			socketWriter.write(0x1d);
			socketWriter.write((char) 'k');
			// socketWriter.write(120);
			socketWriter.write(67);
			socketWriter.write(value.length());
			socketWriter.write(value);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 执行命令
	 * 
	 * @author Lyon_Yan <br/>
	 *         <b>time</b>: 2015年9月1日 下午2:12:19
	 * @param command
	 *            org.lyon_yan.android.utils.esc_pos.COMMAND
	 * @return
	 */
	public boolean process(char[] command) {
		try {
			socketWriter.write(command);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public void reset() {
		socketWriter.write(COMMAND.INIT);
		socketWriter.write(COMMAND.ALIGN_LEFT);
		socketWriter.write(COMMAND.FONT_BOLD_NO);
		socketWriter.write(COMMAND.FONT_HEIGHT_NO);
		socketWriter.write(COMMAND.FONT_LARGE_NO);
		socketWriter.write(COMMAND.FONT_UNDERLINE_NO);
		socketWriter.write(COMMAND.FONT_WIDTH_NO);
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * 写入字符串
	 * 
	 * @author Lyon_Yan <br/>
	 *         <b>time</b>: 2015年9月1日 下午2:13:17
	 * @param str
	 * @return
	 */
	public boolean write(String str) {
		// TODO Auto-generated method stub
		try {
			socketWriter.write(str);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
}
