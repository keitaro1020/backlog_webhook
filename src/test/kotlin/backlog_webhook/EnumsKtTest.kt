package backlog_webhook

import org.junit.Test

import org.junit.Assert.*

/**
 * Created by keitaro.shishido on 2017/09/29.
 */
class EnumsKtTest {

    @Test
    fun getChangeField() {
        assertEquals(backlog_webhook.getChangeField(null), ChangeField.unknown)
        assertEquals(backlog_webhook.getChangeField(""), ChangeField.unknown)
        assertEquals(backlog_webhook.getChangeField("commit"), ChangeField.commit)
    }

    @Test
    fun getTaskStatus() {
        assertEquals(backlog_webhook.getTaskStatus(null), TaskStatus.unknown)
        assertEquals(backlog_webhook.getTaskStatus(""), TaskStatus.unknown)
        assertEquals(backlog_webhook.getTaskStatus("1"), TaskStatus._1)
    }

    @Test
    fun getPullRequestStatus() {
        assertEquals(backlog_webhook.getPullRequestStatus(null), PullRequestStatus.unknown)
        assertEquals(backlog_webhook.getPullRequestStatus(""), PullRequestStatus.unknown)
        assertEquals(backlog_webhook.getPullRequestStatus("1"), PullRequestStatus._1)
    }

}