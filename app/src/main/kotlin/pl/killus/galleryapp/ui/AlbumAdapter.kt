package pl.killus.galleryapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.flexbox.FlexboxLayoutManager
import pl.killus.galleryapp.R
import pl.killus.galleryapp.databinding.AlbumItemBinding

class AlbumAdapter (
    private val data: List<Album> = listOf(),
    private val click: (Album) -> Unit
) : RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position], click)
    override fun getItemCount() = data.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        AlbumItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    class ViewHolder(private val b: AlbumItemBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(entry: Album, click: (Album) -> Unit) {
            b.apply {
                Glide.with(image).load(entry.thumbnailPath).apply(options).into(image)
                root.setOnClickListener { click(entry) }
                text.text = entry.name
                (root.layoutParams as FlexboxLayoutManager.LayoutParams).apply {
                    flexBasisPercent = .48f
                }
                text.setBackgroundColor(0xA0000000.toInt())
            }
        }
    }
    companion object {
        val options = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
    }
}