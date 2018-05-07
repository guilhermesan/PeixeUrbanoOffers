package com.guilhermesan.peixeurbanooffers.ui.screens.offers

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.guilhermesan.datacontracts.vos.Offer
import com.guilhermesan.peixeurbanooffers.R
import com.guilhermesan.peixeurbanooffers.extencions.formatCurrency
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_offer_view.view.*

class OfferAdapter  : PagedListAdapter<Offer ,OfferAdapter.OfferViewHolder>(OfferDiffCallback) {


    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        var item = getItem(position)
        if (item != null) holder.bind(item)
    }


    val offerSelectedLiveData = MutableLiveData<Offer>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_offer_view, parent, false)
        return OfferViewHolder(view)
    }



    inner class OfferViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        fun bind(offer: Offer) {
            with(view) {
                tvDescription.text = offer.description
                tvTitle.text = offer.title
                tvPrice.text = offer.price.formatCurrency()
                setOnClickListener { offerSelectedLiveData.value = offer }
                Picasso.get().load(offer.thumbnailUrl).into(ivThumbnail)
            }
        }

    }

    companion object {
        val OfferDiffCallback = object : DiffUtil.ItemCallback<Offer>() {
            override fun areItemsTheSame(oldItem: Offer, newItem: Offer): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Offer, newItem: Offer): Boolean {
                return oldItem == newItem
            }
        }
    }
}