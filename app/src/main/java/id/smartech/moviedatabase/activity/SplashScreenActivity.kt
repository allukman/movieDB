package id.smartech.moviedatabase.activity

import android.os.Bundle
import android.os.Handler
import id.smartech.moviedatabase.R
import id.smartech.moviedatabase.base.BaseActivity
import id.smartech.moviedatabase.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_splash_screen
        super.onCreate(savedInstanceState)

        Handler().postDelayed({
            intents<OnBoardActivity>(this)
            this.finish()
        }, 3000)
    }
}