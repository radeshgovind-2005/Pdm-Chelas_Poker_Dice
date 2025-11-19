package isel.pdm.pokerdice

sealed class AppTag(val value: String) {
    data object Error: AppTag("APP_ERROR")
    data object Info: AppTag("APP_INFO")
    data object GameRules: AppTag("APP_GAME_RULES")
}