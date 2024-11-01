package com.polstat.perpustakaan.controller;

import com.polstat.perpustakaan.dto.MemberDto;
import com.polstat.perpustakaan.service.MemberService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MemberGraphqlController {
    @Autowired
    private MemberService memberService;

    @QueryMapping
    public List<MemberDto> members() {
        return memberService.getMembers(null); // Pass null to get all members
    }

    @QueryMapping
    public MemberDto memberById(@Argument String memberID) {
        List<MemberDto> members = memberService.getMembers(memberID);
        if (members.isEmpty()) {
            return null; // or handle not found case as per your application's requirement
        }
        return members.get(0);
    }

    @MutationMapping
    public MemberDto createMember(@Argument String memberID, @Argument String name,
            @Argument String address, @Argument String phoneNumber) {
        MemberDto memberDto = MemberDto.builder()
                .memberID(memberID)
                .name(name)
                .address(address)
                .phoneNumber(phoneNumber)
                .build();
        return memberService.createMember(memberDto);
    }

    @MutationMapping
    public MemberDto updateMember(@Argument Long id,
            @Argument String memberID,
            @Argument String name,
            @Argument String address,
            @Argument String phoneNumber) {
        MemberDto memberDto = memberService.getMemberById(id);
        if (memberDto == null) {
            throw new RuntimeException("Member not found with ID: " + id);
        }

        memberDto.setMemberID(memberID); // Optional: if you want to update memberID too
        memberDto.setName(name);
        memberDto.setAddress(address);
        memberDto.setPhoneNumber(phoneNumber);
        return memberService.updateMember(memberDto);
    }

    @MutationMapping
    public String deleteMember(@Argument Long id) {
        try {
            memberService.deleteMember(id);
            return "Member with ID " + id + " deleted successfully.";
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to delete member: " + e.getMessage());
        }
    }
}
