package org.web.controller;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.web.service.UserService;

@Controller
@RequestMapping("/user.do")
public class UserController 
{

	@Autowired
	private UserService userService;
	
	@RequestMapping(params = "method=qryUserList")
	public void qryUserList(HttpServletResponse response , HttpServletRequest request)
	{
		PrintWriter out = null;
		try
		{
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();
			HttpSession session = request.getSession();
			Map<String,String> userMap = (Map<String,String>)session.getAttribute("UserInfo");
			String page = request.getParameter("page");
			String rows = request.getParameter("rows");
			String retVal = userService.qryUserList(page , rows);
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
	
	@RequestMapping(params = "method=newUser")
	public void newUser(String userName , String realName , String startDate , String endDate , String classPhare , HttpServletResponse response , HttpServletRequest request)
	{
		PrintWriter out = null;
		JSONObject json = new JSONObject();
		try
		{
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();
			HttpSession session = request.getSession();
			Map<String,String> userMap = (Map<String,String>)session.getAttribute("UserInfo");
			if(userMap != null)
			{
				String userIp = request.getRemoteAddr();
				out.write(userService.newUser(userName, realName, startDate, endDate, classPhare, userIp));
			}
			else
			{
				json.put("success", 0);
				json.put("msg", "新增用户失败");
				out.write(json.toString());
			}
		}
		catch(Exception err)
		{
			err.printStackTrace();
			json.put("success", 0);
			json.put("msg", err.getMessage());
			out.write(json.toString());
		}
		finally
		{
			if(out != null)
			{
				out.close();
			}
		}
	}
	
	@RequestMapping(params = "method=updateUser")
	public void updateUser(String userId , String userName , String realName , String startDate , String endDate , String classPhare , HttpServletResponse response , HttpServletRequest request)
	{
		PrintWriter out = null;
		JSONObject json = new JSONObject();
		try
		{
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();
			HttpSession session = request.getSession();
			Map<String,String> userMap = (Map<String,String>)session.getAttribute("UserInfo");
			if(userMap != null)
			{
				out.write(userService.updateUserInfo(userId, userName, realName, startDate, endDate, classPhare));
			}
			else
			{
				json.put("success", 0);
				json.put("msg", "修改用户失败");
				out.write(json.toString());
			}
		}
		catch(Exception err)
		{
			err.printStackTrace();
			json.put("success", 0);
			json.put("msg", err.getMessage());
			out.write(json.toString());
		}
		finally
		{
			if(out != null)
			{
				out.close();
			}
		}
	}
	
	@RequestMapping(params = "method=delUser")
	public void delUser(String userId , HttpServletResponse response , HttpServletRequest request)
	{
		PrintWriter out = null;
		JSONObject json = new JSONObject();
		try
		{
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();
			HttpSession session = request.getSession();
			Map<String,String> userMap = (Map<String,String>)session.getAttribute("UserInfo");
			if(userMap != null)
			{
				out.write(userService.delUser(userId));
			}
			else
			{
				json.put("success", 0);
				json.put("msg", "删除用户失败");
				out.write(json.toString());
			}
		}
		catch(Exception err)
		{
			err.printStackTrace();
			json.put("success", 0);
			json.put("msg", err.getMessage());
			out.write(json.toString());
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
