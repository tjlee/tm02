package utilities;

import entities.ResultData;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * File operations routine
 */
public class FileProcessor {

    private static final Logger logger = Logger.getLogger(FileProcessor.class);

    private FileProcessor() {

    }

    /**
     * Writes results into the specified file
     *
     * @param fileName file name to write resutls to
     * @param results  ResultData collection to write
     */
    public static void writeResultToFile(String fileName, List<ResultData> results) {
        try {
            FileUtils.writeLines(new File(fileName), results);
        } catch (IOException e) {
            logger.error(String.format("Unable to write to result file '%s'", fileName), e);
        }
    }

    /**
     * Get files by extention mask in specified dir recursively
     *
     * @param directory dir to search files recursively
     * @param fileMask  file extensions to search
     * @return full file path collection
     */
    public static List<String> getFileListByMask(String directory, String fileMask) {
        List<String> filesToWorkWith = new ArrayList<>();
        try {
            Iterator iterator = FileUtils.iterateFiles(new File(directory), new String[]{fileMask}, true);
            while (iterator.hasNext()) {
                File f = (File) iterator.next();
                filesToWorkWith.add(f.getAbsolutePath());
            }
        } catch (IllegalArgumentException e) {
            logger.error(String.format("Directory '%s' do not exist", directory), e);
        }
        return filesToWorkWith;
    }
}
