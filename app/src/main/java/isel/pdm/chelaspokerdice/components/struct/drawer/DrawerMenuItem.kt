package isel.pdm.chelaspokerdice.components.struct.drawer

data class DrawerMenuItem(
    val id: String,
    val label: String,
    val action: () -> Unit
)