package sosping.be.domain.beach.dto;

import sosping.be.domain.beach.domain.BeachData;
import sosping.be.domain.beach.domain.RecommendationLevel;

public record BeachRecommendationDTO(
        BeachData beachData,
        RecommendationLevel recommendationLevel,
        java.sql.Time recommendationTime
) {
}
