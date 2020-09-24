package com.imran.cavista.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.imran.cavista.R
import com.imran.cavista.factory.ImageListViewModelFactory
import com.imran.cavista.viewmodel.ImageListViewModel
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ImageListActivity : AppCompatActivity(), KodeinAware {

    private lateinit var mViewModel: ImageListViewModel
    private val factory: ImageListViewModelFactory by instance()
    override val kodein by kodein()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initalise()
    }

    private fun initalise() {
        mViewModel = ViewModelProvider(this, factory).get(ImageListViewModel::class.java)
        mViewModel.imageListLiveData.observe(this, Observer { imageWrapperList ->
            //TODO: work here
            imageWrapperList.forEach {
                Log.d("imran", "${it.title}, ${it.images[0].link}")
            }
        })

        mViewModel.errorLiveDat.observe(this, Observer {
            //TODO: Work here
        })

    }
}
