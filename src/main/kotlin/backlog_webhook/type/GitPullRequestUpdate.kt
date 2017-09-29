package backlog_webhook.type

import backlog_webhook.*
import com.beust.klaxon.JsonObject
import com.beust.klaxon.array
import difflib.DiffUtils

class GitPullRequestUpdate(jsonObj: JsonObject): BacklogWebhookRequest(jsonObj) {

    lateinit var projectName: String
    lateinit var pullRequestNo: String

    lateinit var diff: String
    lateinit var changeField: ChangeField
    lateinit var beforeValue: String
    lateinit var afterValue: String

    override fun make() {
        label = "プルリクエスト更新"

        val project = jsonObj.getObj("project")
        val content = jsonObj.getObj("content")

        key = "( 担当:" + content.getObj("assignee").getString("name") + " )"
        summary = "「" + content.getString("summary") + "」"
        url = baseUrl + "git/" + project.getString("projectKey") + "/" + content.getObj("repository").getString("name") + "/pullRequests/" + content.getInt("number")

        projectName = project.getString("projectKey") + "/" + content.getObj("repository").getString("name")
        pullRequestNo = content.getInt("number").toString()
        diff = content.getString("diff")
        changeField = getChangeField(content.array<JsonObject>("changes")?.getOrNull(0)?.getString("field"))
        beforeValue = content.array<JsonObject>("changes")?.getOrNull(0)?.getString("old_value") ?: "-"
        afterValue = content.array<JsonObject>("changes")?.getOrNull(0)?.getString("new_value") ?: "-"
    }

    override fun slackMessage(): String {
        return """
*${label}*
[${projectName}] Pull request updated by ${name}
"""
    }

    override fun slackComment(): String {
        return """
<${url}|#${pullRequestNo} : ${summary}>
変更箇所：${changeField.value}
${getBeforeAfterValue(beforeValue, afterValue)}
${diff}
"""
    }

    private fun getBeforeAfterValue(beforeValue: String, afterValue: String): String {
        when(changeField) {
            ChangeField.status -> {
                return "${getPullRequestStatus(beforeValue).value} -> ${getPullRequestStatus(afterValue).value}"
            }
            ChangeField.description -> {
                val beforeList = beforeValue.split("\n")
                val afterList= afterValue.split("\n")
                val diffList = DiffUtils.diff(beforeList, afterList)

                val result = StringBuilder()
                diffList.deltas.forEach {
                    result.append("[${it.typename()}]\n")
                    if(it.original.lines.isNotEmpty()) {
                        result.append("  <${it.original.position}> ${it.original.lines}\n")
                        result.append("  ↓\n")
                    }
                    result.append("  <${it.revised.position}> ${it.revised.lines}\n")
                }
                return result.toString()
            }
        }
        return "${beforeValue} -> ${afterValue}"
    }
}