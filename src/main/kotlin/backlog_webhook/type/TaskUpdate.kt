package backlog_webhook.type

import backlog_webhook.*
import com.beust.klaxon.JsonObject
import com.beust.klaxon.array
import difflib.Delta
import difflib.DiffUtils

class TaskUpdate(jsonObj: JsonObject): BacklogWebhookRequest(jsonObj) {

    lateinit var changeField: ChangeField
    lateinit var beforeValue: String
    lateinit var afterValue: String

    override fun make() {
        label = "課題を更新"

        val project = jsonObj.getObj("project")
        val content = jsonObj.getObj("content")

        key = "[" + project.getString("projectKey") + "-" + content.getInt("key_id") + "]"
        summary = "「" + content.getString("summary") + "」"
        url = baseUrl + "view/" + project.getString("projectKey") + "-" + content.getInt("key_id")
        comment = content.getObj("comment").getString("content")

        changeField = getChangeField(content.array<JsonObject>("changes")?.getOrNull(0)?.getString("field"))
        beforeValue = content.array<JsonObject>("changes")?.getOrNull(0)?.getString("old_value") ?: "-"
        afterValue = content.array<JsonObject>("changes")?.getOrNull(0)?.getString("new_value") ?: "-"
    }

    override fun slackMessage(): String {
        return """
*${label}*
<${url}|${key} ${summary} (by ${name})>
"""
    }

    override fun slackComment(): String {
        return """
変更箇所：${changeField.value}
${getBeforeAfterValue(beforeValue, afterValue)}

コメント：
${comment}
"""
    }

    private fun getBeforeAfterValue(beforeValue: String, afterValue: String): String {
        when(changeField) {
            ChangeField.status -> {
                return "${getTaskStatus(beforeValue).value} -> ${getTaskStatus(afterValue).value}"
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

fun <T> Delta<T>.typename(): String {
    when (this.type) {
        Delta.TYPE.INSERT -> return "追加"
        Delta.TYPE.CHANGE -> return "変更"
        Delta.TYPE.DELETE -> return "削除"
        else              -> return "不明"
    }
}