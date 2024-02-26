package pl.killus.galleryapp.utils

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
abstract class Activity<out B : ViewBinding>(
    private val inflate: (LayoutInflater) -> B
) : AppCompatActivity() {
    protected val b by lazy { inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)
    }
}


