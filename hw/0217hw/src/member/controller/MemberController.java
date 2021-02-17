package member.controller;

import java.util.List;

import member.model.exception.MemberException;
import member.model.service.MemberService;
import member.model.vo.Member;
import member.view.MemberMenu;

public class MemberController {
	
	private MemberService memberService = new MemberService();

	
	//컨트롤러에서 예외를 잡는곳.
	public List<Member> selectAll() {
		List<Member> list = null;
		try {
			list = memberService.selectAll();
		} catch(MemberException e) {
			//추가로 나중에 할것(서버로깅,관리자 이메일 알림)
			
			new MemberMenu().displayError(e.getMessage() + " : 관리자에게 문의하세요.");
		}
		return list;
	}
	
	public int insertMember(Member member) {
		int result=0;
		try {
			result = memberService.insertMember(member);
			
		} catch(MemberException e) {
			new MemberMenu().displayError(e.getMessage() + " : 관리자에게 문의하세요.");
		}
		
		return result;
	}

	public Member selectId(String memberId) {
		Member member = null;
		try {
			member = memberService.selectId(memberId);
		} catch (MemberException e) {
			new MemberMenu().displayError(e.getMessage() + " : 관리자에게 문의하세요.");
		}
		
		return member;
	}

	public Member selectName(String memberName) {
		Member member = null;
		try {
			member = memberService.selectName(memberName);
		} catch (MemberException e) {
			new MemberMenu().displayError(e.getMessage() + " : 관리자에게 문의하세요.");
		}
		return member;
	}

	public int updateMemberPassword(String updateMemberId, Member member) {
		
		int result=0;
		try {
			result = memberService.updatePassword(updateMemberId, member);
			
		} catch(MemberException e) {
			new MemberMenu().displayError(e.getMessage() + " : 관리자에게 문의하세요.");
		}
		return result;
	}

	public int updateMemberEmail(String updateMemberId, Member member) {
		int result=0;
		try {
			result = memberService.updateMemberEmail(updateMemberId, member);
			
		} catch(MemberException e) {
			new MemberMenu().displayError(e.getMessage() + " : 관리자에게 문의하세요.");
		}
		return result;
	}

	public int updateMemberPhone(String updateMemberId, Member member) {
		int result=0;
		try {
			result = memberService.updateMemberPhone(updateMemberId, member);
		} catch(MemberException e) {
			new MemberMenu().displayError(e.getMessage() + " : 관리자에게 문의하세요.");
		}
		return result;
	}

	public int updateMemberAddress(String updateMemberId, Member member) {
		int result=0;
		try {
			result = memberService.updateMemberAddress(updateMemberId, member);
		} catch(MemberException e) {
			new MemberMenu().displayError(e.getMessage() + " : 관리자에게 문의하세요.");
		}
		return result;
	}

	public int deleteMember(Member member) {
		int result=0;
		try {
			result = memberService.deleteMember(member);
		} catch(MemberException e) {
			new MemberMenu().displayError(e.getMessage() + " : 관리자에게 문의하세요.");
		}
		return result;
	}

	//삭제회원 조회
	public List<Member> selectDeleteAll() {
		List<Member> list = null;
		try {
			list = memberService.selectDeleteAll();
		} catch(MemberException e) {
			new MemberMenu().displayError(e.getMessage() + " : 관리자에게 문의하세요.");
		}
		return list;
	}
}
