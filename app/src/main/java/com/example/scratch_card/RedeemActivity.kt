package com.example.scratch_card

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.scratch_card.databinding.ActivityRedeemBinding
import android.view.View


class RedeemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_redeem)

        // get reference to ImageView
        val img = findViewById(R.id.img_star) as ImageView
        // set on-click listener
        img.setOnClickListener {
            // your code to perform when the user clicks on the ImageView
            Toast.makeText(this@RedeemActivity, "You clicked on ImageView.", Toast.LENGTH_SHORT).show()
        }
    }
//        binding.imgStar.setOnClickListener(
//            Toast.makeText(this,"you clicked on image", Toast.LENGTH_SHORT).show()
//        )


}






