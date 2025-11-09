package isel.pdm.pokerdice.domain

import isel.pdm.pokerdice.domain.values.isValidName
import isel.pdm.pokerdice.domain.values.isValidPassword

/**
 * Data class representing user credentials as they are collected in the client application.
 * @param username The user's name.
 * @param password The user's password.
 */
data class UserCredentials(val username: String, val password: String) {
    init {
        username.isValidName()?.let{ throw it }
        password.isValidPassword()?.let{throw it}
    }
}

/**
 * Exception thrown when user credentials are invalid.
 */
class InvalidCredentialsException : Exception("Invalid user credentials provided")