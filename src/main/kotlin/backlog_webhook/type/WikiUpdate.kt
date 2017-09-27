package backlog_webhook.type

import backlog_webhook.*
import com.beust.klaxon.JsonObject

class WikiUpdate(jsonObj: JsonObject): BacklogWebhookRequest(jsonObj) {

    override fun make() {
        label = "Wiki更新"

        val content = jsonObj.getObj("content")

        key = ""
        summary = "「" + content.getString("name") + "」"
        url = baseUrl + "alias/wiki/" + content.getInt("id")
        comment = name + "さんがWikiページを更新しました。"
    }
}