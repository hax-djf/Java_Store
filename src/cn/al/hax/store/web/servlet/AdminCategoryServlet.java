package cn.al.hax.store.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.al.hax.store.domain.Category;
import cn.al.hax.store.service.CategoryService;
import cn.al.hax.store.service.serviceImp.CategoryServiceImp;
import cn.al.hax.store.utils.UUIDUtils;
import cn.al.hax.store.web.base.BaseServlet;

public class AdminCategoryServlet extends BaseServlet {
	private CategoryService CategoryService=new CategoryServiceImp();
	/**
	 * findAllCats 查询全部的分类信息
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String findAllCats(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取全部分类信息
		List<Category> list=CategoryService.getAllCats();
		//全部分类信息放入request
		req.setAttribute("allCats", list);
		//转发到/admin/category/list.jsp
		return "/admin/category/list.jsp";
	}
	
	//addCategoryUI（Mvc）
	public String addCategoryUI(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		return "/admin/category/add.jsp";
	}
	/**
	 * addCategory 添加分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String addCategory(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取分类名称
		String cname=req.getParameter("cname");
		//创建分类ID
		String id=UUIDUtils.getId();
		Category c=new Category();
		c.setCid(id);
		c.setCname(cname);
		//调用业务层添加分类功能
		CategoryService.addCategory(c);
		//重定向到查询全部分类信息
		resp.sendRedirect(req.getContextPath()+"/AdminCategoryServlet?method=findAllCats");
		return null;
	}
}
