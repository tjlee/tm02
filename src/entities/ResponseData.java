package entities;

import com.google.gson.annotations.SerializedName;

/**
 * Represents response data for json parsing
 */
public class ResponseData {
    @SerializedName("status")
    private TestStatus status;

    /**
     * If stutus is not in PASSED, FAILED, ERROR it sets in UNKNOWN
     *
     * @param status
     */
    public ResponseData(TestStatus status) {
        this.status = status;
    }

    public TestStatus getStatus() {
        if (status == null) {
            return TestStatus.UNKNOWN;
        } else {
            return status;
        }
    }

    public void setStatus(TestStatus status) {
        this.status = status;
    }

}
