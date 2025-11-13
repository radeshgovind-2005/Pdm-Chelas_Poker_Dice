package isel.pdm.pokerdice.data

import isel.pdm.pokerdice.domain.AuthInfo
import isel.pdm.pokerdice.domain.Lobbies
import isel.pdm.pokerdice.domain.Lobby
import isel.pdm.pokerdice.domain.User
import isel.pdm.pokerdice.domain.UserCredentials
import isel.pdm.pokerdice.domain.values.Name

object FakeDataGenerator {

    private val fakeUsers = listOf(
        "Alice", "Bob", "Charlie", "Diana", "Eve", "Frank", "Grace",
        "Henry", "Ivy", "Jack", "Karen", "Leo", "Mia"
    ).map { name ->
        User(
            userCredentials = UserCredentials(
                username = name.lowercase(),
                password = "Password123"
            ),
            authInfo = AuthInfo(Name.create(name).getOrThrow(), "genarated-token")
        )
    }
    private val opa = listOf("Julia","Joana","Sara","Sofia").map { name ->
        User(
            userCredentials = UserCredentials(
                username = name.lowercase(),
                password = "Password123"
            ),
            authInfo = AuthInfo(Name.create(name).getOrThrow(), "genarated-token")
        )
    }

    private val lobbyTemplates = listOf(
        Triple("Poker Masters", "Competitive poker tournament", 4 to 20),
        Triple("Casual Friday", "Friendly Friday games", 3 to 15),
        Triple("High Rollers", "For experienced players", 6 to 30),
        Triple("Beginner's Luck", "New players welcome!", 2 to 10),
        Triple("Poker Isel", "Isel poker competition", 4 to 20),
        Triple("Casual PDM", "PDM study break games", 3 to 15),
        Triple("Gamblers", "High stakes games", 6 to 30),
        Triple("Weekend Warriors", "Saturday night special", 5 to 25),
        Triple("Quick Draw", "Fast-paced poker", 3 to 10),
        Triple("All In Club", "Aggressive players only", 4 to 20)
    )

    fun generateLobbies(count: Int = 7): Lobbies {
        require(count in 1..lobbyTemplates.size) {
            "Count must be between 1 and ${lobbyTemplates.size}"
        }

        return lobbyTemplates.take(count).mapIndexed { index, (name, desc, playersRounds) ->
            val (expectedPlayers, nOfRounds) = playersRounds
            val l = Lobby.create(
                name = name,
                description = desc,
                expectedPlayers = expectedPlayers,
                nOfRounds = nOfRounds,
                host = fakeUsers[index]
            ).getOrThrow()
            if(name == "Gamblers") {
                opa.forEach{
                    l.lobbyPlayers.add(it)
                }
            }
            l
        }
    }

    // Helper to get a random user for testing
    fun getRandomUser(): User = fakeUsers.random()

    // Helper to get all fake users
    fun getFakeUsers(): List<User> = fakeUsers

    // Helper to find user by username
    fun getUserByUsername(username: String): User? =
        fakeUsers.find { it.userCredentials.username == username }
}