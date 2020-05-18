package com.geappliances.nativeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import io.flutter.embedding.android.FlutterActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickGreen(view: View) {
        startActivity(FlutterActivity.withCachedEngine("green").build(this))
    }

    fun onClickRed(view: View) {
        startActivity(FlutterActivity.withCachedEngine("red").build(this))
    }
}
