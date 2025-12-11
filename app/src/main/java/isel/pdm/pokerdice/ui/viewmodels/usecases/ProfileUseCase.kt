package isel.pdm.pokerdice.ui.viewmodels.usecases

import isel.pdm.pokerdice.domain.user.UserStats
import isel.pdm.pokerdice.repo.AuthRepository
import isel.pdm.pokerdice.services.AuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProfileUseCase(
    private val authService: AuthService,
    private val authRepo: AuthRepository
)  {
    suspend fun logout()=
        withContext(Dispatchers.IO) {
            runCatching {
                val info = authRepo.getAuthInfo()
                if(info?.authToken != null){
                    authService.logout(info.authToken)
                    authRepo.clearAuthInfo()
                }
            }
        }

    suspend fun getStats(): Result<Pair<UserStats, String>> =
        runCatching {
            val auth = authRepo.getAuthInfo()
            if(auth==null ) throw Exception("User not authenticated")
            val token = auth.authToken
            if(token==null ) throw Exception("Session Expired")
            Pair(authService.getUserStats(token), auth.username.value)
        }

}