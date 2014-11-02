package org.web.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.web.util.QryCenter;
import org.web.util.QryException;

@Repository
public class MenuDao {

	private QryCenter qryCenter;

	public QryCenter getQryCenter() {
		return qryCenter;
	}

	@Resource
	public void setQryCenter(QryCenter qryCenter) {
		this.qryCenter = qryCenter;
	}
	
	public List<Map<String,String>> getMainMenu() throws QryException
	{
		String query = "select * from vclass_menu where parent_menu_id = 0 and sts = '00A'";
		return qryCenter.executeSqlByMapListWithTrans(query, new ArrayList());
	}
	
	public List<Map<String,String>> getSubMenuItem(String menuId , String userPriv) throws QryException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("select * from vclass_menu ");
		if(!"#".equals(userPriv))
		{
			sb.append(" where menu_priv != '#' or menu_priv is null ");
		}
		sb.append(" start with parent_menu_id = ? connect by prior menu_id = parent_menu_id ");
		ArrayList paramList = new ArrayList();
		paramList.add(menuId);
		return qryCenter.executeSqlByMapListWithTrans(sb.toString(), paramList);
	}
	
}
