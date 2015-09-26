/*
 * MainFrm.java
 *
 * Created on __DATE__, __TIME__
 */

package com.Zs.View;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import sun.java2d.pipe.DrawImage;
import sun.rmi.transport.LiveRef;

import ZS.JLable.Camera.CameraCapture.IgetBufferedImag;
import ZS.Net.CarmeraTherad;
import ZS.Net.NetThread;
import ZS.Util.Bgp.BackgroundPanel;
import ZS.Util.Time.TestDate;
import ZsUtil.Beep;

/**
 *
 * @author  __USER__
 */
public class MainFrm extends javax.swing.JFrame implements IgetBufferedImag {
	//******************报警***********************//
	public Beep beep = new Beep();
	//***********************NET ****************/
	private BufferedImage image;
	private VideoThread videoThread = null;
	private String imageURL = "http://192.168.8.1:8083/?action=snapshot";
	public int linkStation = 0;// 0 断开 1 正在连接 2以连接
	private NetThread netThread = null;
	public CarmeraTherad carmeraTherad;
	//******************************************//
	private BackgroundPanel bgp;
	private String Port;
	private Integer ComNumber = 1;
	private static byte[] byt = { 0x10, 0x00, 0x11 };//用来发送命令！
	private static ZS.JLable.Camera.CameraCapture carCameraCapture = new ZS.JLable.Camera.CameraCapture(); //声明全局变量CameraCapture，BufferedImage
	private BufferedImage imgForSave = null;
	private int cameraFlag = 0;
	//建立一个线程MyThread（它也是一个类），在这个线程中是获得视频流的方法
	private MyThread mThred = new MyThread();/*把类MyThread实体化变成对象，
																				即对象是mThred（通常首字母改成小写）*/

	public class MyThread extends Thread { /*定义类MyThread，因为要使用到对话框的类jLabel1，所以不能
											                             另建一个.java文件来定义MyThread，
											                             而CameraCapture，TestDate则可以*/
		public void run() {
			try {
				carCameraCapture.addIgetBufferedImag(new IgetBufferedImag() {
					@Override
					//重写串口函数
					public void getGrabImage(BufferedImage bufferedImage) {
						// TODO Auto-generated method stub
						jLabel2.setIcon(new ImageIcon(bufferedImage));//从CameraCapture传过来数据
						imgForSave = bufferedImage;
					}
				});
				carCameraCapture.creatCarmera();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/** Creates new form MainFrm 
	 * @throws MalformedURLException */
	public MainFrm() throws MalformedURLException {
		//改变系统默认字体
		Font font = new Font("Dialog", Font.PLAIN, 12);
		java.util.Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource) {
				UIManager.put(key, font);
			}
		}

		initComponents();//所有控件等类都付处置，记住此初始化函数一定要放在第一位，否则出现空指针现象！
		initBackground(); //背景初始化一般放第二位！
		this.SearchingTabbedPane.addTab("简单检索", new SearchingSimplePanel());
		this.SearchingTabbedPane.addTab("专家检索", new SearchingExpertPanel());
		videoThread = new VideoThread();
	}

	//第1步：自己写一个初始化装背景的容器函数；第2步，单独写一个类BackgroundPanel；
	private void initBackground() {
		bgp = new BackgroundPanel((new ImageIcon("images\\9.jpg")).getImage());//引入背景图片
		//bgp.setBounds(0,0,400,300);
		this.jPanelNB.add(bgp);
	}

	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jPanelNB = new javax.swing.JPanel();
		jLabel2 = new javax.swing.JLabel();
		SearchingTabbedPane = new javax.swing.JTabbedPane();
		linkStationLabel = new javax.swing.JLabel();
		jScrollPane1 = new javax.swing.JScrollPane();
		mainTextArea = new javax.swing.JTextArea();
		linkjLabel = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		openVideoJlibel = new javax.swing.JLabel();
		jietujLabel = new javax.swing.JLabel();
		doorStationImageLabel = new javax.swing.JLabel();
		temperLabel = new javax.swing.JLabel();
		cpuTemLabel = new javax.swing.JLabel();
		doorStationLabel = new javax.swing.JLabel();
		outsideStationLabel = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		linkStationLabel.setFont(new java.awt.Font("微软雅黑", 1, 14));
		linkStationLabel.setText("\u8fde\u63a5\u72b6\u6001:\u672a\u8fde\u63a5");

		mainTextArea.setColumns(20);
		mainTextArea.setRows(5);
		jScrollPane1.setViewportView(mainTextArea);

		linkjLabel.setIcon(new javax.swing.ImageIcon(
				"F:\\MyEclipse2\\Zs_Fender_Client\\images\\wifi-NO.png")); // NOI18N
		linkjLabel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				linkjLabelMouseClicked(evt);
			}

			public void mouseEntered(java.awt.event.MouseEvent evt) {
				linkjLabelMouseEntered(evt);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				linkjLabelMouseExited(evt);
			}
		});

		jLabel5.setIcon(new javax.swing.ImageIcon(
				"F:\\MyEclipse2\\Zs_Fender_Client\\images\\admini.png")); // NOI18N
		jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jLabel5MouseClicked(evt);
			}

			public void mouseEntered(java.awt.event.MouseEvent evt) {
				jLabel5MouseEntered(evt);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				jLabel5MouseExited(evt);
			}
		});

		openVideoJlibel.setIcon(new javax.swing.ImageIcon(
				"F:\\MyEclipse2\\images\\eye-play.png")); // NOI18N
		openVideoJlibel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				openVideoJlibelMouseClicked(evt);
			}

			public void mouseEntered(java.awt.event.MouseEvent evt) {
				openVideoJlibelMouseEntered(evt);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				openVideoJlibelMouseExited(evt);
			}
		});

		jietujLabel.setIcon(new javax.swing.ImageIcon(
				"F:\\MyEclipse2\\Zs_Fender_Client\\images\\camera.png")); // NOI18N
		jietujLabel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				try {
					jietujLabelMouseClicked(evt);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			public void mouseEntered(java.awt.event.MouseEvent evt) {
				jietujLabelMouseEntered(evt);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				jietujLabelMouseExited(evt);
			}

			public void mousePressed(java.awt.event.MouseEvent evt) {
				jietujLabelMousePressed(evt);
			}

			public void mouseReleased(java.awt.event.MouseEvent evt) {
				jietujLabelMouseReleased(evt);
			}
		});

		doorStationImageLabel.setIcon(new javax.swing.ImageIcon(
				"F:\\MyEclipse2\\Zs_Fender_Client\\images\\door.png")); // NOI18N

		temperLabel.setFont(new java.awt.Font("微软雅黑", 1, 14));
		temperLabel.setText("\u5ba4\u6e29");

		cpuTemLabel.setFont(new java.awt.Font("微软雅黑", 1, 14));
		cpuTemLabel.setText("CPU\u6e29\u5ea6");

		doorStationLabel.setFont(new java.awt.Font("微软雅黑", 1, 14));
		doorStationLabel.setText("\u95e8\u72b6\u6001");

		outsideStationLabel.setFont(new java.awt.Font("微软雅黑", 1, 14));
		outsideStationLabel.setText("\u95e8\u5916\u60c5\u51b5");

		javax.swing.GroupLayout jPanelNBLayout = new javax.swing.GroupLayout(
				jPanelNB);
		jPanelNB.setLayout(jPanelNBLayout);
		jPanelNBLayout
				.setHorizontalGroup(jPanelNBLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanelNBLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanelNBLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanelNBLayout
																		.createSequentialGroup()
																		.addGroup(
																				jPanelNBLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jietujLabel)
																						.addComponent(
																								openVideoJlibel))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				jPanelNBLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								linkjLabel,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								jLabel5))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				89,
																				Short.MAX_VALUE)
																		.addComponent(
																				doorStationImageLabel)
																		.addGap(29,
																				29,
																				29)
																		.addGroup(
																				jPanelNBLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								temperLabel)
																						.addComponent(
																								cpuTemLabel)
																						.addComponent(
																								doorStationLabel)
																						.addComponent(
																								outsideStationLabel)
																						.addComponent(
																								linkStationLabel))
																		.addGap(118,
																				118,
																				118))
														.addGroup(
																jPanelNBLayout
																		.createSequentialGroup()
																		.addComponent(
																				jLabel2,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				609,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
										.addGroup(
												jPanelNBLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																SearchingTabbedPane,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																491,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jScrollPane1,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																492,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(146, 146, 146)));
		jPanelNBLayout
				.setVerticalGroup(jPanelNBLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanelNBLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanelNBLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanelNBLayout
																		.createSequentialGroup()
																		.addComponent(
																				jLabel2,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				401,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				jPanelNBLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addGroup(
																								jPanelNBLayout
																										.createSequentialGroup()
																										.addGap(16,
																												16,
																												16)
																										.addGroup(
																												jPanelNBLayout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.TRAILING,
																																false)
																														.addComponent(
																																linkjLabel,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																Short.MAX_VALUE)
																														.addComponent(
																																openVideoJlibel,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																Short.MAX_VALUE))
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addGroup(
																												jPanelNBLayout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.LEADING)
																														.addComponent(
																																jietujLabel)
																														.addComponent(
																																jLabel5)))
																						.addGroup(
																								jPanelNBLayout
																										.createSequentialGroup()
																										.addGap(92,
																												92,
																												92)
																										.addComponent(
																												temperLabel)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												cpuTemLabel)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												doorStationLabel)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												outsideStationLabel)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												linkStationLabel))
																						.addGroup(
																								jPanelNBLayout
																										.createSequentialGroup()
																										.addGap(102,
																												102,
																												102)
																										.addComponent(
																												doorStationImageLabel))))
														.addGroup(
																jPanelNBLayout
																		.createSequentialGroup()
																		.addComponent(
																				SearchingTabbedPane,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				510,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				jScrollPane1,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				120,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addComponent(jPanelNB,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addComponent(jPanelNB,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {
		SignFrm a = new SignFrm();
		a.setVisible(true);
		a.setMain(this);
		this.setEnabled(false);
	}

	private void jietujLabelMouseReleased(java.awt.event.MouseEvent evt) {
		jietujLabel.setIcon(new ImageIcon("images\\camera.png"));
	}

	private void jietujLabelMousePressed(java.awt.event.MouseEvent evt) {
		jietujLabel.setIcon(new ImageIcon("images\\camera-yes.png"));
	}

	private void jietujLabelMouseExited(java.awt.event.MouseEvent evt) {
		jietujLabel.setIcon(new ImageIcon("images\\camera.png"));
	}

	private void jietujLabelMouseEntered(java.awt.event.MouseEvent evt) {
		jietujLabel.setIcon(new ImageIcon("images\\camera-on.png"));
	}

	private void jLabel5MouseExited(java.awt.event.MouseEvent evt) {
		jLabel5.setIcon(new ImageIcon("images\\admini.png"));
	}

	private void jLabel5MouseEntered(java.awt.event.MouseEvent evt) {
		jLabel5.setIcon(new ImageIcon("images\\admini-on.png"));
	}

	private void linkjLabelMouseExited(java.awt.event.MouseEvent evt) {
		if (linkStation == 0) {
			linkjLabel.setIcon(new ImageIcon("images\\wifi-NO.png"));
		}
		if (linkStation == 2) {
			linkjLabel.setIcon(new ImageIcon("images\\wifi-yes.png"));
		}
	}

	private void linkjLabelMouseEntered(java.awt.event.MouseEvent evt) {
		if (linkStation == 0) {
			linkjLabel.setIcon(new ImageIcon("images\\连接.png"));
		}
		if (linkStation == 2) {
			linkjLabel.setIcon(new ImageIcon("images\\断开.png"));
		}
	}

	private void openVideoJlibelMouseExited(java.awt.event.MouseEvent evt) {
		if (cameraFlag == 1) {
			openVideoJlibel.setIcon(new ImageIcon("images\\eye-pause.png"));
		}
		if (cameraFlag == 0 || cameraFlag == 2) {
			openVideoJlibel.setIcon(new ImageIcon("images\\eye-play.png"));
		}

	}

	private void openVideoJlibelMouseEntered(java.awt.event.MouseEvent evt) {
		if (cameraFlag == 1) {
			openVideoJlibel.setIcon(new ImageIcon("images\\eye-pause-on.png"));
		}
		if (cameraFlag == 0 || cameraFlag == 2) {
			openVideoJlibel.setIcon(new ImageIcon("images\\eye-play-on.png"));
		}

	}

	private void jietujLabelMouseClicked(java.awt.event.MouseEvent evt)
			throws IOException {
		saveImage();
	}

	/**
	 * 保存截图
	 * @throws IOException 
	 */
	public void saveImage() throws IOException {
		if (cameraFlag == 1)//如果监控时开着的
		{
			File file2 = new File("eshark");
			if (file2.exists()) {
				System.out.println("目录已经存在不需要创建！！");
			} else {
				//如果要创建的多级目录不存在才需要创建。
				file2.mkdirs();
			}
			ImageIO.write(image, "jpg", new File("eshark/" + "eshark监控"
					+ TestDate.getTime() + ".jpg"));
			//			setMainTextArea("截图成功！");

		} else {
			//			setMainTextArea("请您先打开监控，在截图！");
		}

	}

	private void openVideoJlibelMouseClicked(java.awt.event.MouseEvent evt) {
		openVideo();
	}

	private void linkjLabelMouseClicked(java.awt.event.MouseEvent evt) {
		linkNetClick();
	}

	private void linkButtonActionPerformed(java.awt.event.ActionEvent evt) {
		linkNetClick();
	}

	private void linkNetClick() {
		switch (linkStation) {
		case 0:
			netThread = new NetThread(this, carCameraCapture);
			netThread.start();
			linkStation = 1;
			setlinkStationLabelText("连接状态：正在连接");
			linkjLabel.setIcon(new ImageIcon("images\\wifi-middle.png"));
			break;
		case 1:
			netThread.interrupt();
			linkStation = 0;
			setlinkStationLabelText("连接状态：断开");
			linkjLabel.setIcon(new ImageIcon("images\\wifi-NO.png"));
			break;
		case 2:
			netThread.interrupt();
			linkStation = 0;
			setlinkStationLabelText("连接状态：断开");
			linkjLabel.setIcon(new ImageIcon("images\\wifi-NO.png"));
			break;
		default:
			break;
		}
	}

	public void openVideo() {
		switch (cameraFlag) {
		case 0:
			cameraFlag = 1;
			openVideoJlibel.setIcon(new ImageIcon("images\\eye-pause.png"));
			//			btnCamera.setText("暂停监控");
			videoThread.start();
			//			netThread.send("to phone&视频已打开");

			break;
		case 1:
			openVideoJlibel.setIcon(new ImageIcon("images\\eye-play.png"));
			//			btnCamera.setText("重启监控");
			cameraFlag = 2;
			videoThread.suspend();
			//			netThread.send("to phone&视频已关闭");
			this.jLabel2.setIcon(null);

			break;
		case 2:
			openVideoJlibel.setIcon(new ImageIcon("images\\eye-pause.png"));
			//			btnCamera.setText("暂停监控");
			cameraFlag = 1;
			videoThread.resume();
			//			netThread.send("to phone&视频已打开");

			break;

		default:
			break;
		}
	}

	public void openCarmera() {
		switch (cameraFlag) {
		case 0:
			cameraFlag = 1;
			//			btnCamera.setText("暂停监控");
			mThred.start();
			netThread.send("to phone&视频已打开");

			break;
		case 1:
			//			btnCamera.setText("重启监控");
			cameraFlag = 2;
			mThred.suspend();
			netThread.send("to phone&视频已关闭");
			this.jLabel2.setIcon(null);

			break;
		case 2:
			//			btnCamera.setText("暂停监控");
			cameraFlag = 1;
			mThred.resume();
			netThread.send("to phone&视频已打开");

			break;

		default:
			break;
		}

	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new MainFrm().setVisible(true);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JTabbedPane SearchingTabbedPane;
	private javax.swing.JLabel cpuTemLabel;
	private javax.swing.JLabel doorStationImageLabel;
	private javax.swing.JLabel doorStationLabel;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JPanel jPanelNB;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JLabel jietujLabel;
	private javax.swing.JLabel linkStationLabel;
	private javax.swing.JLabel linkjLabel;
	private javax.swing.JTextArea mainTextArea;
	private javax.swing.JLabel openVideoJlibel;
	private javax.swing.JLabel outsideStationLabel;
	private javax.swing.JLabel temperLabel;

	// End of variables declaration//GEN-END:variables

	@Override
	public void getGrabImage(BufferedImage bufferedImage) {
		// TODO Auto-generated method stub

	}

	//******************NET****************//
	public void setLinkJlabelIcon(Icon icon) {
		//		linkButton.setText(str);
		linkjLabel.setIcon(icon);
	}

	public void setlinkStationLabelText(String str) {
		linkStationLabel.setText(str);
	}

	public int getCameraFlag() {
		return cameraFlag;
	}

	public void setCameraFlag(int cameraFlag) {
		this.cameraFlag = cameraFlag;
	}

	public void catchCarmera() {
		try {
			ImageIO.write(imgForSave, "jpg", new File("D:\\carmera.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //调用了TestDate类中的getTime()函数
		System.out.println("截图成功  for send");
	}

	//**************Public 函数*************//
	public void setMainTextArea(String str) {
		mainTextArea.setText(mainTextArea.getText() + TestDate.getTime()
				+ "----->" + str + "\n");
	}

	class VideoThread extends Thread {
		URL url = new URL(imageURL);

		public VideoThread() throws MalformedURLException {
		}

		@Override
		public void run() {
			while (true) {
				InputStream inputStream;
				try {
					inputStream = url.openConnection().getInputStream();
					image = ImageIO.read(inputStream);//对得到的bufferimage进行缩放处理
					BufferedImage newImage = new BufferedImage(
							image.getWidth(), image.getHeight(),
							BufferedImage.TYPE_INT_BGR);
					Graphics graphics = newImage.createGraphics();
					graphics.drawImage(image, 0, 0, image.getWidth(),
							image.getHeight(), null);
					jLabel2.setIcon(new ImageIcon(newImage));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 门状态 
	 * 1 
	 * @param i
	 */
	public void setdoorStation(int i) {
		switch (i) {
		case 0:
			doorStationLabel.setText("门状态:关着呢");
			doorStationImageLabel.setIcon(new ImageIcon(
					"images\\door_closed.png"));
			break;
		case 1:
			doorStationLabel.setText("门状态:虚掩着，未警戒");
			doorStationImageLabel.setIcon(new ImageIcon(
					"images\\door_almose_closed.png"));
			break;
		case 2:
			doorStationLabel.setText("门状态: 开着呢");
			doorStationImageLabel.setIcon(new ImageIcon("images\\door.png"));
			break;
		case 3:
			doorStationLabel.setText("门状态:正在报警！");
			break;
		case 4:
			doorStationLabel.setText("门状态: 虚掩着，警戒开启");
			doorStationImageLabel.setIcon(new ImageIcon(
					"images\\door_almose_closed.png"));
			break;

		default:
			break;
		}
	}

	public void setCpuLabel(String temp) {
		cpuTemLabel.setText("CPU温度:" + temp + "°");
	}

	public void setTemperLabel(String tmp) {
		int temp = Integer.valueOf(tmp);

		temperLabel.setText("室温:" + temp / 10.0 + "°");
	}

	public void setOutsideStationLabel(String tmp) {
		if (Integer.valueOf(tmp) == 1) {
			outsideStationLabel.setText("门外状况：有人");

		} else {
			outsideStationLabel.setText("门外状况：没人");
		}
	}

}