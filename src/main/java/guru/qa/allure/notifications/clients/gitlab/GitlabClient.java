package guru.qa.allure.notifications.clients.gitlab;

import guru.qa.allure.notifications.clients.Notifier;
import guru.qa.allure.notifications.config.gitlab.Note;
import guru.qa.allure.notifications.exceptions.MessagingException;
import guru.qa.allure.notifications.template.MarkdownTemplate;
import guru.qa.allure.notifications.template.data.MessageData;
import kong.unirest.ContentType;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;


public class GitlabClient implements Notifier {
    private Note note;

    public GitlabClient(Note note) {
        this.note = note;
    }

    @Override
    public void sendText(MessageData messageData) throws MessagingException {
        sendData(messageData);
    }

    @Override
    public void sendPhoto(MessageData messageData, byte[] bytes) throws MessagingException {

        JsonNode jsonBody = Unirest.post("https://{url}/api/v4/projects/{projectId}/uploads")
                .routeParam("url", note.getUrl())
                .routeParam("projectId", note.getProjectId())
                .header(note.getApiKey(), note.getApiToken())
                .field("file", new ByteArrayInputStream(bytes), ContentType.APPLICATION_OCTET_STREAM, "file.png")
                .asJson()
                .getBody();

        jsonBody.getObject().toMap().computeIfPresent("markdown", (k, v) -> messageData.getAdditionalData().put("chartSource", v));

        sendData(messageData);
    }

    public void sendData(MessageData messageData) throws MessagingException {
        Map<String, Object> body = new HashMap<>();
        body.put("body", new MarkdownTemplate(messageData).create("markdownGitlab.ftl"));

        Unirest.post("https://{url}/api/v4/projects/{projectId}/merge_requests/{mergeRequestIid}/notes")
                .routeParam("url", note.getUrl())
                .routeParam("projectId", note.getProjectId())
                .routeParam("mergeRequestIid", note.getMergeRequestIid())
                .header(note.getApiKey(), note.getApiToken())
                .header("Content-Type", ContentType.APPLICATION_JSON.getMimeType())
                .body(body)
                .asString()
                .getBody();

    }


}
