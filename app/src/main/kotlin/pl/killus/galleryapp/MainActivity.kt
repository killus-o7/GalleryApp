package pl.killus.galleryapp

import android.content.ContentResolver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import pl.killus.galleryapp.databinding.ActivityMainBinding
import pl.killus.galleryapp.ui.Image
import pl.killus.galleryapp.ui.ImageAdapter
import pl.killus.galleryapp.utils.BaseActivity
import pl.killus.galleryapp.utils.SquareGridLayoutManager

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val b by lazy {ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val contentResolver: ContentResolver = applicationContext.contentResolver
        val imagePaths = fetchImagePaths(contentResolver)
        b.recycler.layoutManager = SquareGridLayoutManager(this, 3)
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