package com.lyon_yan.module.printer;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.osinfo.core.webapp.epson.EpsonPosPrinterCommand;
import org.redot.clover.print.template.PrintTemplateUtils;

import org.lyon_yan.android.utils.image.BarcodeCreater;
import org.lyon_yan.android.utils.image.ImageTools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {
	private AppCompatEditText ip = null;
	private AppCompatEditText cmd = null;
	private AppCompatButton open = null;
	private AppCompatButton close = null;
	private AppCompatButton send = null;
	private AppCompatTextView response = null;
	private PrintWriter socketWriter = null;
	private DataInputStream socketReader = null;
	private Socket client = null;
	private Handler handler = null;

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
		ip.setText("192.168.1.51");
		cmd = (AppCompatEditText) findViewById(R.id.editText2);
		cmd.setText("print");
		open = (AppCompatButton) findViewById(R.id.button1);
		close = (AppCompatButton) findViewById(R.id.button2);
		send = (AppCompatButton) findViewById(R.id.button3);
		response = (AppCompatTextView) findViewById(R.id.textView1);
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
						client = new Socket();
						if (ip.getText().toString().equals("")) {

						} else {
							try {
								client.connect(new InetSocketAddress(ip
										.getText().toString(), 9100), 1000);// 创建一个
																			// socket
								handler.post(new Runnable() {

									@Override
									public void run() {
										// TODO Auto-generated method stub
										close.setVisibility(View.VISIBLE);
										open.setVisibility(View.GONE);
									}
								});
								socketWriter = new PrintWriter(
										new OutputStreamWriter(client
												.getOutputStream(), "GBK"));// 创建输入输出数据流
								socketReader = new DataInputStream(client
										.getInputStream());
								// socketWriter.write(EpsonPosPrinterCommand.FS);
								// socketWriter.write('(');
								// socketWriter.write(26);
								// socketWriter.write(0);
								// socketWriter.write(33);
								// socketWriter.write(3);
								// socketWriter.write(";;;;;;");
								// socketWriter.flush();

								socketWriter.write("已連接打印機...\n");
								socketWriter.flush();

								// response();

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
						try {
							if (socketWriter != null) {
								socketWriter.write("正在關閉打印綫程..." + "\n \n \n ");
								socketWriter.flush();
								socketWriter
										.println(PrintTemplateUtils.LINE_SPACE);
								socketWriter.flush();
								socketWriter.println(PrintTemplateUtils.CUT);
								socketWriter.flush();

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
								socketWriter.flush();

								// response();
								socketWriter.close();
								socketReader.close();
							}
							if (client != null) {
								if (!client.isClosed()) {
									client.close();
								}
							}
							handler.post(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									open.setVisibility(View.VISIBLE);
									close.setVisibility(View.GONE);
								}
							});
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
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
						if (socketWriter != null) {
							if (cmd.getText().toString().equals("print")) {
								print();
							} else {
								socketWriter.println(cmd.getText().toString());
								socketWriter.flush();
								socketWriter.write(PrintTemplateUtils.VOICE);
								socketWriter.flush();
							}
							// response();
						}
					}
				}.start();
			}
		});
	}

	//
	// private void response() {
	// // TODO Auto-generated method stub
	//
	// handler.post(new Runnable() {
	//
	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	// String result = "";
	// try {
	// while (socketReader.readBoolean()) {
	// result += socketReader.readLine();
	// }
	// response.setText(result);
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// });
	// }

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
		// 打印条形码
		// socketWriter.write(0x1d);
		// socketWriter.write((char)'k');
		// // socketWriter.write(120);
		// socketWriter.write(67);
		// socketWriter.write(13);
		// socketWriter.write("2013062900001");
		// //打印二维码
		// socketWriter.write(0x1d);
		// socketWriter.write(0x28);
		// socketWriter.write(0x6b);
		// socketWriter.write(43);
		// socketWriter.write(0);
		// socketWriter.write(49);
		// socketWriter.write(80);
		// socketWriter.write(48);
		// socketWriter.write("abcdefg");
		// socketWriter.flush();
		//
		// socketWriter.write(0x1d);
		// socketWriter.write(0x28);
		// socketWriter.write(0x6b);
		// socketWriter.write(43);
		// socketWriter.write(0);
		// socketWriter.write(49);
		// socketWriter.write(80);
		// socketWriter.write(48);

		socketWriter.write(PrintTemplateUtils.INIT);
		socketWriter.flush();
		char[] c0 = new char[] { EpsonPosPrinterCommand.GS, 0x28, 0x6b, 3, 0,
				49, 67, 3 };
		socketWriter.write(c0);
		socketWriter.flush();
		char[] c01 = new char[] { EpsonPosPrinterCommand.GS, 0x28, 0x6b, 3, 0,
				49, 69, 48 };
		socketWriter.write(c01);
		socketWriter.flush();

		char[] c1 = new char[] { EpsonPosPrinterCommand.GS, 0x28, 0x6b, 14, 0,
				49, 80, 48 };
		socketWriter.write(c1);
		socketWriter.write("abcdfhjjdf");
		socketWriter.flush();
		char[] c2 = new char[] { EpsonPosPrinterCommand.GS, 0x28, 0x6b, 14, 0,
				49, 81, 48 };
		socketWriter.write(c2);
		// socketWriter.print(EpsonPosPrinterCommand.printAndFeedLines(5));
		socketWriter.flush();

		// 打印二维码
		// Bitmap bmp = ImageTools.toGrayscale(BitmapFactory
		// .decodeStream(getResources().openRawResource(
		// R.drawable.ic_launcher)));
		// Bitmap bmp=BarcodeCreater.creatBarcode(getBaseContext(),
		// "588655122333", 512, 512, false, 2);
		//
		// char[] data = new char[] { 0x1B, 0x33, 0x00 };
		// socketWriter.write(data);
		// data[0] = (byte) 0x00;
		// data[1] = (byte) 0x00;
		// data[2] = (byte) 0x00; // 重置参数
		//
		// int pixelColor;
		//
		// // ESC * m nL nH 点阵图
		// char[] escBmp = new char[] { 0x1B, 0x2A, 0x00, 0x00, 0x00 };
		//
		// escBmp[2] = (byte) 0x21;
		//
		// // nL, nH
		// escBmp[3] = (char) (bmp.getWidth() % 256);
		// escBmp[4] = (char) (bmp.getWidth() / 256);
		//
		// // 每行进行打印
		// for (int i = 0; i < bmp.getHeight() / 24 + 1; i++) {
		// socketWriter.write(escBmp);
		//
		// for (int j = 0; j < bmp.getWidth(); j++) {
		// for (int k = 0; k < 24; k++) {
		// if (((i * 24) + k) < bmp.getHeight()) {
		// pixelColor = bmp.getPixel(j, (i * 24) + k);
		// if (pixelColor != -1) {
		// data[k / 8] += (byte) (128 >> (k % 8));
		// }
		// }
		// }
		//
		// socketWriter.write(data);
		// // 重置参数
		// data[0] = (byte) 0x00;
		// data[1] = (byte) 0x00;
		// data[2] = (byte) 0x00;
		// }
		// // 换行
		// char[] byte_send1 = new char[2];
		// byte_send1[0] = 0x0d;
		// byte_send1[1] = 0x0a;
		// socketWriter.write(byte_send1);
		// }
		// socketWriter.flush();
	}

}
