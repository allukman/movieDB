package id.smartech.moviedatabase.util.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.smartech.moviedatabase.R
import id.smartech.moviedatabase.databinding.ItemPeopleBinding
import id.smartech.moviedatabase.model.PeopleModel
import id.smartech.moviedatabase.remote.ApiClient

class PeopleAdapter(private val items: ArrayList<PeopleModel>): RecyclerView.Adapter<PeopleAdapter.PeopleHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun addList(list: List<PeopleModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class PeopleHolder(val binding: ItemPeopleBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleHolder {
        return PeopleHolder(
                DataBindingUtil.inflate(
                        (LayoutInflater.from(parent.context)),
                        R.layout.item_people,
                        parent, false
                )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PeopleHolder, position: Int) {
        val item = items[position]
        val poster = ApiClient.BASE_URL_IMAGE + item.profilePath

        holder.binding.tvName.text = item.name

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
        fun onClickItem(data: PeopleModel)
    }
}