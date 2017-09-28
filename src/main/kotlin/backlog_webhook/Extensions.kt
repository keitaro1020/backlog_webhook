package backlog_webhook

import com.beust.klaxon.*
import difflib.Delta

fun JsonObject.getString(fieldName: String, defaultValue: String = ""): String {
    return this.string(fieldName) ?: defaultValue
}

fun JsonObject.getInt(fieldName: String, defaultValue: Int = -1): Int {
    return this.int(fieldName) ?: defaultValue
}

fun JsonObject.getObj(fieldName: String): JsonObject {
    return this.obj(fieldName) ?: JsonObject()
}

fun <T> Delta<T>.typename(): String {
    when (this.type) {
        Delta.TYPE.INSERT -> return "追加"
        Delta.TYPE.CHANGE -> return "変更"
        Delta.TYPE.DELETE -> return "削除"
        else              -> return "不明"
    }
}