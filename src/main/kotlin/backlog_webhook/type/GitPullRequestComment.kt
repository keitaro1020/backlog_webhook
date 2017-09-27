package backlog_webhook.type

import backlog_webhook.*
import com.beust.klaxon.JsonObject

class GitPullRequestComment(jsonObj: JsonObject): BacklogWebhookRequest(jsonObj) {

    override fun make() {
        label = "プルリクエストコメント"

        val project = jsonObj.getObj("project")
        val content = jsonObj.getObj("content")

        key = "( 担当:" + content.getObj("assignee").getString("name") + " )"
        summary = "「" + content.getString("summary") + "」"
        url = baseUrl + "git/" + project.getString("projectKey") + "/" + content.getObj("repository").getString("name") + "/pullRequests/" + content.getInt("number") + "#comment-" + content.getInt("id")
        comment = content.getObj("comment").getString("content")
    }
}