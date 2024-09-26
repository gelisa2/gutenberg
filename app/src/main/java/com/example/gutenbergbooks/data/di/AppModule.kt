package com.example.gutenbergbooks.data.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.room.Room
import com.example.gutenbergbooks.data.local.dao.BooksDao
import com.example.gutenbergbooks.data.local.db.BookDatabase
import com.example.gutenbergbooks.data.remote.BookApi
import com.example.gutenbergbooks.data.repository.BookRepositoryImpl
import com.example.gutenbergbooks.domain.repository.BookRepository
import com.example.utils.NetworkUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofitApi(okHttpClient: OkHttpClient): BookApi {
        return Retrofit.Builder()
            .baseUrl("https://gutendex.com")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(BookApi::class.java)
    }

    @Provides
    @Singleton
    fun provideClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)

        httpClient.addInterceptor(logging)
        return httpClient.build()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): BookDatabase {
        return Room.databaseBuilder(
            appContext,
            BookDatabase::class.java,
            "book_database"
        ).build()
    }

    @Provides
    fun provideBookDao(database: BookDatabase): BooksDao {
        return database.bookDao()
    }

    @Provides
    @Singleton
    fun provideRepository(bookApi: BookApi, booksDao: BooksDao, networkUtils: NetworkUtils): BookRepository {
        return BookRepositoryImpl(bookApi, booksDao, networkUtils)
    }


    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideNetworkHelper(context: Context): NetworkUtils {
        return NetworkUtils(context)
    }

}

