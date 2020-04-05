package cn.al.hax.store.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

public class verifyStroke {
	private int w=70;
	private int h=35;
	private Random r=new Random();
	private String[] fontNames= {"宋体","华文偕体","黑体","微软雅黑","偕体_GB2312"};
	private String codes= "234567890qwertyuiopasdfghjkllzxcvbnmZXCVBNMLKHGFDSAQWWERTYUIOP";
	private Color bgcolor=new Color(255,255,255);
	private String text;
	//生成随机颜色
	private Color randomColor()  {
		int red=r.nextInt(150);
		int green=r.nextInt(150);
		int bule=r.nextInt(150);
		return new Color(red,green,bule);
	}
	//生成随机的字体
	private Font randomFont() {
		int index=r.nextInt(this.fontNames.length);
		String font=this.fontNames[index];
		//生成随机的样式
		//0 无样式 1 粗体 2 偕体 3粗体+偕体
		int style=r.nextInt(4);
		//生成随机的字号 24-29之间
		int size=r.nextInt(5)+24; 
		return new Font(font,style,size);
	}
	//生成干扰线
	private void drawline(BufferedImage mage) {
	int num =3;//一共画三条线
	Graphics2D g2=(Graphics2D) mage.getGraphics();
	for(int i=0;i<num;i++) {
		//生成2个点之间的坐标 及四个值
		int x1=r.nextInt(w);
		int y1=r.nextInt(h);
		int x2=r.nextInt(w);
		int y2=r.nextInt(h);
		g2.setStroke(new BasicStroke(1.5F));
		g2.setColor(Color.blue);
		g2.drawLine(x1,y1,x2,y2);// 画线	
	}
	}
	//创建一个bufferedImage缓冲区
		private BufferedImage createImage() {
		BufferedImage image=new BufferedImage(w, h, BufferedImage.TYPE_INT_BGR);
		Graphics2D g2=(Graphics2D) image.getGraphics();
		g2.setColor(this.bgcolor);
		g2.fillRect(0, 0, w, h);
		return image;
	}
	//随机生成一个字符
	private char randomChar() {
		int index=r.nextInt(codes.length());
		return codes.charAt(index);
	}
	public BufferedImage getImage() {
	BufferedImage image=createImage();
	Graphics2D g2=(Graphics2D) image.getGraphics();
	StringBuilder sb=new StringBuilder();//用来装载生成的验证码
	for(int i=0;i<4;i++) {
		//随机生成一个字母
		String s=randomChar()+"";
		sb.append(s);//将字母添加到sb中
		float x=i*1.0F*w/4;//设置当前字符的X的坐标
		g2.setFont(randomFont());//设置随机字体
		g2.setColor(randomColor());//生成随机的颜色
		g2.drawString(s, x, h-5);//画图	
	}
	this.text=sb.toString();
	drawline(image);
	return image;
	}
	public String getText() {
		return this.text;
	}
	//将图片保存到指定的输出流中
	public static void output(BufferedImage image,OutputStream out) {
		try {
			ImageIO.write(image,"JPEG",out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
			
}
