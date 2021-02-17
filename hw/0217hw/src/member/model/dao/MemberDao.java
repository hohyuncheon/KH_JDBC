package member.model.dao;

//스테틱 메소드를 클래스. 으로 안쓰기 위해 만들어준 임포트
import static common.JDBCTemplate.close;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import member.model.exception.MemberException;
import member.model.vo.Member;

public class MemberDao {
	
	private Properties prop = new Properties();
	
	/*
	 * MemberDao 객체 생성시 (최초 1회) member-query.properties의 내용을 읽어다  prop에 저장한다
	 * 
	 * dao메소드 호출시마다 prop으로부터 쿼리를 꺼내 가져다 사용한다.
	 */
	
	public MemberDao() {
		String fileName = "resources/member-query.properties";
		String selectAll;
		String insertMember;
		try {
			prop.load(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public List<Member> selectAll(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectAll");
		List<Member> list = null;
		
		
		try {
			//3. PreparedStatement 객체생성(미완성쿼리 = ?가 있는쿼리)
			pstmt = conn.prepareStatement(sql);
			//3.1 ?가 있다면 값대입
			//4. 실행 : DML(executeUpdate) -> int ,   DQL(executeQuery) -> ResultSet 으로 리턴처리.
			rset = pstmt.executeQuery();
			//4.1 ResultSet을 -> Java객체 옮겨담기
			list = new ArrayList<>();
			while(rset.next()) {
				String memberId = rset.getString("member_id");
				String password = rset.getString("password");
				String memberName = rset.getString("member_name");
				String gender = rset.getString("gender");
				int age = rset.getInt("age");
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("address");
				String hobby = rset.getString("hobby");
				Date enrollDate = rset.getDate("enroll_date");
				Member member = new Member(memberId, password, memberName, gender, age, email, phone, address, hobby, enrollDate);
				list.add(member);
				
			}
		} catch (SQLException e) {
			//e.printStackTrace();
			//예외를 전환하여 던진다
			//RuntimeExceptionm, 의미분명한 커스텀 예외객체로 전환
			throw new MemberException("회원 전체 조회", e);
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public int insertMember(Connection conn, Member member) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertMember");

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getGender());
			pstmt.setInt(5, member.getAge());
			pstmt.setString(6, member.getEmail());
			pstmt.setString(7, member.getPhone());
			pstmt.setString(8, member.getAddress());
			pstmt.setString(9, member.getHobby());
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new MemberException("회원 가입", e);
		}
		return result;
	}

	public Member selectId(Connection conn, String memberId) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectId");
		Member member = null;
		
		try {
			//3. PreparedStatement 객체생성(미완성쿼리 = ?가 있는쿼리)
			pstmt = conn.prepareStatement(sql);
			//3.1 ?가 있다면 값대입
			pstmt.setString(1, memberId);//select * from member where member_id = 'honggd'
			//4. 실행 : DML(executeUpdate) -> int ,   DQL(executeQuery) -> ResultSet 으로 리턴처리.
			rset = pstmt.executeQuery();
			//4.1 ResultSet을 -> Java객체 옮겨담기
			while(rset.next()) {
				memberId = rset.getString("member_id");
				String password = rset.getString("password");
				String memberName = rset.getString("member_name");
				String gender = rset.getString("gender");
				int age = rset.getInt("age");
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("address");
				String hobby = rset.getString("hobby");
				Date enrollDate = rset.getDate("enroll_date");
				
				member = new Member(memberId, password, memberName, gender, age, email, phone, address, hobby, enrollDate);
			}
		} catch (SQLException e) {
			throw new MemberException("회원 아이디 조회", e);
			
		} finally {
			//5. 자원반납(생성역순 rset - pstmt)
			close(rset);
			close(pstmt);
		}
		return member;
	}

	public Member selectName(Connection conn, String memberName) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectName");
		Member member = null;
		
		try {
			//3. PreparedStatement 객체생성(미완성쿼리 = ?가 있는쿼리)
			pstmt = conn.prepareStatement(sql);
			//3.1 ?가 있다면 값대입
			pstmt.setString(1, "%"+memberName+"%");
			//4. 실행 : DML(executeUpdate) -> int ,   DQL(executeQuery) -> ResultSet 으로 리턴처리.
			rset = pstmt.executeQuery();
			//4.1 ResultSet을 -> Java객체 옮겨담기
			while(rset.next()) {
				String memberId = rset.getString("member_id");
				String password = rset.getString("password");
				memberName = rset.getString("member_name");
				String gender = rset.getString("gender");
				int age = rset.getInt("age");
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("address");
				String hobby = rset.getString("hobby");
				Date enrollDate = rset.getDate("enroll_date");
				
				member = new Member(memberId, password, memberName, gender, age, email, phone, address, hobby, enrollDate);
			}
		} catch (SQLException e) {
			throw new MemberException("회원 이름 조회", e);			
		//자원반납
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return member;
	}

	//updateMemberId와 일치하는 맴버 비밀번호 변경
	
	public int updatePassword(Connection conn, String updateMemberId, Member member) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("updatePassword");
		int result = 0;
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getPassword());
			pstmt.setString(2, updateMemberId);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new MemberException("비밀번호 변경", e);
		}
		
		return result;
	}

	//updateMemberId와 일치하는 맴버 이메일 변경
	public int updateMemberEmail(Connection conn, String updateMemberId, Member member) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("updateMemberEmail");
		int result = 0;
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getEmail());
			pstmt.setString(2, updateMemberId);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new MemberException("이메일 변경", e);
		}
		
		return result;
	}

	//updateMemberId와 일치하는 맴버 휴대폰번호 변경
	public int updateMemberPhone(Connection conn, String updateMemberId, Member member) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("updateMemberPhone");
		int result = 0;
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getPhone());
			pstmt.setString(2, updateMemberId);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new MemberException("휴대폰번호 변경", e);
		}
		
		return result;
	}

	//updateMemberId와 일치하는 주소 변경
	public int updateMemberAddress(Connection conn, String updateMemberId, Member member) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("updateMemberAddress");
		int result = 0;
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getAddress());
			pstmt.setString(2, updateMemberId);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new MemberException("주소 변경", e);
		}
		
		return result;
	}

	//ID와 비밀번호가 일치하는 행 삭제

	public int deleteMember(Connection conn, Member member) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("deleteMember");
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getPassword());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new MemberException("회원삭제", e);
		}
		
		return result;
	}


	public List<Member> selectDeleteAll(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectDeleteAll");
		List<Member> list = null;
		
		
		try {
			//3. PreparedStatement 객체생성(미완성쿼리 = ?가 있는쿼리)
			pstmt = conn.prepareStatement(sql);
			//3.1 ?가 있다면 값대입
			//4. 실행 : DML(executeUpdate) -> int ,   DQL(executeQuery) -> ResultSet 으로 리턴처리.
			rset = pstmt.executeQuery();
			//4.1 ResultSet을 -> Java객체 옮겨담기
			list = new ArrayList<>();
			while(rset.next()) {
				String memberId = rset.getString("member_id");
				String password = rset.getString("password");
				String memberName = rset.getString("member_name");
				String gender = rset.getString("gender");
				int age = rset.getInt("age");
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("address");
				String hobby = rset.getString("hobby");
				Date enrollDate = rset.getDate("enroll_date");
				Date deleteDate = rset.getDate("del_date");
				Member member = new Member(memberId, password, memberName, gender, age, email, phone, address, hobby, enrollDate, deleteDate);
				list.add(member);
				
			}
		} catch (SQLException e) {
			//e.printStackTrace();
			//예외를 전환하여 던진다
			//RuntimeExceptionm, 의미분명한 커스텀 예외객체로 전환
			throw new MemberException("회원 전체 조회", e);
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

}
