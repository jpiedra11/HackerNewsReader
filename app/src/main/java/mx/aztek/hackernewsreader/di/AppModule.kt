package mx.aztek.hackernewsreader.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mx.aztek.hackernewsreader.data.HackerNewsApi
import mx.aztek.hackernewsreader.data.SharedPreferences
import mx.aztek.hackernewsreader.utils.Constants.BASE_URL
import mx.aztek.hackernewsreader.utils.Network.hasNetwork
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApi(context: Application): HackerNewsApi {
        val cacheSize = (5 * 1024 * 1024).toLong()
        val myCache = Cache(context.cacheDir, cacheSize)

        val okHttpClient =
            OkHttpClient
                .Builder()
                .cache(myCache)
                .addInterceptor { chain ->
                    var request = chain.request()
                    request =
                        if (hasNetwork(context)!!) {
                            request
                                .newBuilder()
                                .header("Cache-Control", "public, max-age=" + 5)
                                .build()
                        } else {
                            request
                                .newBuilder()
                                .header(
                                    "Cache-Control",
                                    "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7,
                                ).build()
                        }
                    chain.proceed(request)
                }.build()

        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(HackerNewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Application): SharedPreferences = SharedPreferences(context)
}
