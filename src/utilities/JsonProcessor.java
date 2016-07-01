package utilities;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import entities.TestData;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Json operations routine
 */
public class JsonProcessor {
    private static final Logger logger = Logger.getLogger(JsonProcessor.class);

    private JsonProcessor() {

    }

    /**
     * Prepares files contents for further serialization
     *
     * @param filesToProcess collection of full paths to files to process
     * @return
     */
    public static List<TestData> processFilesForJsonReady(List<String> filesToProcess) {
        List<TestData> resultSet = new ArrayList<>();

        for (String file : filesToProcess) {
            TestData testData = processJsonFile(file);
            // if json file contains valid json
            if (testData != null) {
                testData.setSourceFile(file);
                resultSet.add(testData);
            }
        }
        return resultSet;
    }


    private static TestData processJsonFile(String pathToFile) {
        try {
            String rawString = FileUtils.readFileToString(new File(pathToFile), "UTF-8");

            // as we need to process a lot of small json files
            // according to http://blog.takipi.com/the-ultimate-json-library-json-simple-vs-gson-vs-jackson-vs-json/

            Gson gson = new Gson();
            return gson.fromJson(rawString, TestData.class);
        } catch (JsonSyntaxException e) {
            logger.error(String.format("File '%s' contains malformed JSON", pathToFile), e);

        } catch (IOException e) {
            logger.error(String.format("Unable to process file '%s'", pathToFile), e);
        }

        return null;
    }

}
