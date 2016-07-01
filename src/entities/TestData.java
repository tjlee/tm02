package entities;

import com.google.gson.annotations.SerializedName;

/**
 * Represents request data for json
 */
public class TestData {
    @SerializedName("server_key")
    private String serverKey;

    @SerializedName("test_data")
    private String dataForTests;
    private String sourceFile;

    /**
     * Describes request data
     *
     * @param serverKey
     * @param dataForTests
     */
    public TestData(String serverKey, String dataForTests) {
        this.serverKey = serverKey;
        this.dataForTests = dataForTests;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    public String getDataForTests() {
        return dataForTests;
    }

    public void setDataForTests(String dataForTests) {
        this.dataForTests = dataForTests;
    }


    public String getServerKey() {
        return serverKey;
    }

    public void setServerKey(String serverKey) {
        this.serverKey = serverKey;
    }
}
