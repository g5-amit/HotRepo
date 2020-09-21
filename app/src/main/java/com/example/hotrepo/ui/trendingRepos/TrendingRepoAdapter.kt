package com.example.hotrepo.ui.trendingRepos

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.DiffResult.NO_POSITION
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.hotrepo.R
import com.example.hotrepo.databinding.RepoItemLytBinding
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import java.util.*
import javax.inject.Inject

/**
* Adapter for binding Repo data items into ViewHolders
 * Images are bound using Glide
 * Support collapse and expanding of list item
* */
@ActivityScoped
class TrendingRepoAdapter @Inject constructor(
    @ActivityContext private val context: Context
) : RecyclerView.Adapter<TrendingRepoAdapter.RepoViewHolder>() {

    private var lastClickedIndex = -1
    private val items = ArrayList<RepoItemUIModel>()

    fun setItems(items: List<RepoItemUIModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val binding: RepoItemLytBinding = RepoItemLytBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    interface RepoItemListener {
        fun onClickedRepoItem(repoUrl: String)
    }

    inner class RepoViewHolder(private val itemBinding: RepoItemLytBinding) : RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        private lateinit var repoItem: RepoItemUIModel
        init {
            itemBinding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if(adapterPosition != NO_POSITION){

                // collapse last expanded item
                if(lastClickedIndex != -1 && adapterPosition != lastClickedIndex){
                    val lastItem = items[lastClickedIndex]
                    lastItem.isExpanded = false
                    notifyItemChanged(lastClickedIndex)
                    lastClickedIndex = -1
                }

                //collapse and expand current item
                val item =items[adapterPosition]
                item.isExpanded = !item.isExpanded
                if(item.isExpanded){
                    lastClickedIndex = adapterPosition
                }
                notifyItemChanged(adapterPosition)
            }
        }

        fun bind(item: RepoItemUIModel, position: Int) {
            Log.d("TrendingRepoAdapter", "Bind Data for position = $position")
            this.repoItem = item
            item.avatar?.let {
                Glide.with(itemBinding.root)
                    .load(item.avatar)
                    .apply(
                        RequestOptions.circleCropTransform()
                            .placeholder(R.drawable.avtar_placeolder)
                            .error(R.drawable.avtar_placeolder)
                    )
                    .into(itemBinding.avtar)
            }
            item.languageColor?.let { itemBinding.langColor.setBackgroundColor(Color.parseColor(it)) }

            itemBinding.author.text = item.author ?: ""
            itemBinding.name.text = item.name ?: ""
            itemBinding.descWithUrl.text =
                String.format("%s%s", item.description ?: "", item.url)
            itemBinding.lang.text = item.language ?: ""
            itemBinding.starValue.text = String.format("%s", item.stars)
            itemBinding.forkValue.text = String.format("%s", item.forks)
            if(item.isExpanded)
                itemBinding.repogroupDetails.visibility = View.VISIBLE
            else
                itemBinding.repogroupDetails.visibility = View.GONE
        }
    }
}
