package backlog_webhook

import backlog_webhook.type.*
import com.beust.klaxon.*
import com.github.kittinunf.fuel.httpPost

class App : AppBase() {
    override fun handlerSub() {
        val type = jsonObj.getInt("type", -1)

        val typeRequest = getTypeRequest(type)

        if (typeRequest != null) {
            logger.log(typeRequest.slackMessage())

            val map = mutableMapOf<String, Any?>()
            map.put("token", System.getenv("SLACKTOKEN"))
            map.put("channel", request.getString("channel"))
            map.put("username", System.getenv("BOTNAME"))
            map.put("text", typeRequest.slackMessage())

            if (typeRequest.slackComment().isNotEmpty()) {

                map.put("attachments", JsonArray(JsonObject(mapOf(
                        "color" to "#42ce9f",
                        "fields" to JsonArray(JsonObject(mapOf(
                                "value" to typeRequest.slackComment(),
                                "short" to false
                        )))
                ))).toJsonString())
            }

            val (request, response, _) = "https://slack.com/api/chat.postMessage".httpPost(map.toList()).responseString()
            logger.log("req:" + request.toString())
            logger.log("res:" + response.toString())
            output.write(JsonObject(mapOf("result" to "success")).toJsonString().toByteArray())
        }
    }

    fun getTypeRequest(type: Int): BacklogWebhookRequest? {
        when(type) {
            1   -> return TaskAdd(jsonObj)
            2   -> return TaskUpdate(jsonObj)
            3   -> return TaskComment(jsonObj)
            14  -> return TaskCollectUpdate(jsonObj)
            5   -> return WikiAdd(jsonObj)
            6   -> return WikiUpdate(jsonObj)
            11  -> return SvnCommit(jsonObj)
            12  -> return GitPush(jsonObj)
            18  -> return GitPullRequestAdd(jsonObj)
            19  -> return GitPullRequestUpdate(jsonObj)
            20  -> return GitPullRequestComment(jsonObj)
        }

        return null
    }

}
