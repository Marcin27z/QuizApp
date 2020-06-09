package com.example.quizapp.repository

import com.example.quizapp.models.*
import com.example.quizapp.repository.local.SolutionsDao
import com.example.quizapp.retrofit.CommonService
import com.example.quizapp.retrofit.StudentService
import com.example.quizapp.retrofit.TutorService
import okhttp3.ResponseBody
import retrofit2.Call
import java.lang.Exception

class Repository(
    private val commonService: CommonService,
    private val studentService: StudentService,
    private val tutorService: TutorService,
    private val solutionsDao: SolutionsDao
) {

    fun getSubjects(): Call<List<SubjectInfo>?> = commonService.getSubjects()

    fun getQuiz(quizName: String): Call<Quiz?> =
        commonService.getQuiz(quizName)

    fun subscribeToSubject(subjectName: String): Call<Boolean> =
        studentService.subscribeToSubject(subjectName)

    fun submitSolution(solutionDto: SolutionDto): Call<ResponseBody> =
        studentService.submitSolution(solutionDto)

    fun getQuizzesInfo(): Call<List<QuizInfo>?> =
        studentService.getQuizzesInfo()

    suspend fun fetchSolvedQuizzesInfo() {
        try {
            solutionsDao.updateData(studentService.getSolvedQuizzesInfo())
        } catch (e: Exception) {

        }
    }

    fun getSolvedQuizzesInfoFlow() = solutionsDao.getAllFlow()

    fun getQuizzesInfoForSubject(subjectName: String): Call<List<QuizInfo>?> =
        studentService.getQuizzesInfoForSubject(subjectName)

    suspend fun unsubscribeFromSubject(subjectName: String): ResponseBody =
        studentService.unsubscribeFromSubject(subjectName)

    suspend fun createSubject(subjectName: String): Boolean =
        tutorService.createSubject(subjectName)

    suspend fun deleteSubject(subjectName: String): Call<ResponseBody> =
        tutorService.deleteSubject(subjectName)

    suspend fun addQuizToSubject(quiz: QuizDto, subjectName: String): Boolean =
        tutorService.addQuizToSubject(quiz, subjectName)

    fun getSolutionsForQuiz(quizName: String): Call<List<SolutionInfo>> =
        tutorService.getSolutionsForQuiz(quizName)

    suspend fun getQuizzesInfoTutor(): List<QuizInfoTutor> =
        tutorService.getQuizzesInfo()

    suspend fun getQuizzesInfoTutorForSubject(subjectName: String): List<QuizInfoTutor> =
        tutorService.getQuizzesInfoForSubject(subjectName)

    suspend fun clearDatabase() = solutionsDao.deleteAll()
}