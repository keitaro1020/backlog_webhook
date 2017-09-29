package backlog_webhook.type

import backlog_webhook.*
import com.beust.klaxon.JsonObject
import com.beust.klaxon.obj

class GitPullRequestAdd(jsonObj: JsonObject): BacklogWebhookRequest(jsonObj) {

    override val label = "プルリクエスト追加"

    lateinit var projectName: String
    lateinit var pullRequestNo: String

    lateinit var branch: String
    lateinit var base: String

    override fun make() {
        val project = jsonObj.getObj("project")
        val content = jsonObj.getObj("content")

        summary = content.getString("summary")
        url = baseUrl + "git/" + project.getString("projectKey") + "/" + content.getObj("repository").getString("name") + "/pullRequests/" + content.getInt("number")
        comment = content.getString("description")

        projectName = project.getString("projectKey") + "/" + content.getObj("repository").getString("name")
        pullRequestNo = content.getInt("number").toString()
        branch = content.getString("branch")
        base = content.getString("base")
    }

    override fun slackMessage(): String {
        return """
*${label}*
[${projectName}] Pull request created by ${name}
"""
    }

    override fun slackComment(): String {
        return """
<${url}|#${pullRequestNo} : ${summary}> (${branch} to ${base})
${comment}
"""
    }
}