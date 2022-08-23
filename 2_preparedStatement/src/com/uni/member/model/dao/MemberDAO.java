package com.uni.member.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.uni.member.model.dto.Member;
/* 1.Connection 객체연결하기 
 * 2.Statement 객체 생성하기 
 * 3.ResultSet 객체 생성하기 
 * 4.Sql작성하기 
 * 5.ResultSet  결과담기 
 * 6.list 에 객체 하나씩 담기 
 * 7.close 하기 (자원반납 - 생성의 역순)
 */
/* DAO(Database Access Object) : 데이터베이스 접근용 객체
 *  => CRUD 연산을 담당하는 메소드들의 집합으로 이루어진 클래스이다.
 *  Create: 삽입 (Insert)
 *  Read : 조회 (Select)
 *  Update: 수정 (Update)
 *  Delete: 삭제(Delete)
 *  */
public class MemberDAO {

	public ArrayList<Member> selectAll() {
		ArrayList<Member> list = null;
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER";//자동으로 세미콜론 붙여서 실행
		
		try {
//			1. JDBC driver 등록 처리 : 해당 database 벤더 사가 제공하는 클래스 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
//			2. 등록된 클래스를 이용해서 db연결
//			드라이버타입@ip주소:포트번호:db이름(SID)
//			orcl:사용자정의설치, thin:자동으로 설치
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "student", "student");
			
			System.out.println("conn : " + conn);//성공하면 connection 값, 실패하면 null
			
//			3. 쿼리문을 실행할 statement 객체 생성
			stmt = conn.createStatement();
			
//			4. 쿼리문을 전송, 실행한 결과를 resultset으로 받기.
			rset = stmt.executeQuery(sql);
			
//			5. 받은 결과값을 객체에 옮겨서 저장하기
			list = new ArrayList<Member>();
			
			while(rset.next()) {
				Member m = new Member();
				m.setUserId(rset.getString("USERID"));
				m.setPassword(rset.getString("PASSWORD"));
				m.setUserName(rset.getString("USERNAME"));
				m.setGender(rset.getString("GENDER"));
				m.setAge(rset.getInt("AGE"));
				m.setEmail(rset.getString("EMAIL"));
				m.setPhone(rset.getString("PHONE"));
				m.setAddress(rset.getString("ADDRESS"));
				m.setHobby(rset.getString("HOBBY"));
				m.setEnrollDate(rset.getDate("ENROLLDATE"));
				
				list.add(m);
			}
			
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(rset != null) rset.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		
		return list;
	}

	public Member selectOne() {
		Member m = new Member();
		return null;
	}

	public Member selectOne(String memberId) {
		Member m = null;
		
		Connection conn = null;
//		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USERID = ?";
		
		
		
		try {
//			1. JDBC driver 등록 처리 : 해당 database 벤더 사가 제공하는 클래스 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
//			2. 등록된 클래스를 이용해서 db연결
//			드라이버타입@ip주소:포트번호:db이름(SID)
//			orcl:사용자정의설치, thin:자동으로 설치
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "student", "student");
			
			System.out.println("conn : " + conn);//성공하면 connection 값, 실패하면 null
			
//			3. 쿼리문을 실행할 statement 객체 생성
//			stmt = conn.createStatement();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			
//			4. 쿼리문을 전송, 실행한 결과를 resultset으로 받기.
//			rs = stmt.executeQuery(sql);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				m = new Member();
				m.setUserId(rset.getString("USERID"));
				m.setPassword(rset.getString("PASSWORD"));
				m.setUserName(rset.getString("USERNAME"));
				m.setGender(rset.getString("GENDER"));
				m.setAge(rset.getInt("AGE"));
				m.setEmail(rset.getString("EMAIL"));
				m.setPhone(rset.getString("PHONE"));
				m.setAddress(rset.getString("ADDRESS"));
				m.setHobby(rset.getString("HOBBY"));
				m.setEnrollDate(rset.getDate("ENROLLDATE"));
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(rset != null) rset.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e2) {
				// TODO: handle exception
			}
		}
		
		return m;
	}

	public List<Member> selectByName(String memberName) {
		ArrayList<Member> list = null;
		Connection conn = null;// DB에 연결할 객체
//		Statement stmt = null;// 실행할 쿼리
		PreparedStatement pstmt = null;
		ResultSet rset = null;// Select 한후 결과값 받아올 객체

		String sql = "SELECT * FROM MEMBER WHERE USERNAME = '%'||?||'%'";// 자동으로 세미콜론을 붙여 실행되므로 붙히지않는다

		try {
	
			// 1.Jdbc driver 등록 처리 : 해당 database 벤더 사가 제공하는 클래스 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 등록성공");
	
			// 2.등록된 클래스를 이용해서 db연결
			// 드라이버타입@ip주소:포트번호:db이름(SID)
			// orcl:사용자정의설치 , thin : 자동으로 설치 //ip주소 -> localhost 로 변경해도됨
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "student", "student");
	
			System.out.println("conn=" + conn);// 성공하면 connection 값, 실패하면 null값
	
			// 3. 쿼리문을 실행할 statement 객체 생성
	//		stmt = conn.createStatement();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberName);
			
			// 4.쿼리문 전송, 실행결과를 ResultSet 으로 받기
	//		rset = stmt.executeQuery(sql);
			rset = pstmt.executeQuery();
	
			// 5. 받은결과값을 객체에 옮겨서 저장하기
	
			list = new ArrayList<Member>();
	
			while (rset.next()) {
	
				Member m = new Member();
				m.setUserId(rset.getString("USERID"));
				m.setPassword(rset.getString("PASSWORD"));
				m.setUserName(rset.getString("USERNAME"));
				m.setGender(rset.getString("GENDER"));
				m.setAge(rset.getInt("AGE"));
				m.setEmail(rset.getString("EMAIL"));
				m.setPhone(rset.getString("PHONE"));
				m.setAddress(rset.getString("ADDRESS"));
				m.setHobby(rset.getString("HOBBY"));
				m.setEnrollDate(rset.getDate("ENROLLDATE"));
	
				list.add(m);
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {// 자원반납의 순서는 생성의 역순이다
				if(rset != null) rset.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list;
	}

	public int insertMember(Member m) {
		
		int result = 0;
		Connection conn = null;// DB에 연결할 객체
//		Statement stmt = null;// 실행할 쿼리
		PreparedStatement pstmt = null;
		
		String sql = "INSERT INTO MEMBER VALUES(?,?,?,?,?,?,?,?,?,sysdate)";
//			+ "'"+ m.getUserId()+"' , "
//			+ "'"+ m.getPassword()+"' , "	
//			+ "'"+ m.getUserName()+"' , "
//			+ "'"+ m.getGender()+"' , "
//			+ m.getAge()+","
//			+ "'"+ m.getEmail()+"' , "
//			+ "'"+ m.getPhone()+"' , "
//			+ "'"+ m.getAddress()+"' , "
//			+ "'"+ m.getHobby()+"' , "
//			+ " sysdate )";
		
		System.out.println(sql);
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "student", "student");
			System.out.println("conn=" + conn);// 성공하면 connection 값, 실패하면 null값
	//		stmt = conn.createStatement();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getPassword());
			pstmt.setString(3, m.getUserName());
			pstmt.setString(4, m.getGender());
			pstmt.setInt(5, m.getAge());
			pstmt.setString(6, m.getEmail());
			pstmt.setString(7, m.getPhone());
			pstmt.setString(8, m.getAddress());
			pstmt.setString(9, m.getHobby());
			
			conn.setAutoCommit(false);
	//		result = stmt.executeUpdate(sql);//처리한 행의 갯수를 리턴(int형)
			result = pstmt.executeUpdate();
			
			if(result > 0)	conn.commit();//적용
			else			conn.rollback();//되돌리기

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				conn.setAutoCommit(true);
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}

	public int updateMember(Member m) {
		int result = 0;
		Connection conn = null;// DB에 연결할 객체
		PreparedStatement pstmt = null;// 실행할 쿼리
		
		String sql = "UPDATE MEMBER SET "
			+ " PASSWORD = ? , "
			+ " EMAIL = ? , "	
			+ " PHONE =? , "
			+ " ADDRESS = ? "
			+ " WHERE USERID = ?";
			
		
		System.out.println(sql);
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "student", "student");
			System.out.println("conn=" + conn);// 성공하면 connection 값, 실패하면 null값
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getPassword());
			pstmt.setString(2, m.getEmail());
			pstmt.setString(3, m.getPhone());
			pstmt.setString(4, m.getAddress());
			pstmt.setString(5, m.getUserId());
			
			conn.setAutoCommit(false);
			result = pstmt.executeUpdate();//처리한 행의 갯수를 리턴(int형)
			
			if(result > 0) conn.commit();//적용
			else conn.rollback();//되돌리기

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}

	public int deleteMember(String memberId) {
		int result = 0;
		Connection conn = null;// DB에 연결할 객체
		PreparedStatement pstmt = null;// 실행할 쿼리
		
		String sql = "DELETE FROM MEMBER WHERE USERID = ?";
		
		System.out.println(sql);
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "student", "student");
			System.out.println("conn=" + conn);// 성공하면 connection 값, 실패하면 null값
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			conn.setAutoCommit(false);
			result = pstmt.executeUpdate(sql);//처리한 행의 갯수를 리턴(int형)
			
			if(result > 0) conn.commit();//적용
			else conn.rollback();//되돌리기

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}
}
