package com.geappliances.nativeapp

import android.app.Application
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

class NativeApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initFlutterEngine()
    }

    private fun initFlutterEngine() {

        val greenEngine = FlutterEngine(this)
        greenEngine.navigationChannel.setInitialRoute("green")
        greenEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )
        FlutterEngineCache.getInstance().put("green", greenEngine)

        val redEngine = FlutterEngine(this)
        redEngine.navigationChannel.setInitialRoute("red")
        redEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )
        FlutterEngineCache.getInstance().put("red", redEngine)
    }

    fun getFlutterEngine(engineId: String): FlutterEngine?
            = FlutterEngineCache.getInstance().get(engineId)
}