package isel.pdm.pokerdice.ui.viewmodels.create

data class CreateState(
    val name: String = "",
    val description: String = "",
    val expectedPlayer: Int? = null ,
    val maxRounds: Int? = null ,
    val balance: Int? = null ,
    val ante: Int? = null,

    val nameError: String? = null,
    val descriptionError: String? = null,
    val expectedPlayerError: String? = null,
    val maxRoundsError: String? = null,
    val balanceError: String? = null,
    val anteError: String? = null,

    val isCreateEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)