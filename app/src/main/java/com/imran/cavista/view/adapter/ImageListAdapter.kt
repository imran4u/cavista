package com.imran.cavista.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.imran.cavista.R
import com.imran.cavista.databinding.ImageListItemBinding
import com.imran.cavista.model.ImageWrapper

/**
 * Created by imran on 2020-09-25.
 */
class ImageListAdapter(
    private val imageList: MutableList<ImageWrapper>,
    private val clickListener: ItemClickListener
) : RecyclerView.Adapter<ImageListAdapter.AdapterHolder>() {
    private var mBinding: ImageListItemBinding? = null

    interface ItemClickListener {
        fun itemClick(imageWrapper: ImageWrapper)
    }

    class AdapterHolder(private var binding: ImageListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(imageWrapper: ImageWrapper) = binding.apply {
            mData = imageWrapper
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterHolder {
        mBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.image_list_item,
            parent,
            false
        )
        return AdapterHolder(mBinding!!)
    }

    override fun getItemCount() = imageList.size

    override fun onBindViewHolder(holder: AdapterHolder, position: Int) {
        val imageWrapper = imageList[position]
        holder.bindData(imageWrapper)
        holder.itemView.setOnClickListener {
            clickListener.itemClick(imageWrapper)
        }
    }

    fun addAllData(data: List<ImageWrapper>) {
        imageList.addAll(data)
        notifyDataSetChanged()
    }


}
