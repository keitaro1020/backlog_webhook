package backlog_webhook.type

import backlog_webhook.*
import com.beust.klaxon.JsonObject

class TaskCollectUpdate(jsonObj: JsonObject): BacklogWebhookRequest(jsonObj) {

    override val label = "まとめて更新"

    override fun make() {
        val project = jsonObj.getObj("project")

        key = ""
        summary = ""
        url = baseUrl + "projects/" + project.getString("projectKey")
        comment = name + "さんが課題をまとめて操作しました。"
    }
}