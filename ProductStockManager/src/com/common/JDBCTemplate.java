package com.common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {
private static Connection conn = null;
	
	public static Connection getConnection() {
		if(conn == null) {
			/*
			 * 이전 프로젝트에서는
			 * JDBC 드라이버 로드 ,db 연결을 위한 정보(url, id, password) 를 직업 코드에 작성(정적 코딩)
			 * --> 추후 DB 자체 변경 또는 연결 정보가 변경되는 경우 코드를 직접 수정하고 다시 컴파일 해야함
			 * --> 유지보수에 불편
			 * 
			 * 별도의 Properties 파일을 만들어 프로그램실행시 동적으로 DB 연결 정보를 불러올 수 있도록 진행
			 * 
			 * */
			try {
				//외부에서 정보를 읽어와서 저장할 Properties 객체 선언 및 생성
				Properties prop = new Properties();
				prop.load(new FileReader("resources\\driver.properties"));
				
				Class.forName(prop.getProperty("driver"));
				
				conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("user"), prop.getProperty("password"));
				
				conn.setAutoCommit(false);
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return conn;
	}
	
	public static void close(Connection conn) {
		 
		try {
			if(conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void commit(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.commit();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void rollback(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.rollback();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void close(Statement stmt) {
		try {
			if(stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rset) {
		try {
			if(rset != null && !rset.isClosed()) {
				rset.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
