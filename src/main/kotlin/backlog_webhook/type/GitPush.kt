package backlog_webhook.type

import backlog_webhook.*
import com.beust.klaxon.JsonObject
import com.beust.klaxon.array

class GitPush(jsonObj: JsonObject): BacklogWebhookRequest(jsonObj) {

    override fun make() {
        label = "Gitプッシュ"

        val project = jsonObj.getObj("project")
        val content = jsonObj.getObj("content")

        var gitRev = ""
        var gitComment = ""
        val revisions = content.array<JsonObject>("revisions")
        if (revisions != null && revisions.size > 0) {
            gitRev = revisions[0].getString("rev")
            gitComment = revisions[0].getString("comment")
        }

        key = "[${gitRev}]"
        summary = ""
        url = baseUrl + "git/" + project.getString("projectKey") + "/" + content.getObj("repository").getString("name") + "/" + content.getString("revision_type") + "/" + gitRev
        comment = gitComment
    }
}