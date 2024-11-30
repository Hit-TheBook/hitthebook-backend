package dreamteam.hitthebook.common.commonutil;

import dreamteam.hitthebook.domain.member.enumulation.EmblemEnumlation;
import lombok.Data;

public record ConditionEmblem<T>(T condition, EmblemEnumlation emblem) {
}
