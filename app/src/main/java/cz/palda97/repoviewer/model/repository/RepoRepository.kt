package cz.palda97.repoviewer.model.repository

import androidx.lifecycle.MutableLiveData
import cz.palda97.repoviewer.log
import cz.palda97.repoviewer.model.Either
import cz.palda97.repoviewer.model.db.dao.BranchDao
import cz.palda97.repoviewer.model.db.dao.CommitDao
import cz.palda97.repoviewer.model.entity.Branch
import cz.palda97.repoviewer.model.entity.BranchFactory
import cz.palda97.repoviewer.model.entity.CommitFactory
import cz.palda97.repoviewer.model.entity.GitCommit
import cz.palda97.repoviewer.model.network.GithubIon
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class RepoRepository(
    private val githubIon: GithubIon,
    private val commitDao: CommitDao,
    private val branchDao: BranchDao
) {

    enum class StatusCode {
        NOT_FOUND, CONNECTION_PROBLEM, PARSING_ERROR, LOADING, OK
    }

    private suspend fun downloadCommits(repoFullName: String): Either<StatusCode, List<GitCommit>> {
        val downloaded = when (val res = githubIon.getRecentCommits(repoFullName)) {
            is Either.Left -> {
                log(res.value)
                if (res.value is ClassCastException)
                    return Either.Left(StatusCode.NOT_FOUND)
                return Either.Left(StatusCode.CONNECTION_PROBLEM)
            }
            is Either.Right -> res.value!!
        }
        val commits = CommitFactory().fromJsonArray(downloaded) ?: return Either.Left(
            StatusCode.PARSING_ERROR
        )
        return Either.Right(commits)
    }

    private suspend fun downloadBranches(repoFullName: String): Either<StatusCode, List<Branch>> {
        val downloaded = when (val res = githubIon.getBranches(repoFullName)) {
            is Either.Left -> {
                log(res.value)
                if (res.value is ClassCastException)
                    return Either.Left(StatusCode.NOT_FOUND)
                return Either.Left(StatusCode.CONNECTION_PROBLEM)
            }
            is Either.Right -> res.value!!
        }
        val branches = BranchFactory().fromJsonArray(downloaded) ?: return Either.Left(
            StatusCode.PARSING_ERROR
        )
        return Either.Right(branches)
    }

    private suspend fun storeCommits(commits: List<GitCommit>) {
        commitDao.replaceCommits(commits)
    }

    private suspend fun storeBranches(branches: List<Branch>) {
        branchDao.replaceBranches(branches)
    }

    val liveCommitStatus = MutableLiveData<StatusCode>()
    val liveBranchStatus = MutableLiveData<StatusCode>()

    private suspend fun cacheCommits(repoFullName: String) {
        liveCommitStatus.postValue(StatusCode.LOADING)
        val commits = when (val res = downloadCommits(repoFullName)) {
            is Either.Left -> {
                liveCommitStatus.postValue(res.value)
                return
            }
            is Either.Right -> res.value
        }
        storeCommits(commits)
        liveCommitStatus.postValue(StatusCode.OK)
    }

    private suspend fun cacheBranches(repoFullName: String) {
        liveBranchStatus.postValue(StatusCode.LOADING)
        val branches = when (val res = downloadBranches(repoFullName)) {
            is Either.Left -> {
                liveBranchStatus.postValue(res.value)
                return
            }
            is Either.Right -> res.value
        }
        storeBranches(branches)
        liveBranchStatus.postValue(StatusCode.OK)
    }

    suspend fun cache(repoFullName: String) = coroutineScope {
        val commits = launch { cacheCommits(repoFullName) }
        val branches = launch { cacheBranches(repoFullName) }
        commits.join()
        branches.join()
    }

    val liveCommits = commitDao.liveAllCommits()
    val liveBranches = branchDao.liveAllBranches()
}