package mocks;

import java.time.LocalDate;


public class MetricsRecord {
    private final double airTemp;
    private final double atmosphericPressure;
    private final LocalDate date;
    private final String day;

    private final double gustSpeed;
    private final double precipitation;

    private final double relativeHumidity;

    private final double solar;

    private final double strikeDistance;

    private final double strikes;

    private final double vapourPressure;

    private final double windDirection;

    private final double windSpeed;

    public MetricsRecord(double temp, double atmosphere, LocalDate date, String day, double gustSpeed, double precipitation, double relativeHumidity, double solar, double strikeDistance, double strikes, double vapourPressure, double windDirection, double windSpeed) {
        this.day = day;
        this.airTemp = temp;
        this.atmosphericPressure = atmosphere;
        this.date = date;
        this.gustSpeed = gustSpeed;
        this.precipitation = precipitation;
        this.relativeHumidity = relativeHumidity;
        this.solar = solar;
        this.strikeDistance = strikeDistance;
        this.strikes = strikes;
        this.vapourPressure = vapourPressure;
        this.windDirection = windDirection;
        this.windSpeed = windSpeed;
    }

    public double getAirTemp() {
        return airTemp;
    }

    public double getAtmosphericPressure() {
        return atmosphericPressure;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }
    public double getGustSpeed() {
        return gustSpeed;
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public double getRelativeHumidity() {
        return relativeHumidity;
    }

    public double getSolar() {
        return solar;
    }

    public double getStrikeDistance() {
        return strikeDistance;
    }

    public double getStrikes() {
        return strikes;
    }

    public double getVapourPressure() {
        return vapourPressure;
    }

    public double getWindDirection() {
        return windDirection;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    @Override
    public String toString() {
        return "MetricsRecord{" +
                "airTemp=" + airTemp +
                ", atmosphericPressure=" + atmosphericPressure +
                ", date=" + date +
                ", day='" + day + '\'' +
                ", gustSpeed=" + gustSpeed +
                ", precipitation=" + precipitation +
                ", relativeHumidity=" + relativeHumidity +
                ", solar=" + solar +
                ", strikeDistance=" + strikeDistance +
                ", strikes=" + strikes +
                ", vapourPressure=" + vapourPressure +
                ", windDirection=" + windDirection +
                ", windSpeed=" + windSpeed +
                '}';
    }
}
