package isel.pdm.pokerdice.domain


import isel.pdm.pokerdice.domain.values.Name

data class AuthInfo(val userName: Name, val authToken: String)