package id.smartech.moviedatabase.util.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.smartech.moviedatabase.R
import id.smartech.moviedatabase.databinding.ItemCastBinding
import id.smartech.moviedatabase.model.CastModel
import id.smartech.moviedatabase.remote.ApiClient

class MovieCasterAdapter(private val items: ArrayList<CastModel>): RecyclerView.Adapter<MovieCasterAdapter.MovieCasterHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun addList(list: List<CastModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class MovieCasterHolder(val binding: ItemCastBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCasterHolder {
        return MovieCasterHolder(
                DataBindingUtil.inflate(
                        (LayoutInflater.from(parent.context)),
                        R.layout.item_cast,
                        parent, false
                )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MovieCasterHolder, position: Int) {
        val item = items[position]
        val poster = ApiClient.BASE_URL_IMAGE + item.profilePath

        holder.binding.tvName.text = item.name
        holder.binding.tvCharacter.text = item.character

        Glide.with(holder.itemView)
                .load(poster)
                .placeholder(R.drawable.white)
                .error(R.drawable.white)
                .into(holder.binding.ivProfileImage)

        holder.itemView.setOnClickListener {
            onItemClickCallback.onClickItem(item)
        }
    }

    interface OnItemClickCallback {
        fun onClickItem(data: CastModel)
    }

}