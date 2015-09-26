package ZS.Net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;

import ZS.JLable.Camera.CameraCapture;
import ZS.Util.Time.TestDate;

import com.Zs.DbUtil.DbUtil;
import com.Zs.View.MainFrm;
/**
 * 用来连接服务器
 * @author AL
 *
 */
public class NetThread extends Thread{
	public static final  String IP = "127.0.0.1";
	private String str;
	private Socket socket;
	private MainFrm mainFrm;
	private PrintWriter printWriter;
	private CameraCapture cameraCapture;
	private BreathsThread breathsThread = new BreathsThread();
	private boolean haveMan = false;//是否有人在门外
	@Override
	public void run() {
		try {
			breathsThread.start();
			Socket socket = new Socket(IP,8089);
			mainFrm.setLinkJlabelIcon(new ImageIcon("images\\wifi-yes.png"));
			mainFrm.setlinkStationLabelText("连接状态：已连接");
			printWriter = new PrintWriter(socket.getOutputStream(),true);
			printWriter.println("computer client is connected");
			InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			while(true)
			{
				str=null;
				str = bufferedReader.readLine();
				System.out.println("str");
				if(str!=null)
				{
					
					String str1=null;
					str1 = str.split("&")[0];//取得接受数据的第一位
					if(str.endsWith("sever is connected"))
					{
						continue;
					}
					if(str1.equals("record")&&str.length()>3)//如果是记录用的数据
					{
						String[] strTemp={str.split("&")[1],str.split("&")[2],str.split("&")[3]};
						mainFrm.setMainTextArea(DbUtil.changeNetRecordToStr(strTemp)+" "+TestDate.getTime());
						continue;//跳过本次循环
					}
					if(str.equals("开始报警")||str.equals("start alarm"))
					{
						mainFrm.beep.start();
						continue;//跳过本次循环
					}else if(str.equals("停止报警")||str.equals("stop alarm"))
					{
						mainFrm.beep.stop();
						continue;//跳过本次循环
					}
					
					updateStation(str);
					System.out.println("Client computer get --->"+str);
				}else
				{
					break;
				}
			}
			
		} catch (UnknownHostException e) {
			mainFrm.setLinkJlabelIcon(new ImageIcon("images\\wifi-yes.png"));
			mainFrm.setlinkStationLabelText("连接状态：网络错误");
			socket = null;
			this.interrupt();
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	private void updateStation(String str2) {
		if(str!=null)
		{
			char mesg[] =str.toCharArray();
			String mesgStr = null;
			for(int i=0;i<mesg.length;i++)
			{
				switch (mesg[i]) {
				case 's'://门状态
					if((i+1)<=mesg.length)//判断是否会越界
					{
						mesgStr  = mesg[i+1]+"";
						System.out.println("门状态:"+mesgStr);
						mainFrm.setdoorStation( Integer.valueOf(mesgStr) );//设置门状态
					}
					break;
				case 'T'://CPU温度
					if(i+2<=mesg.length)
					{
						mesgStr = mesg[i+1]+""+mesg[i+2]+"";
						System.out.println("CPU温度："+mesgStr);
						mainFrm.setCpuLabel(mesgStr);
					}
					break;
				case 't'://室温
					if(i+4<=mesg.length)
					{
						mesgStr = mesg[i+1]+""+mesg[i+2]+""+mesg[i+3]+""+mesg[i+4]+"";
						System.out.println("室温："+mesgStr);
						mainFrm.setTemperLabel(mesgStr);
					}
					break;
				case 'o'://操作
					if((i+2)<=mesg.length)//判断是否会越界
					{
						mesgStr = mesg[i+1]+ (mesg[i+2]+"");
						System.out.println("操作编号:"+mesgStr);
						updateTextArea(mesgStr);
					}
					break;
				case 'h'://人体感应触发状态
					if(i+1<=mesg.length)
					{
						mesgStr = mesg[i+1]+"";
						System.out.println("门外状态:"+mesgStr);
						if(Integer.valueOf(mesgStr)==1)
						{
							haveMan = true;
						}
						else
						{
							haveMan = false;
						}
//						System.out.println("室温："+mesgStr);
						mainFrm.setOutsideStationLabel(mesgStr);
					}
					break;
				default:
					break;
				}
			}
		}else
		{
			
		}
		
	}
	private void updateTextArea(String mesgStr) {
		switch ( Integer.valueOf(mesgStr) ) {
		case 22://有人按门铃
			mainFrm.setMainTextArea("有人按门铃！");
			break;
		case 20://陌生人按指纹
			mainFrm.setMainTextArea("无法识别指纹！");
			break;
		case 21://亲戚按指纹
			mainFrm.setMainTextArea("您的亲戚马敬川来了！");
			break;	
		case 81://赵老板用指纹开门
			mainFrm.setMainTextArea("主人赵思晨按指纹开门.");
			break;	
		case 82://叶健用指纹开门
			mainFrm.setMainTextArea("主人叶健按指纹开门");
			break;	
		case 80://钥匙开门
			mainFrm.setMainTextArea("主人用钥匙开门");
			break;	
		case 41://蓝牙开门
			mainFrm.setMainTextArea("主人通过手机蓝牙开门");
			break;
		case 40://蓝牙关门
			mainFrm.setMainTextArea("主人通过手机蓝牙关门");
			break;
		case 43://蓝牙手动打开报警
			mainFrm.setMainTextArea("主人通过手机蓝牙手动打开报警！！");
			break;
		case 44://蓝牙手动关闭报警
			mainFrm.setMainTextArea("主人通过手机蓝牙手动关闭报警！");
			break;
		case 31://WIFI开门
			mainFrm.setMainTextArea("主人通过网络用手机开门");
			break;
		case 30://WIFI关门
			mainFrm.setMainTextArea("主人通过网络用手机关门");
			break;
		case 33://WIFi 报警
			mainFrm.setMainTextArea("主人通过网络用手机手动报警！！");
			break;
		case 34://WIFI关闭报警
			mainFrm.setMainTextArea("主人通过网络用手机手动关闭报警！");
			break;
		case 51://短信开门
			mainFrm.setMainTextArea("主人通过短信开门");
			break;
		case 50://短信关门
			mainFrm.setMainTextArea("主人通过短信关门");
			break;
		default:
			break;
			
		}
		
	}
	public void send(String str) {
		printWriter.println(str);
	}
	
	public NetThread(MainFrm mainFrm,CameraCapture cameraCapture) 
	{
		this.mainFrm = mainFrm;
		this.cameraCapture = cameraCapture;
	}
	
	class BreathsThread extends Thread
	{
		@Override
		public void run() {
			try {
				while(true)
				{
					this.sleep(3000);
					if(haveMan)
					{
						mainFrm.saveImage();
					}	
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
		
}
