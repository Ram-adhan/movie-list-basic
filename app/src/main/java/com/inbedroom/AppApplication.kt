package com.inbedroom

import android.app.Application
import com.inbedroom.myapplication.data.RepositoryClass
import com.willowtreeapps.hyperion.crash.CrashPlugin
import com.willowtreeapps.hyperion.timber.TimberPlugin
import timber.log.Timber

class AppApplication: Application() {
    companion object {
        private lateinit var _repository: RepositoryClass
        val repositoryClass get() = _repository
    }
    override fun onCreate() {
        super.onCreate()
        _repository = RepositoryClass()
        Timber.plant(Timber.DebugTree())
    }
}