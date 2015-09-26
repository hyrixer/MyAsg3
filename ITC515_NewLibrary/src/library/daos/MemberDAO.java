package library.daos;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import library.interfaces.daos.IMemberDAO;
import library.interfaces.daos.IMemberHelper;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

public class MemberDAO implements IMemberDAO {

	
	int nextId;
	IMemberHelper helper;
	Map<Integer, IMember> memberMap = new HashMap<Integer, IMember>();
	
	public MemberDAO (IMemberHelper helper) {
		if (helper == null) {
			throw new IllegalArgumentException("helper cannot be null");
		} else {
			this.helper = helper;
		}
	}
	
	@Override
	public IMember addMember(String firstName, String lastName,
			String ContactPhone, String emailAddress) {
		// TODO Auto-generated method stub
		IMember newMember = helper.makeMember(firstName, lastName, ContactPhone, emailAddress, nextId);
		memberMap.put(nextId, newMember);
		nextId++;
		
		return newMember;
		
	}

	@Override
	public IMember getMemberByID(int id) {
		// TODO Auto-generated method stub
		IMember member = memberMap.get(id);
		if (member == null) {
			return null;
		}
		return member;
	}

	@Override
	public List<IMember> listMembers() {
		// TODO Auto-generated method stub
		List <IMember> memberList = new LinkedList<IMember>();
		for (int i = 0; i < memberMap.size(); i++) {
			memberList.add(memberMap.get(i));
		}
		return memberList;
	}

	@Override
	public List<IMember> findMembersByLastName(String lastName) {
		// TODO Auto-generated method stub
		List <IMember> memberList = new LinkedList<IMember>();
		for (int i = 0; i < memberMap.size(); i++) {
			IMember member = memberMap.get(i);
			if (member.getLastName() == lastName) {
				memberList.add(member);
			}
		}
		return memberList;
		
	}

	@Override
	public List<IMember> findMembersByEmailAddress(String emailAddress) {
		// TODO Auto-generated method stub
		List <IMember> memberList = new LinkedList<IMember>();
		for (int i = 0; i < memberMap.size(); i++) {
			IMember member = memberMap.get(i);
			if (member.getEmailAddress() == emailAddress) {
				memberList.add(member);
			}
		}
		return memberList;
	}

	@Override
	public List<IMember> findMembersByNames(String firstName, String lastName) {
		// TODO Auto-generated method stub
		List <IMember> memberList = new LinkedList<IMember>();
		for (int i = 0; i < memberMap.size(); i++) {
			IMember member = memberMap.get(i);
			if (member.getFirstName() == firstName && member.getLastName() == lastName) {
				memberList.add(member);
			}
		}
		return memberList;
	}

}
