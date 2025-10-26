package isel.pdm.chelaspokerdice

import android.app.Application
import kotlin.getValue
import isel.pdm.chelaspokerdice.services.fakeservice.FakeLobbyService

class HostApplication : Application() {

    val lobbyService by lazy { FakeLobbyService() }
}