package com.imran.cavista.view

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.imran.cavista.R
import com.imran.cavista.databinding.ActivityListImageBinding
import com.imran.cavista.factory.ImageListViewModelFactory
import com.imran.cavista.util.snackbar
import com.imran.cavista.viewmodel.ImageListViewModel
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ImageListActivity : AppCompatActivity(), KodeinAware {

    private lateinit var mViewModel: ImageListViewModel
    private val factory: ImageListViewModelFactory by instance()
    override val kodein by kodein()
    private lateinit var binding: ActivityListImageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.image_list)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_image)
        initialise()
        lifecycleScope.launch {
            mViewModel.getImages(1, "vanilla")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initialise() {
        mViewModel = ViewModelProvider(this, factory).get(ImageListViewModel::class.java)
        mViewModel.imageListLiveData.observe(this, Observer { imageWrapperList ->
            //TODO: work here
            imageWrapperList.forEach {
                Log.d("imran", "${it.title}, ${it.images?.let { images -> images[0].link }}")
            }
        })

        mViewModel.errorLiveDat.observe(this, {
            binding.root.snackbar(it)
        })

    }


}
