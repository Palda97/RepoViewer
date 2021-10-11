package cz.palda97.repoviewer.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cz.palda97.repoviewer.model.db.dao.BranchDao
import cz.palda97.repoviewer.model.db.dao.CommitDao
import cz.palda97.repoviewer.model.db.dao.RepositoryDao
import cz.palda97.repoviewer.model.entity.Branch
import cz.palda97.repoviewer.model.entity.GitCommit
import cz.palda97.repoviewer.model.entity.Repository

/**
 * Room database class for this application.
 */
@Database(
    entities = [Repository::class, GitCommit::class, Branch::class],
    version = 2,
    exportSchema = true
)
abstract class RepoViewerDatabase : RoomDatabase() {

    abstract fun repositoryDao(): RepositoryDao
    abstract fun commitDao(): CommitDao
    abstract fun branchDao(): BranchDao

    companion object {

        private var instance: RepoViewerDatabase? = null

        fun getInstance(context: Context): RepoViewerDatabase {
            if (instance == null) {
                synchronized(RepoViewerDatabase::class.java) {
                    if (instance == null) {
                        instance =
                            Room.databaseBuilder(context, RepoViewerDatabase::class.java, "database")
                                .fallbackToDestructiveMigration()
                                .build()
                    }
                }
            }
            return instance!!
        }
    }
}