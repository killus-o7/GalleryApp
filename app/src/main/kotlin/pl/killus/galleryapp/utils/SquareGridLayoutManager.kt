package pl.killus.galleryapp.utils

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SquareGridLayoutManager(context: Context, spanCount: Int) : GridLayoutManager(context, spanCount) {
    override fun onMeasure(recycler: RecyclerView.Recycler, state: RecyclerView.State, widthSpec: Int, heightSpec: Int) {
        super.onMeasure(recycler, state, widthSpec, heightSpec)
        val width = View.MeasureSpec.getSize(widthSpec)
        val height = View.MeasureSpec.getSize(heightSpec)
        val minDimension = width.coerceAtMost(height)
        setMeasuredDimension(minDimension, minDimension)
    }
}