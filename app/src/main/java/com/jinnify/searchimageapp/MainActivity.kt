package com.jinnify.searchimageapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.jinnify.searchimageapp.parser.PixaboyParser

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val parser = PixaboyParser()
        println(parser.searchImageFrom("승진"))

    }
}
