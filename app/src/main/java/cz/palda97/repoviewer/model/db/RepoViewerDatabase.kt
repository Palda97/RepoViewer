package cz.palda97.repoviewer.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cz.palda97.repoviewer.model.db.dao.RepositoryDao
import cz.palda97.repoviewer.model.entity.Repository

@Database(
    entities = [Repository::class],
    version = 1,
    exportSchema = true
)
abstract class RepoViewerDatabase : RoomDatabase() {

    abstract fun serverDao(): RepositoryDao

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