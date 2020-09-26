package com.imran.cavista.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.imran.cavista.R
import com.imran.cavista.databinding.ActivityListImageBinding
import com.imran.cavista.factory.ImageListViewModelFactory
import com.imran.cavista.model.ImageWrapper
import com.imran.cavista.util.ConstantUtil.KEY_IMAGE_LINK
import com.imran.cavista.util.ConstantUtil.KEY_IMAGE_TITLE
import com.imran.cavista.util.ConstantUtil.KEY_IMAGE_WRAPPER_ID
import com.imran.cavista.util.GridSpacingDecoration
import com.imran.cavista.util.snackbar
import com.imran.cavista.view.adapter.ImageListAdapter
import com.imran.cavista.view.adapter.ImageListAdapter.ItemClickListener
import com.imran.cavista.viewmodel.ImageListViewModel
import kotlinx.android.synthetic.main.activity_list_image.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ImageListActivity : AppCompatActivity(), KodeinAware {

    private lateinit var mViewModel: ImageListViewModel
    private val mFactory: ImageListViewModelFactory by instance()
    override val kodein by kodein()
    private lateinit var mBinding: ActivityListImageBinding
    private lateinit var mImageListAdapter: ImageListAdapter
    private var queryJob: Job? = null
    private val DEBOUNCE: Long = 250


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.image_list)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_list_image)
        initialise()
        lifecycleScope.launch {
            progressView.visibility = View.VISIBLE
            mViewModel.getImages(1)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initialise() {
        mViewModel = ViewModelProvider(this, mFactory).get(ImageListViewModel::class.java)
        mViewModel.imageListLiveData.observe(this, Observer { imageWrapperList ->
            mImageListAdapter.addAllData(imageWrapperList)
            progressView.visibility = View.GONE
            if (imageWrapperList.isEmpty()) {
                noResultView.visibility = View.VISIBLE
            } else {
                noResultView.visibility = View.GONE
            }
        })

        mViewModel.errorLiveDat.observe(this, {
            mBinding.root.snackbar(it)
        })

        //searchView
        searchView.setOnQueryTextListener(queryTextListener)
        searchView.onActionViewExpanded()
        //recyclerView
        val spanCount = 3
        recyclerView.layoutManager = GridLayoutManager(this, spanCount)
        mImageListAdapter = ImageListAdapter(mViewModel.imageList, itemClickListener)
        recyclerView.adapter = mImageListAdapter
        recyclerView.addItemDecoration(GridSpacingDecoration(spanCount, 16, false, 0))


    }

    private val queryTextListener = object : SearchView.OnQueryTextListener,
        androidx.appcompat.widget.SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(p0: String?): Boolean {
            // do nothing
            return false
        }

        override fun onQueryTextChange(query: String?): Boolean {
            queryJob?.cancel()
            queryJob = lifecycleScope.launch(Dispatchers.Main) {
                delay(DEBOUNCE)
                mViewModel.getImages(1, query)
            }

            return true
        }

    }

    private val itemClickListener = object : ItemClickListener {
        override fun itemClick(imageWrapper: ImageWrapper) {
            val intent = Intent(this@ImageListActivity, ImageDetailActivity::class.java)
            var imageLink: String? = null
            if (imageWrapper.images != null && imageWrapper.images.isNotEmpty()) {
                imageLink = imageWrapper.images[0].link
            }
            intent.putExtra(KEY_IMAGE_TITLE, imageWrapper.title)
            intent.putExtra(KEY_IMAGE_LINK, imageLink)
            intent.putExtra(KEY_IMAGE_WRAPPER_ID, imageWrapper.id)
            startActivity(intent)
        }
    }


}
