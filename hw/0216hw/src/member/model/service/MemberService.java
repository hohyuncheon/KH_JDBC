package member.model.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import common.JDBCTemplate;
import member.model.dao.MemberDao;
import member.model.vo.Member;
import static common.JDBCTemplate.*;//스테틱 메소드를 클래스. 으로 안쓰기 위해 만들어준 임포트

/*
 * 1번째 프로잭트에서 DAO
 * 1. DriverClass 등록(최초1회)
 * 2. Connection 객체생성 url, user, password
 * 2.1 자동커밋 false 설정
 * 3. PreparedStatement 객체생성(미완성쿼리 = ?가 있는쿼리)
 * 3.1 ?가 있다면 값대입
 * 4. 실행 : DML(executeUpdate) -> int ,   DQL(executeQuery) -> ResultSet 으로 리턴처리.
 * 4.1 ResultSet을 -> Java객체 옮겨담기
 * 5. 트랜잭션(DML)(분기처리, commit/rollback)
 * 6. 자원반납(생성역순 rset - pstmt - conn)
 * 
 * 
 * 2번째 프로잭트에선(이번 프로젝트에서)
 * 
 * Service
 * 1. DriverClass 등록(최초1회)
 * 2. Connection 객체생성 url, user, password
 * 2.1 자동커밋 false 설정
 * 
 * ----------Dao 요청-------------
 * 6. 트랜잭션(DML)(분기처리, commit/rollback)
 * 7. 자원반납(생성역순 rset - pstmt - conn)
 * 
 * 
 * Dao

 * 3. PreparedStatement 객체생성(미완성쿼리 = ?가 있는쿼리)
 * 3.1 ?가 있다면 값대입
 * 4. 실행 : DML(executeUpdate) -> int ,   DQL(executeQuery) -> ResultSet 으로 리턴처리.
 * 4.1 ResultSet을 -> Java객체 옮겨담기
 * 5. 자원반납(생성역순 rset - pstmt - conn)
 * 
 * 
 */
public class MemberService {

	private MemberDao memberDao = new MemberDao();

	// 서비스 부분을 간략하게 만들어 쓰는방법.
	public List<Member> selectAll() {
		Connection conn = getConnection();
		List<Member> list = memberDao.selectAll(conn); // dao 요청
		close(conn); // 자원반납
		return list;
	}

	/*
	 * Service 1. DriverClass 등록(최초1회) 2. Connection 객체생성 url, user, password 2.1
	 * 자동커밋 false 설정
	 * 
	 * ----------Dao 요청------------- 6. 트랜잭션(DML)(분기처리, commit/rollback) 7.
	 * 자원반납(생성역순 rset - pstmt - conn)
	 */

//	public List<Member> selectAll_() {
//		String driverClass = "oracle.jdbc.OracleDriver";
//		String url = "jdbc:oracle:thin:@localhost:1521:xe";
//		String user = "student";
//		String password = "student";
//		Connection conn = null;
//		List<Member> list = null;
//
//		try {
//			// 1. DriverClass 등록(최초1회)
//			Class.forName(driverClass);
//			// 2. Connection 객체생성 url, user, password
//			conn = DriverManager.getConnection(url, user, password);
//
//			// 2.1 자동커밋 false 설정
//			conn.setAutoCommit(false);// 해도되고안해도됨. 작성안해도 자동커밋됨.
//
//			// ----------Dao 요청-------------
//			// Connection객체 전달
//			list = memberDao.selectAll(conn);// conn전달하는게 중요
//			// 6. 트랜잭션(DML)(분기처리, commit/rollback)
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//
//		} finally {
//
//			// 7. 자원반납(생성역순 rset - pstmt - conn)
//			try {
//				if (conn != null)
//					conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return list;
//	}

	public int insertMember(Member member) {
		Connection conn = getConnection();
		int result = memberDao.insertMember(conn, member);
		if (result > 0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}

	public Member selectId(String memberId) {
		Connection conn = getConnection();
		Member member = memberDao.selectId(conn, memberId); // dao 요청
		close(conn); // 자원반납
		return member;
	}

	
	public Member selectName(String memberName) {
		Connection conn = getConnection();
		Member member = memberDao.selectName(conn, memberName); // dao 요청
		close(conn); // 자원반납
		return member;
	}

	public int updatePassword(String updateMemberId, Member member) {
		Connection conn = getConnection();
		int result = memberDao.updatePassword(conn, updateMemberId, member);
		if (result > 0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}

	public int updateMemberEmail(String updateMemberId, Member member) {
		Connection conn = getConnection();
		int result = memberDao.updateMemberEmail(conn, updateMemberId, member);
		if (result > 0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}

	public int updateMemberPhone(String updateMemberId, Member member) {
		Connection conn = getConnection();
		int result = memberDao.updateMemberPhone(conn, updateMemberId, member);
		if (result > 0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}

	public int updateMemberAddress(String updateMemberId, Member member) {
		Connection conn = getConnection();
		int result = memberDao.updateMemberAddress(conn, updateMemberId, member);
		if (result > 0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}

	public int deleteMember(Member member) {
		Connection conn = getConnection();
		int result = memberDao.deleteMember(conn, member);
		if (result > 0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}

}
