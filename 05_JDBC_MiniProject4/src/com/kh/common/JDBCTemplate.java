package com.kh.common;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {

	public static Connection getConnection() {

		
		Connection conn = null;
		
		Properties prop = new Properties();
		
		try {
			
			prop.load(new FileInputStream("resources/driver.properties"));
			
			//  1) JDBC Driver 등록
			Class.forName(prop.getProperty("driver"));
			
			//2)  COnnection 객체 생성
			conn = DriverManager.getConnection(prop.getProperty("url"),
												prop.getProperty("username"),
												prop.getProperty("password"));
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	
	}

	// 2.  전달받은 jDBC용 객체를 자원반납시켜주는 메소드
	// 2_1 Connection 객체를 매개변수로 전달받아서 반납시켜주는 메소드
	public static void close(Connection conn) {

		// 애초에 NullpointerException 방치하기 위함
		try {

			if(conn != null && !conn.isClosed()){ // 안 닫혀있을 떄마 닫기
				conn.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}//close()
	
	
	// 2_2) Statement 객체를 매개변수로 전달받아서 반납시켜주는 메소드
	// => 다형성으로 인해 PreparedStatment(자식) 객체 또한 매개변수로 전달 가능하다.
	public static void close(Statement stmt) {
		try {
			if(stmt != null &&  !stmt.isClosed()) {
				stmt.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	// 2_3 ) ResultSet 객체를 매개변수로 전달받아서 반납시켜주는 메소드
	public static void close(ResultSet rset) {
		
		try {
			if(rset != null && !rset.isClosed()) {
				
				rset.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}//close

	// 3. 전달받은 Connection 객체를 가지고 트랜잭션 처리를 해주는 메소드
	// 3_1) 매개변수로 전달받은 Connection 객체를 가지고 COMMIT 시켜주는 메소드
	public static void commit(Connection conn) {
		
		try {
			if(conn != null && !conn.isClosed()) {
				
				conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	} //commit
	
	// 3_2) 매개변수로 전달받은 Connection 객체를 가지고 ROLLBACK 시켜주는 메소드
	public static void rollback(Connection conn) {
		
		try {
			if(conn != null && !conn.isClosed()) {
				conn.rollback();
			}
		} catch (SQLException e) {
	
			e.printStackTrace();
		}
		
	}//rollback
}
