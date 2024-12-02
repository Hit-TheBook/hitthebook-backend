package dreamteam.hitthebook.domain.member.service;

import dreamteam.hitthebook.common.jwt.JwtTokenProvider;
import dreamteam.hitthebook.domain.member.dto.LoginDto;
import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.member.helper.LoginHelper;
import dreamteam.hitthebook.domain.member.helper.MemberHelper;
import dreamteam.hitthebook.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static dreamteam.hitthebook.domain.member.dto.MemberDto.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberHelper memberHelper;

    public NicknameDto getNickname(String emailId) {
        Member member = memberHelper.findMemberByEmailId(emailId);
        return memberHelper.toNicknameDto(member);
    }

    public void modifyNickname(String emailId, String nickname) {
        Member member = memberHelper.findMemberByEmailId(emailId);
        memberHelper.updateNewNickname(nickname, member);
    }

    public void isSameNickname(String nickname){
        memberHelper.findSameNickname(nickname);
    }

    public LevelDto getLevel(String emailId){
        Member member = memberHelper.findMemberByEmailId(emailId);
        return memberHelper.toLevelDto(member);
    }

    public EmblemDto getEmblem(String emailId){
        Member member = memberHelper.findMemberByEmailId(emailId);
        return memberHelper.toEmblemDto(member);
    }
}
