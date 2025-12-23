package isel.pdm.pokerdice.domain.model.user

data class UserStats(
    val gamesPlayed: String,
    val matchesWon: String,
    val winRate: String,
    val roundsWon: String,
    val lobbiesHosted: String,
    val invitesSent: String,
    val epicHands: String,
    val totalBalance: String
)

// Mappers.kt ou dentro do Service
fun Map<String, String>.toUserStats(): UserStats {
    // Usamos o operador ?: "0" para garantir que nunca crasha se faltar uma chave
    return UserStats(
        gamesPlayed = this["Games Played"] ?: "0",
        matchesWon = this["Matches Won"] ?: "0",
        winRate = this["Win Rate"] ?: "0%",
        roundsWon = this["Rounds Won"] ?: "0",
        lobbiesHosted = this["Lobbies Hosted"] ?: "0",
        invitesSent = this["Invites Sent"] ?: "0",
        epicHands = this["Five of a Kinds"] ?: "0",
        totalBalance = this["Total Balance"] ?: "0"
    )
}