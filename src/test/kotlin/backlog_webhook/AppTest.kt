package backlog_webhook

import backlog_webhook.type.*
import com.beust.klaxon.JsonObject
import org.junit.Test
import org.junit.Assert.*

/**
 * Created by keitaro.shishido on 2017/09/29.
 */
class AppTest {

    val app: App = App()
    val jsonObj: JsonObject = JsonObject()

    @Test
    fun handlerSub() {
        app.jsonObj = jsonObj
        app.handlerSub()
    }

    @Test
    fun getTypeRequest_0() {
        var actual = app.getTypeRequest(0)
        assertEquals(actual, null)
    }

    @Test
    fun getTypeRequest_1() {
        app.jsonObj = jsonObj
        var actual = app.getTypeRequest(1)
        assertEquals(actual!!::class, TaskAdd(jsonObj)::class)
    }

    @Test
    fun getTypeRequest_2() {
        app.jsonObj = jsonObj
        var actual = app.getTypeRequest(2)
        assertEquals(actual!!::class, TaskUpdate(jsonObj)::class)
    }

    @Test
    fun getTypeRequest_3() {
        app.jsonObj = jsonObj
        var actual = app.getTypeRequest(3)
        assertEquals(actual!!::class, TaskComment(jsonObj)::class)
    }

    @Test
    fun getTypeRequest_14() {
        app.jsonObj = jsonObj
        var actual = app.getTypeRequest(14)
        assertEquals(actual!!::class, TaskCollectUpdate(jsonObj)::class)
    }

    @Test
    fun getTypeRequest_5() {
        app.jsonObj = jsonObj
        var actual = app.getTypeRequest(5)
        assertEquals(actual!!::class, WikiAdd(jsonObj)::class)
    }

    @Test
    fun getTypeRequest_6() {
        app.jsonObj = jsonObj
        var actual = app.getTypeRequest(6)
        assertEquals(actual!!::class, WikiUpdate(jsonObj)::class)
    }

    @Test
    fun getTypeRequest_11() {
        app.jsonObj = jsonObj
        var actual = app.getTypeRequest(11)
        assertEquals(actual!!::class, SvnCommit(jsonObj)::class)
    }

    @Test
    fun getTypeRequest_12() {
        app.jsonObj = jsonObj
        var actual = app.getTypeRequest(12)
        assertEquals(actual!!::class, GitPush(jsonObj)::class)
    }

    @Test
    fun getTypeRequest_18() {
        app.jsonObj = jsonObj
        var actual = app.getTypeRequest(18)
        assertEquals(actual!!::class, GitPullRequestAdd(jsonObj)::class)
    }

    @Test
    fun getTypeRequest_19() {
        app.jsonObj = jsonObj
        var actual = app.getTypeRequest(19)
        assertEquals(actual!!::class, GitPullRequestUpdate(jsonObj)::class)
    }

    @Test
    fun getTypeRequest_20() {
        app.jsonObj = jsonObj
        var actual = app.getTypeRequest(20)
        assertEquals(actual!!::class, GitPullRequestComment(jsonObj)::class)
    }
}