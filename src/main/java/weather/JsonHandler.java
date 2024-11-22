package weather;

import mocks.MetricsRecord;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class JsonHandler {
    public static JSONArray getJsonFile(String fileName) throws Exception {
        try (InputStream inputStream = JsonHandler.class.getResourceAsStream("/" + fileName)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File is not here: " + fileName);
            }
            String json = new Scanner(inputStream, StandardCharsets.UTF_8)
                    .useDelimiter("\\A")
                    .next();
            return new JSONArray(json);
        }
    }

    public static List<MetricsRecord> mapJsonArray(JSONArray ja) {
        List<MetricsRecord> list = new ArrayList<>();
        for (int i = 0; i < ja.length(); i++) {
            JSONObject jo = ja.getJSONObject(i);
            if (jo.isNull("airtemp")) {
                continue;
            }
            list.add(new MetricsRecord(
                    jo.getDouble("airtemp"),
                    jo.getDouble("atmosphericpressure"),
                    LocalDate.parse(jo.getString("time").split("T")[0]),
                    jo.getString("dayofweek"),
                    jo.getDouble("gustspeed"),
                    jo.getDouble("precipitation"),
                    jo.getDouble("relativehumidity"),
                    jo.getDouble("solar"),
                    jo.getDouble("strikedistance"),
                    jo.getDouble("strikes"),
                    jo.getDouble("vapourpressure"),
                    jo.getDouble("winddirection"),
                    jo.getDouble("windspeed")
            ));

        }
        return list;

    }

    public static String getDailyMetrics(List<MetricsRecord> records) {
        Map<LocalDate, List<MetricsRecord>> dateGroupsList = records.stream()
                .collect(Collectors.groupingBy(MetricsRecord::getDate));

        DoubleSummaryStatistics totalTemp = records.stream().mapToDouble(MetricsRecord::getAirTemp).boxed().collect(Collectors.summarizingDouble(d -> d));
        DoubleSummaryStatistics totalAtmos = records.stream().mapToDouble(MetricsRecord::getAtmosphericPressure).boxed().collect(Collectors.summarizingDouble(d -> d));
        DoubleSummaryStatistics totalGust = records.stream().mapToDouble(MetricsRecord::getGustSpeed).boxed().collect(Collectors.summarizingDouble(d -> d));
        DoubleSummaryStatistics totalPrecipitation = records.stream().mapToDouble(MetricsRecord::getPrecipitation).boxed().collect(Collectors.summarizingDouble(d -> d));
        DoubleSummaryStatistics totalHumidity = records.stream().mapToDouble(MetricsRecord::getRelativeHumidity).boxed().collect(Collectors.summarizingDouble(d -> d));
        DoubleSummaryStatistics totalSolar = records.stream().mapToDouble(MetricsRecord::getSolar).boxed().collect(Collectors.summarizingDouble(d -> d));
        DoubleSummaryStatistics totalSDistance = records.stream().mapToDouble(MetricsRecord::getStrikeDistance).boxed().collect(Collectors.summarizingDouble(d -> d));
        DoubleSummaryStatistics totalStrike = records.stream().mapToDouble(MetricsRecord::getStrikes).boxed().collect(Collectors.summarizingDouble(d -> d));
        DoubleSummaryStatistics totalVapour = records.stream().mapToDouble(MetricsRecord::getVapourPressure).boxed().collect(Collectors.summarizingDouble(d -> d));
        DoubleSummaryStatistics totalWindDirection = records.stream().mapToDouble(MetricsRecord::getWindDirection).boxed().collect(Collectors.summarizingDouble(d -> d));
        DoubleSummaryStatistics totalWindSpeed = records.stream().mapToDouble(MetricsRecord::getWindSpeed).boxed().collect(Collectors.summarizingDouble(d -> d));


        dateGroupsList.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    List<MetricsRecord> contentOfKey = entry.getValue();

                    DoubleSummaryStatistics onlyDoubleTemp = contentOfKey.stream().mapToDouble(MetricsRecord::getAirTemp).boxed().collect(Collectors.summarizingDouble(d -> d));
                    DoubleSummaryStatistics onlyDoubleAtmos = contentOfKey.stream().mapToDouble(MetricsRecord::getAtmosphericPressure).boxed().collect(Collectors.summarizingDouble(d -> d));
                    DoubleSummaryStatistics onlyDoubleGust = contentOfKey.stream().mapToDouble(MetricsRecord::getGustSpeed).boxed().collect(Collectors.summarizingDouble(e -> e));
                    DoubleSummaryStatistics onlyDoublePrecipitation = contentOfKey.stream().mapToDouble(MetricsRecord::getPrecipitation).boxed().collect(Collectors.summarizingDouble(e -> e));
                    DoubleSummaryStatistics onlyDoubleSolar = contentOfKey.stream().mapToDouble(d -> Math.ceil(d.getSolar())).boxed().collect(Collectors.summarizingDouble(e -> e));
                    DoubleSummaryStatistics onlyDoubleHumidity = contentOfKey.stream().mapToDouble(d -> Math.ceil(d.getRelativeHumidity())).boxed().collect(Collectors.summarizingDouble(e -> e));
                    DoubleSummaryStatistics onlyDoubleSDistance = contentOfKey.stream().mapToDouble(d -> Math.ceil(d.getStrikeDistance())).boxed().collect(Collectors.summarizingDouble(e -> e));
                    DoubleSummaryStatistics onlyDoubleStrikes = contentOfKey.stream().mapToDouble(d -> Math.ceil(d.getStrikes())).boxed().collect(Collectors.summarizingDouble(e -> e));
                    DoubleSummaryStatistics onlyDoubleVapour = contentOfKey.stream().mapToDouble(d -> Math.ceil(d.getVapourPressure())).boxed().collect(Collectors.summarizingDouble(e -> e));
                    DoubleSummaryStatistics onlyDoubleWindDirection = contentOfKey.stream().mapToDouble(d -> Math.ceil(d.getWindDirection())).boxed().collect(Collectors.summarizingDouble(e -> e));
                    DoubleSummaryStatistics onlyDoubleWindSpeed = contentOfKey.stream().mapToDouble(d -> Math.ceil(d.getWindSpeed())).boxed().collect(Collectors.summarizingDouble(e -> e));
//

                    System.out.printf("Date: %s, Day: %s%n " +
                                    "Average Air Temperature: %.2f, Max: %.2f, Min: %.2f%n " +
                                    "Average Atmospheric: %.2f, Max Atmos: %.2f, Min Atmos: %.2f%n" +
                                    "Average Gust Speed: %.2f, Max: %.2f, Min: %.2f%n" +
                                    "Average Precipitation: %.2f, Max: %.2f, Min: %.2f%n" +
                                    "Average Relative Humidity: %.2f, Max: %.2f, Min: %.2f%n" +
                                    "Average Solar: %.2f, Max: %.2f, Min: %.2f%n" +
                                    "Average Strike Distance: %.2f, Max: %.2f, Min: %.2f%n" +
                                    "Average Strike: %.2f, Max: %.2f, Min: %.2f%n" +
                                    "Average Vapour Pressure: %.2f, Max: %.2f, Min: %.2f%n" +
                                    "Average Wind Direction: %.2f, Max: %.2f, Min: %.2f%n" +
                                    "Average Wind Speed: %.2f, Max: %.2f, Min: %.2f%n" +
                                    "--------------------------------------------------------- %n",


                            entry.getKey(), entry.getKey().getDayOfWeek(),
                            onlyDoubleTemp.getAverage(), onlyDoubleTemp.getMax(), onlyDoubleTemp.getMin(),
                            onlyDoubleAtmos.getAverage(), onlyDoubleAtmos.getMax(), onlyDoubleAtmos.getMin(),
                            onlyDoubleGust.getAverage(), onlyDoubleGust.getMax(), onlyDoubleGust.getMin(),
                            onlyDoublePrecipitation.getAverage(), onlyDoublePrecipitation.getMax(), onlyDoublePrecipitation.getMin(),
                            onlyDoubleHumidity.getAverage(), onlyDoubleHumidity.getMax(), onlyDoubleHumidity.getMin(),
                            onlyDoubleSolar.getAverage(), onlyDoubleSolar.getMax(), onlyDoubleSolar.getMin(),
                            onlyDoubleSDistance.getAverage(), onlyDoubleSDistance.getMax(), onlyDoubleSDistance.getMin(),
                            onlyDoubleStrikes.getAverage(), onlyDoubleStrikes.getMax(), onlyDoubleStrikes.getMin(),
                            onlyDoubleVapour.getAverage(), onlyDoubleVapour.getMax(), onlyDoubleVapour.getMin(),
                            onlyDoubleWindDirection.getAverage(), onlyDoubleWindDirection.getMax(), onlyDoubleWindDirection.getMin(),
                            onlyDoubleWindSpeed.getAverage(), onlyDoubleWindSpeed.getMax(), onlyDoubleWindSpeed.getMin()
                    );
                });

        return String.format("Air Temp: %n Average: %.2f Min: %.2f Max: %.2f %n" +
                        "Atmospheric Pressure: %n Average: %.2f Min: %.2f Max: %.2f %n" +
                        "GustSpeed: %n Average: %.2f Min: %.2f Max: %.2f %n" +
                        "Precipitation: %n Average: %.2f Min: %.2f Max: %.2f %n" +
                        "Relative Humidity: %n Average: %.2f Min: %.2f Max: %.2f %n" +
                        "Solar: %n Average: %.2f Min: %.2f Max: %.2f %n" +
                        "Strike Distance: %n Average: %.2f Min: %.2f Max: %.2f %n" +
                        "Strike: %n Average: %.2f Min: %.2f Max: %.2f %n" +
                        "Vapour Pressure: %n Average: %.2f Min: %.2f Max: %.2f %n" +
                        "Wind Direction: %n Average: %.2f Min: %.2f Max: %.2f %n" +
                        "Wind Speed: %n Average: %.2f Min: %.2f Max: %.2f %n" +
                        "--------------------------------------------------------- %n",


                totalTemp.getAverage(), totalTemp.getMin(), totalTemp.getMax(),
                totalAtmos.getAverage(), totalAtmos.getMin(), totalAtmos.getMax(),
                totalGust.getAverage(), totalGust.getMin(), totalGust.getMax(),
                totalPrecipitation.getAverage(), totalPrecipitation.getMin(), totalPrecipitation.getMax(),
                totalHumidity.getAverage(), totalHumidity.getMin(), totalHumidity.getMax(),
                totalSolar.getAverage(), totalSolar.getMin(), totalSolar.getMax(),
                totalSDistance.getAverage(), totalSDistance.getMin(), totalSDistance.getMax(),
                totalStrike.getAverage(), totalStrike.getMin(), totalStrike.getMax(),
                totalVapour.getAverage(), totalVapour.getMin(), totalVapour.getMax(),
                totalWindDirection.getAverage(), totalWindDirection.getMin(), totalWindDirection.getMax(),
                totalWindSpeed.getAverage(), totalWindSpeed.getMin(), totalWindSpeed.getMax()

        );
    }

    public static void main(String[] args) {
        try {
            String fileName = "weather-stations-city-of-geelong.json";
            JSONArray ja = getJsonFile(fileName);
            System.out.println(getDailyMetrics(mapJsonArray(ja)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

