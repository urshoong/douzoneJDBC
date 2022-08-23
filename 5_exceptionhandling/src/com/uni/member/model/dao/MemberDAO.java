package com.uni.member.model.dao;

import static com.uni.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.uni.common.exception.MemberException;
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
	
	private Properties prop = null;
	
	public MemberDAO() {
		prop = new Properties();
		try {
//			prop.load(new FileReader("resources/query.properties"));
			prop.loadFromXML(new FileInputStream("mapper/query.xml"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Member> selectAll(Connection conn) throws MemberException {
		ArrayList<Member> list = null;
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
//		String sql = "SELECT * FROM MEMBER";//자동으로 세미콜론 붙여서 실행
		String sql = prop.getProperty("selectAll");
		
		System.out.println(sql);
		try {
//			3. 쿼리문을 실행할 statement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
//			4. 쿼리문을 전송, 실행한 결과를 resultset으로 받기.
			rset = pstmt.executeQuery();
			
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
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new MemberException("selectAll " + e.getMessage());
		} finally {
			try {
				close(rset);
				close(pstmt);
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		
		return list;
	}

	public Member selectOne(Connection conn, String memberId) throws MemberException {
		Member m = null;
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
//		String sql = "SELECT * FROM MEMBER WHERE USERID = ?";
		String sql = prop.getProperty("selectOne");
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			
//			4. 쿼리문을 전송, 실행한 결과를 resultset으로 받기.
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
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new MemberException("selectOne " + e.getMessage());
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return m;
	}

	public List<Member> selectByName(Connection conn, String memberName) throws MemberException {
		ArrayList<Member> list = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;// Select 한후 결과값 받아올 객체

//		String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE ?";// 자동으로 세미콜론을 붙여 실행되므로 붙히지않는다
		String sql = prop.getProperty("selectByName");

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+memberName+"%");
			
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

		} catch (Exception e) {
			e.printStackTrace();
			throw new MemberException("selectByName " + e.getMessage());
		} finally {
			close(rset);
			close(pstmt);
		}

		return list;
	}

	public int insertMember(Connection conn, Member m) throws MemberException {
		
		int result = 0;
		PreparedStatement pstmt = null;
		
//		String sql = "INSERT INTO MEMBER VALUES(?,?,?,?,?,?,?,?,?,sysdate)";
		String sql = prop.getProperty("insertMember");
		
		try {
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
			
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw new MemberException("insertMember " + e.getMessage());
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	public int updateMember(Connection conn, Member m) throws MemberException {
		int result = 0;
		PreparedStatement pstmt = null;// 실행할 쿼리
		
//		String sql = "UPDATE MEMBER SET PASSWORD = ? ,EMAIL = ? ,PHONE =? ,ADDRESS = ? WHERE USERID = ?";
		String sql = prop.getProperty("updateMember");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getPassword());
			pstmt.setString(2, m.getEmail());
			pstmt.setString(3, m.getPhone());
			pstmt.setString(4, m.getAddress());
			pstmt.setString(5, m.getUserId());
			
			conn.setAutoCommit(false);
			result = pstmt.executeUpdate();//처리한 행의 갯수를 리턴(int형)
			
		} catch (Exception e) {
			throw new MemberException("updateMember " + e.getMessage());
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	public int deleteMember(Connection conn, String memberId) throws MemberException {
		int result = 0;
		PreparedStatement pstmt = null;// 실행할 쿼리
		
//		String sql = "DELETE FROM MEMBER WHERE USERID = ?";
		String sql = prop.getProperty("deleteMember");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			result = pstmt.executeUpdate();//처리한 행의 갯수를 리턴(int형)

		} catch (Exception e) {
			throw new MemberException("deleteMember " + e.getMessage());
		}finally {
			close(pstmt);
		}
		
		return result;
	}
}
