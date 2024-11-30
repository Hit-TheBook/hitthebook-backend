package dreamteam.hitthebook.configuration;

import dreamteam.hitthebook.common.commonutil.Level;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class LevelConfig {
    @Bean
    public List<Level> levels() {
        return List.of(
                new Level(1, "BLUE Ⅴ", 0, 199),
                new Level(2, "BLUE Ⅳ", 200, 399),
                new Level(3, "BLUE Ⅲ", 400, 599),
                new Level(4, "BLUE Ⅱ", 600, 799),
                new Level(5, "BLUE Ⅰ", 800, 999),
                new Level(6, "GREEN Ⅴ", 1000, 1499),
                new Level(7, "GREEN Ⅳ", 1500, 1000),
                new Level(8, "GREEN Ⅲ", 2000, 2499),
                new Level(9, "GREEN Ⅱ", 2500, 2999),
                new Level(10, "GREEN Ⅰ", 3000, 3499),
                new Level(11, "YELLOW Ⅴ", 3500, 4499),
                new Level(12, "YELLOW Ⅳ", 4500, 5499),
                new Level(13, "YELLOW Ⅲ", 5500, 6499),
                new Level(14, "YELLOW Ⅱ", 6500, 7499),
                new Level(15, "YELLOW Ⅰ", 7500, 8499),
                new Level(16, "ORANGE Ⅴ", 8500, 10999),
                new Level(17, "ORANGE Ⅳ", 11000, 13499),
                new Level(18, "ORANGE Ⅲ", 13500, 15999),
                new Level(19, "ORANGE Ⅱ", 16000, 18499),
                new Level(20, "ORANGE Ⅰ", 18500, 20999),
                new Level(21, "RED Ⅴ", 21000, 25999),
                new Level(22, "RED Ⅳ", 26000, 30999),
                new Level(23, "RED Ⅲ", 31000, 35999),
                new Level(24, "RED Ⅱ", 36000, 40999),
                new Level(25, "RED Ⅰ", 41000, 999999999)
        );
    }
}
