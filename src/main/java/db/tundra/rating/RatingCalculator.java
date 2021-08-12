package db.tundra.rating;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Class that processes given input stream and calculates summary of product ratings.
 * For convenience, the main method is also provided to use it as standalone program - see README.md
 */
public class RatingCalculator {

    private static final String FILE_NAME_OPTION = "-f";
    private static final String VERBOSE_OPTION = "-v";

    private static boolean withLogging;

    /**
     * Produces summary for given input stream.
     *
     * @param in - input data
     * @return JSON - text with summary
     */
    public String calculate(InputStream in) {

        RatingParser parser = new RatingParser();

        Map<String, IntSummaryStatistics> ratingStatistics = new BufferedReader(
            new InputStreamReader(in, StandardCharsets.UTF_8))
            .lines()
            .map(parser::getProductRatingOrEmptyValue)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(
                Collectors.groupingBy(ProductRating::getProduct,
                    Collectors.summarizingInt(ProductRating::getRating)));

        if (withLogging) {
            for (Map.Entry<String, IntSummaryStatistics> e : ratingStatistics.entrySet()) {
                info(e.toString());
            }
        }

        return ReportBuilder.build(ratingStatistics, parser.getValidLinesCount(), parser.getInvalidLinesCount());
    }

    /**
     * Executes RatingCalculator for given input file.
     *
     * @param args - input file
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        String fileName = getOptionValue(args, FILE_NAME_OPTION);
        withLogging = hasOption(args, VERBOSE_OPTION);

        if (fileName == null) {
            info("Please specify rating file: -f <rating_file>");
            return;
        }
        if (Files.exists(Path.of(fileName))) {
            long time = System.nanoTime();
            String json = new RatingCalculator().calculate(new FileInputStream(fileName));
            time = System.nanoTime() - time;
            time = TimeUnit.NANOSECONDS.toMillis(time);
            info("File %s computed in %d ms\n%s", fileName, time, json);
        } else {
            info("File %s not found", fileName);
        }
    }

    /**
     * Gets given option from command line parameters or returns null if not found.
     */
    private static String getOptionValue(String[] args, String option) {
        Arrays.stream(args).iterator();
        for (int i = 0; i < args.length; i++) {
            if (option.equals(args[i]) && i < args.length - 1) {
                return args[i + 1];
            }
        }
        return null;
    }

    /**
     * Checks if given option is provided in command line parameters.
     */
    private static boolean hasOption(String[] args, String option) {
        return Arrays.stream(args).anyMatch(arg -> Objects.equals(arg, option));
    }

    /**
     * Logs message to standard output.
     */
    private static void info(String format, Object... args) {
        System.out.printf(format + "\n", args);
    }
}
