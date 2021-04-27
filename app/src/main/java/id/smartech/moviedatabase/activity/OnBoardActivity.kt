package id.smartech.moviedatabase.activity

import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import id.smartech.moviedatabase.R
import id.smartech.moviedatabase.base.BaseActivity
import id.smartech.moviedatabase.databinding.ActivityOnBoardBinding
import id.smartech.moviedatabase.model.OnBoardModel
import id.smartech.moviedatabase.util.adapter.OnBoardAdapter

class OnBoardActivity : BaseActivity<ActivityOnBoardBinding>() {

    private val adapter = OnBoardAdapter(
        listOf(
            OnBoardModel("Godzilla vs Kong", R.drawable.poster),
            OnBoardModel("Nobody", R.drawable.poster2),
            OnBoardModel("Justice League", R.drawable.poster3),
            OnBoardModel("The Falcon and Winter Soldier", R.drawable.poster4),
            OnBoardModel("The Flash", R.drawable.poster5)
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_on_board
        super.onCreate(savedInstanceState)

        bind.onboardViewpager.adapter = adapter
        setupIndicators()
        setCurrentIndicator(0)
        bind.onboardViewpager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })

        bind.btnNext.setOnClickListener {
            intents<MainActivity>(this)
        }
    }

    private fun setupIndicators(){
        val indicators = arrayOfNulls<ImageView>(adapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(8,0,8,0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
                this?.layoutParams = layoutParams
            }
            bind.container.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(index: Int){
        val childCount = bind.container.childCount
        for (i in 0 until childCount) {
            val imageView = bind.container[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
            }
        }
    }
}