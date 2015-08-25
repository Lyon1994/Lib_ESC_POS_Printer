/**
 ************************************************
 * @Company 上海竹枫信息技术有限公司
 * @WebSite http://www.zf-info.com
 * @Organize 欧斯英孚开源组织 http://www.osinfo.org
 * @Founder Lucifer.Zhou 
 * @MSN zhoujianguo_leo@hotmail.com
 * @Mail leo821031@gmail.com
 * 竹枫含义：
 * 【典故】君向楚，我归秦，便分路青竹丹枫。
 * 【释义】青竹生南方，丹枫长北地。借指南北。 
 ************************************************
 */
package org.osinfo.core.webapp.epson;

//import java.awt.image.BufferedImage;

/**
 * @Date 2010-7-30 上午10:56:57
 * @Description 描述 Epson指令封装
 */
public class EpsonPosPrinterCommand {
	public static final char HT = 0x9;
	public static final char LF = 0x0A;
	public static final char CR = 0x0D;
	public static final char ESC = 0x1B;
	public static final char DLE = 0x10;
	public static final char GS = 0x1D;
	public static final char FS = 0x1C;
	public static final char STX = 0x02;
	public static final char US = 0x1F;
	public static final char CAN = 0x18;
	public static final char CLR = 0x0C;

	/**
	 * n = 1: Transmit printer status n = 2: Transmit offline status n = 3:
	 * Transmit error status n = 4: Transmit paper roll sensor status
	 */
	public static final char[] DLE_EOT_n = new char[] { DLE, 0x04, 0x01 };

	/**
	 * n = 1: Recover from an error and restart printing from the line where the
	 * error occurred n = 2: Recover from an error aft clearing the receive and
	 * print buffers
	 */
	public static final char[] DLE_ENQ_n = new char[] { DLE, 0x05, 0x01 };

	/**
	 * n = 1 m = 0, 1 1 ≤ t ≤ 8 m Connector pin m = 0: Drawer kick-out connector
	 * pin 2. m = 1: Drawer kick-out connector pin 5. pulse ON time is [t × 100
	 * ms] and the OFF time is [t × 100 ms].
	 */
	public static final char[] DLE_DC4_n_m_t = new char[] { DLE, 0x14, 0x01,
			0x00, 0x01 };

	public static final char[] ESC_SELECT_DEF_CHAR = new char[] { ESC, '%',
			0x00 };
	public static final char[] ESC_CANCEL_DEF_CHAR = new char[] { ESC, '%',
			0x01 };
	// Define user-defined characters
	// ESC & y c1 c2 [x1 d1...d(y ×××× x1)]...[xk d1...d(y ×××× xk)]
	// Select bit-image mode
	// ESC * m nL nH d1...dk

	public static final char[] ESC_UNDER_LINE_OFF = new char[] { ESC, '-', 0x00 };
	public static final char[] ESC_UNDER_LINE_ON = new char[] { ESC, '-', 0x01 };

	public static final char[] ESC_DEFAULT_LINE_SP = new char[] { ESC, '2' };

	public static final char[] ESC_ENABLE_PRINTER = new char[] { ESC, '=', 0x01 };

	public static final char[] ESC_INIT = new char[] { ESC, '@' };

	public static final char[] ESC_HT_RESET = new char[] { ESC, 'D', };

	public static final char[] ESC_EM_OFF = new char[] { ESC, 'E', 0x00 };
	public static final char[] ESC_EM_ON = new char[] { ESC, 'E', 0x01 };

	public static final char[] ESC_BLOD_OFF = new char[] { ESC, 'G', 0x00 };
	public static final char[] ESC_BLOD_ON = new char[] { ESC, 'G', 0x01 };

	public static final char[] ESC_CHARSET_CHINESS = new char[] { ESC, 'R', 15 };

	public static final char[] ESC_ALIGN_LEFT = new char[] { ESC, 'a', 0x00 };
	public static final char[] ESC_ALIGN_CENTER = new char[] { ESC, 'a', 0x01 };
	public static final char[] ESC_ALIGN_RIGHT = new char[] { ESC, 'a', 0x02 };

	public static final char[] ESC_PAPER_END_SENSOR_DISABLE_ALL = new char[] {
			ESC, 'c', '3', 0x00 };
	public static final char[] ESC_PAPER_END_SENSOR_ENABLE_ALL = new char[] {
			ESC, 'c', '3', 0x0F };
	public static final char[] ESC_PAPER_END_SENSOR_ENABLE_NEAR = new char[] {
			ESC, 'c', '3', 0x01 };
	public static final char[] ESC_PAPER_END_SENSOR_ENABLE_ROLL = new char[] {
			ESC, 'c', '3', 0x04 };

	public static final char[] ESC_STOP_PRINT_SENSOR_DISABLE = new char[] {
			ESC, 'c', '4', 0x00 };
	public static final char[] ESC_STOP_PRINT_SENSOR_ANABLE = new char[] { ESC,
			'c', '4', 0x01 };

	public static final char[] ESC_PANEL_BUTTON_DISABLE = new char[] { ESC,
			'c', '5', 0x00 };
	public static final char[] ESC_PANEL_BUTTON_ENABLE = new char[] { ESC, 'c',
			'5', 0x01 };

	public static final char[] ESC_UPSIDE_OFF = new char[] { ESC, '{', 0x00 };
	public static final char[] ESC_UPSIDE_ON = new char[] { ESC, '{', 0x01 };

	public static final char[] ESC_CUT_PAPER = new char[] { GS, 'V', 0x00 };
	public static final char[] ESC_CUT_MODE = new char[] { GS, 'V', 0x00 };

	// GS IIII n
	// GS a n
	// FS 2 c1 c2 [d1...dk]

	public static final char[] ESC_TRANSMIT_PAPER_STATUS = new char[] { GS,
			'r', 0x01 };
	public static final char[] ESC_TRANSMIT_DRAWER_STATUS = new char[] { GS,
			'r', 0x02 };

	public static final char[] ESC_UNDERLINE_OFF = new char[] { FS, '-', 0x00 };
	public static final char[] ESC_UNDERLINE_ON = new char[] { FS, '-', 0x01 };

	public static final char[] ESC_CN_MODE_OFF = new char[] { FS, '.' };

	public static final char[] ESC_CN_MODE = new char[] { FS, '&' };

	public static final char[] ESC_CN_SIZE_QUADRUPLE_OFF = new char[] { FS,
			'W', 0x00 };
	public static final char[] ESC_CN_SIZE_QUADRUPLE_ON = new char[] { FS, 'W',
			0x01 };

	public static final char[] ESC_OPEN_DRAWER = new char[] { STX, 'M' };
	public static final char[] ESC_OPEN_DRAWER_US = new char[] { US, 'M' };

	public static final char[] ESC_DRAWER_RATE_9600 = new char[] { STX, 'B',
			0x00 };
	public static final char[] ESC_DRAWER_RATE_2400 = new char[] { STX, 'B',
			0x02 };

	public static char[] setPrintMode(boolean fontB, boolean both,
			boolean doubleWidth, boolean doubleHeight, boolean underLine) {
		int n = 0;
		if (fontB) {
			n |= 1;
		}
		if (both) {
			n |= 1 << 3;
		}
		if (doubleHeight) {
			n |= 1 << 4;
		}
		if (doubleWidth) {
			n |= 1 << 5;
		}
		if (underLine) {
			n |= 1 << 7;
		}
		return new char[] { ESC, '!', (char) n };
	}

	public static char[] setCharSpacing(int n) {
		n = (n > -1 || n < 256 ? n : 0);
		/**
		 * Set right-side character spacing 0 ≤ n ≤ 255
		 */
		return new char[] { ESC, ' ', (char) n };
	}

	public static char[] setLineSpacing(int n) {
		n = (n > -1 || n < 256 ? n : 24);
		return new char[] { ESC, '3', (char) n };
	}

	public static char[] cancelUserDefineCharacters(int offset) {
		if (offset < 0 || (offset + 31) > 126) {
			return new char[0];
		}
		return new char[] { ESC, '?', (char) (31 + offset) };
	}

	public static char[] setHT() {
		// TODO
		return new char[] { ESC, 'D' };
	}

	public static char[] printAndFeedPaper(int n) {
		n = (n > 255 ? 255 : n);
		n = (n < 0 ? 0 : n);
		return new char[] { ESC, 'J', (char) n };
	}

	public static char[] printAndFeedLines(int n) {
		n = (n > 255 ? 255 : n);
		n = (n < 0 ? 0 : n);
		return new char[] { ESC, 'd', (char) n };
	}

	public static char[] generatePulse(int onTime, int offTime) {
		int t2 = 255 * 2;
		int t5 = 255 * 5;
		offTime = (offTime < onTime ? onTime : offTime);
		offTime = (offTime > t5 ? t5 : offTime);
		int m = (offTime > t2 ? 1 : 0);
		int ot1 = (m == 1 ? onTime / 5 : onTime / 2);
		int ot2 = (m == 1 ? offTime / 5 : offTime / 2);
		return new char[] { ESC, 'p', (char) m, (char) ot1, (char) ot2 };
	}

	public static char[] selectCharacterCodeTable(int n) {
		return new char[] { ESC, 't', (char) n };
	}

	public static char[] printNvBitImage(int n, int m) {
		return new char[] { ESC, 'p', (char) n, (char) m };
	}

	// public static char[] setNvBitImage(BufferedImage[] images) {
	// return null;
	// }

	public static char[] testPrint(int paper, int pattern) {
		paper = (paper == 0 || paper == 1 || paper == 2 || paper == 48
				|| paper == 49 || paper == 50) ? paper : 0;
		pattern = (pattern == 1 || pattern == 2 || pattern == 3
				|| pattern == 49 || pattern == 50 || pattern == 51) ? pattern
				: 1;
		return new char[] { ESC, '(', 'A', 0x02, 0x00, (char) paper,
				(char) pattern };
	}

	public static char[] setCutMode(int n) {
		n = n % 256;
		int m = 66;
		return new char[] { GS, 'V', (char) m, (char) n };
	}

	public static char[] setMulticharCharMode(boolean doubleWidth,
			boolean doubleHeight, boolean underLine) {
		int n = 0;
		if (doubleWidth) {
			n |= 1 << 2;
		}
		if (doubleHeight) {
			n |= 1 << 3;
		}
		if (underLine) {
			n |= 1 << 7;
		}
		return new char[] { FS, '!', (char) n };
	}

	public static final char[] ESC_FONT_A = new char[] { ESC, 'M', 0x00 };
	public static final char[] ESC_FONT_B = new char[] { ESC, 'M', 0x01 };

	public static char[] getFontA() {
		return new char[] { ESC, 'M', 0x00 };
	}

	public static char[] getFontB() {
		return new char[] { ESC, 'M', 0x01 };
	}

	public static char[] getColorDefault() {
		return new char[] { ESC, 'r', 0x00 };
	}

	public static char[] getColorRed() {
		return new char[] { ESC, 'r', 0x01 };
	}

	public static char[] setDisplayRate(char n) {
		return new char[] { STX, 'B', (char) n };
	}

	public static char[] sendDisplayData(String data) {
		if (data == null || data.length() == 0) {
			return new char[0];
		}
		char[] chars = data.toCharArray();
		int len = chars.length + 4;
		char[] bs = new char[len];
		bs[0] = ESC;
		bs[1] = 'Q';
		bs[2] = 'A';
		bs[len - 1] = CR;
		for (int i = 0; i < chars.length; i++) {
			bs[i + 3] = chars[i];
		}
		return bs;
	}

	public static char[] setDisplayState(char n) {
		return new char[] { ESC, 's', (char) n };
	}

	public static void main(String[] args) throws Exception {
		// String imagePixelToPosString =
		// ImagePixelUtils.imagePixelToPosString("C:/2.bmp", 0);
		// test_1();
		// ImagePixelUtils.printImageToDots("C:/test.bmp");
		// ImagePixelUtils.imagePixelToPosString_24("C:/f.bmp", 32);
		// ImagePixelUtils.imagePixelToPosString_8("C:/test.bmp", 1);
		// System.out.println(char.SIZE);
		// System.out.println(Integer.toBinaryString(1));
		// java.text.DecimalFormat df = new java.text.DecimalFormat("00000000");
		// System.out.println(df.format(0x01));
	}
}
