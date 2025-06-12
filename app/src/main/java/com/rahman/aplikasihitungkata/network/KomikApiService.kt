package com.rahman.aplikasihitungkata.network

import com.rahman.aplikasihitungkata.model.ImageListApiResponse
import com.rahman.aplikasihitungkata.model.ApiStatus
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import kotlin.getValue
import kotlin.jvm.java


private const val BASE_URL = "https://mamanapi.my.id/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface KomikApiService {
    @GET("api.php")
    suspend fun getAllImages(
        @Header("Authorization") userEmail: String
    ): ImageListApiResponse

    @Multipart
    @POST("api.php")
    suspend fun uploadImage(
        @Header("Authorization") userEmail: String,
        @Part("nama") nama: RequestBody?,
        @Part("genre") genre: RequestBody?,
        @Part("rilis") rilis: RequestBody?,
        @Part gambar: MultipartBody.Part
    ): ApiStatus

    @Multipart
    @POST("api.php")
    suspend fun updateImage(
        @Header("Authorization") userEmail: String,
        @Query("id") imageId: Int,
        @Part("nama") nama: RequestBody?,
        @Part("genre") genre: RequestBody?,
        @Part("rilis") rilis: RequestBody?,
        @Part gambar: MultipartBody.Part?
    ): ApiStatus

    @DELETE("api.php")
    suspend fun deleteImage(
        @Header("Authorization") userEmail: String,
        @Query("id") imageId: Int
    ): ApiStatus
}

object ImageApi {
    val service: KomikApiService by lazy {
        retrofit.create(KomikApiService::class.java)
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }
