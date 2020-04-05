package cn.al.hax.store.utils;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//支持重定向和转发的2中操作
public class BaseServlet extends HttpServlet {
	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// localhost:8080/store/productServlet?method=addProduct
		String method = req.getParameter("method");

		if (null == method || "".equals(method) || method.trim().equals("")) {
			method = "execute";
		}

		// 注意:此处的this代表的是子类的对象
		// System.out.println(this);
		// 子类对象字节码对象
		Class clazz = this.getClass();

		try {
			// 查找子类对象对应的字节码中的名称为method的方法.这个方法的参数类型是:HttpServletRequest.class,HttpServletResponse.class
			Method md = clazz.getMethod(method, HttpServletRequest.class, HttpServletResponse.class);
			if(null!=md){
				String result = (String) md.invoke(this, req, resp);
				if(!result.contains(":")) {
            		//直接转发
            		req.getRequestDispatcher(result).forward(req, resp);
            	}else {
            		int index=result.indexOf(":");
            		//拆分以后数据 ，进行转发
            		String temp=result.substring(index+1);
            		if(result.substring(0,index).equals("f")) {
            			req.getRequestDispatcher(temp).forward(req, resp);
            		}else if(result.substring(0,index).equals("r")) {
            			//重调用
            			resp.sendRedirect(req.getContextPath()+temp);
            		}else {
            			throw new RuntimeException("当前版本不支持");
            		}
			}
		} 

	}catch (Exception e) {
		e.printStackTrace();
	}
}
	  //写一个execute的方法，方便子类去重写
		public String execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
			return null;
		}
}