package com.lalitj.mvvmpractice

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lalitj.mvvmpractice.adapters.CommunicationListAdapter
import com.lalitj.mvvmpractice.databinding.ActivityMainBinding
import com.lalitj.mvvmpractice.models.RaisedCommunicationResponseDTO
import com.lalitj.mvvmpractice.network.Result
import com.lalitj.mvvmpractice.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.altruist.BajajExperia.Models.SMSCommunicationDTO
import org.json.JSONTokener
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var communicationListAdapter: CommunicationListAdapter
    var communicationList: MutableList<RaisedCommunicationResponseDTO> = mutableListOf()
    @Inject lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpCommunicationAdapter()
//        val viewModel = MainViewModel()
        binding.lifecycleOwner = this
        viewModel.fetchList()
        viewModel.communicationData.observe(this, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    if (result.isLoading)
                        binding.pbLoader.visibility = View.VISIBLE
                    else
                        binding.pbLoader.visibility = View.GONE
                }
                is Result.Success -> {
                    val smsCommunicationDTO = result.data as SMSCommunicationDTO
                    communicationList.clear()
                    smsCommunicationDTO.finalList?.let { it1 -> communicationList.addAll(it1) }
                    communicationListAdapter.notifyDataSetChanged()
                    binding.pbLoader.visibility = View.GONE
                }
                is Result.SessionExpired -> {
                    Toast.makeText(this, result.message, Toast.LENGTH_LONG).show()
                    binding.pbLoader.visibility = View.GONE
                }
                is Result.ActivityResult -> TODO()
                is Result.Empty -> TODO()
                is Result.Error -> {
                    Toast.makeText(this, result.message, Toast.LENGTH_LONG).show()
                    binding.pbLoader.visibility = View.GONE
                }
                is Result.Retry -> TODO()
            }
        })
    }

    private fun setUpCommunicationAdapter() {
        val layoutManager
                : RecyclerView.LayoutManager =
            LinearLayoutManager(this)
        binding.rvRequestList.layoutManager = layoutManager
        binding.rvRequestList.itemAnimator = DefaultItemAnimator()
        communicationListAdapter = CommunicationListAdapter(this, communicationList)
        binding.rvRequestList.adapter = communicationListAdapter
    }
}