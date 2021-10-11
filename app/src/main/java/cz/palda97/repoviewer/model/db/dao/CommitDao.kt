package cz.palda97.repoviewer.model.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import cz.palda97.repoviewer.model.entity.GitCommit

@Dao
abstract class CommitDao {

    @Query("delete from gitcommit")
    abstract suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(commits: List<GitCommit>)

    @Query("select * from gitcommit")
    abstract fun liveAllCommits(): LiveData<List<GitCommit>>

    @Transaction
    open suspend fun replaceCommits(commits: List<GitCommit>) {
        deleteAll()
        insert(commits)
    }
}