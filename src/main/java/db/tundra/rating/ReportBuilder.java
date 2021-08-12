package db.tundra.rating;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class that creates JSON text according to specification.
 */
public class ReportBuilder {

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Builds JSON text for given data.
     *
     * @param ratingStatistics  - aggregated ratings
     * @param validLinesCount   - number of valid lines
     * @param invalidLinesCount - number of invalid lines
     * @return JSON text
     */
    public static String build(Map<String, IntSummaryStatistics> ratingStatistics, int validLinesCount,
        int invalidLinesCount) {

        // sort ratings by average rating (descending) and by name of the product (ascending)
        List<String> maxAvgRating = ratingStatistics.entrySet().stream()
            .sorted(Comparator.<Map.Entry<String, IntSummaryStatistics>>comparingDouble(
                entry -> entry.getValue().getAverage()).reversed().thenComparing(Map.Entry::getKey))
            .map(Map.Entry::getKey).limit(3).collect(Collectors.toList());

        // sort ratings by average rating (ascending) and by name of the product (ascending)
        List<String> minAvgRating = ratingStatistics.entrySet().stream()
            .sorted(Comparator.<Map.Entry<String, IntSummaryStatistics>>comparingDouble(
                entry -> entry.getValue().getAverage()).thenComparing(Map.Entry::getKey))
            .map(Map.Entry::getKey).limit(3).collect(Collectors.toList());

        // finds product with maximum number of ratings
        String maxRatingCount = ratingStatistics.entrySet().stream().
            max(Comparator.<Map.Entry<String, IntSummaryStatistics>>comparingLong(
                entry -> entry.getValue().getCount()).thenComparing(Map.Entry::getKey, Comparator.reverseOrder()))
            .map(Map.Entry::getKey).orElse(null);

        // finds product with minimum number of ratings
        String minRatingCount = ratingStatistics.entrySet().stream().
            min(Comparator.<Map.Entry<String, IntSummaryStatistics>>comparingLong(
                entry -> entry.getValue().getCount()).thenComparing(Map.Entry::getKey))
            .map(Map.Entry::getKey).orElse(null);

        // prepare report
        RatingReport report = new RatingReport(validLinesCount, invalidLinesCount, maxAvgRating, minAvgRating,
            maxRatingCount, minRatingCount);

        // generate JSON from report
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(report);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
