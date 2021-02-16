package member.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import member.model.vo.Member;
import static common.JDBCTemplate.*;//스테틱 메소드를 클래스. 으로 안쓰기 위해 만들어준 임포트

public class MemberDao {

	/*
	 * Dao
	 * 3. PreparedStatement 객체생성(미완성쿼리 = ?가 있는쿼리)
	 * 3.1 ?가 있다면 값대입
	 * 4. 실행 : DML(executeUpdate) -> int ,   DQL(executeQuery) -> ResultSet 으로 리턴처리.
	 * 4.1 ResultSet을 -> Java객체 옮겨담기
	 * 5. 자원반납(생성역순 rset - pstmt - conn)
	 * 
	 */
	
	public List<Member> selectAll(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select * from member order by enroll_date desc";
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
			e.printStackTrace();
		} finally {
//		 //5. 자원반납(생성역순 rset - pstmt - conn)
//			try {
//				if(rset != null)
//					rset.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			try {
//				if(pstmt != null)
//					pstmt.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public int insertMember(Connection conn, Member member) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "insert into member values(?, ?, ?, ?, ?, ?, ?, ?, ?, default)"; //안에 세미콜론 절대 찍지말것.

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
			e.printStackTrace();
		}
		
		
		
		
		return result;
	}

	public Member selectId(Connection conn, String memberId) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select * from member where member_id = ?";
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
			e.printStackTrace();
			
		//자원반납
		} finally {
			close(rset);
			close(pstmt);
		}
		return member;
	}

	public Member selectName(Connection conn, String memberName) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select * from member where member_name like ?";
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
			e.printStackTrace();
			
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
		String sql = "update member set password =? where member_id = ?"; //안에 세미콜론 절대 찍지말것.
		int result = 0;
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getPassword());
			pstmt.setString(2, updateMemberId);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	//updateMemberId와 일치하는 맴버 이메일 변경
	public int updateMemberEmail(Connection conn, String updateMemberId, Member member) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "update member set Email =? where member_id = ?"; //안에 세미콜론 절대 찍지말것.
		int result = 0;
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getEmail());
			pstmt.setString(2, updateMemberId);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	//updateMemberId와 일치하는 맴버 휴대폰번호 변경
	public int updateMemberPhone(Connection conn, String updateMemberId, Member member) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "update member set Phone =? where member_id = ?"; //안에 세미콜론 절대 찍지말것.
		int result = 0;
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getPhone());
			pstmt.setString(2, updateMemberId);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	//updateMemberId와 일치하는 주소 변경
	public int updateMemberAddress(Connection conn, String updateMemberId, Member member) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "update member set Address =? where member_id = ?"; //안에 세미콜론 절대 찍지말것.
		int result = 0;
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getAddress());
			pstmt.setString(2, updateMemberId);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	//ID와 비밀번호가 일치하는 행 삭제

	public int deleteMember(Connection conn, Member member) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "delete member where member_id = ? and password = ?"; //안에 세미콜론 절대 찍지말것.
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getPassword());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
