package com.company.dawen.di

import android.app.Application
import androidx.room.Room
import com.company.dawen.model.repositories.NoteRepository
import com.company.dawen.model.repositories.NoteRepositoryImpl
import com.company.dawen.model.data.roomDB.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNotesDatabase(app: Application): NoteDatabase {
        return Room
            .databaseBuilder(app, NoteDatabase::class.java, "notes_db")
            .build()
    }

    @Provides
    @Singleton
    fun providesNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.dao)
    }


}