package com.guilhermesan.peixeurbanooffers.ui.screens.splash

import android.animation.Animator
import android.animation.ValueAnimator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.guilhermesan.peixeurbanooffers.R
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val anim = ValueAnimator.ofFloat(0f,1f)
        val metrics = resources.displayMetrics
        val initialXPosition = metrics.widthPixels + ivLogo.layoutParams.width
        val finalPosition = ivLogo.layoutParams.width * -1
        ivLogo.x = initialXPosition.toFloat()
        anim.duration = 4500
        anim.addUpdateListener{
            val trasnslationY =  Math.cos((it.animatedValue as Float * -10f).toDouble()).toFloat()
            ivLogo.translationY = trasnslationY * 20 * metrics.density
            Log.i("position", trasnslationY.toString())
            ivLogo.rotation = trasnslationY * (-25)
            ivLogo.x = initialXPosition - ((initialXPosition - finalPosition) * it.animatedValue as Float)
        }
        anim.addListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(p0: Animator?) {

            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationStart(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                alphaAnimation()
            }

        })
        anim.start()

    }

    fun alphaAnimation(){
        ivLogoFixed
                .animate()
                .setDuration(500)
                .translationYBy(resources.displayMetrics.density * -15)
                .alphaBy(1f)
                .setListener(object :Animator.AnimatorListener{
                    override fun onAnimationRepeat(p0: Animator?) {

                    }

                    override fun onAnimationEnd(p0: Animator?) {

                    }

                    override fun onAnimationCancel(p0: Animator?) {

                    }

                    override fun onAnimationStart(p0: Animator?) {

                    }

                })
                .start()
    }
}
