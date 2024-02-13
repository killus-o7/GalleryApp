package pl.killus.galleryapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
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
                Glide.with(image).load(entry.url).apply(options).into(image)
                b.root.setOnClickListener { click(entry) }
            }
        }
    }
    companion object {
        val options = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .override(200, 200)
    }
}