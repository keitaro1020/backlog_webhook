package backlog_webhook

import com.beust.klaxon.*

fun JsonObject.getString(fieldName: String, defaultValue: String = ""): String {
    return this.string(fieldName) ?: defaultValue
}

fun JsonObject.getInt(fieldName: String, defaultValue: Int = -1): Int {
    return this.int(fieldName) ?: defaultValue
}

fun JsonObject.getObj(fieldName: String): JsonObject {
    return this.obj(fieldName) ?: JsonObject()
}
