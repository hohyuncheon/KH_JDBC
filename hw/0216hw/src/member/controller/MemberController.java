package member.controller;

import java.util.List;

import member.model.service.MemberService;
import member.model.vo.Member;

public class MemberController {
	
	private MemberService memberService = new MemberService();

	
	
	public List<Member> selectAll() {
		return memberService.selectAll();
	}
	
	public int insertMember(Member member) {
		return memberService.insertMember(member);
	}

	public Member selectId(String memberId) {
		return memberService.selectId(memberId);
	}

	public Member selectName(String memberName) {
		return memberService.selectName(memberName);
	}

	public int updateMemberPassword(String updateMemberId, Member member) {
		return memberService.updatePassword(updateMemberId, member);
	}

	public int updateMemberEmail(String updateMemberId, Member member) {
		return memberService.updateMemberEmail(updateMemberId, member);
	}

	public int updateMemberPhone(String updateMemberId, Member member) {
		return memberService.updateMemberPhone(updateMemberId, member);
	}

	public int updateMemberAddress(String updateMemberId, Member member) {
		// TODO Auto-generated method stub
		return memberService.updateMemberAddress(updateMemberId, member);
	}

	public int deleteMember(Member member) {
		return memberService.deleteMember(member);
	}

}
