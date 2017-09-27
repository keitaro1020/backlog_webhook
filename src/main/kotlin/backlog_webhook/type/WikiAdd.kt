package backlog_webhook.type

import backlog_webhook.*
import com.beust.klaxon.JsonObject

class WikiAdd(jsonObj: JsonObject): BacklogWebhookRequest(jsonObj) {

    override fun make() {
        label = "Wiki追加"

        val content = jsonObj.getObj("content")

        key = ""
        summary = "「" + content.getString("name") + "」"
        url = baseUrl + "alias/wiki/" + content.getInt("id")
        comment = name + "さんがWikiページを追加しました。"
    }
}