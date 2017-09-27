package backlog_webhook

import com.beust.klaxon.JsonObject

abstract class BacklogWebhookRequest(val jsonObj: JsonObject) {

    init {
        makeBase()
        make()
    }

    lateinit var baseUrl: String

    lateinit var label: String
    lateinit var key: String
    lateinit var summary: String
    lateinit var url: String
    lateinit var comment: String
    lateinit var name: String

    fun makeBase(): Unit {
        baseUrl = System.getenv("BASEURL")
        name = jsonObj.getObj("createdUser").getString("name")
    }

    abstract fun make(): Unit

    fun slackMessage(): String {
        val message = StringBuilder()

        if (key.length > 0) {
            message.append(key).append(" ")
        }

        if (label.length > 0) {
            message.append(label).append(" ")
        }

        if (summary.length > 0) {
            message.append(summary).append(" ")
        }

        if (name.length > 0) {
            message.append("by ").append(name)
        }

        message.append("\n")

        if (url.length > 0) {
            message.append(url).append("\n")
        }

        return message.toString()
    }

    fun slackComment(): String {
        if (comment.length > 200) {
            return comment.substring(0..200) + "..."
        }
        return comment
    }
}