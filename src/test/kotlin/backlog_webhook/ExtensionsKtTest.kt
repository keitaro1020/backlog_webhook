package backlog_webhook

import com.beust.klaxon.JsonObject
import org.junit.Test

import org.junit.Assert.*

import backlog_webhook.getString
import difflib.*

/**
 * Created by keitaro.shishido on 2017/09/29.
 */
class ExtensionsKtTest {
    @Test
    fun getString() {
        var obj = JsonObject()
        obj.put("key", "value")

        assertEquals(obj.getString("key"), "value")
        assertEquals(obj.getString("key2", "default"), "default")
        assertEquals(obj.getString("key3"), "")
    }

    @Test
    fun getInt() {
        var obj = JsonObject()
        obj.put("key", 0)

        assertEquals(obj.getInt("key"), 0)
        assertEquals(obj.getInt("key2", 10), 10)
        assertEquals(obj.getInt("key3"), -1)
    }

    @Test
    fun getObj() {
        var obj = JsonObject()
        obj.put("key", 0)
        var obj2 = JsonObject()
        obj2.put("key", obj)

        assertEquals(obj2.getObj("key"), obj)
        assertEquals(obj2.getObj("key2"), JsonObject())
    }

    @Test
    fun typename() {
        val insert = InsertDelta<String>(Chunk<String>(0, emptyList()), Chunk<String>(0, emptyList()))
        val delete = DeleteDelta<String>(Chunk<String>(0, emptyList()), Chunk<String>(0, emptyList()))
        val change = ChangeDelta<String>(Chunk<String>(0, emptyList()), Chunk<String>(0, emptyList()))
        assertEquals(insert.typename(), "追加")
        assertEquals(delete.typename(), "削除")
        assertEquals(change.typename(), "変更")
    }

    @Test
    fun toLineOmit() {
        var org = """1
2
3
4
5"""
        var tmp = """1
2
3
4
..."""
        assertEquals(org.toLineOmit(5), org)
        assertEquals(org.toLineOmit(4), tmp)
    }

    @Test
    fun getEnv() {
        assertEquals(getEnv("TEST", "default"), "default")
        assertEquals(getEnv("USER"), "keitaro.shishido")
    }

}