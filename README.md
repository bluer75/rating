Simple calculator that processes rating of products from given file/input stream.

RatingCalculator class extracts product and rating from valid lines and aggregates them.
ProductRating class stores name ot the product and given rating. 
RatingParser class checks if each line is valid according to specification. Only ASCI characters are supported.
ReportBuilder class extracts data needed for report and creates JSON text.
RatingReport class represents the summary of ratings, it's used to generate JSON text.

This Program has been implemented as Maven project with Java 14 and JUnit 5.
To simplify execution, maven-assembly-plugin has been used to build single executable jar with all dependencies.

Program can be compiled (including execution of junit tests) with command:
mvn clean install

Program can be executed from root folder of the project with command:
java -jar target/rating-1.0-SNAPSHOT-jar-with-dependencies.jar -f <rating-file>
or using sample file:
java -jar target/rating-1.0-SNAPSHOT-jar-with-dependencies.jar -f target/classes/input.csv
Additional -v option prints to standard output summary for all ratings. 

The program prints produced JSON text to standard output - formatted according to provided schema.

Please note that performance, security and thread safety aspects have not been fully evaluated with this implementation.
For very big input file the program may run out of memory and, as it processes the file sequentially, it may take some time to complete.
