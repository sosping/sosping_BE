package sosping.be.domain.beach.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sosping.be.domain.beach.domain.BeachData;
import sosping.be.domain.beach.dto.BeachDataDescriptionDTO;
import sosping.be.domain.beach.dto.BeachNameDTO;
import sosping.be.domain.beach.dto.BeachRecommendationDTO;
import sosping.be.domain.beach.service.BeachDataService;
import sosping.be.global.aspect.LogMonitoring;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Beach")
@RequestMapping("api/beach")
public class BeachRecommenderController {
    private final BeachDataService beachDataService;

    private final Logger LOGGER = LoggerFactory.getLogger(BeachRecommenderController.class);

    @Operation(summary = "현재 시간 기준 장소 추천", description = "현재 일자 시각 기준 데이터를 제공한다.")
    @LogMonitoring
    @GetMapping("/recommendation/now")
    public ResponseEntity<List<BeachRecommendationDTO>> getTodayBeachRecommendation() {
        List<BeachRecommendationDTO> recommendationDTOS = beachDataService.recommendBeachToday();

        return ResponseEntity.ok(recommendationDTOS);
    }


    @Operation(summary = "특정일 장소 추천", description = "2024-05-18T12:00:00 와 같은 형식으로 입력하면 해당 날 이후 데이터 제공")
    @LogMonitoring
    @GetMapping("/recommendation/{date_time}")
    public ResponseEntity<List<BeachRecommendationDTO>> getTodayBeachRecommendation(
            @PathVariable(name = "date_time") String dateTime) {

        LOGGER.info(dateTime);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime localDateTime;
        try {
            localDateTime = LocalDateTime.parse(dateTime, formatter);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }

        List<BeachRecommendationDTO> recommendationDTOS = beachDataService.recommendBeachByDateTime(localDateTime);
        return ResponseEntity.ok(recommendationDTOS);
    }


    // 해변 목록
    @Operation(summary = "지역 목록")
    @GetMapping("/locations")
    public ResponseEntity<List<String>> getBeachLocationNames() {
        return ResponseEntity.ok(beachDataService.findBeachLocationNames());

    }

    // 해변별 해안
    @Operation(summary = "지역별 해안")
    @GetMapping("/locations/{locationName}/beach")
    public ResponseEntity<List<BeachNameDTO>> getBeachNames(@PathVariable(value = "locationName") String locationName) {
        return ResponseEntity.ok(beachDataService.findBeachNames(locationName));

    }

    // 해안 기상 정보
    @Operation(summary = "해안 기상 정보")
    @GetMapping("location/{locationName}/beach/{beachName}/data")
    public ResponseEntity<BeachDataDescriptionDTO> getBeachData(@PathVariable String locationName,
                                                                @PathVariable String beachName) {
        return ResponseEntity.ok(beachDataService.findBeachDateByLocationBeachName(locationName, beachName));
    }
}
