package com.meetSuccess.FoodResturant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.meetSuccess.FoodResturant.Adapter.PostAdapter
import com.meetSuccess.FoodResturant.Util.ApiState
import com.meetSuccess.FoodResturant.ViewModel.MainViewModel
import com.meetSuccess.FoodResturant.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var postAdapter: PostAdapter
    private val mainViewModel:MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerview()

        mainViewModel.getPost()

        lifecycleScope.launchWhenStarted {
            mainViewModel._postStateFlow.collect {it->
                when(it){
                    is ApiState.Loading->{
                        binding.recyclerview.isVisible=false
                        binding.progressBar.isVisible=true
                    }
                    is ApiState.Failure -> {
                        binding.recyclerview.isVisible = false
                        binding.progressBar.isVisible = false
                        Log.d("main", "onCreate: ${it.msg}")
                    }
                    is ApiState.Success->{
                        binding.recyclerview.isVisible = true
                        binding.progressBar.isVisible = false
                        postAdapter.setData(it.data)
                        postAdapter.notifyDataSetChanged()
                    }
                    is ApiState.Empty->{

                    }
                }
            }
        }

    }

    private fun initRecyclerview() {
        postAdapter= PostAdapter(ArrayList())
        binding.recyclerview.apply {
            setHasFixedSize(true)
            layoutManager=LinearLayoutManager(this@MainActivity)
            adapter=postAdapter
        }
    }
}