package backlog_webhook.type

import backlog_webhook.*
import com.beust.klaxon.JsonObject

class TaskCollectUpdate(jsonObj: JsonObject): BacklogWebhookRequest(jsonObj) {

    override fun make() {
        label = "まとめて更新"

        val project = jsonObj.getObj("project")

        key = ""
        summary = ""
        url = baseUrl + "projects/" + project.getString("projectKey")
        comment = name + "さんが課題をまとめて操作しました。"
    }
}