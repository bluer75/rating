package db.tundra.rating;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests for RatingParser.
 */
public class RatingParserTest {

    private final static Optional<ProductRating> EMPTY_RATING = Optional.empty();

    private RatingParser parser;
    private Map<String, Optional<ProductRating>> buyerTestData = new HashMap<>();

    {
        buyerTestData.put("buyer,shop01,product-01,1", ratingFor("product-01", 1));
        buyerTestData.put("Buyer1,shop01,product-02,2", ratingFor("product-02", 2));
        buyerTestData.put("1buyer,shop01,product-01,1", EMPTY_RATING);
        buyerTestData.put("buyer-01,shop01,product-01,1", EMPTY_RATING);
    }

    private Map<String, Optional<ProductRating>> shopTestData = new HashMap<>();

    {
        shopTestData.put("buyer01,shop,product-01,1", ratingFor("product-01", 1));
        shopTestData.put("buyer01,Shop01,product-02,2", ratingFor("product-02", 2));
        shopTestData.put("buyer01,1shop01,product-01,1", EMPTY_RATING);
        shopTestData.put("buyer01,shop-01,product-01,1", EMPTY_RATING);
    }

    private Map<String, Optional<ProductRating>> productTestData = new HashMap<>();

    {
        productTestData.put("buyer01,shop01,product-01,1", ratingFor("product-01", 1));
        productTestData.put("buyer01,shop01,Product-02,2", ratingFor("Product-02", 2));
        productTestData.put("buyer01,shop01,product-03,3", ratingFor("product-03", 3));
        productTestData.put("buyer01,shop01,product-04,4", ratingFor("product-04", 4));
        productTestData.put("buyer01,shop01,product-5,5", ratingFor("product-5", 5));
        productTestData.put("buyer01,shop01,pr--1od-2uct-99,5", ratingFor("pr--1od-2uct-99", 5));
        productTestData.put("buyer01,shop01,product-0,1", EMPTY_RATING);
        productTestData.put("buyer01,shop01,product01,1", EMPTY_RATING);
        productTestData.put("buyer01,shop01,product-100,1", EMPTY_RATING);
        productTestData.put("buyer01,shop01,-product-99,1", EMPTY_RATING);
    }

    private Map<String, Optional<ProductRating>> ratingTestData = new HashMap<>();

    {
        ratingTestData.put("buyer01,shop01,product-01,1", ratingFor("product-01", 1));
        ratingTestData.put("buyer01,shop01,product-02,2", ratingFor("product-02", 2));
        ratingTestData.put("buyer01,shop01,product-03,3", ratingFor("product-03", 3));
        ratingTestData.put("buyer01,shop01,product-04,4", ratingFor("product-04", 4));
        ratingTestData.put("buyer01,shop01,product-05,5", ratingFor("product-05", 5));
        ratingTestData.put("buyer01,shop01,product-06,005", ratingFor("product-06", 5));
        ratingTestData.put("buyer01,shop01,product-01,0", EMPTY_RATING);
        ratingTestData.put("buyer01,shop01,product-01,6", EMPTY_RATING);
        ratingTestData.put("buyer01,shop01,product-01,010", EMPTY_RATING);
    }

    @BeforeEach
    public void prepare() {
        parser = new RatingParser();
    }

    @Test
    public void testBuyer() {
        testParser(buyerTestData);
    }

    @Test
    public void testShop() {
        testParser(shopTestData);
    }

    @Test
    public void testProduct() {
        testParser(productTestData);
    }

    @Test
    public void testRating() {
        testParser(ratingTestData);
    }

    private void testParser(Map<String, Optional<ProductRating>> testData) {
        int expectedValidLines = 0;
        int expectedInvalidLines = 0;
        Optional<ProductRating> rating = null;
        for (Map.Entry<String, Optional<ProductRating>> entry : testData.entrySet()) {
            rating = parser.getProductRatingOrEmptyValue(entry.getKey());
            assertNotNull(rating, "rating cannot be null");
            assertEquals(entry.getValue(), rating);
            if (entry.getValue().isEmpty()) {
                expectedInvalidLines++;
            } else {
                expectedValidLines++;
            }
        }
        assertEquals(expectedValidLines, parser.getValidLinesCount());
        assertEquals(expectedInvalidLines, parser.getInvalidLinesCount());
    }

    private static Optional<ProductRating> ratingFor(String product, int rating) {
        return Optional.of(new ProductRating(product, rating));
    }
}
