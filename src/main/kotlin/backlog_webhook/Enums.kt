package backlog_webhook

enum class ChangeField(val key: String, val value: String) {
    unknown("unknown", "不明"),
    status("status", "状態"),
    commit("commit", "コミット"),
    parentIssue("parentIssue", "親課題"),
    startDate("startDate", "開始日"),
    limitDate("limitDate", "期限日"),
    description("description", "詳細"),
    assigner("assigner", "担当者")
}

fun getChangeField(key: String?): ChangeField {
    return ChangeField.values().singleOrNull { it.key == key } ?: ChangeField.unknown
}

enum class TaskStatus(val key: String, val value: String) {
    unknown("unknown", "不明"),
    _1("1", "未対応"),
    _2("2", "処理中"),
    _3("3", "処理済み"),
    _4("4", "完了"),
}

fun getTaskStatus(key: String?): TaskStatus {
    return TaskStatus.values().singleOrNull { it.key == key } ?: TaskStatus.unknown
}

enum class PullRequestStatus(val key: String, val value: String) {
    unknown("unknown", "不明"),
    _1("1", "Open"),
    _3("3", "Merged"),
}

fun getPullRequestStatus(key: String?): PullRequestStatus {
    return PullRequestStatus.values().singleOrNull { it.key == key } ?: PullRequestStatus.unknown
}
