package utilities;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import entities.ResponseData;
import entities.ResultData;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * HTTP operations routine
 */
public class HttpProcessor {

    private static final Logger logger = Logger.getLogger(HttpProcessor.class);

    private HttpProcessor() {

    }

    /**
     * Sends post request as text/plain UTF-8
     *
     * @param jsonString json data to send
     * @param endpoint   target endpoint
     * @return
     */
    public static HttpResponse sendPostRequest(String jsonString, String endpoint) {
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(endpoint);
            StringEntity params = new StringEntity(jsonString, "UTF-8");
            request.addHeader("content-type", "text/plain");
            request.setEntity(params);
            return httpClient.execute(request);
        } catch (IOException e) {
            logger.error(String.format("Unable to send POST request to '%s'", endpoint), e);
        }

        return null;
    }


    private static String readResponseEntityToString(HttpResponse response) {
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } catch (IOException e) {
            logger.error("Unable to process response", e);
        }
        return null;
    }


    /**
     * Converts HttpResponse to ResultData
     *
     * @param response         response
     * @param fileInProcessing file with json to process
     * @return
     */
    public static ResultData processResponse(HttpResponse response, String fileInProcessing) {
        if (response != null) {
            int statusCode = response.getStatusLine().getStatusCode();
            String result = readResponseEntityToString(response);

            // if response is empty
            if (result == null) {
                return null;
            }

            try {
                Gson gson = new Gson();
                ResponseData responseData = gson.fromJson(result, ResponseData.class);
                // if response data is in another json format
                if (responseData != null) {
                    return new ResultData(fileInProcessing, statusCode, responseData.getStatus());
                }
            } catch (JsonSyntaxException e) {
                logger.error(String.format("Unable to process response Json for file '%s'", fileInProcessing), e);
            }
        } else {
            logger.error(String.format("Unable to process response for file '%s'", fileInProcessing));
        }
        return null;
    }
}
