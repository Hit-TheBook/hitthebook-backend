package dreamteam.hitthebook.domain.member.dto;

import dreamteam.hitthebook.domain.member.entity.Emblem;
import dreamteam.hitthebook.domain.member.entity.Inventory;
import dreamteam.hitthebook.domain.member.enumulation.EmblemEnumlation;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MemberDto {

    @Data
    @NoArgsConstructor
    public static class NicknameDto{
        public String nickname;
        public NicknameDto(String nickname){
            this.nickname = nickname;
        }
    }

    @Data
    @NoArgsConstructor
    public static class LevelDto{
        private int point;
        private int level;
        private String levelName;
        private int minPoint;
        private int maxPoint;
        public LevelDto(int point, int level, String levelName, int minPoint, int maxPoint){
            this.point = point;
            this.level = level;
            this.levelName = levelName;
            this.minPoint = minPoint;
            this.maxPoint = maxPoint;
        }
    }

    @Data
    @NoArgsConstructor
    public static class EmblemDto{
        private List<EmblemDtoContent> emblemDtoContents = new ArrayList<>();
        public EmblemDto(List<Inventory> inventorys){
            for(Inventory inventory : inventorys){
                emblemDtoContents.add(new EmblemDtoContent(inventory));
            }
        }
    }

    // emblemContent와 헷깔리지 않도록 DtoContent적용
    @Data
    @NoArgsConstructor
    public static class EmblemDtoContent{
        private Long emblemId;
        private EmblemEnumlation emblemName;
        private String emblemContent;
        private LocalDateTime emblemCreateAt;
        public EmblemDtoContent(Inventory inventory){
            this.emblemId = inventory.getEmblem().getEmblemId();
            this.emblemName = inventory.getEmblem().getEmblemName();
            this.emblemContent = inventory.getEmblem().getEmblemContent();
            this.emblemCreateAt = inventory.getCreatedAt();
        }
    }
}
