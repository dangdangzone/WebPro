package org.web.controller;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.web.service.LoginService;
import org.web.util.QryException;
import org.web.util.SecurityCodeCreater;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

@Controller
@RequestMapping("/login.do")
public class LoginController 
{
	@Autowired
	private LoginService loginService;
	
	@RequestMapping(params = "method=getSecurityImage")
	public void getSecurityImage(HttpServletResponse response , HttpServletRequest request)
	{
		try
		{
			HttpSession session = request.getSession();
			OutputStream os = response.getOutputStream();
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
			String code = loginService.getSecurityCode();
			session.setAttribute("code", code);
			encoder.encode(SecurityCodeCreater.getImage(code)); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@RequestMapping(params = "method=logout")
	public ModelAndView logout(HttpServletRequest request)
	{
		ModelAndView model = new ModelAndView("login");
		HttpSession session = request.getSession();
		Map<String,String> map = (Map<String,String>)session.getAttribute("UserInfo");
		if(map != null)
		{
			loginService.updateUserInfo(map, request);
		}
		session.removeAttribute("UserInfo");
		return model;
	}
	
	@RequestMapping(params = "method=showIndex")
	public ModelAndView showIndex()
	{
		ModelAndView model = new ModelAndView("index");
		try 
		{
			List<Map<String, String>> list = loginService.showIndex();
			model.addObject("menuList", list);
		}
		catch (QryException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(params = "method=login")
	public void login(String username , String password , String code , HttpServletResponse response , HttpServletRequest request)
	{
		PrintWriter out = null;
		try
		{
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();
			String retVal = loginService.login(username , password , code , request);
			out.write(retVal);
		}
		catch(Exception err)
		{
			err.printStackTrace();
		}
		finally
		{
			if(out != null)
			{
				out.close();
			}
		}
	}
	
	@RequestMapping(params = "method=menuLeft")
	public void menuLeft(String menuId , HttpServletResponse response)
	{
		PrintWriter out = null;
		try
		{
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();
			String retVal = loginService.menuLeft(menuId);
			out.write(retVal);
		}
		catch(Exception err)
		{
			err.printStackTrace();
		}
		finally
		{
			if(out != null)
			{
				out.close();
			}
		}
	}
}
