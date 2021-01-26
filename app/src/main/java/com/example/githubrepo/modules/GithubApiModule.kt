package com.example.githubrepo.modules

import android.util.Log
import com.example.githubrepo.data.GithubApi
import com.example.githubrepo.data.api.GithubApiClient
import com.example.githubrepo.data.api.GithubApiClientImpl
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val TIME_OUT: Long = 30
const val BASE_URL = "https://api.github.com/"

val githubApiModule = module {
    factory { provideHttpLoggingInterceptor() }
    factory { provideGson() }
    factory { provideOkHttpClient(get()) }
    factory { provideGithubApi(get()) }
    single { provideRetrofit(get(), get()) }
}

val githubApiClientModule = module {
    single<GithubApiClient> { GithubApiClientImpl(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}

fun provideGithubApi(retrofit: Retrofit): GithubApi = retrofit.create(GithubApi::class.java)

fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            Log.d("GithubAPI", message)
        }
    })
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    return interceptor
}

fun provideGson(): Gson {
    return GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
        .setLenient()
        .create()
}

fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    val builder = OkHttpClient.Builder()
    builder
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
        .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            requestBuilder.addHeader(
                "Authorization",
                Credentials.basic("santugowda", "a38cbbf65cc7607097d61e652a32b03ad0f947c0")
            )
            chain.proceed(requestBuilder.build())
        }
    builder.addInterceptor(httpLoggingInterceptor)
    return builder.build()
}
