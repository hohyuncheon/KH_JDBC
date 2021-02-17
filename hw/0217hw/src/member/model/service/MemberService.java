package member.model.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import common.JDBCTemplate;
import member.model.dao.MemberDao;
import member.model.vo.Member;
import static common.JDBCTemplate.*;//스테틱 메소드를 클래스. 으로 안쓰기 위해 만들어준 임포트

public class MemberService {

	private MemberDao memberDao = new MemberDao();

	// 서비스 부분을 간략하게 만들어 쓰는방법.
	public List<Member> selectAll() {
		Connection conn = getConnection();
		List<Member> list = memberDao.selectAll(conn); // dao 요청
		close(conn); // 자원반납
		return list;
	}


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


	public List<Member> selectDeleteAll() {
		Connection conn = getConnection();
		List<Member> list = memberDao.selectDeleteAll(conn); // dao 요청
		close(conn); // 자원반납
		return list;
	}

}
