package org.web.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web.dao.LoginDao;
import org.web.dao.UserDao;
import org.web.util.MD5Util;
import org.web.util.ObjectCensor;
import org.web.util.QryException;
import org.web.util.StringUtil;
import org.web.util.SysDate;

@Service
public class UserService 
{
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private LoginDao loginDao;
	
	private String[] pharse = {"第六阶段","第五阶段","第四阶段","第三阶段","第二阶段","第一阶段"};
		
	public String qryUserList(String page , String rows) throws QryException
	{
		List<Map<String,String>> list =  userDao.qryUserList(page , rows);
		for(int i = 0,n = list.size();i < n;i++)
		{
			Map map = list.get(i);
			String userPriv = StringUtil.getMapKeyVal(map, "userPriv");
			StringBuffer sb = new StringBuffer();
			for(int j = userPriv.length() - 1;j>=0;j--)
			{
				if(userPriv.charAt(j) == '1')
				{
					sb.append(pharse[j]).append(",");
				}
			}
			if(sb.capacity() != 0)
			{
				String temp = sb.toString();
				map.put("selClass", temp.substring(0, temp.length()-1));
			}
		}
		return JSONArray.fromObject(list).toString();
	}
	
	public String updateUserInfo(String userId , String userName , String realName , String startDate , String endDate , String classPhare) throws QryException
	{
		JSONObject json = new JSONObject();
		List<Map<String,String>> userList = userDao.qryUserByUserId(userId);
		if(ObjectCensor.checkListIsNull(userList))
		{
			Map<String,String> map = userList.get(0);
			map.put("userName", userName);
			map.put("realName", realName);
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			String[] classArr = classPhare.split(",");
			StringBuffer userPriv = new StringBuffer("000000");
			for(int i = 0,n = classArr.length;i < n;i++)
			{
				int pos = Integer.parseInt(classArr[i]);
				userPriv.setCharAt(pos, '1');
			}
			map.put("userPriv", userPriv.toString());
			loginDao.updateUserInfo(map);
			json.put("success", 1);
			json.put("msg", "修改用户信息成功");
		}
		else
		{
			json.put("success", 0);
			json.put("msg", "修改用户信息失败");
		}
		return json.toString();
	}
	
	public String newUser(String userName , String realName , String startDate , String endDate , String classPhare , String userIp) throws QryException, NoSuchAlgorithmException, UnsupportedEncodingException
	{
		JSONObject json = new JSONObject();
		List<Map<String,String>> userList = userDao.qryUserByUserName(userName);
		if(!ObjectCensor.checkListIsNull(userList))
		{
			Map<String,String> map = new HashMap<String,String>();
			map.put("userPass", MD5Util.getEncryptedPwd("123456"));
			map.put("userName", userName);
			map.put("realName", realName);
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("lastDate", SysDate.getDate());
			map.put("userIp", userIp);
			String[] classArr = classPhare.split(",");
			StringBuffer userPriv = new StringBuffer("000000");
			for(int i = 0,n = classArr.length;i < n;i++)
			{
				int pos = Integer.parseInt(classArr[i]);
				userPriv.setCharAt(pos, '1');
			}
			map.put("userPriv", userPriv.toString());
			userDao.addUser(map);
			json.put("success", 1);
			json.put("msg", "新增用户成功");
		}
		else
		{
			json.put("success", 0);
			json.put("msg", "该账户已注册");
		}
		return json.toString();
	}
	
	public String delUser(String userId)
	{
		JSONObject json = new JSONObject();
		userDao.delUser(userId);
		json.put("success", 1);
		json.put("msg", "新增用户成功");
		return json.toString();
	}
	
}
