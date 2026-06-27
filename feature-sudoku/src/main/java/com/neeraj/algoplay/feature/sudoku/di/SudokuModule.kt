package com.neeraj.algoplay.feature.sudoku.di

import com.neeraj.algoplay.feature.sudoku.algorithm.SudokuGenerator
import com.neeraj.algoplay.feature.sudoku.data.repository.SudokuRepositoryImpl
import com.neeraj.algoplay.feature.sudoku.domain.repository.SudokuRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.random.Random

@Module
@InstallIn(SingletonComponent::class)
object SudokuModule {

    @Provides
    @Singleton
    fun provideRandom(): Random = Random.Default

    @Provides
    @Singleton
    fun provideSudokuRepository(generator: SudokuGenerator): SudokuRepository {
        return SudokuRepositoryImpl(generator)
    }
}
