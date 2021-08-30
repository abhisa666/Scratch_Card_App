package com.example.scratch_card

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.anupkumarpanwar.scratchview.ScratchView
import com.example.scratch_card.databinding.ActivityMainBinding
import com.facebook.ads.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.KITKAT)

    private lateinit var adView: AdView
    private lateinit var interstitialAd:InterstitialAd

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)

        // Initialize FB Ads
        AudienceNetworkAds.initialize(this)

//        AdSettings.addTestDevice("15159d6e-79e4-43ad-a705-be455442ee63")
        AdSettings.setTestMode(true)

        adView = AdView(
            this@MainActivity,
            "267438701874261_267441251874006",
            AdSize.BANNER_HEIGHT_50
        )


        binding.bannerContainer?.addView(adView)
        val adListener: AdListener = object : AdListener {
            override fun onError(ad: Ad, adError: AdError) {
// Ad error callback
                Toast.makeText(
                    this@MainActivity,
                    "Error: " + adError.errorMessage,
                    Toast.LENGTH_LONG
                )
                    .show()

                Log.e("FBAds Error", adError.errorMessage)
            }

            override fun onAdLoaded(ad: Ad) {
// Ad loaded callback
            }

            override fun onAdClicked(ad: Ad) {
// Ad clicked callback
                Log.e("FBAds", "Clicked on the Ad")
            }

            override fun onLoggingImpression(ad: Ad) {
// Ad impression logged callback
            }
        }

// Request an ad
        adView.loadAd(adView.buildLoadAdConfig().withAdListener(adListener).build())

        //////////

        interstitialAd = InterstitialAd(this, "267438701874261_267440925207372")

        // Create listeners for the Interstitial Ad
        // Create listeners for the Interstitial Ad
        val interstitialAdListener: InterstitialAdListener = object : InterstitialAdListener {
            override fun onInterstitialDisplayed(ad: Ad) {
                // Interstitial ad displayed callback
                Log.e("FBFullAd", "Interstitial ad displayed.")
            }

            override fun onInterstitialDismissed(ad: Ad) {
                // Interstitial dismissed callback
                Log.e("FBFullAd", "Interstitial ad dismissed.")
            }

            override fun onError(ad: Ad, adError: AdError) {
                // Ad error callback
                Log.e("FBFullAd", "Interstitial ad failed to load: " + adError.errorMessage)
            }

            override fun onAdLoaded(ad: Ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d("FBFullAd", "Interstitial ad is loaded and ready to be displayed!")
                // Show the ad
                interstitialAd.show()
            }

            override fun onAdClicked(ad: Ad) {
                // Ad clicked callback
                Log.d("FBFullAd", "Interstitial ad clicked!")
            }

            override fun onLoggingImpression(ad: Ad) {
                // Ad impression logged callback
                Log.d("FBFullAd", "Interstitial ad impression logged!")
            }
        }




        binding.scratchMsg?.isVisible = false


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            checkDateChangeAndReset()
        }

        //Hiding the toast
        binding.toast?.isVisible = false

        loadStars()

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
//
//        val editor = sharedPreferences.edit()
//        editor.apply {
//            putInt("SCRATCHES_LEFT_KEY",10)
//            putInt("STARS_KEY",0)
//        }.apply()

        val numOfScratchesLeft = sharedPreferences.getInt("SCRATCHES_LEFT_KEY", 10)
        binding.leftScratches?.text = numOfScratchesLeft.toString()

        if(binding.leftScratches?.text!="0"){
            createScratchCard()
        }else{
            binding.imgStar1.isVisible = false
            binding.scratchView1.isVisible = false
            binding.scratchMsg?.isVisible = true
        }

        binding.buttonRdm.setOnClickListener {
            val intent = Intent(this, RedeemActivity::class.java)
            startActivity(intent)
            finishActivity(1001)
            // For auto play video ads, it's recommended to load the ad
            // at least 30 seconds before it is shown
            interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                    .withAdListener(interstitialAdListener)
                    .build()
            )
        }

        binding.buttonShr.setOnClickListener {
            val packageManeger = packageManager;

            try{
                intent = Intent(Intent.ACTION_SEND)
                intent.setType("text/plain")
                val text = "\uD83D\uDC8E\uD83D\uDC8EScratch Card To Win Diamonds \uD83D\uDC8E\uD83D\uDC8E \n" +
                        "✅  मार्केट में एकदम नया इसलिए एप्रोक्स 90% स्क्रैच कार्ड में better luck नही आ रहा\n" +
                        "\n" +
                        "✅ नो MLM, नो रेफरल\n" +
                        " \n" +
                        "✅ नो टास्क, नो आदर एप्प डाउनलोड\n" +
                        "\n" +
                        "✅  रोजाना एप्रोक्स 500 रु. कमाई,\n" +
                        "\n" +
                        "✅  पमेंट प्रूफ :  प्ले स्टोर पर दिए स्क्रीन शॉट देखें\n" +
                        "\n" +
                        " Download link\n" +
                        "डाउनलोड करें अभी और डायमंड्स कमाएं \uD83D\uDC47\uD83C\uDFFB\uD83D\uDC47\uD83C\uDFFB\uD83D\uDCA5\uD83D\uDC8E"

                val info = packageManeger.getPackageInfo(
                    "com.whatsapp",
                    PackageManager.GET_META_DATA
                )

                intent.setPackage("com.whatsapp")
                intent.putExtra(Intent.EXTRA_TEXT, text)

                startActivity(intent)
            }catch (e: PackageManager.NameNotFoundException){
                Log.e("Whatsapp", "Whatsapp is not installed")
                Toast.makeText(
                    applicationContext,
                    "Whatsapp is not installed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onDestroy() {
        super.onDestroy()
        if(adView!=null){
            adView.destroy()
        }
    }



    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun createScratchCard() {
        binding.scratchView1?.setRevealListener(object : ScratchView.IRevealListener {

            override fun onRevealed(scratchView: ScratchView?) {

                // Create listeners for the Interstitial Ad
                // Create listeners for the Interstitial Ad
                val interstitialAdListener: InterstitialAdListener = object : InterstitialAdListener {
                    override fun onInterstitialDisplayed(ad: Ad) {
                        // Interstitial ad displayed callback
                        Log.e("FBFullAd", "Interstitial ad displayed.")
                    }

                    override fun onInterstitialDismissed(ad: Ad) {
                        // Interstitial dismissed callback
                        Log.e("FBFullAd", "Interstitial ad dismissed.")
                    }

                    override fun onError(ad: Ad, adError: AdError) {
                        // Ad error callback
                        Log.e("FBFullAd", "Interstitial ad failed to load: " + adError.errorMessage)
                    }

                    override fun onAdLoaded(ad: Ad) {
                        // Interstitial ad is loaded and ready to be displayed
                        Log.d("FBFullAd", "Interstitial ad is loaded and ready to be displayed!")
                        // Show the ad
                        interstitialAd.show()
                    }

                    override fun onAdClicked(ad: Ad) {
                        // Ad clicked callback
                        Log.d("FBFullAd", "Interstitial ad clicked!")
                    }

                    override fun onLoggingImpression(ad: Ad) {
                        // Ad impression logged callback
                        Log.d("FBFullAd", "Interstitial ad impression logged!")
                    }
                }


                var starsWon = Random.nextInt(20)
                if(starsWon==0){
                    starsWon = Random.nextInt(20)
                }

                // Make Toast
                binding.toastMsg?.text = "You have won $starsWon Diamonds"
                binding.toast?.isVisible = true
//                Timer().schedule(object : TimerTask() {
//                    override fun run() {
//                        binding.toast?.isVisible = false
//                    }
//                }, 1000)

                Timer().schedule(object : TimerTask() {
                    override fun run() {
                        this@MainActivity.runOnUiThread(Runnable {
                            binding.toast?.isVisible = false
                        })
                    }
                }, 2000)

//                Toast.makeText(
//                    applicationContext,
//                    "You have won $starsWon Diamonds",
//                    Toast.LENGTH_SHORT
//                ).show()

//                scratchView?.isVisible = false
                val stars: String = binding.numStar?.text.toString()
                Log.i("Main", "Diamonds $stars")
                binding.numStar?.text = (stars.toInt() + starsWon).toString()

                // Save the number of stars in the shared preferences
                saveStars(stars.toInt() + starsWon)

                val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                val numOfScratchesLeft = sharedPreferences.getInt("SCRATCHES_LEFT_KEY", 10)
                val editor = sharedPreferences.edit()
                editor.apply {
                    putInt("SCRATCHES_LEFT_KEY", numOfScratchesLeft - 1)
                }.apply()

                val numOfScratchesLeftAfterChange = sharedPreferences.getInt(
                    "SCRATCHES_LEFT_KEY",
                    10
                )
                binding.leftScratches?.text = numOfScratchesLeftAfterChange.toString()

                if(numOfScratchesLeftAfterChange==7||numOfScratchesLeftAfterChange==4||numOfScratchesLeftAfterChange==1){
                    // For auto play video ads, it's recommended to load the ad
                    // at least 30 seconds before it is shown
                    interstitialAd.loadAd(
                        interstitialAd.buildLoadAdConfig()
                            .withAdListener(interstitialAdListener)
                            .build()
                    )
                    Log.i("Adsss","Add should come after this scratch")
                    //   .withAdListener(interstitialAdListener)
                }

                if (numOfScratchesLeftAfterChange == 0) {
                    binding.imgStar1.isVisible = false
                    binding.scratchView1.isVisible = false
                    binding.scratchMsg?.isVisible = true
                } else {
                    Log.e("check", "Not 0 scratches")
                    Timer().schedule(object : TimerTask() {
                        override fun run() {
                            this@MainActivity.createScratchCard()
                            binding.scratchView1.mask()
                        }
                    }, 2000)

                }


            }

            override fun onRevealPercentChangedListener(scratchView: ScratchView?, percent: Float) {
                if (percent >= .5f) {
                    scratchView?.reveal()
                }

            }
        })
    }

    private fun saveStars(stars: Int) {
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putInt("STARS_KEY", stars)
        }.apply()
    }
    private fun loadStars() {
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedStars = sharedPreferences.getInt("STARS_KEY", 0)
        binding.numStar?.text  = savedStars.toString()
        Log.i("Load Stars", "Getting the stars")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkDateChangeAndReset(){

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        
        val savedDate = sharedPreferences.getString("TODAY_DATE_KEY", "")
        Log.i("DateChange","savedDate $savedDate")

        val todayDate = LocalDate.now().toString()
        Log.i("DateChange","todayDate $todayDate")

        if(savedDate==""){
            val editor = sharedPreferences.edit()
            editor.apply {
                putString("TODAY_DATE_KEY", todayDate)
            }.apply()
        }else{
            if(savedDate!=todayDate){
                Log.i("DateChange","Date change so the scratch reset to 10")
                val editor = sharedPreferences.edit()
                editor.apply {
                    putInt("SCRATCHES_LEFT_KEY", 10)
                }.apply()

                editor.apply {
                    putString("TODAY_DATE_KEY", todayDate)
                }.apply()

            }
        }


    }

}

