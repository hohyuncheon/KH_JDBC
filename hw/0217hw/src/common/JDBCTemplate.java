package common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;


/*
 * Service, Dao클래스의 공통부문을 static메소드 제공
 * 예외처리를 공통부분에서 작성하므로, 사용(호출)하는 쪽에서 간결한 코드작성할 수 있다.
 * 
 */
public class JDBCTemplate {
	static String driverClass;
	static String url ;
	static String user;
	static String password;

	// static 초기화 블럭을 만들어 1회만 실행되게한다
	// (static 초기화블럭이라 클래스가 처음실행될때 1번 실행된다. 그냥 초기화블럭은 객체생성시 1회)
	static {
		//data-source.properties의 내용을 읽어서 변수에 대입
		Properties prop = new Properties();
		String fileName = "resources/data-source.properties";
		try {
			prop.load(new FileReader(fileName));
//			System.out.println(prop);
			driverClass = prop.getProperty("driverClass");
			url = prop.getProperty("url");
			user = prop.getProperty("user");
			password = prop.getProperty("password");
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			//1. DriverClass등록(최초1회)
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		Connection conn = null;
		try {
			// 2. Connection 객체생성 url, user, password
			conn = DriverManager.getConnection(url, user, password);
			// 2.1 자동커밋 false 설정
			conn.setAutoCommit(false);// 해도되고안해도됨. 작성안해도 자동커밋됨.
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;
	}

	public static void close(Connection conn) {
		// 7. 자원반납(생성역순 rset - pstmt - conn)
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// DAO 클래스에서 finally 절 간략하게 만들기 위한 resultset용
	public static void close(ResultSet rset) {
		try {
			if (rset != null)
				rset.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// DAO 클래스에서 finally 절 간략하게 만들기 위한 PreparedStatement용
	public static void close(PreparedStatement pstmt) {
		try {
			if (pstmt != null)
				pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	//service클래스에서 간략히 만들기 위함.
	public static void commit(Connection conn) {
		try {
			if (conn != null)
				conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	//service클래스에서 간략히 만들기 위함.
	public static void rollback(Connection conn) {
		try {
			if (conn != null)
				conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
