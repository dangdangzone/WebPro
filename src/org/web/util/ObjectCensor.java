package org.web.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;

public class ObjectCensor
{
    private ObjectCensor()
    {}


    public static boolean checkObjectIsNull( Object obj )
    {
        if ( obj == null )
        {
            return true;
        }
        else
        {
            if ( obj instanceof String )
            {
                return checkStringIsNull( ( String )obj );
            }
            else if ( obj instanceof Map )
            {
                return checkMapIsNull( ( Map )obj );
            }
            else if ( obj instanceof Collection )
            {
                return checkCollectionIsNull( ( Collection )obj );
            }
            else if ( obj instanceof Object[] )
            {
                return checkArrayIsNull( ( Object[] )obj );
            }
            else
            {
                return false;
            }
        }
    }

    private static boolean checkStringIsNull( String str )
    {
        if ( "".equals( str ) )
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private static boolean checkMapIsNull( Map map )
    {
	if( map.size()>0){
    		return false;
    	}else{
            return true;
    	}
    }

    private static boolean checkCollectionIsNull( Collection collection )
    {
    	if(collection.size()>0 ){
    		return false;
    	}else{
    		return true;
    	}
     }

    private static boolean checkArrayIsNull( Object[] obj )
    {
        if ( obj.length == 0 )
        {
            return true;
        }
        else
        {
            for ( int i = 0; i < obj.length; i++ )
            {
                if ( obj[i] != null )
                {
                    return false;
                }
            }

            return true;
        }
    }
    
    public static boolean isStrRegular(String... str)
    {
    	for(int i=0,n=str.length;i<n;i++)
    	{
    		if(str[i]==null||"".equals(str[i])||"null".equals(str[i]))
    		{
    			return false;
    		}
    	}
    	return true;
    }
    
    public static boolean checkListIsNull(List list)
    {
    	if(list!=null && list.size()!=0)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
}
