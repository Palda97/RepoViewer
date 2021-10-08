package cz.palda97.repoviewer.model.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import cz.palda97.repoviewer.model.entity.Repository

@Dao
abstract class RepositoryDao {

    @Query("delete from repository")
    abstract suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(repositories: List<Repository>)

    @Query("select * from repository")
    abstract fun liveAllRepositories(): LiveData<List<Repository>>

    @Transaction
    open suspend fun replaceRepositories(repositories: List<Repository>) {
        deleteAll()
        insert(repositories)
    }
}