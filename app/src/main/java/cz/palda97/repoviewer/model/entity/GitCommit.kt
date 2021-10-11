package cz.palda97.repoviewer.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GitCommit(
    @PrimaryKey(autoGenerate = false) val sha: String,
    val message: String
)
