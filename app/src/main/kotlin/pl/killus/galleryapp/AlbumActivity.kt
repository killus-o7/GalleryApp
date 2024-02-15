package pl.killus.galleryapp

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import pl.killus.galleryapp.databinding.ActivityAlbumBinding
import pl.killus.galleryapp.ui.Album
import pl.killus.galleryapp.ui.AlbumAdapter
import pl.killus.galleryapp.utils.BaseActivity
import pl.killus.galleryapp.utils.fetchData

class AlbumActivity : BaseActivity<ActivityAlbumBinding>() {
    override val b by lazy { ActivityAlbumBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val albums =
            applicationContext.contentResolver.fetchData(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                arrayOf(
                    MediaStore.Images.Media.BUCKET_ID,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                    MediaStore.Images.Media.DATE_TAKEN
                )
            ) { Album(it[0], it[1]) }.apply {
                add(0, Album(null, "wszystkie"))
            }.distinct()

        b.recycler.apply {
            adapter = AlbumAdapter(albums) {
                val intent = Intent(context, MainActivity::class.java)
                if (it.id != null) intent.putExtra(
                    "album_query",
                    MediaStore.Images.Media.BUCKET_ID + " ='" + it.id + "'"
                )
                startActivity(intent)
            }
            layoutManager = FlexboxLayoutManager(context).apply {
                flexDirection = FlexDirection.ROW
                flexWrap = FlexWrap.WRAP
                justifyContent = JustifyContent.CENTER
            }
        }
    }
}