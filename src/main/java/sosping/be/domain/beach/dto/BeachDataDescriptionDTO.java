package sosping.be.domain.beach.dto;

import sosping.be.domain.beach.domain.BeachData;

public record BeachDataDescriptionDTO(
        BeachData beachData,
        String descriptionWind,
        String descriptionWave

) {
}
