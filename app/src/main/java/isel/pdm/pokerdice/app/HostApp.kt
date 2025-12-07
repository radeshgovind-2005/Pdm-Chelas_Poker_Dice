package isel.pdm.pokerdice.app

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ProcessLifecycleOwner

class HostApp: Application() {

    private val logger by lazy { AppLog(this::class.java.simpleName) }

    lateinit var container: AppContainer
        private set

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        logger.i("Host App Created: Initializing Dependencies")
        container = AppContainer(this)
        val lifecycleMonitor = AppLifecycleMonitor(this, logger)
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleMonitor)
    }
}