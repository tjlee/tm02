package com.company;


import com.google.gson.Gson;
import entities.ResultData;
import entities.TestData;
import org.apache.commons.cli.*;
import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;
import utilities.FileProcessor;
import utilities.HttpProcessor;
import utilities.JsonProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Main
 */
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);

    // move to properties file on further framework dev
    private static final String TOKEN = "40ke08ujmlb8lf";
    private static final String ENDPOINT = "http://localhost:8787/test";
    private static final String RESULT_FILE_NAME = "./result.txt";

    private Main() {
    }


    /**
     * -d="path/to/data/dir/with/json"
     *
     * @param args
     */
    public static void main(String[] args) {
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        options.addOption("d", "dir", true, "Path to data folder with json files to process. \n" +
                "By default tries to connect to http://localhost:8787/test and send POST request. \n" +
                "Request json format: {\"server_key\": \"REPLACE-ME\",\"test_data\": \"Some arbitrary string\"}\n" +
                "Response json format: {\"status\": \"PASSED\"} \n" +
                "Stores results to result.txt(rewritten on each launch). \nResult format: filename::HTTP-code::status\n");

        try {
            CommandLine line = parser.parse(options, args);
            if (line.hasOption("d")) {
                String[] arguments = line.getOptionValues("d");

                List<String> filesToProcess = FileProcessor.getFileListByMask(arguments[0], "json");
                List<TestData> jsonToProcess = JsonProcessor.processFilesForJsonReady(filesToProcess);
                List<ResultData> results = new ArrayList<>();

                for (TestData testData : jsonToProcess) {
                    testData.setServerKey(TOKEN);
                    HttpResponse response = HttpProcessor.sendPostRequest(new Gson().toJson(testData), ENDPOINT);
                    ResultData resultData = HttpProcessor.processResponse(response, testData.getSourceFile());

                    if(resultData != null){
                        results.add(resultData);
                    }
                }

                if (!results.isEmpty()) {
                    FileProcessor.writeResultToFile(RESULT_FILE_NAME, results);
                }

            } else {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("TaskM02", options);
            }
        } catch (ParseException e) {
            logger.error(e);
        }
    }
}
