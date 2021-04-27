package id.smartech.moviedatabase.util.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.smartech.moviedatabase.R
import id.smartech.moviedatabase.databinding.ItemMoviesBinding
import id.smartech.moviedatabase.model.TvMovieModel
import id.smartech.moviedatabase.remote.ApiClient.Companion.BASE_URL_IMAGE

class TvMovieAdapter(private val items: ArrayList<TvMovieModel>): RecyclerView.Adapter<TvMovieAdapter.TrendingHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun addList(list: List<TvMovieModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class TrendingHolder(val binding: ItemMoviesBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingHolder {
        return TrendingHolder(
                DataBindingUtil.inflate(
                        (LayoutInflater.from(parent.context)),
                        R.layout.item_movies,
                        parent, false
                )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TrendingHolder, position: Int) {
        val item = items[position]
        val poster = BASE_URL_IMAGE + item.posterPath
        var title: String? = null
        var date: String? = null

        title = if (item.title.isNullOrEmpty()) {
            item.name
        } else {
            item.title
        }

        date = if (item.releaseDate.isNullOrEmpty()) {
            item.firstAirDate
        } else {
            item.releaseDate
        }

        holder.binding.tvTitle.text = title
        holder.binding.tvReleaseDate.text = date
        holder.binding.tvVoteAverage.text = item.voteAverage.toString()

        Glide.with(holder.itemView)
                .load(poster)
                .placeholder(R.drawable.white)
                .error(R.drawable.white)
                .into(holder.binding.ivPoster)

        holder.itemView.setOnClickListener {
            onItemClickCallback.onClickItem(item)
        }

    }

    interface OnItemClickCallback {
        fun onClickItem(data: TvMovieModel)
    }
}