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
import com.lalitj.mvvmpractice.models.RaisedCommunicationResponseDTO
import com.lalitj.mvvmpractice.network.Result
import com.lalitj.mvvmpractice.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var communicationListAdapter: CommunicationListAdapter
    var communicationList: MutableList<RaisedCommunicationResponseDTO> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpCommunicationAdapter()
        MainViewModel().getCommunicationList().observe(this, Observer {
            when (it) {
                is Result.Success<*> -> {
                    communicationList.clear()
                    communicationList.addAll(it.data as MutableList<RaisedCommunicationResponseDTO>)
                    communicationListAdapter.notifyDataSetChanged()
                    pb_loader.visibility = View.GONE
                }
                is Result.SessionExpired -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    pb_loader.visibility = View.GONE
                }
                is Result.ActivityResult -> TODO()
                is Result.Empty -> TODO()
                is Result.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    pb_loader.visibility = View.GONE
                }
                is Result.Loading -> {
                    if(it.isLoading)
                        pb_loader.visibility = View.VISIBLE
                    else
                        pb_loader.visibility = View.GONE
                }
                is Result.Retry -> TODO()
            }
        })
    }

    private fun setUpCommunicationAdapter() {
        val layoutManager
                : RecyclerView.LayoutManager =
            LinearLayoutManager(this)
        rv_request_list.layoutManager = layoutManager
        rv_request_list.itemAnimator = DefaultItemAnimator()
        communicationListAdapter = CommunicationListAdapter(this, communicationList)
        rv_request_list.adapter = communicationListAdapter
    }
}