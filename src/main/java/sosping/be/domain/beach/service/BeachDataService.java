package sosping.be.domain.beach.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sosping.be.domain.beach.domain.BeachData;
import sosping.be.domain.beach.domain.RecommendationLevel;
import sosping.be.domain.beach.dto.BeachDataDescriptionDTO;
import sosping.be.domain.beach.dto.BeachNameDTO;
import sosping.be.domain.beach.dto.BeachRecommendationDTO;
import sosping.be.domain.beach.repository.BeachDataRepository;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
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

    public BeachDataDescriptionDTO findBeachDateByLocationBeachName(String locationName, String beachName) {
        LocalDate currentDate = LocalDate.now();
        LocalTime targetTime = LocalTime.of(12, 0);

        Date date = Date.valueOf(currentDate);
        Time time = Time.valueOf(targetTime);

        BeachData beachData = beachDataRepository.findByLocationNameANDBeachNameAndTime(locationName, beachName, date, time);
        String descriptionWind = "";
        String descriptionWave = "";

        if (beachData.getWaveHeight() < 0.5) {
            descriptionWave = "무릎정도 높이의 파도가 쳐요";
        } else if (beachData.getWaveHeight() < 1) {
            descriptionWave = "무릎 ~ 허리정도 높이의 파도가 쳐요";

        } else {
            descriptionWave = "허리 높이 이상의 파도가 쳐요";
        }

        if (beachData.getWindSpeed() < 4) {
            descriptionWind = "바람이 거의 없어요";
        } else if (beachData.getWindSpeed() < 7) {
            descriptionWind = "약간의 바람이 불어요";
        } else if (beachData.getWindSpeed() < 10) {
            descriptionWind = "바람이 조금 강하게 불어요";
        } else if (beachData.getWindSpeed() < 14) {
            descriptionWind = "강한 바람이 불어요";
        } else {
            descriptionWind = "매우 강한 바람이 불어요";
        }

        return new BeachDataDescriptionDTO(beachData, descriptionWind, descriptionWave);
    }
}
