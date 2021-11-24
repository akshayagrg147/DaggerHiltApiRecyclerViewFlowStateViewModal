package com.meetSuccess.FoodResturant.Network

import com.meetSuccess.FoodResturant.Model.Post
import retrofit2.http.GET

interface ApiService {

    @GET("posts")
   suspend fun getPost():List<Post>
}