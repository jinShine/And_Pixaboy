package com.jinnify.searchimageapp.scene.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.jinnify.searchimageapp.R
import com.jinnify.searchimageapp.core.Constant
import kotlinx.android.synthetic.main.viewholder_image.*

class DetailActivity : AppCompatActivity() {

    override fun onEnterAnimationComplete() {
        super.onEnterAnimationComplete()

        val intent = intent
        val itemUrl = intent.getStringExtra(Constant.Intent.SELECTED_IMAGE_URL)

        Glide.with(this)
            .load(itemUrl)
            .into(itemImageView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFinishAfterTransition()

        return true
    }
}
