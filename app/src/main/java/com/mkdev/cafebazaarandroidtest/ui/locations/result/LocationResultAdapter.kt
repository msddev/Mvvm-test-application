package com.mkdev.cafebazaarandroidtest.ui.locations.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.mkdev.cafebazaarandroidtest.core.BaseAdapter
import com.mkdev.cafebazaarandroidtest.databinding.ItemVenueBinding
import com.mkdev.cafebazaarandroidtest.domain.model.explore.Venue

class LocationResultAdapter(
    private val callBack: (Venue) -> Unit
) : BaseAdapter<Venue>(diffCallback) {

    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        val mBinding = ItemVenueBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        val viewModel = LocationResultViewModel()
        mBinding.viewModel = viewModel

        mBinding.rootItemView.setOnClickListener {
            mBinding.viewModel?.item?.get()?.let {
                callBack.invoke(it)
            }
        }
        return mBinding
    }

    override fun bind(binding: ViewDataBinding, position: Int) {
        (binding as ItemVenueBinding).viewModel?.item?.set(getItem(position))
        binding.executePendingBindings()
    }
}

val diffCallback = object : DiffUtil.ItemCallback<Venue>() {
    override fun areContentsTheSame(oldItem: Venue, newItem: Venue): Boolean =
        oldItem == newItem

    override fun areItemsTheSame(oldItem: Venue, newItem: Venue): Boolean =
        oldItem.id == newItem.id
}
