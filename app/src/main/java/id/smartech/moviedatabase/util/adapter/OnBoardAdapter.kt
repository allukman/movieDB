package id.smartech.moviedatabase.util.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.smartech.moviedatabase.R
import id.smartech.moviedatabase.model.OnBoardModel

class OnBoardAdapter(private val items: List<OnBoardModel>): RecyclerView.Adapter<OnBoardAdapter.IntroSlideViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroSlideViewHolder {
        return IntroSlideViewHolder(
            LayoutInflater.from(parent.context).inflate(
            R.layout.item_onboard,
            parent, false
        ))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: IntroSlideViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class IntroSlideViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textTitle = view.findViewById<TextView>(R.id.tv_title)
        private val imageIcon = view.findViewById<ImageView>(R.id.iv_image)

        fun bind(data: OnBoardModel) {
            textTitle.text = data.title
            imageIcon.setImageResource(data.image)
        }
    }
}