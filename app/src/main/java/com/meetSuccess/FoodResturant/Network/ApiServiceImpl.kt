package com.meetSuccess.FoodResturant.Network

import com.meetSuccess.FoodResturant.Model.Post
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val apiService: ApiService) {

    suspend fun getPost():List<Post> = apiService.getPost()
}