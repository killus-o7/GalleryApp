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
import pl.killus.galleryapp.utils.Activity
import pl.killus.galleryapp.utils.fetchData

class AlbumActivity : Activity<ActivityAlbumBinding>(ActivityAlbumBinding::inflate) {
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
            ) { Album(it[1], it[0]) }.apply {
                add(0, Album("wszystkie", "-1"))
            }.distinct()

        albums.forEach { album ->
            applicationContext.contentResolver.fetchData(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                arrayOf(
                    MediaStore.Images.Media.DATA
                ),
                "%s = '%s'".format(
                    MediaStore.Images.Media.BUCKET_ID, album.id
                ).takeIf { album.id != "-1" }
            ) { album.thumbnailPath = it[0] }
        }

        b.recycler.apply {
            adapter = AlbumAdapter(albums) {
                val intent = Intent(context, ImageActivity::class.java)
                if (it.id != "-1") intent.putExtra(
                    "album_query",
                    "%s = '%s'".format(
                        MediaStore.Images.Media.BUCKET_ID, it.id
                    )
                )

                intent.putExtra("title", it.name)
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