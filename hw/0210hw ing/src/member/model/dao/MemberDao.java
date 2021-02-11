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

/*
 * DAO
 * Data Access Object
 * DB에 접근하는 클래스
 * 
 * 이 순서대로 작성 필요.
 * 1. 드라이버 클래스 등록(최초 1회만 하면됨)
 * 2. Connection 객체 생성(url, user, password 필요)
 * 3. 자동커밋여부 설정: true/false true는 기본값 true로 하면 자동커밋됨. 
 * 		false로 해서 app에서 직접 트랜잭션 할예정
 * 4. PreparedStatement 객체생성(미완성쿼리로 preparedStatement생성예정) 및 값대입
 * 5. Statement 객체 실행.DB에 쿼리요청
 * 6. 응답처리 DML : int리턴,  DQL:ResultSet리턴 ->다시 자바객체로 변환작업 필요
 * 7. 트랜잭션처리(DML)
 * 8. 자원반납(생성의 역순)
 * 
 */

//db에 접근하는 메소드 insert 하는곳
public class MemberDao {
	String driverClass = "oracle.jdbc.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";//dbDriver타입 @아이피주소or도메인:포트번호orDB버전
	String user = "student";
	String password = "student";

	public int insertMember(Member member) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "insert into member values(?, ?, ?, ?, ?, ?, ?, ?, ?, default)"; //안에 세미콜론 절대 찍지말것.
		int result = 0;
		
//		 1. 드라이버 클래스 등록(최초 1회만 하면됨)
		try {
			Class.forName(driverClass);
			
//		 2. Connection 객체 생성(url, user, password 필요)
			conn = DriverManager.getConnection(url, user, password);
//		 3. 자동커밋여부 설정(DML): true/false true는 기본값 true로 하면 자동커밋됨.
//		 	false로 해서 app에서 직접 트랜잭션 할예정
			conn.setAutoCommit(false);
//		 4. PreparedStatement 객체생성(미완성쿼리로 preparedStatement생성예정) 및 값대입
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
//		 5. Statement 객체 실행.DB에 쿼리요청
//		 6. 응답처리 DML : int리턴,  DQL:ResultSet리턴 ->다시 자바객체로 변환작업 필요
			result = pstmt.executeUpdate();//dml=qurrey dql=update 두종류밖에없음 제대로 처리됐다면 result에 1 아니면 0
			
			
//		 7. 트랜잭션처리(DML)
			if(result > 0)
				conn.commit();
			else 
				conn.rollback();
			
		} catch (ClassNotFoundException e) {
			//catch 절에 오면 ojdbc6.jar프로젝트와 연동이 실패된것.
			e.printStackTrace();
		} //클래스등록
		catch (SQLException e) {
			e.printStackTrace();
		}finally {
//		 8. 자원반납(생성의 역순)
			try {
				if(pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	//dql을 한다. 단순조회.
	public List<Member> selectAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select * from member order by enroll_date desc"; //절대 세미콜론 찍지말것.
		List<Member> list = null;
		
		try {
			//1. 드라이버 클래스 등록(최초 1회만 하면됨)
			Class.forName(driverClass);
			//2. Connection 객체 생성(url, user, password 필요)
			//3. 자동커밋여부 설정: true/false true는 기본값 true로 하면 자동커밋됨. 
			conn = DriverManager.getConnection(url, user, password);
			//false로 해서 app에서 직접 트랜잭션 할예정
			//4. PreparedStatement 객체생성(미완성쿼리로 preparedStatement생성예정) 및 값대입
			pstmt = conn.prepareStatement(sql); // 물음표가 없다 값대입을 안해도 된다. 채워넣을게 없다는뜻.
			//5. Statement 객체 실행.DB에 쿼리요청
			rset = pstmt.executeQuery(); //미리대입했기 때문에 sql 없는버젼?
			//6. 응답처리 DML : int리턴,  DQL:ResultSet리턴 ->다시 자바객체로 변환작업 필요
			//테이블이 리턴됐따고 생각. 한행한행을 읽어와서 맴버로 바꿔준다.그리고 맴버객체를 list에 추가한다.
			//다음행 존재여부리턴. 커서가 다음행에 접근
			list = new ArrayList<>();
			while(rset.next()) {
				//컬럼명은 대소문자를 구분하지 않는다.
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
			}//7. 트랜잭션처리(DQL일때 처리안함.)
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
	
		} finally {
			//8. 자원반납(생성의 역순)
			try {
				if(rset != null)
				 rset.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(pstmt != null)
				 pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
				 conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}

	//맴버 한명을 리턴
	public Member selectOne(String memberId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select * from member where member_id = ?";
		Member member = null;
		
		try {
			//1. 드라이버 클래스 등록(최초 1회만 하면됨)
			Class.forName(driverClass);
			//2. Connection 객체 생성(url, user, password 필요)
			//3. 자동커밋여부 설정: true/false true는 기본값 true로 하면 자동커밋됨. 
			conn = DriverManager.getConnection(url, user, password);
			//false로 해서 app에서 직접 트랜잭션 할예정
			//4. PreparedStatement 객체생성(미완성쿼리로 preparedStatement생성예정) 및 값대입
			pstmt = conn.prepareStatement(sql); // 물음표가 없다 값대입을 안해도 된다. 채워넣을게 없다는뜻.
			pstmt.setString(1, memberId);//select * from member where member_id = 'honggd'
			//5. Statement 객체 실행.DB에 쿼리요청
			rset = pstmt.executeQuery(); //미리대입했기 때문에 sql 없는버젼?
			//6. 응답처리 DML : int리턴,  DQL:ResultSet리턴 ->다시 자바객체로 변환작업 필요
			//테이블이 리턴됐따고 생각. 한행한행을 읽어와서 맴버로 바꿔준다.
			//다음행 존재여부리턴. 커서가 다음행에 접근
			while(rset.next()) {
				//컬럼명은 대소문자를 구분하지 않는다.
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
				}//7. 트랜잭션처리(DQL일때 처리안함.)
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
	
		} finally {
			//8. 자원반납(생성의 역순)
			try {
				if(rset != null)
				 rset.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(pstmt != null)
				 pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
				 conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return member;
	}

	
	
	//3. 이름조회는 이름 일부를 입력해도 조회가 될 수 있도록한다.
	public List<Member> selectOne2(String memberName) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rset = null;
			String sql = "select * from member where member_name like ?";
			List<Member> list = null;
			
			try {
				//1. 드라이버 클래스 등록(최초 1회만 하면됨)
				Class.forName(driverClass);
				//2. Connection 객체 생성(url, user, password 필요)
				//3. 자동커밋여부 설정: true/false true는 기본값 true로 하면 자동커밋됨. 
				conn = DriverManager.getConnection(url, user, password);
				//false로 해서 app에서 직접 트랜잭션 할예정
				//4. PreparedStatement 객체생성(미완성쿼리로 preparedStatement생성예정) 및 값대입
				pstmt = conn.prepareStatement(sql); // 물음표가 없다 값대입을 안해도 된다. 채워넣을게 없다는뜻.
				pstmt.setString(1, "%"+memberName+"%");//앞뒤에 %를 입력해줘 일부를 입력해도 검색되게끔
				//5. Statement 객체 실행.DB에 쿼리요청
				rset = pstmt.executeQuery(); //미리대입했기 때문에 sql 없는버젼?
				//6. 응답처리 DML : int리턴,  DQL:ResultSet리턴 ->다시 자바객체로 변환작업 필요
				//테이블이 리턴됐따고 생각. 한행한행을 읽어와서 맴버로 바꿔준다.
				//다음행 존재여부리턴. 커서가 다음행에 접근
				list = new ArrayList<>();
				while(rset.next()) {
					//컬럼명은 대소문자를 구분하지 않는다.
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
					
					Member member = new Member(memberId, password, memberName, gender, age, email, phone, address, hobby, enrollDate);
					list.add(member);
					}//7. 트랜잭션처리(DQL일때 처리안함.)
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
		
			} finally {
				//8. 자원반납(생성의 역순)
				try {
					if(rset != null)
					 rset.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					if(pstmt != null)
					 pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					if(conn != null)
					 conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return list;
	}

	
	
	public int updateMember(Member member) {
		return 0;
	}

}

//@실습문제
//3. 이름조회는 이름 일부를 입력해도 조회가 될 수 있도록한다.
//5. 회원정보변경은 암호, 이메일, 전화번호, 주소, 취미를 일괄변경하도록한다.
//6. 탈퇴는 delete처리한다.
