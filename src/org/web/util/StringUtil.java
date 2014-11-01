package org.web.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Category;
import org.jdom.Document;
import org.jdom.Element;


 


public class StringUtil {
    private static Category cat;
    public StringUtil() {
    }

    public static String firstCharLowerCase(String s){
        if(s==null || "".equals(s)) return ("");
        return s.substring(0,1).toLowerCase()+s.substring(1);
    }

    public static String firstCharUpperCase(String s){
       if(s==null || "".equals(s)) return ("");
       return s.substring(0,1).toUpperCase()+s.substring(1);
   }

    public static String[] splitStringByTokenCount(String value, String token, int arrSize){
        Map map=new HashMap();

        if(arrSize==0) return null;

        int cnt = StringUtils.countMatches(value, token);
        if(!token.equals(value.substring(value.length()-token.length())))
            cnt ++;

        int len = cnt%arrSize==0?(cnt/arrSize):(cnt/arrSize)+1;
        String[] rets = new String[len];

        if(len==1) rets[0] = value;
        else{
            int count = 0;
            int ssIdx = 0;
            int iBeginIdx = 0;
            int idx = 0;
            while((idx = value.indexOf(token, idx)) != -1){
                count++;
                if(count%arrSize==0) {
                    rets[ssIdx] = value.substring(iBeginIdx,idx);
                    ssIdx++;
                    iBeginIdx = idx + token.length();
                }
                idx += token.length();
            }
            if(ssIdx+1==len){
                 rets[ssIdx]= value.substring(iBeginIdx);
            }

        }
        return rets;
    }

    public static String trimTokenAndNextFirstCharUpperCase(String value, String token)
    {
        if(value==null||"".equals(value)) return value;

        String[] ss = StringUtils.split(value, token);

        ss[0] = ss[0].toLowerCase();
        for(int i=1; i<ss.length; i++)
            ss[i] = firstCharUpperCase(ss[i].toLowerCase());


        return org.apache.commons.lang.StringUtils.join(ss);
    }

      public static String  getConverColName(String property) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < property.length(); i++) { 
          char cur = property.charAt(i);
          if (Character.isUpperCase(cur)) {
            sb.append("_");
            sb.append(Character.toLowerCase(cur));
          }
          else {
            sb.append(cur);
          }
        }
        return sb.toString();
      }

     public static Date strToDate(String s){

        Date mydate=new Date();

        if (s.length()==10){
            mydate.setYear(Integer.parseInt(s.substring(0,4)));
            mydate.setMonth(Integer.parseInt(s.substring(5,7)));
            mydate.setDate(Integer.parseInt(s.substring(8,10)));
        }
        if (s.length()==8){
            mydate.setYear(Integer.parseInt(s.substring(0,4)));
            mydate.setMonth(Integer.parseInt(s.substring(4,6)));
            mydate.setDate(Integer.parseInt(s.substring(6,8)));
        }
        return mydate;
     }

      public static String  getDtoName_ByVo(String voName) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < voName.length(); i++) { 
          char cur = voName.charAt(i);
          if (Character.isUpperCase(cur)) {
            sb.append("_");
            sb.append(Character.toLowerCase(cur));
          }
          else {
            sb.append(cur);
          }
        }
        return sb.toString();
      }

      public static String getRstFieldName_ByVoFldName(String voName) {
        StringBuffer sb = new StringBuffer();

        sb.append(Character.toUpperCase(voName.charAt(0)));
        for (int i = 1; i < voName.length(); i++) {
              char cur = voName.charAt(i);
              if (Character.isUpperCase(cur)&& i>0 ) {
                sb.append("_");
                sb.append(Character.toUpperCase(cur));
              }
              else {
                sb.append(cur);
              }
            }
            return sb.toString();
          }

      public static String getVoFldName_ByRstFldName(String voName) {
        StringBuffer sb = new StringBuffer();
        sb.append(Character.toLowerCase(voName.charAt(0)));
        for (int i = 1; i < voName.length(); i++) { 
          char cur = voName.charAt(i);
          char interval='_';
          if (cur==interval) {
            if (i+1<voName.length()){
                sb.append(Character.toUpperCase(voName.charAt(i+1)));
                i++;
            }
          }
          else {
            sb.append(cur);
          }
        }
        return sb.toString();
      }

      public static Object map_to_vo(Map row, Class type)  {
        try{

        Object vo = type.newInstance(); 
        Field[] fields = type.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
          String name = fields[i].getName(); 
          String nameInRow = getDtoName_ByVo(name);
          Object value = row.get(nameInRow); 
          if (value ==null) continue;
          String methodName = "set" + Character.toUpperCase(name.charAt(0)) +
              name.substring(1); 
          Class argClass = null;

          if (value instanceof Class) {
            argClass = (Class)value;
            value = null;

          }else{
            argClass=fields[i].getType();

          }
          String datatype=fields[i].getType().getName();
          try{
                if (datatype.equals("java.lang.Long")){
                    Method method = type.getMethod(methodName, new Class[] {argClass});
                    method.invoke(vo, new Long[] {new Long(value.toString())});
                }
                if (datatype.equals("java.lang.Double")){
                    Method method = type.getMethod(methodName, new Class[] {argClass}); 
                    method.invoke(vo, new Double[] {new Double(value.toString())});
                }
                if (datatype.equals("java.lang.Float")){
                    Method method = type.getMethod(methodName, new Class[] {argClass}); 
                    method.invoke(vo, new Float[] {new Float(value.toString())});
                }
                if (datatype.equals("java.util.Date")){
                    Method method = type.getMethod(methodName, new Class[] {argClass});
                    Date mydate=strToDate(value.toString());
                    method.invoke(vo, new java.util.Date[] {mydate});
                }
                if (datatype.equals("java.lang.String")){
                    Method method = type.getMethod(methodName, new Class[] {argClass}); 
                    method.invoke(vo, new Object[] {value});
                }

          }catch(Exception e){
            e.printStackTrace();
          }
        }
        return vo;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
      }

      public static Object row_to_vo(Map row, Class type)  {
        try{

        Object vo = type.newInstance();

        Field[] fields =type.getDeclaredFields(); 

        for (int i = 0; i < fields.length; i++) {
          String name = fields[i].getName(); 
          String nameInRow = getRstFieldName_ByVoFldName(name);
          Object value = row.get(nameInRow);
          if (value ==null) continue;
          String methodName = "set" + Character.toUpperCase(name.charAt(0)) +
              name.substring(1); 
          Class argClass = null;

          if (value instanceof Class) {
            argClass = (Class)value;
            value = null;

          }else{
            argClass=fields[i].getType();

          }
          String datatype=fields[i].getType().getName();
          try{
                if (datatype.equals("java.lang.Long")){
                    Method method = type.getMethod(methodName, new Class[] {argClass});
                    method.invoke(vo, new Long[] {new Long(value.toString())});
                }
                if (datatype.equals("java.lang.Double")){
                    Method method = type.getMethod(methodName, new Class[] {argClass});
                    method.invoke(vo, new Double[] {new Double(value.toString())});
                }
                if (datatype.equals("java.lang.Float")){
                    Method method = type.getMethod(methodName, new Class[] {argClass});
                    method.invoke(vo, new Float[] {new Float(value.toString())});
                }
                if (datatype.equals("java.util.Date")){
                    Method method = type.getMethod(methodName, new Class[] {argClass});
                    Date mydate=strToDate(value.toString());
                    method.invoke(vo, new java.util.Date[] {mydate});
                }
                if (datatype.equals("java.lang.String")){
                    Method method = type.getMethod(methodName, new Class[] {argClass});
                    method.invoke(vo, new Object[] {value});
                }

          }catch(Exception e){
            e.printStackTrace();
          }
        }
        return vo;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
      }

      public static Map getShortName_Map(Map map,String tabName)  {
        Iterator ite=map.keySet().iterator();
        Map retMap=new HashMap();
        while(ite.hasNext()){
            String key=(String)ite.next();
            if (key.length()<=tabName.length()) continue;
            String pre=key.substring(0,tabName.length()).toUpperCase();
            if (pre.equals(tabName.toUpperCase())){
                String fieldName=key.substring(tabName.length()+1);
                retMap.put(fieldName,map.get(key));
            }
        }
        return retMap;
      }

      public static Object map_to_Model(Map map,Class type) {
        try{
            Object obj=type.newInstance();
            Iterator ite=map.keySet().iterator();
            Map modelMap=new HashMap();
            while(ite.hasNext()){
                String key=(String)ite.next();
                String voFldName=getVoFldName_ByRstFldName(key);
                modelMap.put(voFldName,map.get(key));
            }

            BeanUtils.copyProperties(obj,modelMap);
            return(obj);
        }catch(Exception e){
            e.printStackTrace();
            return(null);
        }
      }

      public static String getLastToken(String str, String tokenSeparator)
        {
            return str.substring(str.lastIndexOf(tokenSeparator) + 1, str.length());
        }

        public static boolean isNull(String s)
        {
            return s == null || s.length() < 1;
        }

        public static boolean isNull(String s, String val)
        {
            return isNull(s) || s.compareTo(val) == 0;
        }

        public static String stackTrace(Throwable t)
        {
            StringWriter sw = new StringWriter();
            t.printStackTrace(new PrintWriter(sw));
            String s = sw.toString();
            try
            {
                sw.close();
            }
            catch(IOException e)
            {
                cat.error("::stackTrace - cannot close the StringWriter object", e);
            }
            return s;
        }

        public static String dbString(String v)
        {
            StringBuffer sb = new StringBuffer();
            return isNull(v) ? "" : sb.append("'").append(v).append("'").toString();
        }

        public static String dumpHashTable(Hashtable table, boolean html)
        {
            Enumeration keys = table.keys();
            Enumeration values = table.elements();
            StringBuffer sb = new StringBuffer();
            String eof = "\n";
            if(html)
                eof = "<br>\n";
            for(; keys.hasMoreElements(); sb.append("  key [").append(keys.nextElement().toString()).append("] = [").append(values.nextElement().toString()).append("]").append(eof));
            return sb.toString();
        }

        public static String addURLParameter(String URL, String paramName, String paramValue)
        {
            String param = paramName + "=" + paramValue;
            return addURLParameter(URL, param);
        }

        public static String addURLParameter(String URL, String parameter)
        {
            StringBuffer sb = new StringBuffer(URL);
            if(URL.lastIndexOf(63) == -1)
                sb.append("?");
            else
                sb.append("&");
            sb.append(parameter);
            return sb.toString();
        }

        public static String remove(String str, String until)
        {
            String val = null;
            int indx = str.indexOf(until);
            if(indx != -1)
                val = str.substring(indx + until.length(), str.length());
            return val;
        }

        public static List stringMid(String sSource,String sIntervel){
            String sDes="";
            List lst=new ArrayList();
            int nindex;
            while(sSource.length()>0) {
                if (sSource.equals(sIntervel)){
                    break;
                }

                nindex=sSource.indexOf(sIntervel);
                if (nindex<0 && (!sSource.equals(sIntervel))){
                    lst.add(sSource);
                    break;
                }
                sDes=sSource.substring(0,nindex);
                sSource=sSource.substring(nindex+sIntervel.length());

                if (sDes.length()>=sIntervel.length())
                    if (sDes.substring(0,sIntervel.length()).equals(sIntervel)){
                        continue;
                    }else{
                        lst.add(sDes);
                    }
            }
            return(lst);
        }
        public static List getSubMapList(List maplst,Map mapComp){
            if (maplst==null || maplst.size()==0) return null;
            if (mapComp==null) return null;

            Iterator iteKey=mapComp.keySet().iterator();
            if (iteKey==null) return null;
            List lstKey=new ArrayList();
            while(iteKey.hasNext()) {
                lstKey.add(iteKey.next());
            }
            if (lstKey.size()==0 ) return null;

            List lstResult=new ArrayList();
            for(int i=0;i<maplst.size();i++){
                Map oneRow=(Map)maplst.get(i);
                int equalFlag=1; 
                for(int j=0;j<lstKey.size();j++){
                    String key=(String)lstKey.get(j);
                    String val1=getMapKeyVal(oneRow,key);
                    String val2=getMapKeyVal(mapComp,key);
                            if (!val1.equals(val2)) {
                            equalFlag=0;
                            break;
                    }
                }
                if ( equalFlag==1){
                    lstResult.add(oneRow);
                }

            }

            return lstResult;
        }
        
        public static List getBlurSubMapList(List maplst,Map mapComp){
            if (maplst==null || maplst.size()==0) return null;
            if (mapComp==null) return null;

            Iterator iteKey=mapComp.keySet().iterator();
            if (iteKey==null) return null;
            List lstKey=new ArrayList();
            while(iteKey.hasNext()) 
            {
                lstKey.add(iteKey.next());
            }
            if (lstKey.size()==0 ) return null;

            List lstResult=new ArrayList();
            for(int i=0;i<maplst.size();i++)
            {
                Map oneRow=(Map)maplst.get(i);
                int equalFlag=1; 
                for(int j=0;j<lstKey.size();j++)
                {
                    String key=(String)lstKey.get(j);
                    String val1=getMapKeyVal(oneRow,key);
                    String val2=getMapKeyVal(mapComp,key);
                    if ((","+val1+",").indexOf(","+val2+",")==-1) 
                    {
                      equalFlag=0;
                      break;
                    }
                }
                if ( equalFlag==1)
                {
                    lstResult.add(oneRow);
                }
            }
            return lstResult;
        }
        
     public  static boolean checkStringIsNum(Object cheStrT)
 {  
      String cheStr=cheStrT.toString();
      for(int i=0;i<cheStr.length();i++)
      {
       
    	  if( new String("9876543210").indexOf(cheStr.substring(i,i+1)) == -1 &&  !".".equals(cheStr.substring(i,i+1)) )
         return false;
      } 
      return true;
 }

    public static String getMapKeyVal(Map map,String key){
        if (map==null) return "";
        if (key==null) return "";
        Object obj=map.get(key);
        String result;
        if (obj==null){
            result="";
        }else{
            result=obj.toString();
        }
        return result;
    }
    
    public static String getJSONObjectKeyVal(JSONObject object, String key)
    {
		if(object == null)
		{
		    return "";
		}
		if(key == null)
		{
		    return "";
		}
		Object obj = object.get(key);
	        String result;
        if (obj == null || "null".equals(obj) )
        {
            result = "";
        }
        else
        {
            result = obj.toString();
        }
        return result;
    }
    
    public static List sortMapListByColumn(String columnName,List mapList){
        List returnList = new ArrayList();
        if (mapList==null || mapList.size()==0) return returnList;
        List uniqueValList = new ArrayList();
        for(int i=0;i<mapList.size();i++){
            HashMap map = (HashMap) mapList.get(i);
            String columnVal = getMapKeyVal(map,columnName);
            if("".equals(columnVal)){
                return returnList;
            }
            HashMap tempMap = new HashMap();
            tempMap.put(columnName,columnVal);
            if(getSubMapList(uniqueValList,tempMap)==null || 
                    getSubMapList(uniqueValList,tempMap).size()==0){
                uniqueValList.add(tempMap);
            }
        }
        for(int k=0;k<uniqueValList.size();k++){
            HashMap uniqueValMap = (HashMap)uniqueValList.get(k);
            if(getSubMapList(mapList,uniqueValMap)!=null && 
                    getSubMapList(mapList,uniqueValMap).size()!=0){
                returnList.add(getSubMapList(mapList,uniqueValMap));
            }
        }
        return returnList;
    }
    
    public static String makeColumsToStr(List list , String key,String divide){
		if(list == null || list.size() == 0 || "".equals(key))
		{
			return "";
		}
		String str = "";
		for(int i = 0 ; i < list.size() ; i++){
			Map mapComp = (Map)list.get(i);
			String keyValue = getMapKeyVal(mapComp,key);
			if(i == 0 && list.size()==1){
				str+=keyValue;
			}else if(i<list.size()-1){
				str+=keyValue+divide;
			}else{
				str+=keyValue;
			}
		}
		return str;
	}
    
    public static boolean isMobileNO(String mobiles){
    	if(mobiles==null) return false;
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();   
    }
    
    public static boolean isEmail(String email){
    	if(email==null) return false;
    	String str="^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    } 
    
    public static Timestamp getTimestampByJT(JSONObject json)
    {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		date.setTime(Long.parseLong(StringUtil.getJSONObjectKeyVal(json, "time")));
		String dateStr = sdf.format(date);
		Timestamp sta = Timestamp.valueOf(dateStr);
		return sta;
    }
    
    public static String filterEnter(String str)
    {
    	int ind;
    	StringBuffer sb = new StringBuffer();
    	while((ind = str.lastIndexOf("\n")) != -1)
    	{
        	sb.append(str.substring(0,ind));
        	sb.append("\\n");
        	sb.append(str.substring(ind+1));
        	str = sb.toString();
        	sb.delete(0, str.length());
    	}
    	return str;
    }
    
    public static String transDataToObj(String str)
    {
    	int ind;
    	StringBuffer sb = new StringBuffer();
    	for(int i = 0,n = str.length();i < n;i++)
    	{
    		char ch = str.charAt(i);
    		if(ch == '_')
    		{
    			char sr = str.charAt(i+1);
    			sb.append((char)(sr-32));
    		}
    		else
    		{
    			sb.append(ch);
    		}
    	}
    	return sb.toString();
    }

    public static String transObjToData(String str)
    {
    	StringBuffer sb = new StringBuffer();
    	for(int i = 0 , n = str.length();i < n;i++)
    	{
    		char ch = str.charAt(i);
    		if(ch >= 'A' && ch <= 'Z')
    		{
    			sb.append("_"+(char)(ch+32));
    		}
    		else
    		{
    			sb.append(ch);
    		}
    	}
    	return sb.toString();
    }
    
	public static String addSpace(StringBuffer str,int num)
	{
		int count = num - str.toString().length();
		for(int i = 0;i < count;i++)
		{
			str.append(" ");
		}
		return str.toString();
	}
	
	 public  static boolean checkStringIsMoney(Object cheStrT)
	 {  
		if(!ObjectCensor.checkObjectIsNull(cheStrT))
		{
			String str = String.valueOf(cheStrT);
			String regex = "^[0-9]+.?[0-9]*$";
			if(str.matches(regex))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	 }
	 
	 public static String uploadfileConverter(String staffId , String createDate , String fileName)
	 {
		if(createDate.indexOf(".") != -1)
		{
		    createDate = createDate.substring(0, createDate.indexOf("."));
		}
		createDate = createDate.replaceAll(":", "_");
		createDate = createDate.replaceAll(" ", "_");
		return staffId + "_" + createDate + "_" + fileName;
	 }
	 
	 public static String fromListToString(List list,String name)
	 {
		 if(ObjectCensor.checkListIsNull(list))
		 {
			 StringBuffer sb = new StringBuffer();
			 for(int i = 0,n = list.size();i < n;i++)
			 {
				 Map map = (Map)list.get(i);
				 String val = StringUtil.getMapKeyVal(map, name);
				 sb.append(val + ",");
			 }
			 if(!"".equals(sb.toString()))
			 {
				 String result = sb.toString();
				 return result.substring(0,result.length()-1);
			 }
		 }
		 return "";
	 }
	 
	 public static String fromJsonToString(JSONArray json,String name)
	 {
		 if(!ObjectCensor.checkObjectIsNull(json))
		 {
			 StringBuffer sb = new StringBuffer();
			 for(int i = 0,n = json.size();i < n;i++)
			 {
				 JSONObject obj = json.getJSONObject(i);
				 sb.append(StringUtil.getJSONObjectKeyVal(obj, name)+",");
			 }
			 if(!"".equals(sb.toString()))
			 {
				 String result = sb.toString();
				 return result.substring(0,result.length()-1);
			 }
		 }
		 return "";
	 }
	 
	 public static String transformChr(String content , String ctrl, String chr)
	 {
		content = "'"+content+"'";
		String temp = "'||chr("+chr+")||'";
    	content = content.replace(ctrl, temp);
    	content = content.replace("||''||", "||");
    	content = content.replace("''||chr("+chr+")||'", "'");
    	int becnt = 0 , encnt = content.length() - 1;
    	while(becnt < content.length())
    	{
    		if(content.charAt(becnt) == '|' || (content.charAt(becnt) == '\'' && becnt != 0))
    		{
    			becnt++;
    		}
    		else
    		{
    			break;
    		}
    	}
    	while(encnt >= 0)
    	{
    		if(content.charAt(encnt) == '|' || (content.charAt(encnt) == '\'' && encnt != content.length() - 1))
    		{
    			encnt--;
    		}
    		else
    		{
    			break;
    		}
    	}
    	content = content.substring(becnt,encnt+1);
    	return content;
	 }
	 
    public static void sqlInOperAdjust(String strList , String column , StringBuffer query)
    {
    	String[] arrList = strList.split(",");
    	if(arrList.length == 1)
    	{
			query.append(" " +column+ " in  (" +strList+") ");
    	}
    	else
    	{
    		query.append(" ( ");
    		int cnt = 0;
    		StringBuffer sb = new StringBuffer();
    		for(int i = 0,n = arrList.length;i < n;i++)
    		{
    			if(cnt < 1000)
    			{
    				sb.append(arrList[i] + ",");
    				cnt++;
    			}
    			else
    			{
    				String temp = sb.toString();
    				query.append(" " + column + " in (" +temp.substring(0,temp.length()-1)+ ")#");
    				sb = new StringBuffer();
    				cnt = 0;
    			}
    		}
    		if(cnt < 1000)
    		{
    			String temp = sb.toString();
    			query.append(" " + column + " in (" +temp.substring(0,temp.length()-1)+ ")");
    		}
    		else
    		{
    			String temp = query.toString();
    			temp = temp.substring(0, temp.length() - 1);
    			query.setLength(0);
    			query.append(temp);
    		}
    		query.append(" ) ");
    		String temp = query.toString();
    		temp = temp.replace("#", " or ");
    		query.setLength(0);
    		query.append(temp);
    	}
    }
    
	public static int compareStr(String keyo , String keyt)
	{
		keyo = keyo.trim();
		keyt = keyt.trim();
		int olen = keyo.length();
		int tlen = keyt.length();
		int poso = 0;
		int post = 0;
		keyo = keyo.toLowerCase();
		keyt = keyt.toLowerCase();
		while(poso < olen && post < tlen)
		{
			char och = keyo.charAt(poso);
			char tch = keyt.charAt(post);
			if(och > tch)
			{
				return 1;
			}
			else if(och < tch)
			{
				return -1;
			}	
			poso++;
			post++;
		}
		if(olen > tlen)
		{
			return -1;
		}
		else if(olen < tlen)
		{
			return 1;
		}
		return 0;
	}
    
	public static <T> void getSubObjectList(List<T> list , Map<String,String> paraMap) throws Exception
	{
		List<String> strMapLst = new ArrayList<String>(paraMap.keySet());
		for(int i = list.size() - 1;i >= 0;i--)
		{
			T t = list.get(i);
			boolean flag = false;
			for(int j = 0 , m = strMapLst.size();j < m;j++)
			{
				String key = strMapLst.get(j);
				StringBuffer metName = new StringBuffer();
				metName.append("get");
				metName.append(key.substring(0,1).toUpperCase());
				metName.append(key.substring(1));
				Method met = t.getClass().getMethod(metName.toString(), new Class[]{});
				String value = StringUtil.getMapKeyVal(paraMap, key);
				Object valObj = met.invoke(t, new Object[]{});
				if(valObj instanceof String)
				{
					String valStr = (String)valObj;
					if(!valObj.equals(value))
					{
						flag = true;
						break;
					}
				}
				else if(valObj instanceof Integer)
				{
					Integer valInt = (Integer)valObj;
					if(valInt.intValue() != Integer.parseInt(value))
					{
						flag = true;
						break;
					}
				}
				else if(valObj instanceof Long)
				{
					Long valLong = (Long)valObj;
					if(valLong.longValue() != Long.parseLong(value))
					{
						flag = true;
						break;
					}
				}
				else if(valObj instanceof Float)
				{
					Float valFloat = (Float)valObj;
					if(valFloat.floatValue() != Float.parseFloat(value))
					{
						flag = true;
						break;
					}
				}
				else if(valObj instanceof Double)
				{
					Double valDouble = (Double)valObj;
					if(valDouble.doubleValue() != Double.parseDouble(value))
					{
						flag = true;
						break;
					}
				}
				else
				{
					if(valObj != value)
					{
						flag = true;
						break;
					}
				}
			}
			if(flag)
			{
				list.remove(i);
			}
		}
	}
	
	public static String getSplitStr(String sep , String... paramLst)
	{
		StringBuffer sb = new StringBuffer();
		int i,n = paramLst.length;
		if(n != 0)
		{
			for(i = 0;i < n - 1;i++)
			{
				sb.append(paramLst[i]);
				sb.append(sep);
			}
			sb.append(paramLst[i]);
		}
		return sb.toString();
	}
	
	public static String getTimeoutDate(Date date ,String deadline, String format)
	{
		if(!ObjectCensor.checkObjectIsNull(date)&&ObjectCensor.isStrRegular(deadline , format))
		{
			char ch = deadline.charAt(deadline.length() - 1);
			long dateTime = date.getTime();
			String dataStr = "";
			switch(ch)
			{
				case 'm':
					dataStr = deadline.substring(0,deadline.length()-1);
					dateTime += Integer.parseInt(dataStr)*60*1000;
					break;
				case 'h':
					dataStr = deadline.substring(0,deadline.length()-1);
					dateTime += Integer.parseInt(dataStr)*60*60*1000;
					break;
				case 'd':
					dataStr = deadline.substring(0,deadline.length()-1);
					dateTime += Integer.parseInt(dataStr)*60*60*1000*24;
					break;
				case 'c':
					dataStr = deadline.substring(0,deadline.length()-1);
					dateTime += (Integer.parseInt(dataStr)/1000/60/60/24+1)*24*60*60*1000;
					break;
			}
			Date rstDate = new Date(dateTime);
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(rstDate);
		}
		return "";
	}
	
    public static Map<String,String> convertReqMapToMap(Map<String,String[]> srcMap)
    {
    	Map<String,String> map = new HashMap<String,String>();
    	for(Map.Entry<String, String[]> entry : srcMap.entrySet())
    	{
    		String key = entry.getKey();
    		String[] value = entry.getValue();
    		map.put(key, value[0]);
    	}
    	return map;
    }
    
    public static int convertStr(String str)
    {
    	int num = 1;
    	int pos = 1;
    	for(int i = str.length()-1,n = 0;i >= 0;i--)
    	{
    		if(str.charAt(i) == '1')
    		{
    			num += pos;
    		}
    		pos <<= 1;
    	}
    	return num-1;
    }
    
    public static void main(String[] args) {
		System.out.println(convertStr("011001"));
	}
    
}
