package com.polstat.perpustakaan.service;

import com.polstat.perpustakaan.dto.MemberDto;
import com.polstat.perpustakaan.entity.Member;
import com.polstat.perpustakaan.mapper.MemberMapper;
import com.polstat.perpustakaan.repository.MemberRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public MemberDto createMember(MemberDto memberDto) {
        Member member = memberRepository.save(MemberMapper.mapToMember(memberDto));
        return MemberMapper.mapToMemberDto(member);
    }

    @Override
    public MemberDto updateMember(MemberDto memberDto) {
        Member member = memberRepository.save(MemberMapper.mapToMember(memberDto));
        return MemberMapper.mapToMemberDto(member);
    }

    @Override
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with ID: " + id));
        memberRepository.delete(member);
    }
    

    @Override
    public List<MemberDto> getMembers(String memberID) {
        if (memberID == null || memberID.isEmpty()) {
            List<Member> members = (List<Member>) memberRepository.findAll();
            return members.stream()
                    .map(MemberMapper::mapToMemberDto)
                    .collect(Collectors.toList());
        } else {
            List<Member> members = memberRepository.findByMemberID(memberID);
            return members.stream()
                    .map(MemberMapper::mapToMemberDto)
                    .collect(Collectors.toList());
        }
    }

    public MemberDto getMemberById(Long id) {
        Optional<Member> memberOptional = memberRepository.findById(id);
        return memberOptional.map(MemberMapper::mapToMemberDto)
                             .orElse(null); // Return null if not found
    }
}
