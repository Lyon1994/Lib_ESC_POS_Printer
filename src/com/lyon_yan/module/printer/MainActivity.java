package com.lyon_yan.module.printer;

import java.io.IOException;

import org.lyon_yan.android.utils.esc_pos.EscPosSupport;
import org.lyon_yan.android.utils.esc_pos.EscPosSupport.COMMAND;
import org.lyon_yan.android.utils.image.QRFactory;
import org.redot.clover.print.template.PrintTemplateUtils;

import com.google.zxing.WriterException;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * test class
 * 
 * @author Lyon_Yan <br/>
 *         <b>time</b>: 2015年9月1日 下午1:57:24
 */
public class MainActivity extends AppCompatActivity {
	private AppCompatEditText ip = null;
	private AppCompatEditText cmd = null;
	private AppCompatButton open = null;
	private AppCompatButton close = null;
	private AppCompatButton send = null;
	private Handler handler = null;
	private EscPosSupport escPosSupport = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initListener();
	}

	private void initView() {
		// TODO Auto-generated method stub
		ip = (AppCompatEditText) findViewById(R.id.editText1);
		ip.setText("192.168.1.52");
		cmd = (AppCompatEditText) findViewById(R.id.editText2);
		cmd.setText("print");
		open = (AppCompatButton) findViewById(R.id.button1);
		close = (AppCompatButton) findViewById(R.id.button2);
		send = (AppCompatButton) findViewById(R.id.button3);
	}

	private void initListener() {
		// TODO Auto-generated method stub
		handler = new Handler(getMainLooper());
		close.setVisibility(View.GONE);
		open.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						super.run();
						if (ip.getText().toString().equals("")) {

						} else {
							try {
								if (escPosSupport != null) {
									escPosSupport.destory();
									escPosSupport = null;
								}
								escPosSupport = new EscPosSupport(ip.getText()
										.toString(), 9100, 1000);
								handler.post(new Runnable() {
									@Override
									public void run() {
										// TODO Auto-generated method stub
										close.setVisibility(View.VISIBLE);
										open.setVisibility(View.GONE);
									}
								});
								escPosSupport.write("已連接打印機...\n");
								escPosSupport.flush();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}.start();
			}
		});
		close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						super.run();
						if (escPosSupport != null) {
							escPosSupport.destory();
						}
						handler.post(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								open.setVisibility(View.VISIBLE);
								close.setVisibility(View.GONE);
							}
						});
					}
				}.start();
			}
		});
		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						super.run();
						if (escPosSupport != null) {
							if (cmd.getText().toString().equals("print")) {
								print();
							} else {
								escPosSupport.println(cmd.getText().toString());
								escPosSupport.flush();
								escPosSupport.process(PrintTemplateUtils.VOICE);
								escPosSupport.flush();
							}
						}
					}
				}.start();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void print() {
		escPosSupport.println("\r\n");
		try {
			escPosSupport.printBitmap(QRFactory.createQRCode(
					"http://www.baidu.com", 500));
			escPosSupport.println("\r\n");
			escPosSupport.println("\r\n");
			escPosSupport.println("\r\n");
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		escPosSupport.printOneCodeByEAN13("2013062900001");

		escPosSupport.println("\r\n");
		escPosSupport.println("\r\n");
		escPosSupport.println("\r\n");
		escPosSupport.println("\r\n");
		escPosSupport.process(COMMAND.VOICE);
		escPosSupport.flush();
		escPosSupport.process(COMMAND.CUT);
		escPosSupport.flush();
	}

	/**
 * 
 */
	// public void print() {
	// // 打印条形码
	// // socketWriter.write(0x1d);
	// // socketWriter.write((char)'k');
	// // // socketWriter.write(120);
	// // socketWriter.write(67);
	// // socketWriter.write(13);
	// // socketWriter.write("2013062900001");
	// // //打印二维码
	// // socketWriter.write(0x1d);
	// // socketWriter.write(0x28);
	// // socketWriter.write(0x6b);
	// // socketWriter.write(43);
	// // socketWriter.write(0);
	// // socketWriter.write(49);
	// // socketWriter.write(80);
	// // socketWriter.write(48);
	// // socketWriter.write("abcdefg");
	// // socketWriter.flush();
	// //
	// // socketWriter.write(0x1d);
	// // socketWriter.write(0x28);
	// // socketWriter.write(0x6b);
	// // socketWriter.write(43);
	// // socketWriter.write(0);
	// // socketWriter.write(49);
	// // socketWriter.write(80);
	// // socketWriter.write(48);
	//
	// socketWriter.write(PrintTemplateUtils.INIT);
	// socketWriter.flush();
	// char[] c0 = new char[] { EpsonPosPrinterCommand.GS, 0x28, 0x6b, 3, 0,
	// 49, 67, 3 };
	// socketWriter.write(c0);
	// socketWriter.flush();
	// char[] c01 = new char[] { EpsonPosPrinterCommand.GS, 0x28, 0x6b, 3, 0,
	// 49, 69, 48 };
	// socketWriter.write(c01);
	// socketWriter.flush();
	//
	// char[] c1 = new char[] { EpsonPosPrinterCommand.GS, 0x28, 0x6b, 14, 0,
	// 49, 80, 48 };
	// socketWriter.write(c1);
	// socketWriter.write("abcdfhjjdf");
	// socketWriter.flush();
	// char[] c2 = new char[] { EpsonPosPrinterCommand.GS, 0x28, 0x6b, 14, 0,
	// 49, 81, 48 };
	// socketWriter.write(c2);
	// // socketWriter.print(EpsonPosPrinterCommand.printAndFeedLines(5));
	// socketWriter.flush();
	//
	// // 打印二维码
	// // Bitmap bmp = ImageTools.toGrayscale(BitmapFactory
	// // .decodeStream(getResources().openRawResource(
	// // R.drawable.ic_launcher)));
	// // Bitmap bmp=BarcodeCreater.creatBarcode(getBaseContext(),
	// // "588655122333", 512, 512, false, 2);
	// //
	// // char[] data = new char[] { 0x1B, 0x33, 0x00 };
	// // socketWriter.write(data);
	// // data[0] = (byte) 0x00;
	// // data[1] = (byte) 0x00;
	// // data[2] = (byte) 0x00; // 重置参数
	// //
	// // int pixelColor;
	// //
	// // // ESC * m nL nH 点阵图
	// // char[] escBmp = new char[] { 0x1B, 0x2A, 0x00, 0x00, 0x00 };
	// //
	// // escBmp[2] = (byte) 0x21;
	// //
	// // // nL, nH
	// // escBmp[3] = (char) (bmp.getWidth() % 256);
	// // escBmp[4] = (char) (bmp.getWidth() / 256);
	// //
	// // // 每行进行打印
	// // for (int i = 0; i < bmp.getHeight() / 24 + 1; i++) {
	// // socketWriter.write(escBmp);
	// //
	// // for (int j = 0; j < bmp.getWidth(); j++) {
	// // for (int k = 0; k < 24; k++) {
	// // if (((i * 24) + k) < bmp.getHeight()) {
	// // pixelColor = bmp.getPixel(j, (i * 24) + k);
	// // if (pixelColor != -1) {
	// // data[k / 8] += (byte) (128 >> (k % 8));
	// // }
	// // }
	// // }
	// //
	// // socketWriter.write(data);
	// // // 重置参数
	// // data[0] = (byte) 0x00;
	// // data[1] = (byte) 0x00;
	// // data[2] = (byte) 0x00;
	// // }
	// // // 换行
	// // char[] byte_send1 = new char[2];
	// // byte_send1[0] = 0x0d;
	// // byte_send1[1] = 0x0a;
	// // socketWriter.write(byte_send1);
	// // }
	// // socketWriter.flush();
	// }
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		if (escPosSupport != null) {
			escPosSupport.destory();
			escPosSupport = null;
			System.gc();
			System.exit(0);
		}
		super.finish();
	}

}
