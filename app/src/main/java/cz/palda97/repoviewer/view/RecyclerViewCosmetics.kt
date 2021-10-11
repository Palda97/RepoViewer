package cz.palda97.repoviewer.view

import android.content.Context
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

/**
 * Object containing methods for making the recycler view pretty.
 */
object RecyclerViewCosmetics {

    /**
     * Add dividers between recycler view items.
     */
    fun RecyclerView.addDividers(context: Context) {
        addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
    }
}