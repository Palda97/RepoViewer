package cz.palda97.repoviewer.model.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import cz.palda97.repoviewer.model.entity.Branch

/**
 * DAO for working with [Branch].
 */
@Dao
abstract class BranchDao {

    @Query("delete from branch")
    abstract suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(branches: List<Branch>)

    @Query("select * from branch")
    abstract fun liveAllBranches(): LiveData<List<Branch>>

    /**
     * Replace already stored branches with the new ones.
     */
    @Transaction
    open suspend fun replaceBranches(branches: List<Branch>) {
        deleteAll()
        insert(branches)
    }
}