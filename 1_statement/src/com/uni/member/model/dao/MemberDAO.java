package com.uni.member.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
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
		ResultSet rs = null;
		
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
			rs = stmt.executeQuery(sql);
			
//			5. 받은 결과값을 객체에 옮겨서 저장하기
			list = new ArrayList<Member>();
			
			while(rs.next()) {
				Member m = new Member();
				m.setUserId(rs.getString("USERID"));
				m.setPassword(rs.getString("PASSWORD"));
				m.setUserName(rs.getString("USERNAME"));
				m.setGender(rs.getString("GENDER"));
				m.setAge(rs.getInt("AGE"));
				m.setEmail(rs.getString("EMAIL"));
				m.setPhone(rs.getString("PHONE"));
				m.setAddress(rs.getString("ADDRESS"));
				m.setHobby(rs.getString("HOBBY"));
				m.setEnrollDate(rs.getDate("ENROLLDATE"));
				
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
				rs.close();
				stmt.close();
				conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		
		return list;
	}

	public Member selectOne(String memberId) {
		Member m = null;
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USERID = '" + memberId + "'";
		
		
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
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				m = new Member();
				m.setUserId(rs.getString("USERID"));
				m.setPassword(rs.getString("PASSWORD"));
				m.setUserName(rs.getString("USERNAME"));
				m.setGender(rs.getString("GENDER"));
				m.setAge(rs.getInt("AGE"));
				m.setEmail(rs.getString("EMAIL"));
				m.setPhone(rs.getString("PHONE"));
				m.setAddress(rs.getString("ADDRESS"));
				m.setHobby(rs.getString("HOBBY"));
				m.setEnrollDate(rs.getDate("ENROLLDATE"));
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e2) {
				// TODO: handle exception
			}
		}
		
		return m;
	}
	
	public int insertMember(Member m) {
		
		int result = 0;
		Connection conn = null;// DB에 연결할 객체
		Statement stmt = null;// 실행할 쿼리
		
		String sql = "INSERT INTO MEMBER VALUES("
			+ "'"+ m.getUserId()+"' , "
			+ "'"+ m.getPassword()+"' , "	
			+ "'"+ m.getUserName()+"' , "
			+ "'"+ m.getGender()+"' , "
			+ m.getAge()+","
			+ "'"+ m.getEmail()+"' , "
			+ "'"+ m.getPhone()+"' , "
			+ "'"+ m.getAddress()+"' , "
			+ "'"+ m.getHobby()+"' , "
			+ " sysdate )";
		
		System.out.println(sql);
		
		try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		System.out.println("드라이버 등록성공");
	
		conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "student", "student");
		System.out.println("conn=" + conn);// 성공하면 connection 값, 실패하면 null값
		stmt = conn.createStatement();
		conn.setAutoCommit(false);
		result = stmt.executeUpdate(sql);//처리한 행의 갯수를 리턴(int형)
		
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
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		return result;
	}

	public List<Member> selectByName(String memberName) {
		ArrayList<Member> list = null;
		Connection conn = null;// DB에 연결할 객체
		Statement stmt = null;// 실행할 쿼리
		ResultSet rset = null;// Select 한후 결과값 받아올 객체

		String sql = "SELECT * FROM MEMBER WHERE USERNAME = '" + memberName + "'";// 자동으로 세미콜론을 붙여 실행되므로 붙히지않는다

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
		stmt = conn.createStatement();

		// 4.쿼리문 전송, 실행결과를 ResultSet 으로 받기
		rset = stmt.executeQuery(sql);

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
			rset.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}

		return list;
	}

	public int updateMember(Member m) {
		int result = 0;
		Connection conn = null;// DB에 연결할 객체
		Statement stmt = null;// 실행할 쿼리
		
		String sql = "UPDATE MEMBER SET "
			+ " PASSWORD = '"+ m.getPassword()+"' , "
			+ " EMAIL = '"+ m.getEmail()+"' , "	
			+ " PHONE ='"+ m.getPhone()+"' , "
			+ " ADDRESS = '"+ m.getAddress()+"' "
			+ " WHERE USERID = '"+ m.getUserId()+"'";
			
		
		System.out.println(sql);
		
		try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		System.out.println("드라이버 등록성공");
	
		conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "student", "student");
		System.out.println("conn=" + conn);// 성공하면 connection 값, 실패하면 null값
		stmt = conn.createStatement();
		conn.setAutoCommit(false);
		result = stmt.executeUpdate(sql);//처리한 행의 갯수를 리턴(int형)
		
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
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		return result;
	}

	public int deleteMember(String userId) {
		int result = 0;
		Connection conn = null;// DB에 연결할 객체
		Statement stmt = null;// 실행할 쿼리
		
		String sql = "DELETE FROM MEMBER WHERE USERID = '"+ userId +"'";
			
		
		System.out.println(sql);
		
		try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		System.out.println("드라이버 등록성공");
	
		conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "student", "student");
		System.out.println("conn=" + conn);// 성공하면 connection 값, 실패하면 null값
		stmt = conn.createStatement();
		conn.setAutoCommit(false);
		result = stmt.executeUpdate(sql);//처리한 행의 갯수를 리턴(int형)
		
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
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		return result;
	}

}
