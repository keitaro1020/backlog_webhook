package backlog_webhook.type

import backlog_webhook.*
import com.beust.klaxon.JsonObject

class SvnCommit(jsonObj: JsonObject): BacklogWebhookRequest(jsonObj) {

    override fun make() {
        label = "SVNコミット"

        val project = jsonObj.getObj("project")
        val content = jsonObj.getObj("content")

        key = "[" + content.getString("rev") + "]"
        summary = "「" + content.getString("summary") + "」"
        url = baseUrl + "rev/" + project.getString("projectKey") + "-" + content.getString("rev")
        comment = content.getString("comment")
    }
}