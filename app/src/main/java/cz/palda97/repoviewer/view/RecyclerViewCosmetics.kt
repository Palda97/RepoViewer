package cz.palda97.repoviewer.view

import android.content.Context
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

object RecyclerViewCosmetics {

    fun RecyclerView.addDividers(context: Context) {
        addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
    }
}