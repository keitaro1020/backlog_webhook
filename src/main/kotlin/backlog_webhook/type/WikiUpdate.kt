package backlog_webhook.type

import backlog_webhook.*
import com.beust.klaxon.JsonObject

class WikiUpdate(jsonObj: JsonObject): BacklogWebhookRequest(jsonObj) {

    lateinit var beforeValue: String
    lateinit var afterValue: String

    override fun make() {
        label = "Wiki更新"

        val content = jsonObj.getObj("content")
        val project = jsonObj.getObj("project")

        key = "${project.getString("name")}(${project.getString("projectKey")})"
        summary = content.getString("name")
        url = baseUrl + "alias/wiki/" + content.getInt("id")
        comment = content.getString("diff").replace("---", "\n---")
    }

    override fun slackMessage(): String {
        return """
*${label}*
<${url}|${key} - ${summary} (by ${name})>
"""
    }

    override fun slackComment(): String {
        return """
変更箇所：
${comment.toLineOmit(10)}
"""
    }
}