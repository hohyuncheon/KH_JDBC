package member.view;

import java.util.List;
import java.util.Scanner;

import member.controller.MemberController;
import member.model.vo.Member;

public class MemberMenu {
	private Scanner sc = new Scanner(System.in);
	private MemberController memberController = new MemberController();

	public void mainMenu() {
		String menu = "==========회원 관리 프로그램==========\n" 
					+ "1.회원 전체조회\n" 
					+ "2.회원 아이디조회\n" 
					+ "3.회원 이름조회\n" 
					+ "4.회원 가입\n"
					+ "5.회원 정보변경\n" 
					+ "6.회원탈퇴\n" + "0. 프로그램 끝내기\n" 
					+ "---------------\n" + "선택 : ";
		
		String update = "****** 회원 정보 변경 메뉴******\r\n" + 
						"1. 암호변경\r\n" + 
						"2. 이메일변경\r\n" + 
						"3. 전화번호변경\r\n" + 
						"4. 주소 변경\r\n" + 
						"9. 메인메뉴 돌아가기";
		
		Member member = null;
		int result = 0;
		String msg = null;
		String memberId = null;
		String memberName = null;
		String updateMemberId = null;
		
		do {
			System.out.println(menu);
			String choice = sc.next();
			List<Member> list = null;
			
			
			switch(choice) {
				//1.회원 전체조회
			case "1":
				list = memberController.selectAll();
				displayMemberList(list);
				break;
				//2.회원 아이디조회
			case "2": 
				memberId = inputMemberId();
				member = memberController.selectId(memberId);
				displayMember(member);
				break;
			case "3":
				//3.회원 이름조회
				memberName = inputMemberName();
				member = memberController.selectName(memberName);
				displayMember(member);
				break;
				//4.회원 가입
			case "4":
				member = inputMember();
				System.out.println("신규회원 확인 : "+ member);
				result = memberController.insertMember(member);
				msg = result > 0 ? "회원 가입 성공!" : "회원 가입 실패!";
				displayMsg(msg);
				break;
				
				
				//5.회원 정보변경
			case "5": 
				updateMemberId = inputUpdateMemberId();
				System.out.println(update);
				
				String choice2 = sc.next();
				switch (choice2) {
					case "1" : 
						System.out.println("***암호변경***");
						member = updateMemberPassword();
						result = memberController.updateMemberPassword(updateMemberId, member);
						// 3. int에 따른 분기처리(성공여부 메세지 호출하겠다는 소리.)
						msg = result > 0 ? "회원 변경 성공!" : "회원 변경 실패!";
						displayMsg(msg);
						break;
					case "2" :
						System.out.println("***이메일변경***");
						member = updateMemberEmail();
						result = memberController.updateMemberEmail(updateMemberId, member);
						// 3. int에 따른 분기처리(성공여부 메세지 호출하겠다는 소리.)
						msg = result > 0 ? "회원 변경 성공!" : "회원 변경 실패!";
						displayMsg(msg);
						break;
					case "3" :
						System.out.println("***전화번호변경***");
						member = updateMemberPhone();
						result = memberController.updateMemberPhone(updateMemberId, member);
						// 3. int에 따른 분기처리(성공여부 메세지 호출하겠다는 소리.)
						msg = result > 0 ? "회원 변경 성공!" : "회원 변경 실패!";
						displayMsg(msg);
						break;
					case "4" :
						System.out.println("***주소 변경***");
						member = updateMemberAddress();
						result = memberController.updateMemberAddress(updateMemberId, member);
						// 3. int에 따른 분기처리(성공여부 메세지 호출하겠다는 소리.)
						msg = result > 0 ? "회원 변경 성공!" : "회원 변경 실패!";
						displayMsg(msg);
						break;
					case "9" : 
						System.out.println("***메인메뉴 돌아가기***");
						break;
					default:
				}
				
				break;
				//6.회원탈퇴
			case "6": 
				member = deleteMember();
				System.out.println("삭제 회원 확인 : " + member);
				result = memberController.deleteMember(member);
				// 3. int에 따른 분기처리(성공여부 메세지 호출하겠다는 소리.)
				msg = result > 0 ? "회원 삭제 성공!" : "회원 삭제 실패! 일치하는 회원이 없습니다";
				displayMsg(msg);
				
				
				break;
				//종료
			case "0": 
				System.out.println("정말 끝내시겠습니까? (y/n) : ");
				if(sc.next().charAt(0) == 'y') {
					return;
				}
				break;
			default :
				System.out.println("잘못 입력하셨습니다");
			}
			
		}while(true);
		
	}
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
	 * 주소변경
	 */
	private Member updateMemberAddress() {
		Member member = new Member();
		System.out.print("변경할 비밀번호 : ");
		member.setAddress(sc.next());
		return member;
	}
	/*
	 * 휴대폰변경
	 */
	private Member updateMemberPhone() {
		Member member = new Member();
		System.out.print("변경할 휴대폰번호 : ");
		member.setPhone(sc.next());
		return member;
	}
	/*
	 * 이메일 변경
	 */
	private Member updateMemberEmail() {
		Member member = new Member();
		System.out.print("변경할 이메일 : ");
		member.setEmail(sc.next());
		return member;
	}

	/*
	 * 암호변경
	 */
	private Member updateMemberPassword() {
		Member member = new Member();
		System.out.print("변경할 암호 : ");
		member.setPassword(sc.next());
		return member;
	}
	/*
	 * 변경할 아이디 입력
	 */
	private String inputUpdateMemberId() {
		System.out.println("변경할 아이디 입력 : ");
		return sc.next();
	}

	/*
	 * 조회할 이름 입력
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

	//출력문
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

	/*
	 * n행의 회원정보 출력
	 */
	private void displayMemberList(List<Member> list) {
		if(list != null && !list.isEmpty()) {
			System.out.println("=============================================================================================================================================================");
			for (int i = 0; i < list.size(); i++) {
				 System.out.println((i + 1)+ " : " + list.get(i));
				 System.out.println("=============================================================================================================================================================");
			}
		}else {
			System.out.println(">>>>>조회된 회원정보가 없습니다");
			
			
		}
	}

}
