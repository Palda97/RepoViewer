package cz.palda97.repoviewer.model.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import cz.palda97.repoviewer.model.entity.Repository

/**
 * DAO for working with [Repository].
 */
@Dao
abstract class RepositoryDao {

    @Query("delete from repository")
    abstract suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(repositories: List<Repository>)

    @Query("select * from repository")
    abstract fun liveAllRepositories(): LiveData<List<Repository>>

    /**
     * Replace already stored repositories with the new ones.
     */
    @Transaction
    open suspend fun replaceRepositories(repositories: List<Repository>) {
        deleteAll()
        insert(repositories)
    }
}