package sosping.be.domain.beach.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.sql.Date;
import java.sql.Time;

@ToString @Getter
@Entity
@Table(name = "beach_data")
public class BeachData {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "beach_data_id")
    private Long beachDataId;

    @Column(name = "date")
    private Date date;

    @Column(name = "time")
    private Time time;

    @Column(name = "wind_speed")
    private Double windSpeed;

    @Column(name = "sky_code")
    private Integer skyCode;

    @Column(name = "sky_status")
    private String skyStatus;

    @Column(name = "wave_height")
    private Double waveHeight;

    @Column(name = "wave_period")
    private Double wavePeriod;

    @Column(name = "tide_height")
    private Double tideHeight;

    @Column(name = "tide_time")
    private Time tideTime;

    @Column(name = "location_name")
    private String locationName;

    @Column(name = "beach_name")
    private String beachName;

    @Builder
    public BeachData(Long beachDataId, Date date, Time time, Double windSpeed, Integer skyCode, String skyStatus, Double waveHeight, Double wavePeriod, Double tideHeight, Time tideTime, String locationName, String beachName) {
        this.beachDataId = beachDataId;
        this.date = date;
        this.time = time;
        this.windSpeed = windSpeed;
        this.skyCode = skyCode;
        this.skyStatus = skyStatus;
        this.waveHeight = waveHeight;
        this.wavePeriod = wavePeriod;
        this.tideHeight = tideHeight;
        this.tideTime = tideTime;
        this.locationName = locationName;
        this.beachName = beachName;
    }

    public BeachData() {

    }
}