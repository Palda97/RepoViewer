package cz.palda97.repoviewer.model.entity

import com.google.gson.Gson
import com.google.gson.JsonArray

class CommitFactory {

    private val gson = Gson()

    fun fromJsonArray(jsonArray: JsonArray): List<GitCommit>? {
        val uncheckedCommits = jsonArray.map {
            gson.fromJson(it, CommitWrapper::class.java)!!
        }
        return uncheckedCommits.map {
            it.gitCommit ?: return null
        }
    }
}

private data class NullableCommit(
    val message: String?
)

private data class CommitWrapper(
    val sha: String?,
    val commit: NullableCommit?
) {
    val gitCommit: GitCommit?
        get() = if (sha != null && commit != null && commit.message != null) GitCommit(
            sha,
            commit.message
        ) else null
}