package sosping.be.domain.beach.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sosping.be.domain.beach.domain.BeachData;
import sosping.be.domain.beach.domain.RecommendationLevel;
import sosping.be.domain.beach.dto.BeachNameDTO;
import sosping.be.domain.beach.dto.BeachRecommendationDTO;
import sosping.be.domain.beach.repository.BeachDataRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BeachDataService {
    private final BeachRecommender beachRecommender;
    private final BeachDataRepository beachDataRepository;

    public List<BeachRecommendationDTO> recommendBeachToday() {
        LocalDateTime localDateTime = LocalDateTime.now();

        if (localDateTime.toLocalTime().isAfter(LocalTime.of(21, 0))) {
            localDateTime = localDateTime.plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        }

        List<BeachData> beachDataList = beachRecommender.recommenderByDate(localDateTime);

        List<BeachRecommendationDTO> beachRecommendationDTOList = beachDataList.stream()
                .map(beachData -> {
                    return new BeachRecommendationDTO(beachData, RecommendationLevel.GOOD, beachData.getTime());
                })
                .toList();

        return beachRecommendationDTOList;
    }

    public List<BeachRecommendationDTO> recommendBeachByDateTime(LocalDateTime localDateTime) {
        List<BeachData> beachDataList = beachRecommender.recommenderByDate(localDateTime);

        List<BeachRecommendationDTO> beachRecommendationDTOList = beachDataList.stream()
                .map(beachData -> {
                    return new BeachRecommendationDTO(beachData, RecommendationLevel.GOOD, beachData.getTime());
                })
                .toList();

        return beachRecommendationDTOList;
    }

    public List<String> findBeachLocationNames() {
        return beachDataRepository.findLocationName();
    }

    public List<BeachNameDTO> findBeachNames(String locationName) {
        return beachDataRepository.findBeachNames(locationName);
    }
}
