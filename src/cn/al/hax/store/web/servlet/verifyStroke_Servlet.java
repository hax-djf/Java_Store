package cn.al.hax.store.web.servlet;

import java.awt.image.BufferedImage;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.al.hax.store.utils.verifyStroke;
/**
 * Servlet implementation class verifyStroke_Servlet
 */
@WebServlet("/verifyStroke_Servlet")
public class verifyStroke_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//请求响应绘制一张图片，并且将图片显示在login页面
	verifyStroke vs=new verifyStroke();
	BufferedImage image=vs.getImage();
	String srcText=vs.getText();//获取到图片的文字
	//将文字信息存储到session中
	request.getSession().setAttribute("srcText", srcText);
	//在将图片显示到页面上面
	verifyStroke.output(image, response.getOutputStream());
	System.out.println("图片加载");
	}
}
