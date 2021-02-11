package member.view;

import java.util.List;
import java.util.Scanner;

import member.controller.MemberController;
import member.model.vo.Member;

public class MemberMenu {

	private MemberController memberController = new MemberController();
	Scanner sc = new Scanner(System.in);

	public void mainMenu() {
		String menu = "==========회원 관리 프로그램==========\n" + "1.회원 전체조회\n" + "2.회원 아이디조회\n" + "3.회원 이름조회\n" + "4.회원 가입\n"
				+ "5.회원 정보변경\n" + "6.회원탈퇴\n" + "0. 프로그램 끝내기\n" + "---------------\n" + "선택 : ";

		while (true) {
			System.out.print(menu);
			int choice = sc.nextInt();
			Member member = null;
			int result = 0;
			String msg = null;
			List<Member> list = null;
			String memberId = null;
			String memberName = null;

			switch (choice) {
			case 1:
				list = memberController.selectAll();
				displayMemberList(list);
				break;
			case 2:
				memberId = inputMemberId(); // 조회할 아이디 입력받기
				member = memberController.selectOne(memberId); // 전달해서 확인하기작업
				displayMember(member); // 출력문
				break;
			case 3:
				memberName = inputMemberName();// 조회할 이름 입력받기
				list = memberController.selectOne2(memberName);
				displayMemberList(list); // 출력문
				break;
			case 4:
				// 1. 신규회원정보 입력 - > Member객체
				member = inputMember();
				System.out.println("신규회원 확인 : " + member);
				// 2. controller에 회원가입 요청(메소드 호출) ->int리턴(처리된 행의 개수)
				result = memberController.insertMember(member);
				// 3. int에 따른 분기처리(성공여부 메세지 호출하겠다는 소리.)
				msg = result > 0 ? "회원 가입 성공!" : "회원 가입 실패!";
				displayMsg(msg);

				break;
			case 5: // 회원정보변경
				member = updateMember();
				System.out.println("변경 회원 확인 : " + member);
				result = memberController.updateMember(member);
				// 3. int에 따른 분기처리(성공여부 메세지 호출하겠다는 소리.)
				msg = result > 0 ? "회원 변경 성공!" : "회원 변경 실패!";
				displayMsg(msg);

				break;
			case 6: // 회원탈퇴
				member = deleteMember();
				System.out.println("삭제 회원 확인 : " + member);
				result = memberController.deleteMember(member);
				// 3. int에 따른 분기처리(성공여부 메세지 호출하겠다는 소리.)
				msg = result > 0 ? "회원 삭제 성공!" : "회원 삭제 실패! 일치하는 회원이 없습니다";
				displayMsg(msg);

				break;
			case 0:
				System.out.println("정말로 끝내시겠습니까?(y/n) : ");
				if (sc.next().charAt(0) == 'y')
					return;// 현재메소드(mainMenu)를 호출한곳으로 돌아감.
				break;
			default:
				System.out.println("잘못 입력하셨습니다");
			}
		}
	}

	/*
	 * 회원삭제
	 */
	private Member deleteMember() {
		System.out.println("삭제할 회원아이디와 비밀번호를 입력하세요.");
		Member member = new Member();
		System.out.print("아이디 : ");
		member.setMemberId(sc.next());
		System.out.print("비밀번호 : ");
		member.setPassword(sc.next());
		return member;
	}

	/*
	 * 회원정보변경
	 */
	private Member updateMember() {
		System.out.println("변경할 회원아이디를 입력하세요.");
		Member member = new Member();
		System.out.print("아이디 : ");
		member.setMemberId(sc.next());
		System.out.print("이름 : ");
		member.setMemberName(sc.next());
		System.out.print("비밀번호 : ");
		member.setPassword(sc.next());
		System.out.print("나이 : ");
		member.setAge(sc.nextInt());
		System.out.print("성별(M/F) : ");// m, f
		member.setGender(String.valueOf(sc.next().toUpperCase().charAt(0)));
		System.out.print("이메일: ");
		member.setEmail(sc.next());
		System.out.print("전화번호(-빼고 입력) : ");
		member.setPhone(sc.next());
		sc.nextLine();// 버퍼에 남은 개행문자 날리기용 (next계열 - nextLine)
		System.out.print("주소 : ");
		member.setAddress(sc.nextLine());
		System.out.print("취미(,로 나열할것) : ");
		member.setHobby(sc.nextLine());
		return member;
	}

	/*
	 * DB에서 이름으로 조회한 1명의 회원 입력
	 */
	private String inputMemberName() {
		System.out.println("조회할 이름 입력 : ");
		return sc.next();
	}

	/*
	 * DB에서 조회한 1명의 회원 출력
	 */
	private void displayMember(Member member) {
		if (member == null) {
			System.out.println(">>>>조회된 회원이 없습니다");

		} else {
			System.out.println("************************************************");
			System.out.println(member);
			System.out.println("************************************************");
		}
	}

	/*
	 * 조회할 회원아이디 입력
	 * 
	 */
	private String inputMemberId() {
		System.out.println("조회할 아이디 입력 : ");
		return sc.next();
	}

	/*
	 * DB에서 조회된 회원객체 N개를 출력하는 메소드
	 */
	private void displayMemberList(List<Member> list) {
		if (list == null || list.isEmpty()) {
			System.out.println(">>>>조회된 행이 없습니다");

		} else {
			System.out.println(
					"*****************************************************************************************************************************************");
			for (Member m : list) {
				System.out.println(m);
			}
			System.out.println(
					"*****************************************************************************************************************************************");
		}

	}

	/*
	 * DML 처리결과 통보용
	 */
	private void displayMsg(String msg) {
		System.out.println(">>> 처리결과 : " + msg);
	}

	/*
	 * 신규회원 정보 입력
	 */
	private Member inputMember() {
		System.out.println("새로운 회원정보를 입력하세요.");
		Member member = new Member();
		System.out.print("아이디 : ");
		member.setMemberId(sc.next());
		System.out.print("이름 : ");
		member.setMemberName(sc.next());
		System.out.print("비밀번호 : ");
		member.setPassword(sc.next());
		System.out.print("나이 : ");
		member.setAge(sc.nextInt());
		System.out.print("성별(M/F) : ");// m, f
		member.setGender(String.valueOf(sc.next().toUpperCase().charAt(0)));
		System.out.print("이메일: ");
		member.setEmail(sc.next());
		System.out.print("전화번호(-빼고 입력) : ");
		member.setPhone(sc.next());
		sc.nextLine();// 버퍼에 남은 개행문자 날리기용 (next계열 - nextLine)
		System.out.print("주소 : ");
		member.setAddress(sc.nextLine());
		System.out.print("취미(,로 나열할것) : ");
		member.setHobby(sc.nextLine());
		return member;
	}

}
