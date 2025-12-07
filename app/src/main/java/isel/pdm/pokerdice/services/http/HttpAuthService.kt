package isel.pdm.pokerdice.services.http

import com.google.gson.Gson
import isel.pdm.pokerdice.app.AppLog
import isel.pdm.pokerdice.domain.types.Password
import isel.pdm.pokerdice.domain.types.Username
import isel.pdm.pokerdice.services.AuthService
import isel.pdm.pokerdice.services.http.HttpLobbyService.DefaultLobbyResponse
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody


class HttpAuthService(
    private val client: OkHttpClient,
    private val gson: Gson,
): AuthService {

    private val logger = AppLog(this::class.java.simpleName)

    companion object {
        private val USER_BASE_URL = BASE_URL + "users"
    }

    override suspend fun login(username: Username, pass: Password): String? {
        logger.i("login for -> $username")
        val body = gson
            .toJson(
                LoginDto(username.value, pass.value)
            ).toRequestBody(JSON_MEDIA_TYPE)
        val request = Request
                .Builder()
                .url(USER_BASE_URL + "/login")
                .post(body)
                .build()
        return client.newCall(request).await().use { response ->
            if (!response.isSuccessful) return@use null
            val responseBody = response.body?.string() ?: return@use null
            try {
                val tokenDto = gson.fromJson(responseBody, TokenDto::class.java)
                return@use tokenDto.token
            } catch (e: Exception) {
                return@use null
            }
        }
    }

    override suspend fun sessionCheck(token: String): String {
        val request = Request
            .Builder()
            .url("${BASE_URL}me")
            .header("Authorization", "Bearer $token")
            .build()
        val response = client.newCall(request).await()

        if (!response.isSuccessful) {
            throw Exception("Failed to Session check: ${response.code}")
        }

        val responseBody = response.body?.string() ?: throw Exception("Empty response")
        val responseDto = gson.fromJson(responseBody, String::class.java)
        return responseDto
    }

    override suspend fun logout(token: String) {
        logger.i("logout requested")
        //POST request -> empty body
        val emptyBody = "".toRequestBody(JSON_MEDIA_TYPE)

        val request = Request.Builder()
            .url(USER_BASE_URL + "/logout")
            .header("Authorization", "Bearer $token")
            .post(emptyBody)
            .build()

        client.newCall(request).await().use { response ->
            if (!response.isSuccessful) {
                logger.w("Server logout failed: ${response.code} - ${response.message}")
            } else {
                logger.i("Server logout successful")
            }
        }
    }

    data class LoginDto(val username: String, val password: String)
    data class TokenDto(val token: String)
}


