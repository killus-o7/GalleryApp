package pl.killus.galleryapp

import android.content.ContentResolver
import android.os.Bundle
import android.provider.MediaStore
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import pl.killus.galleryapp.databinding.ActivityMainBinding
import pl.killus.galleryapp.ui.Image
import pl.killus.galleryapp.ui.ImageAdapter
import pl.killus.galleryapp.utils.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val b by lazy {ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val contentResolver: ContentResolver = applicationContext.contentResolver
        val imagePaths = fetchImagePaths(contentResolver)
        b.recycler.layoutManager = FlexboxLayoutManager(this).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            justifyContent = JustifyContent.CENTER
        }

        b.recycler.adapter =
            ImageAdapter(imagePaths) {

            }
    }

    private fun fetchImagePaths(contentResolver: ContentResolver): List<Image> {
        val imagePaths = mutableListOf<Image>()
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,null,null,null
        )
        cursor?.use { c ->
            while (c.moveToNext()) {
                val path = c.getString(c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                imagePaths.add(Image(path, ""))
            }
        }
        return imagePaths
    }
}