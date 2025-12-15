package isel.pdm.pokerdice.services.fake

import isel.pdm.pokerdice.domain.match.Game
import isel.pdm.pokerdice.domain.match.LobbyContent
import isel.pdm.pokerdice.domain.match.MatchContent
import isel.pdm.pokerdice.domain.match.MatchPlayers
import isel.pdm.pokerdice.domain.match.Round
import isel.pdm.pokerdice.services.MatchService
import isel.pdm.pokerdice.services.events.MatchEvents
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import java.util.UUID

class FakeMatchService : MatchService {

    private var currentGameState: Game? = null
    private val matchEventsFlow = MutableSharedFlow<MatchEvents>()

    // As faces possíveis dos dados de Póquer
    private val diceFaces = listOf('9', 'T', 'J', 'Q', 'K', 'A')

    override suspend fun startMatch(lobbyId: String, token: String): String? {
        delay(1000)
        val newMatchId = UUID.randomUUID().toString()

        currentGameState = Game(
            username = "Me",
            match = MatchContent(
                matchId = newMatchId,
                status = "WAITING",
                isStarted = true,
                isCompleted = false
            ),
            lobby = LobbyContent(
                lobbyId = lobbyId,
                name = "Fake Lobby",
                ante = 100
            ),
            round = Round(
                totalRounds = 5,
                currRound = 1,
                roundBet = 200,
                players = listOf(
                    MatchPlayers(
                        name = "Me",
                        hand = "", // String vazia no início
                        rank = "NONE",
                        balance = 1000,
                        state = "WAITING",
                        rerollsLeft = 2
                    ),
                    MatchPlayers(
                        name = "Bot",
                        hand = "",
                        rank = "NONE",
                        balance = 1000,
                        state = "WAITING",
                        rerollsLeft = 2
                    )
                )
            ),
            msg = "Waiting for players..."
        )

        return newMatchId
    }

    override suspend fun getMatchInfo(matchId: String): Game {
        delay(500)
        return currentGameState ?: throw Exception("Match not found")
    }

    override suspend fun startRound(matchId: String) {
        delay(500)
        updateGameState { game ->
            game.copy(
                match = game.match.copy(status = "PLAYING"),
                msg = "Round Started! Roll your dice."
            )
        }
        emitEvent(MatchEvents.RoundInit(currentGameState!!))
    }

    override suspend fun completeRound(matchId: String) {
        delay(500)
        updateGameState { game ->
            game.copy(
                match = game.match.copy(status = "FINISHED"),
                msg = "Round Completed! Winner calculated."
            )
        }
        emitEvent(MatchEvents.RoundComplete(currentGameState!!))
    }

    override fun subscribeToMatch(matchId: String): Flow<MatchEvents> = flow {
        emit(MatchEvents.Connected("Connected to Fake Match SSE"))

        currentGameState?.let {
            emit(MatchEvents.Subscribed(it))
        }

        matchEventsFlow.collect { event ->
            emit(event)
        }
    }

    override suspend fun rollAllDices(
        matchId: String,
        token: String
    ): Result<Unit> {
        delay(800)

        // Gerar 5 caracteres aleatórios (ex: "A K Q J 9") -> "AKQJ9"
        val newHandString = (1..5)
            .map { diceFaces.random() }
            .joinToString("")

        updatePlayerState("Me") { player ->
            player.copy(
                hand = newHandString,
                state = "PLAYING",
                rerollsLeft = 2,
                rank = "Pair" // Rank falso para teste
            )
        }

        emitEvent(MatchEvents.RollAll(currentGameState!!))
        return Result.success(Unit)
    }

    override suspend fun holdDices(
        matchId: String,
        token: String
    ): Result<Unit> {
        delay(500)
        updatePlayerState("Me") { it.copy(state = "FINISHED") }

        emitEvent(MatchEvents.HoldAll(currentGameState!!))
        return Result.success(Unit)
    }

    override suspend fun rollDices(
        matchId: String,
        dices: List<Int>, // Indices para MANTER (HOLD)
        token: String
    ): Result<Unit> {
        delay(800)

        updatePlayerState("Me") { player ->
            // 1. Converter string "AAJKQ" para array de chars ['A','A','J','K','Q']
            // Se estiver vazia, gera uma mão nova por segurança
            val currentHandChars = if (player.hand.length == 5) {
                player.hand.toCharArray()
            } else {
                CharArray(5) { diceFaces.random() }
            }

            // 2. Rolar dados cujos índices NÃO estão na lista 'dices' (hold)
            for (i in 0 until 5) {
                if (!dices.contains(i)) {
                    currentHandChars[i] = diceFaces.random()
                }
            }

            // 3. Juntar de novo numa string
            player.copy(
                hand = String(currentHandChars),
                rerollsLeft = (player.rerollsLeft - 1).coerceAtLeast(0)
            )
        }

        emitEvent(MatchEvents.ReRoll(currentGameState!!))
        return Result.success(Unit)
    }

    // --- Métodos Auxiliares ---

    private suspend fun emitEvent(event: MatchEvents) {
        matchEventsFlow.emit(event)
    }

    private fun updateGameState(update: (Game) -> Game) {
        currentGameState = currentGameState?.let(update)
    }

    private fun updatePlayerState(playerName: String, update: (MatchPlayers) -> MatchPlayers) {
        updateGameState { game ->
            val round = game.round ?: return@updateGameState game

            val updatedPlayers = round.players.map { player ->
                if (player.name == playerName) update(player) else player
            }

            game.copy(round = round.copy(players = updatedPlayers))
        }
    }
}