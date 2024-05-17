package sosping.be.domain.beach.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import sosping.be.domain.beach.domain.BeachData;
import sosping.be.domain.beach.repository.BeachDataRepository;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BeachRecommender {
    private final BeachDataRepository beachDataRepository;

    private final static Double minWaveHeight = 0.5;
    private final static Double maxWaveHeight = 1.0;
    private final static Double maxTideHeight = 1.0;
    private final static Double maxWindSpeed = 4.0;
    public List<BeachData> recommenderByDate(LocalDateTime localDateTime) {
        Date date = Date.valueOf(localDateTime.toLocalDate());
        Time time = Time.valueOf(LocalTime.of(localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond()));

        return beachDataRepository
                .findBeachData(
                        date,
                        time,
                        minWaveHeight,
                        maxWaveHeight,
                        maxTideHeight,
                        maxWindSpeed,
                        PageRequest.of(0, 3));
    }
}
