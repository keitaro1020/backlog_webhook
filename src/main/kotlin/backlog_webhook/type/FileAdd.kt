package backlog_webhook.type

import backlog_webhook.BacklogWebhookRequest
import backlog_webhook.getObj
import backlog_webhook.getString
import com.beust.klaxon.JsonObject

class FileAdd(jsonObj: JsonObject): BacklogWebhookRequest(jsonObj) {

    override val label = "ファイル追加"

    override fun make() {
        val project = jsonObj.getObj("project")
        val content = jsonObj.getObj("content")

        key = "[" + project.getString("projectKey") + "] " + content.getString("dir") + content.getString("name") + "]"
        url = baseUrl + "file/" + project.getString("projectKey") + content.getString("dir") + content.getString("name")
        comment = content.getString("description")
    }

    override fun slackMessage(): String {
        return """
*${label}*
<${url}|${key} (by ${name})>
"""
    }
}
