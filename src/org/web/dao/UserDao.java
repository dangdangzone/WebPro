package org.web.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.web.util.QryCenter;
import org.web.util.QryException;
import org.web.util.StringUtil;

@Repository
public class UserDao 
{
	private QryCenter qryCenter;

	public QryCenter getQryCenter() {
		return qryCenter;
	}

	@Resource
	public void setQryCenter(QryCenter qryCenter) {
		this.qryCenter = qryCenter;
	}
	
	public List<Map<String,String>> qryUserList(String page , String rows) throws QryException
	{
		String query = "SELECT * FROM (SELECT A.*, ROWNUM RN FROM (select t.user_id,t.user_name,t.user_priv,t.real_name,t.user_ip,to_char(t.start_date,'yyyy-mm-dd') start_date,to_char(t.end_date,'yyyy-mm-dd') end_date from vclass_user t where user_state = '00A' and user_priv != '#') A WHERE ROWNUM <= ?) WHERE RN >= ?";
		ArrayList paramList = new ArrayList();
		int pageNum = Integer.parseInt(page);
		int pageSize = Integer.parseInt(rows);
    	paramList.add(pageNum * pageSize);
    	paramList.add((pageNum - 1) * pageSize + 1);
		return qryCenter.executeSqlByMapListWithTrans(query, paramList);
	}
	
	public List<Map<String,String>> qryUserByUserId(String userId) throws QryException
	{
		String query = "select * from vclass_user where user_state = '00A' and user_id = ? ";
		ArrayList paramList = new ArrayList();
		paramList.add(userId);
		return qryCenter.executeSqlByMapListWithTrans(query, paramList);
	}
	
	public List<Map<String,String>> qryUserByUserName(String userName) throws QryException
	{
		String query = "select * from vclass_user where user_state = '00A' and user_id = ? ";
		ArrayList paramList = new ArrayList();
		paramList.add(userName);
		return qryCenter.executeSqlByMapListWithTrans(query, paramList);
	}
	
	public int addUser(Map<String,String> userMap)
	{
		Connection conn = null;
		Statement stat = null;
		try{
			String userName = StringUtil.getMapKeyVal(userMap, "userName");
			String userPass = StringUtil.getMapKeyVal(userMap, "userPass");
			String userPriv = StringUtil.getMapKeyVal(userMap, "userPriv");
			String realName = StringUtil.getMapKeyVal(userMap, "realName");
			String userIp = StringUtil.getMapKeyVal(userMap, "userIp");
			String lastDate = StringUtil.getMapKeyVal(userMap, "lastDate");
			String startDate = StringUtil.getMapKeyVal(userMap, "startDate");
			String endDate = StringUtil.getMapKeyVal(userMap, "endDate");
			conn = qryCenter.getDataSource().getConnection();
			stat = conn.createStatement();
			String sql = "insert into vclass_user(user_id,user_name,user_pass,user_priv,user_state,real_name,user_ip,last_date,start_date,end_date) values(vclass_seq.nextval,'"+userName+"','"+userPass+"','"+userPriv+"','00A','"+realName+"','"+userIp+"',to_date('"+lastDate+"','yyyy-mm-dd hh24:mi:ss'),to_date('"+startDate+"','yyyy-mm-dd'),to_date('"+endDate+"','yyyy-mm-dd'))";
			return stat.executeUpdate(sql);
		}catch(Exception err)
		{
			err.printStackTrace();
		}
		finally
		{
			if(stat != null)
			{
				try {
					stat.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn != null)
			{
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return -1;
	}
	
	public int delUser(String userId)
	{
		Connection conn = null;
		Statement stat = null;
		try{
			conn = qryCenter.getDataSource().getConnection();
			stat = conn.createStatement();
			String sql = "delete from  vclass_user where user_id = '"+userId+"'";
			return stat.executeUpdate(sql);
		}catch(Exception err)
		{
			err.printStackTrace();
		}
		finally
		{
			if(stat != null)
			{
				try {
					stat.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn != null)
			{
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return -1;
	}
}
