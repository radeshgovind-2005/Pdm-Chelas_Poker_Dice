package isel.pdm.pokerdice.data.services.http

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import isel.pdm.pokerdice.app.AppLog
import isel.pdm.pokerdice.domain.rules.Password
import isel.pdm.pokerdice.domain.rules.Username
import isel.pdm.pokerdice.domain.model.user.SessionInfo
import isel.pdm.pokerdice.domain.model.user.UserStats
import isel.pdm.pokerdice.domain.model.user.toUserStats
import isel.pdm.pokerdice.domain.services.AuthService
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

    override suspend fun sessionCheck(token: String): SessionInfo {
        val request = Request
            .Builder()
            .url("${USER_BASE_URL}/me")
            .header("Authorization", "Bearer $token")
            .build()
        val response = client.newCall(request).await()
        if (!response.isSuccessful) {
            throw Exception("Failed to Session check: ${response.code}")
        }
        val responseBody = response.body?.string() ?: throw Exception("Empty response")
        return gson.fromJson(responseBody, SessionInfo::class.java)
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

    override suspend fun getUserStats(token: String): UserStats {
        val request = Request.Builder()
            .url("${USER_BASE_URL}/me/stats")
            .header("Authorization", "Bearer $token")
            .get()
            .build()

        return client.newCall(request).await().use { response ->
            if (!response.isSuccessful) {
                throw Exception("Failed to fetch stats: ${response.code}")
            }

            val responseBody = response.body?.string() ?: throw Exception("Empty stats response")

            val mapType = object : TypeToken<Map<String, String>>() {}.type
            val statsMap: Map<String, String> = gson.fromJson(responseBody, mapType)

            statsMap.toUserStats()
        }
    }

    data class LoginDto(val username: String, val password: String)
    data class TokenDto(val token: String)
}


