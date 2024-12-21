package com.deepak.backgroundoperations.networkServices

import com.deepak.backgroundoperations.model.PhoneTypesResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface PhoneTypesAPI {

    @GET("objects")
    suspend fun getPhoneTypes(): Response<List<PhoneTypesResponse>>

    companion object {
        operator fun invoke(): PhoneTypesAPI {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.restful-api.dev")
                .build()
                .create(PhoneTypesAPI::class.java)
        }
    }
}