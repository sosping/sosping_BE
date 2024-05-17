package sosping.be.domain.beach.repository;

import org.springframework.data.domain.Pageable;
import sosping.be.domain.beach.domain.BeachData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sosping.be.domain.beach.dto.BeachNameDTO;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface BeachDataRepository extends JpaRepository<BeachData, Long> {

    @Query("SELECT b FROM BeachData b " +
            "WHERE b.date = :date " +
            "AND b.time > :time " +
            "AND b.waveHeight >= :minWaveHeight AND b.waveHeight <= :maxWaveHeight " +
            "AND b.tideHeight < :maxTideHeight " +
            "AND b.windSpeed < :maxWindSpeed " +
            "AND b.tideTime IS NULL " +
            "AND b.skyStatus LIKE '맑음' " +
            "ORDER BY b.skyCode DESC, b.time ASC")
    List<BeachData> findBeachData(
            @Param("date") Date date,
            @Param("time") Time time,
            @Param("minWaveHeight") Double minWaveHeight,
            @Param("maxWaveHeight") Double maxWaveHeight,
            @Param("maxTideHeight") Double maxTideHeight,
            @Param("maxWindSpeed") Double maxWindSpeed,
            Pageable pageable
    );

    @Query("SELECT b.locationName " +
            "FROM BeachData b " +
            "GROUP BY b.locationName ")
    List<String > findLocationName();

    @Query("SELECT new sosping.be.domain.beach.dto.BeachNameDTO(b.locationName, b.beachName) " +
            "FROM BeachData b " +
            "WHERE b.locationName = :locationName " +
            "GROUP BY b.beachName ")
    List<BeachNameDTO> findBeachNames(String locationName);

    @Query("SELECT b " +
            "FROM BeachData b " +
            "WHERE b.locationName = :locationName " +
            "AND b.beachName = :beachName " +
            "AND b.date = :date " +
            "AND b.time = :time")
    BeachData findByLocationNameANDBeachNameAndTime(@Param("locationName") String locationName,
                                                    @Param("beachName") String beachName,
                                                    @Param("date") Date date,
                                                    @Param("time") Time time);

}
