package db.tundra.rating;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser that validates input data.
 */
public class RatingParser {

    /**
     * Pattern defining valid line according to specification. Only ASCI characters are supported.
     */
    private static final Pattern regex = Pattern.compile("" +
        "(?<buyer>[a-zA-Z][a-zA-Z0-9]*)," +
        "(?<shop>[a-zA-Z][a-zA-Z0-9]*)," +
        "(?<product>[a-zA-Z][a-zA-Z0-9\\-]*-0*[1-9][0-9]?)," +
        "(?<rating>0*[1-5])");

    private int validLinesCount;

    private int invalidLinesCount;

    /**
     * Validates given input and constructs Optional holding ProductRating value.
     *
     * @param str - input data
     * @return ProductRating or empty value in case of invalid format
     */
    public Optional<ProductRating> getProductRatingOrEmptyValue(String str) {
        Matcher matcher = regex.matcher(str);
        if (matcher.matches()) {
            validLinesCount++;
            return Optional.of(new ProductRating(matcher.group("product"), Integer.parseInt(matcher.group("rating"))));
        }
        invalidLinesCount++;
        return Optional.empty();
    }

    public int getValidLinesCount() {
        return validLinesCount;
    }

    public int getInvalidLinesCount() {
        return invalidLinesCount;
    }
}
