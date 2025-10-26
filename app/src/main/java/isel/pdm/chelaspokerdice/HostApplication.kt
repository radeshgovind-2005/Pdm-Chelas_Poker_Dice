package isel.pdm.chelaspokerdice

import android.app.Application
import kotlin.getValue
import isel.pdm.chelaspokerdice.services.fakeservice.FakeLobbyService
import isel.pdm.chelaspokerdice.vm.LobbyViewModel

class HostApplication : Application() {

    val lobbyService by lazy { FakeLobbyService() }
    val lobbyViewModel by lazy { LobbyViewModel(lobbyService) }
}