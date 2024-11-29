package dreamteam.hitthebook.domain.member.helper;

import dreamteam.hitthebook.common.exception.DuplicateNicknameException;
import dreamteam.hitthebook.common.exception.ResourceNotFoundException;
import dreamteam.hitthebook.common.util.Level;
import dreamteam.hitthebook.domain.member.entity.Emblem;
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

    public Member findMemberByEmailId(String emailId){
        return memberRepository.findByEmailId(emailId).orElseThrow(ResourceNotFoundException::new);
    }

    public NicknameDto toNicknameDto(Member member){
        return new NicknameDto(member.getNickname());
    }

    public void updateNewNickname(String nickname, Member member) {
        member.setNickname(nickname);
        memberRepository.save(member);
    }

    // 중복된 닉네임이 존재한다면 450번 상태코드
    public void findSameNickname(String nickname){
        if(memberRepository.findByNickname(nickname).isPresent()){throw new DuplicateNicknameException();}
    }

    public void checkLevelRange(int level){
        if(level < 1 || level > 25){throw new ResourceNotFoundException();}
    }

    public Level findLevelByPoints(int points) {
        return levels.stream()
                .filter(level -> points >= level.getMinPoint() && points <= level.getMaxPoint())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Point data is invalid"));
    }

    public void updateMemberLevel(Member member) {
        int points = member.getPoint();
        Level level = findLevelByPoints(points);
        member.setLevel(level.getLevel());
        memberRepository.save(member);
    }

    public LevelDto toLevelDto(Member member){
        int memberLevel = member.getLevel();
        checkLevelRange(memberLevel);
        Level level = levels.get(memberLevel - 1);
        return new LevelDto(member.getPoint(), memberLevel, level.getLevelName(), level.getMinPoint(), level.getMaxPoint());
    }

    public List<Inventory> findEmblemOfMember(Member member){
        return inventoryRepository.findByMember(member);
    }

    public EmblemDto toEmblemDto(Member member){
        return new EmblemDto(findEmblemOfMember(member));
    }

}
