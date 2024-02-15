package pl.killus.galleryapp

import android.content.ContentResolver
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import pl.killus.galleryapp.databinding.ActivityMainBinding
import pl.killus.galleryapp.ui.Album
import pl.killus.galleryapp.ui.Image
import pl.killus.galleryapp.ui.ImageAdapter
import pl.killus.galleryapp.utils.BaseActivity
import pl.killus.galleryapp.utils.fetchData

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val b by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val images =
            applicationContext.contentResolver.fetchData(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                arrayOf(
                    MediaStore.Images.Media.DATA
                ),
                intent.getStringExtra("album_query")
            ) { Image(it[0], "") }

        b.recycler.layoutManager = FlexboxLayoutManager(this).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            justifyContent = JustifyContent.CENTER
        }

        b.recycler.adapter =
            ImageAdapter(images) {

            }
    }
}