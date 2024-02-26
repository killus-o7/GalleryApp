package pl.killus.galleryapp

import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.stfalcon.imageviewer.StfalconImageViewer
import pl.killus.galleryapp.databinding.ActivityImageBinding
import pl.killus.galleryapp.databinding.ImageOverlayBinding
import pl.killus.galleryapp.ui.Image
import pl.killus.galleryapp.ui.ImageAdapter
import pl.killus.galleryapp.utils.Activity
import pl.killus.galleryapp.utils.fetchData

class ImageActivity : Activity<ActivityImageBinding>(ActivityImageBinding::inflate) {
    private lateinit var images: MutableList<Image>
    private var imagesPlaceholderCount = 0
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = intent.getStringExtra("title")
        images = getImages()
        generatePlaceholders()

        b.recycler.layoutManager = FlexboxLayoutManager(this).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            justifyContent = JustifyContent.CENTER
            b.recycler.adapter = ImageAdapter(images, ::imageOnClick)
        }
    }

    private fun imageOnClick(image: Image){
        val overlayBinding = ImageOverlayBinding.inflate(layoutInflater)

        val viewer = StfalconImageViewer
            .Builder(
                this,
                images.dropLast(imagesPlaceholderCount)
            ) { view, img ->
                Glide.with(this).load(img.url).into(view)
            }.withStartPosition(images.indexOf(image))
            .withOverlayView(overlayBinding.root)
            .build()

        overlayBinding.exit.setOnClickListener {
            viewer.close()
        }

        viewer.show()
    }

    private fun getImages() =
        applicationContext.contentResolver.fetchData(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            arrayOf(
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_ADDED
            ),
            intent.getStringExtra("album_query"),
            sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"
        ) { Image(it[0], "", it[1]) }.toMutableList()

    private fun generatePlaceholders() {
        imagesPlaceholderCount = 3-(images.size%3)
        if (images.size%3 == 0) return
        for (i in 1..imagesPlaceholderCount)
            images.add(Image("","","", isPlaceholder = true))
    }
}