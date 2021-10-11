package cz.palda97.repoviewer.model.network

import com.koushikdutta.ion.Ion
import cz.palda97.repoviewer.RepoViewerApplication

class GithubIon {

    suspend fun getRepos(username: String) = Ion.with(RepoViewerApplication.context)
        .load("$BASE_URL/users/$username/repos")
        .asJsonArray()
        .await()

    suspend fun getRecentCommits(repoFullName: String) = Ion.with(RepoViewerApplication.context)
        .load("$BASE_URL/repos/$repoFullName/commits?per_page=10")
        .asJsonArray()
        .await()

    suspend fun getBranches(repoFullName: String) = Ion.with(RepoViewerApplication.context)
        .load("$BASE_URL/repos/$repoFullName/branches")
        .asJsonArray()
        .await()

    companion object {

        private const val BASE_URL = "https://api.github.com"
    }
}
