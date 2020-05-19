package com.geappliances.nativeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import io.flutter.embedding.android.FlutterActivity
import io.flutter.plugin.common.MethodChannel
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as NativeApplication).getFlutterEngine("red")?.let { redEngine ->

            MethodChannel(redEngine.dartExecutor.binaryMessenger, "pallet/red")
                .setMethodCallHandler { call, result ->
                    if (call.method == "getTime") {
                        result.success(getTime())
                    }
                }
        }
    }

    fun onClickGreen(view: View) {
        startActivity(FlutterActivity.withCachedEngine("green").build(this))
    }

    fun onClickRed(view: View) {
        startActivity(FlutterActivity.withCachedEngine("red").build(this))
    }

    fun getTime(): String = Calendar.getInstance().time.toString()
}
