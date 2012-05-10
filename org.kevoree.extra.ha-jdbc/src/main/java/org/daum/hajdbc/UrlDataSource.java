package org.daum.hajdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 07/05/12
 * Time: 11:39
 */
public class UrlDataSource implements javax.sql.DataSource
	{
		private String url;
		private volatile Connection connection;

		public String getUrl()
		{
			return this.url;
		}

		public void setUrl(String url)
		{
			this.url = url;
		}

		@Override
		public synchronized Connection getConnection() throws SQLException
		{
			if (this.connection == null)
			{
				this.connection = DriverManager.getConnection(this.url);
			}

			return this.connection;
		}

		@Override
		public synchronized Connection getConnection(String user, String password) throws SQLException
		{

			if (this.connection == null)
			{
              //  System.out.println("URL = "+this.url+" user="+user+"password="+password);
				this.connection = DriverManager.getConnection(this.url, user, password);
			}

			return this.connection;
		}

		@Override
		public PrintWriter getLogWriter() throws SQLException
		{
			return null;
		}

		@Override
		public int getLoginTimeout() throws SQLException
		{
			return 0;
		}

		@Override
		public void setLogWriter(PrintWriter arg0) throws SQLException
		{
		}

		@Override
		public void setLoginTimeout(int arg0) throws SQLException
		{
		}

		@Override
		public boolean isWrapperFor(Class<?> arg0) throws SQLException
		{
			return false;
		}

		@Override
		public <T> T unwrap(Class<T> arg0) throws SQLException
		{
			return null;
		}
	}