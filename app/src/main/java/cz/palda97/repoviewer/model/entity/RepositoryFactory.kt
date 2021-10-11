package cz.palda97.repoviewer.model.entity

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName

class RepositoryFactory {

    private val gson = Gson()

    fun fromJsonArray(jsonArray: JsonArray): List<Repository>? {
        val uncheckedRepos = jsonArray.map {
            gson.fromJson(it, NullableRepository::class.java)!!
        }
        return uncheckedRepos.map {
            it.repository ?: return null
        }
    }
}

private data class NullableRepository(
    val id: Long,
    val name: String?,
    @SerializedName("full_name") val fullName: String?,
    val description: String?
) {
    val repository: Repository?
        get() = if (id != 0L && name != null && fullName != null) Repository(
            id,
            name,
            fullName,
            description
        ) else null
}