package pl.killus.galleryapp

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
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
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission()
    }

    private fun onPermissionGranted(){
        val albums =
            applicationContext.contentResolver.fetchData(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                arrayOf(
                    MediaStore.Images.Media.BUCKET_ID,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                    MediaStore.Images.Media.DATE_TAKEN
                )
            ) { Album(it[1], it[0]) }.apply {
                add(0, Album("wszystkie"))
            }.distinct()

        albums.forEach {album ->
            applicationContext.contentResolver.fetchData(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                arrayOf(
                    MediaStore.Images.Media.DATA
                ),
                "%s = '%s'".format(
                    MediaStore.Images.Media.BUCKET_ID, album.id
                )
            ) { album.thumbnailPath = it[0] }
        }

        b.recycler.apply {
            adapter = AlbumAdapter(albums) {
                val intent = Intent(context, MainActivity::class.java)
                if (it.id != null) intent.putExtra(
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
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestPermission(){
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_MEDIA_IMAGES
            ) -> {
                // You can use the API that requires the permission.
            }
            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                requestPermissionLauncher.launch(
                    android.Manifest.permission.READ_MEDIA_IMAGES)
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) finish()
        else onPermissionGranted()
    }
}