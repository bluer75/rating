package db.tundra.rating;

import java.util.Objects;

/**
 * Class holding product and the rating.
 */
public class ProductRating {

    private final String product;
    private final int rating;

    public ProductRating(String product, int rating) {
        this.product = product;
        this.rating = rating;
    }

    public String getProduct() {
        return product;
    }

    public int getRating() {
        return rating;
    }

    // generated methods

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductRating that = (ProductRating) o;
        return rating == that.rating && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, rating);
    }

    @Override
    public String toString() {
        return "ProductRating{" +
            "product='" + product + '\'' +
            ", rating=" + rating +
            '}';
    }
}
