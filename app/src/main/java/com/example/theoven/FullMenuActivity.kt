package com.example.theoven

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.theoven.databinding.ActivityFullMenuBinding

class FullMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFullMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { finish() }

        val flyers = listOf(
            R.drawable.menu_flyer_1,
            R.drawable.menu_flyer_2,
            R.drawable.menu_flyer_3,
            R.drawable.menu_flyer_4
        )
        binding.vpFlyers.adapter = FlyerAdapter(flyers)
        updateIndicator(0, flyers.size)
        binding.vpFlyers.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateIndicator(position, flyers.size)
            }
        })
    }

    private fun updateIndicator(position: Int, total: Int) {
        binding.tvPageIndicator.text = "${position + 1} / $total - Pinch to zoom"
    }

    private class FlyerAdapter(private val flyers: List<Int>) :
        RecyclerView.Adapter<FlyerAdapter.ViewHolder>() {

        class ViewHolder(val view: ZoomableImageView) : RecyclerView.ViewHolder(view)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = ZoomableImageView(parent.context)
            view.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.view.setImageResource(flyers[position])
        }

        override fun getItemCount() = flyers.size
    }
}
