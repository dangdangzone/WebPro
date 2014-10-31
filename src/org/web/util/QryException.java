package org.web.util;

public class QryException extends Exception {
    private String sql;

    public QryException(  )
    {
        super();
    }

    public QryException( String message )
    {
        super( message );
    }

    public QryException( String message, String sql )
    {
        super( message );
        this.sql = sql;
    }

    public String toString(  )
    {
        if ( sql == null )
        {
            return super.toString(  );
        }
        else
        {
            return super.toString(  ) + "  <---- Caused by SQL: " + sql.toString(  ) + " ---->";
        }
    }

    public void printStackTrace(  )
    {
        super.printStackTrace(  );

        if ( sql != null )
        {
            System.err.println( "<---- Caused by SQL:" );
            System.err.println(sql);
            System.err.println( "---->" );
        }
    }

}
