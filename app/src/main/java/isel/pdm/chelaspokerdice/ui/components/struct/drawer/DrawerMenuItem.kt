package isel.pdm.chelaspokerdice.ui.components.struct.drawer

data class DrawerMenuItem(
    val id: String,
    val label: String,
    val action: () -> Unit
)