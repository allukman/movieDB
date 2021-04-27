package id.smartech.moviedatabase.util.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import id.smartech.moviedatabase.R
import id.smartech.moviedatabase.databinding.ItemGenresBinding
import id.smartech.moviedatabase.databinding.ItemMoviesBinding
import id.smartech.moviedatabase.model.GenresModel
import id.smartech.moviedatabase.model.MovieModel

class GenresAdapter(private val items: ArrayList<GenresModel>): RecyclerView.Adapter<GenresAdapter.GenresHolder>() {

    fun addList(list: List<GenresModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class GenresHolder(val binding: ItemGenresBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresHolder {
        return GenresHolder(
                DataBindingUtil.inflate(
                        (LayoutInflater.from(parent.context)),
                        R.layout.item_genres,
                        parent, false
                )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: GenresHolder, position: Int) {
        val item = items[position]
        val genreName = item.name

        holder.binding.tvGenre.text = "$genreName"
    }

}