package pl.killus.galleryapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.flexbox.FlexboxLayoutManager
import pl.killus.galleryapp.R
import pl.killus.galleryapp.databinding.ImageItemBinding

class ImageAdapter(
    private val data: List<Image> = listOf(),
    private val click: (Image) -> Unit
) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position], click)
    override fun getItemCount() = data.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
    class ViewHolder(private val b: ImageItemBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(entry: Image, click: (Image) -> Unit) {
            b.apply {
                if (entry.isPlaceholder) root.visibility = View.INVISIBLE
                else root.setOnClickListener { click(entry) }
                Glide.with(image).load(entry.url).apply(RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)).into(image)
                (root.layoutParams as FlexboxLayoutManager.LayoutParams).apply {
                    flexBasisPercent = .3f
                }
            }
        }
    }
}