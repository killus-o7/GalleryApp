package pl.killus.galleryapp.ui

data class Album (
    val name: String,
    val id: String? = null,
    var thumbnailPath: String? = null
)