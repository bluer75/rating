package db.tundra.rating;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Executes end to end tests.
 */
public class RatingCalculatorTest {
    private static final String inputFileName = "input.csv";
    private static final String outputFileName = "output.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    private final RatingCalculator calculator = new RatingCalculator();

    @Test
    public void testCalculate() {
        String json = calculator.calculate(getInputStream(inputFileName));
        assertNotNull(json, "expected non-null object");

        // convert JSON to objects and compare them
        try {
            RatingReport actual = mapper.readValue(json, RatingReport.class);
            RatingReport expected = mapper.readValue(getInputStream(outputFileName), RatingReport.class);
            assertEquals(expected, actual);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    private InputStream getInputStream(String fileName) {
        InputStream in = this.getClass().getResourceAsStream("/" + fileName);
        assertNotNull(in, "file " + fileName + " not found");
        return in;
    }
}
