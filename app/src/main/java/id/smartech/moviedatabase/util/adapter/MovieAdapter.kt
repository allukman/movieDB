package id.smartech.moviedatabase.util.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.smartech.moviedatabase.R
import id.smartech.moviedatabase.databinding.ItemMoviesBinding
import id.smartech.moviedatabase.model.MovieModel
import id.smartech.moviedatabase.remote.ApiClient

class MovieAdapter(private val items: ArrayList<MovieModel>): RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun addList(list: List<MovieModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }


    class MovieHolder(val binding: ItemMoviesBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(
            DataBindingUtil.inflate(
                (LayoutInflater.from(parent.context)),
                R.layout.item_movies,
                parent, false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val item = items[position]
        val poster = ApiClient.BASE_URL_IMAGE + item.posterPath

        holder.binding.tvTitle.text = item.title
        holder.binding.tvReleaseDate.text = item.releaseDate
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
        fun onClickItem(data: MovieModel)
    }
}