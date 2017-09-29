package backlog_webhook.type

import backlog_webhook.*
import com.beust.klaxon.JsonObject

class WikiAdd(jsonObj: JsonObject): BacklogWebhookRequest(jsonObj) {

    override val label = "Wiki追加"

    override fun make() {
        val content = jsonObj.getObj("content")
        val project = jsonObj.getObj("project")

        key = "${project.getString("name")}(${project.getString("projectKey")})"
        summary = content.getString("name")
        url = baseUrl + "alias/wiki/" + content.getInt("id")
        comment = content.getString("content")
    }

    override fun slackMessage(): String {
        return """
*${label}*
<${url}|${key} - ${summary} (by ${name})>
"""
    }

    override fun slackComment(): String {
        return comment.toLineOmit(10)
    }
}