package cz.palda97.repoviewer.model.repository

import androidx.lifecycle.MutableLiveData
import cz.palda97.repoviewer.log
import cz.palda97.repoviewer.model.Either
import cz.palda97.repoviewer.model.SingleLiveEvent
import cz.palda97.repoviewer.model.db.dao.RepositoryDao
import cz.palda97.repoviewer.model.entity.Repository
import cz.palda97.repoviewer.model.entity.RepositoryFactory
import cz.palda97.repoviewer.model.network.GithubIon
import java.lang.ClassCastException

/**
 * Repository for gathering and providing user's Github repositories.
 */
class UserRepository(
    private val githubIon: GithubIon,
    private val repositoryDao: RepositoryDao
) {

    enum class ErrorCode {
        NOT_FOUND, CONNECTION_PROBLEM, PARSING_ERROR
    }

    private suspend fun downloadRepositories(username: String): Either<ErrorCode, List<Repository>> {
        val downloaded = when (val res = githubIon.getRepos(username)) {
            is Either.Left -> {
                log(res.value)
                if (res.value is ClassCastException)
                    return Either.Left(ErrorCode.NOT_FOUND)
                return Either.Left(ErrorCode.CONNECTION_PROBLEM)
            }
            is Either.Right -> res.value!!
        }
        val repositories = RepositoryFactory().fromJsonArray(downloaded) ?: return Either.Left(
            ErrorCode.PARSING_ERROR
        )
        return Either.Right(repositories)
    }

    private suspend fun storeRepositories(repositories: List<Repository>) {
        repositoryDao.replaceRepositories(repositories)
    }

    val liveErrorCode = SingleLiveEvent<ErrorCode>()

    val liveRepositoryLoading = MutableLiveData(false)

    /**
     * Download and store repositories.
     * Updates [liveErrorCode] on error.
     * @return true on success, false otherwise.
     */
    suspend fun cacheRepositories(username: String): Boolean {
        val repositories = when (val res = downloadRepositories(username)) {
            is Either.Left -> {
                liveErrorCode.postValue(res.value)
                return false
            }
            is Either.Right -> res.value
        }
        storeRepositories(repositories)
        return true
    }

    val liveRepositories = repositoryDao.liveAllRepositories()
}