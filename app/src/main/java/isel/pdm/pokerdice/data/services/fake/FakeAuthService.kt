package isel.pdm.pokerdice.data.services.fake

import isel.pdm.pokerdice.domain.rules.Password
import isel.pdm.pokerdice.domain.rules.Username
import isel.pdm.pokerdice.domain.model.user.SessionInfo
import isel.pdm.pokerdice.domain.model.user.UserStats
import isel.pdm.pokerdice.domain.services.AuthService
import kotlinx.coroutines.delay
import java.util.UUID

class FakeAuthService : AuthService {

    private val fakeUsersDb = mapOf(
        "Ronaldo" to "1OlaMundo",
        "Alice" to "1OlaMundo",
        "Bob" to "1OlaMundo"
    )

    private val activeSessions = mutableMapOf<String, String>()

    private val userIds = mapOf(
        "Ronaldo" to 101,
        "Alice" to 102,
        "Bob" to 103
    )

    override suspend fun login(
        username: Username,
        pass: Password
    ): String? {
        // Simular delay de rede
        delay(1000)

        val storedPass = fakeUsersDb[username.value]

        // Verificar credenciais
        return if (storedPass != null && storedPass == pass.value) {
            val token = UUID.randomUUID().toString()
            activeSessions[token] = username.value
            token
        } else {
            null
        }
    }

    override suspend fun sessionCheck(token: String): SessionInfo {
        delay(500)

        val username = activeSessions[token]
            ?: throw Exception("Session expired or invalid")

        val id = userIds[username] ?: 999

        return SessionInfo(
            id = id,
            username = username,
            lobbyId = null,
            matchId = null
        )
    }

    override suspend fun logout(token: String) {
        delay(500)
        activeSessions.remove(token)
    }


    override suspend fun getUserStats(token: String): UserStats {
        delay(500)

        if (!activeSessions.containsKey(token)) {
            throw Exception("Unauthorized")
        }


        return UserStats(
            gamesPlayed = "50",
            matchesWon = "20",
            winRate = "40%",
            roundsWon = "150",
            lobbiesHosted = "10",
            invitesSent = "5",
            epicHands = "3",
            totalBalance = "10500"
        )
    }
}

fun main() {

     (1..3).forEach{ n ->
        val id = UUID.randomUUID().toString()
        println("Id number $n: $id")
    }
    /*
    * Output:
        Id number 1: c280fbe9-7c96-453f-b6d5-822587bb40fd
        Id number 2: 979b1d6a-d369-4a2e-8dab-34cf5a0e4870
        Id number 3: 369a5b08-0da7-4314-aeea-3f52de05249d
    */
}