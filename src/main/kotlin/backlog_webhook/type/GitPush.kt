package backlog_webhook.type

import backlog_webhook.*
import com.beust.klaxon.JsonObject
import com.beust.klaxon.array

class GitPush(jsonObj: JsonObject): BacklogWebhookRequest(jsonObj) {

    lateinit var projectName: String
    lateinit var revKey: String

    override fun make() {
        label = "Gitプッシュ"

        val project = jsonObj.getObj("project")
        val content = jsonObj.getObj("content")

        val revisions = content.array<JsonObject>("revisions")?.getOrNull(0)
        var gitRev = revisions?.getString("rev") ?: ""
        var gitComment = revisions?.getString("comment") ?: ""

        revKey = gitRev.substring(0..9)
        key = "[${revKey}]"
        summary = ""
        url = baseUrl + "git/" + project.getString("projectKey") + "/" + content.getObj("repository").getString("name") + "/" + content.getString("revision_type") + "/" + gitRev
        comment = gitComment

        projectName = project.getString("projectKey") + "/" + content.getObj("repository").getString("name")
    }

    override fun slackMessage(): String {
        return """
*${label}*
[${projectName}] <${url}| commit> pushed by ${name}
"""
    }

    override fun slackComment(): String {
        return """
 [${revKey}] ${comment.replace("\n", " ")}
"""
    }
}