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
        val img1 = findViewById(R.id.img_star) as ImageView
        val img2 = findViewById(R.id.img_pubg) as ImageView
        // set on-click listener
        img1.setOnClickListener {
            // your code to perform when the user clicks on the ImageView
            Toast.makeText(this@RedeemActivity, "पहली बार आपका बॅलेन्स 1000 Diamonds होने के बाद आप अपने बॅलेन्स को ट्रान्स्फर करवा सकते है ! और उसके बाद रोजाना बॅलेन्स ट्रान्स्फर करवा सकते है.", Toast.LENGTH_SHORT).show()
        }

        img2.setOnClickListener {
            // your code to perform when the user clicks on the ImageView
            Toast.makeText(this@RedeemActivity, "पहली बार आपका बॅलेन्स 1000 Diamonds होने के बाद आप अपने बॅलेन्स को ट्रान्स्फर करवा सकते है ! और उसके बाद रोजाना बॅलेन्स ट्रान्स्फर करवा सकते है.", Toast.LENGTH_SHORT).show()
        }

    }

}






