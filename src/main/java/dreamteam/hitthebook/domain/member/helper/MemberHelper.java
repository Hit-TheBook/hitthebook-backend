package dreamteam.hitthebook.domain.member.helper;

import dreamteam.hitthebook.common.exception.DuplicateNicknameException;
import dreamteam.hitthebook.common.exception.ResourceNotFoundException;
import dreamteam.hitthebook.common.commonutil.Level;
import dreamteam.hitthebook.domain.member.entity.Inventory;
import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.member.repository.EmblemRepository;
import dreamteam.hitthebook.domain.member.repository.InventoryRepository;
import dreamteam.hitthebook.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static dreamteam.hitthebook.domain.member.dto.MemberDto.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberHelper {
    private final MemberRepository memberRepository;
    private final List<Level> levels;
    private final EmblemRepository emblemRepository;
    private final InventoryRepository inventoryRepository;

    // 이메일아이디로 멤버검색
    public Member findMemberByEmailId(String emailId){
        return memberRepository.findByEmailId(emailId).orElseThrow(ResourceNotFoundException::new);
    }

    // 닉네임dto 변환 로직
    public NicknameDto toNicknameDto(Member member){
        return new NicknameDto(member.getNickname());
    }

    // 닉네임 갱신
    public void updateNewNickname(String nickname, Member member) {
        member.setNickname(nickname);
        memberRepository.save(member);
    }

    // 중복된 닉네임이 존재한다면 450번 상태코드
    public void findSameNickname(String nickname){
        if(memberRepository.findByNickname(nickname).isPresent()){throw new DuplicateNicknameException();}
    }

    // 레벨이 유효한 범위에 있는지 검색
    public void checkLevelRange(int level){
        if(level < 1 || level > 25){throw new ResourceNotFoundException();}
    }

    // 레벨이 포인트에 맞는 조건의 레벨 검색
    public Level findLevelByPoints(int points) {
        return levels.stream()
                .filter(level -> points >= level.getMinPoint() && points <= level.getMaxPoint())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Point data is invalid"));
    }

    // 레벨dto 변환 로직
    public LevelDto toLevelDto(Member member){
        int memberLevel = member.getLevel();
        checkLevelRange(memberLevel);
        Level level = levels.get(memberLevel - 1);
        return new LevelDto(member.getPoint(), memberLevel, level.getLevelName(), level.getMinPoint(), level.getMaxPoint());
    }

    // 멤버가 가진 엠블럼 검색
    public List<Inventory> findEmblemOfMember(Member member){
        return inventoryRepository.findByMember(member);
    }

    // 엠블럼dto 변환 로직
    public EmblemDto toEmblemDto(Member member){
        return new EmblemDto(findEmblemOfMember(member));
    }

}
