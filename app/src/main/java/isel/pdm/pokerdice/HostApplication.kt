package isel.pdm.pokerdice

import android.app.Application
import isel.pdm.pokerdice.services.fake.FakeLobbyService
import isel.pdm.pokerdice.services.fake.FakeAuthService
import isel.pdm.pokerdice.repo.fake.FakeAuthInfoRepo
import isel.pdm.pokerdice.ui.viewmodels.AuthViewModel
import kotlin.getValue
import isel.pdm.pokerdice.usecases.AuthUseCase

class HostApplication: Application() {
    val lobbyService by lazy { FakeLobbyService() }
    val authService by lazy { FakeAuthService() }
    val authRepo by lazy { FakeAuthInfoRepo() }
    val authUseCase by lazy { AuthUseCase(authService,authRepo) }

}