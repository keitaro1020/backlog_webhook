package backlog_webhook

import com.beust.klaxon.JsonObject

abstract class BacklogWebhookRequest(val jsonObj: JsonObject) {

    init {
        makeBase()
        make()
    }

    lateinit var baseUrl: String

    abstract val label: String

    lateinit var key: String
    lateinit var summary: String
    lateinit var url: String
    lateinit var comment: String
    lateinit var name: String

    fun makeBase(): Unit {
        baseUrl = System.getenv("BASEURL")
        name = jsonObj.getObj("createdUser").getString("name")
    }

    val newline: String = "\n"

    abstract fun make(): Unit

    open fun slackMessage(): String {
        val message = StringBuilder()

        if (key.isNotEmpty()) {
            message.append(key).append(" ")
        }

        if (label.isNotEmpty()) {
            message.append(label).append(" ")
        }

        if (summary.isNotEmpty()) {
            message.append(summary).append(" ")
        }

        if (name.isNotEmpty()) {
            message.append("by ").append(name)
        }

        if (message.isNotEmpty()) {
            message.append("\n")
        }

        if (url.isNotEmpty()) {
            message.append(url).append("\n")
        }

        return message.toString()
    }

    open fun slackComment(): String {
        if (comment.length > 200) {
            return comment.substring(0..200) + "..."
        }
        return comment
    }
}