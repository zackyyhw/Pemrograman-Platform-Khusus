package com.polstat.perpustakaan.service;

import com.polstat.perpustakaan.dto.MemberDto;
import java.util.List;

public interface MemberService {
    MemberDto createMember(MemberDto memberDto);
    MemberDto updateMember(MemberDto memberDto);
    void deleteMember(Long id); 
    List<MemberDto> getMembers(String memberID);  
    MemberDto getMemberById(Long id); 
}
