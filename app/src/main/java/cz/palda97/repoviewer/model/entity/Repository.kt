package cz.palda97.repoviewer.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Repository(
    @PrimaryKey(autoGenerate = false) val id: Long,
    val name: String,
    val fullName: String,
    val description: String?
)
