package db.tundra.rating;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

/**
 * Represents the summary of ratings, it's used to generate JSON text.
 */
@SuppressWarnings("unused")
class RatingReport {

    private final int validLines;
    private final int invalidLines;
    private final List<String> bestRatedProducts;
    private final List<String> worstRatedProducts;
    private final String mostRatedProduct;
    private final String lessRatedProduct;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public RatingReport(@JsonProperty("validLines") int validLines, @JsonProperty("invalidLines") int invalidLines,
        @JsonProperty("bestRatedProducts") List<String> bestRatedProducts,
        @JsonProperty("worstRatedProducts") List<String> worstRatedProducts,
        @JsonProperty("mostRatedProduct") String mostRatedProduct,
        @JsonProperty("lessRatedProduct") String lessRatedProduct) {
        this.validLines = validLines;
        this.invalidLines = invalidLines;
        this.bestRatedProducts = bestRatedProducts;
        this.worstRatedProducts = worstRatedProducts;
        this.mostRatedProduct = mostRatedProduct;
        this.lessRatedProduct = lessRatedProduct;
    }

    public int getValidLines() {
        return validLines;
    }

    public int getInvalidLines() {
        return invalidLines;
    }

    public List<String> getBestRatedProducts() {
        return bestRatedProducts;
    }

    public List<String> getWorstRatedProducts() {
        return worstRatedProducts;
    }

    public String getMostRatedProduct() {
        return mostRatedProduct;
    }

    public String getLessRatedProduct() {
        return lessRatedProduct;
    }

    // generated methods

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RatingReport report = (RatingReport) o;
        return validLines == report.validLines && invalidLines == report.invalidLines && Objects.equals(
            bestRatedProducts, report.bestRatedProducts) && Objects.equals(worstRatedProducts,
            report.worstRatedProducts) && Objects.equals(mostRatedProduct,
            report.mostRatedProduct) && Objects.equals(lessRatedProduct, report.lessRatedProduct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(validLines, invalidLines, bestRatedProducts, worstRatedProducts, mostRatedProduct,
            lessRatedProduct);
    }

    @Override
    public String toString() {
        return "RatingReport{" +
            "validLines=" + validLines +
            ", invalidLines=" + invalidLines +
            ", bestRatedProducts=" + bestRatedProducts +
            ", worstRatedProducts=" + worstRatedProducts +
            ", mostRatedProduct='" + mostRatedProduct + '\'' +
            ", lessRatedProduct='" + lessRatedProduct + '\'' +
            '}';
    }
}
