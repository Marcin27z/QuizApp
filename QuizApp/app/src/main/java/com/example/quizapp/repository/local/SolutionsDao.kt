package com.example.quizapp.repository.local

import androidx.room.*
import com.example.quizapp.models.QuizInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface SolutionsDao {

    @Transaction
    suspend fun updateData(users: List<QuizInfo>) {
        deleteAll()
        insertAll(users)
    }

    @Insert
    suspend fun insertAll(users: List<QuizInfo>)

    @Query("DELETE FROM solutions")
    suspend fun deleteAll()

    @Query("SELECT * FROM solutions")
    fun getAllFlow(): Flow<List<QuizInfo>>
}