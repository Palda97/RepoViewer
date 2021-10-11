package cz.palda97.repoviewer.model.entity

import com.google.gson.Gson
import com.google.gson.JsonArray

class BranchFactory {

    private val gson = Gson()

    fun fromJsonArray(jsonArray: JsonArray): List<Branch>? {
        val uncheckedBranches = jsonArray.map {
            gson.fromJson(it, NullableBranch::class.java)!!
        }
        return uncheckedBranches.map {
            it.branch ?: return null
        }
    }
}

private data class NullableBranch(
    val name: String?
) {
    val branch: Branch?
        get() = if (name != null) Branch(
            name
        ) else null
}