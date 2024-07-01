package guru.qa.allure.notifications.config.gitlab;


import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Note {
    @SerializedName("url")
    private String url;
    @SerializedName("apiKey")
    private String apiKey;
    @SerializedName("apiToken")
    private String apiToken;
    @SerializedName("projectId")
    private String projectId;
    @SerializedName("mergeRequestIid")
    private String mergeRequestIid;
}
