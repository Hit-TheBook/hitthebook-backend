package dreamteam.hitthebook.common.commonutil;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Level {
    private int level;
    private String levelName;
    private int minPoint;
    private int maxPoint;
    public Level(int level, String levelName, int minPoint, int maxPoint) {
        this.level = level;
        this.levelName = levelName;
        this.minPoint = minPoint;
        this.maxPoint = maxPoint;
    }
}
