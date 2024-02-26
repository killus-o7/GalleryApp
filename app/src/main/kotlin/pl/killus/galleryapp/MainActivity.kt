package pl.killus.galleryapp

import android.content.Intent
import android.os.Bundle
import pl.killus.galleryapp.databinding.ActivityMainBinding
import pl.killus.galleryapp.utils.Activity

class MainActivity : Activity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, AlbumActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}