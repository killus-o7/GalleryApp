package pl.killus.galleryapp.ui

data class Image (
    val url: String,
    val title: String,
    val date: String,
    val isPlaceholder: Boolean = false
)