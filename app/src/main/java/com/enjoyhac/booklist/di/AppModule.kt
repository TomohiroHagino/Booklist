package com.enjoyhac.booklist.di

import androidx.compose.ui.graphics.GraphicsLayerScope
import com.enjoyhac.booklist.network.BooksApi
import com.enjoyhac.booklist.repository.BookRepository
import com.enjoyhac.booklist.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideBookRepository(api: BooksApi) = BookRepository(api)

    @Singleton
    @Provides
    fun provideBookApi(): BooksApi {
        return Retrofit.Builder() // Retrofit.Builder を使って Retrofit のインスタンスを構築
            .baseUrl(Constants.BASE_URL) // ベース URL を設定。この URL はリクエスト時に共通部分として使われます。
            .addConverterFactory(GsonConverterFactory.create()) // Gson を使った JSON コンバータを Retrofit に追加 (レスポンスをオブジェクトに変換)
            .build() // 設定した内容を基に Retrofit インスタンスを作成
            .create(BooksApi::class.java) // Retrofit に定義した BooksApi インターフェースの実装を動的に生成
    }

}