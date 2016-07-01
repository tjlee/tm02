package entities;

/**
 * Represents result data
 */
public class ResultData extends Object {
    private String fileName;
    private int httpCode;
    private TestStatus status;

    /**
     * @param fileName file name
     * @param httpCode http response code
     * @param status   status from response json
     */
    public ResultData(String fileName, int httpCode, TestStatus status) {
        this.fileName = fileName;
        this.httpCode = httpCode;
        this.status = status;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public TestStatus getStatus() {
        return status;
    }

    public void setStatus(TestStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("%s::%d::%s", this.fileName, this.httpCode, this.status);
    }


}
