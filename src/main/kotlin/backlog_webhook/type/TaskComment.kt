package backlog_webhook.type

import backlog_webhook.*
import com.beust.klaxon.JsonObject

class TaskComment(jsonObj: JsonObject): BacklogWebhookRequest(jsonObj) {

    override fun make() {
        label = "コメント"

        val project = jsonObj.getObj("project")
        val content = jsonObj.getObj("content")

        key = "[" + project.getString("projectKey") + "-" + content.getInt("key_id") + "]"
        summary = "「" + content.getString("summary") + "」"
        url = baseUrl + "view/" + project.getString("projectKey") + "-" + content.getInt("key_id") + "#comment-" + content.getObj("comment").getInt("id")
        comment = content.getObj("comment").getString("content")
    }
}