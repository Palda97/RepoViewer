package cz.palda97.repoviewer.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Branch(
    @PrimaryKey(autoGenerate = false) val name: String
)
