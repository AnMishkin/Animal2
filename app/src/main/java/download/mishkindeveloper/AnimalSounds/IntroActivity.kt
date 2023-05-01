package download.mishkindeveloper.AnimalSounds

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*


import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import download.mishkindeveloper.AnimalSounds.databinding.*


class IntroActivity : ComponentActivity() {


    private val activities = setOf(
        bee::class.java,
        cat::class.java,

        deer::class.java,
        donkey::class.java,
        Goose::class.java,
        Parrot::class.java,
        Racoon::class.java,

        chiken::class.java,
        elefant::class.java,
        frog::class.java,
        furseal::class.java,
        grasshopper::class.java,
        lamb::class.java,
        lemur::class.java,
        lev::class.java,
        monkey::class.java,
        mouse::class.java,
        owl::class.java,
        peacock::class.java,
        pig::class.java,
        rooster::class.java,
        snake::class.java,
        squirrel::class.java,
        teddy::class.java,
        zebra::class.java,

        chick::class.java,
        cow::class.java,
        dog::class.java,
        duck::class.java,
        eagle::class.java,
        goat::class.java,
        horse::class.java,
        panda::class.java,
        turkey::class.java,
        wolf::class.java ,
                //1.05.2023
        Toucan::class.java
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activities.startRandom(context = this)

    }

}


const val EXTRA_ACTIVITY_NAMES = "extra:activityNames"


fun Collection<Class<out Activity>>.startRandom(context: Context) {

    when {
        isEmpty() -> {

            context.startActivity(Intent(context, finish::class.java))
            finish()
        }
        else -> {


            val randomActivity = random()
            val intent = Intent(context, randomActivity)
            val activityNames = (this - randomActivity).map { it.name }.toTypedArray()
            intent.putExtra(EXTRA_ACTIVITY_NAMES, activityNames)

            context.startActivity(intent)
            finish()
        }
    }
}

@Suppress("UNCHECKED_CAST")
fun Intent.extractActivities(): Set<Class<out Activity>> {
    val activityNames = getStringArrayExtra(EXTRA_ACTIVITY_NAMES)
    val activities = activityNames?.map {
        Class.forName(it) as Class<out Activity>
    }?.toSet()


    var nomer: Int? = activities?.size
    Log.d("Mylog", "колличество активити=$nomer")

    when (nomer) {
        26 -> {


            Log.d("Mylog", "запуск рекламы")
        }
        22 -> {
            Log.d("Mylog", "запуск рекламы")
        }
        18 -> {
            Log.d("Mylog", "запуск рекламы")
        }
        14 -> {
            Log.d("Mylog", "запуск рекламы")
        }
        10 -> {
            Log.d("Mylog", "запуск рекламы")
        }
        6 -> {
            Log.d("Mylog", "запуск рекламы")
        }
        3 -> {
            Log.d("Mylog", "запуск рекламы")
        }
        0 -> {
            Log.d("Mylog", "запуск рекламы")
        }
    }
    return requireNotNull(activities)

}


////////////лев-межстраничный///////////////////////////////////////////////////////////////////////////////
class lev : AppCompatActivity() {
    //закинуть в другие лайаяуты!!!!!!!!!!!!!!!!!!!!!!
    lateinit var binding: ActivityLevBinding
    private var mInterstitialAd: InterstitialAd? = null

    //воспроизведение звука животного
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        var music = false
        super.onCreate(savedInstanceState)
        binding = ActivityLevBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.lev)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        supportActionBar?.hide();
        //запуск рекламы баннерной!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        initAdMob()
        loadInterAd()
        //замена мультяшного животного на обычного и назад
        fun onClickAnimal() {
            binding.imageAnimal.setOnClickListener(View.OnClickListener {
                binding.imageAnimal.setImageResource(R.mipmap.levs)
                binding.imageAnimal.setOnClickListener {
                    binding.imageAnimal.setImageResource(R.mipmap.lev)
                    onClickAnimal()
                }
            })
        }
        onClickAnimal()
        //проигрываем и останавливаем музыку
        fun playMusic() {
            binding.imageZvyk.setOnClickListener {
                if (music) {
                    //mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(this, Uri.parse("android.resource://$packageName/${R.raw.lev}"))
                    mediaPlayer.prepareAsync()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }
        }
        playMusic()
//следующая картинка
        binding.imageNext.setOnClickListener {
            findViewById<View>(R.id.imageNext).visibility = View.INVISIBLE
            //запуск рекламы при переходе на след страницу
            showInterAd()
            //nextImage()

        }
    }

    //передалал переход на следующюю картинку за счет функции
    private fun nextImage() {
        intent.extractActivities().startRandom(context = this)
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
        finish()
    }

    //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    override fun onPause() {
        super.onPause()
        binding.adView.pause()
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
    }

    //функция запуска баннерной рекламы,скопировать на каждый 5 лайяут!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
        loadInterAd()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.adView != null) {
            binding.adView.destroy()
        }
    }

    //скопировать на каждый 5 лайяут фулскрин реклама - с заменой кода!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun loadInterAd() {
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            this,
            "ca-app-pub-3971991853344828/4168444130",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adError?.toString()?.let { Log.d(TAG, it) }
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {

                    mInterstitialAd = interstitialAd
                }
            })
    }

    private fun showInterAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        Log.d(TAG, "Ad was dismissed.")

                        mInterstitialAd = null
                        loadInterAd()
                        nextImage()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        Log.d(TAG, "Ad failed to show.")
                        mInterstitialAd = null
                        nextImage()
                    }

                    override fun onAdShowedFullScreenContent() {
                        Log.d(TAG, "Ad showed fullscreen content.")

                    }
                }
            mInterstitialAd?.show(this)
        } else {
            loadInterAd()
        }
    }

}

//////////////monkey////////////////////////////////////////////////////////////////////////////
class monkey : AppCompatActivity() {
    //закинуть в другие лайаяуты!!!!!!!!!!!!!!!!!!!!!!
    lateinit var binding: ActivityMonkeyBinding
    private var interAd: InterstitialAd? = null

    //воспроизведение звука животного
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        var music = false
        super.onCreate(savedInstanceState)
        binding = ActivityMonkeyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.monkey)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        supportActionBar?.hide();
        //запуск рекламы баннерной!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        initAdMob()
        //замена мультяшного животного на обычного и назад
        fun onClickAnimal() {
            binding.imageAnimal.setOnClickListener(View.OnClickListener {
                binding.imageAnimal.setImageResource(R.mipmap.monkeys)

                binding.imageAnimal.setOnClickListener {
                    binding.imageAnimal.setImageResource(R.mipmap.monkey)
                    onClickAnimal()
                }
            })
        }
        onClickAnimal()
        //проигрываем и останавливаем музыку
        fun playMusic() {
            binding.imageZvyk.setOnClickListener {
                if (music) {
                    //mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(this, Uri.parse("android.resource://$packageName/${R.raw.monkey}"))
                    mediaPlayer.prepareAsync()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }
        }
        playMusic()
//следующая картинка
        binding.imageNext.setOnClickListener {
            nextImage()
            finish()
        }

    }

    //передалал переход на следующюю картинку за счет функции
    private fun nextImage() {
        intent.extractActivities().startRandom(context = this)
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
        finish()
    }

    //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    override fun onPause() {
        super.onPause()
        binding.adView.pause()
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
    }

    //функция запуска баннерной рекламы,скопировать на каждый 5 лайяут!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()

    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.adView != null) {
            binding.adView.destroy()
        }
    }
}

//////////////Cat///////////////////////////////////////////////////////////////////////////////
class cat : AppCompatActivity() {
    //закинуть в другие лайаяуты!!!!!!!!!!!!!!!!!!!!!!
    lateinit var binding: ActivityCatBinding
    private var interAd: InterstitialAd? = null

    //воспроизведение звука животного
    private lateinit var mediaPlayer: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        var music = false
        super.onCreate(savedInstanceState)
        binding = ActivityCatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.cat)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        supportActionBar?.hide();
        //запуск рекламы баннерной!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        initAdMob()
        //замена мультяшного животного на обычного и назад
        fun onClickAnimal() {
            binding.imageAnimal.setOnClickListener(View.OnClickListener {
                binding.imageAnimal.setImageResource(R.mipmap.cats)

                binding.imageAnimal.setOnClickListener {
                    binding.imageAnimal.setImageResource(R.mipmap.cat)
                    onClickAnimal()
                }
            })
        }
        onClickAnimal()
        //проигрываем и останавливаем музыку
        fun playMusic() {
            binding.imageZvyk.setOnClickListener {
                if (music) {
                    //mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(this, Uri.parse("android.resource://$packageName/${R.raw.cat}"))
                    mediaPlayer.prepareAsync()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }

//            mediaPlayer.setOnPreparedListener {
//                mediaPlayer.start()
//            }
        }
        playMusic()
//следующая картинка
        binding.imageNext.setOnClickListener {
            //запуск рекламы при переходе на след страницу
            //showInterAd()
            nextImage()
            finish()
        }
    }

    //передалал переход на следующюю картинку за счет функции
    private fun nextImage() {
        intent.extractActivities().startRandom(context = this)
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
        finish()
    }

    //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    override fun onPause() {
        super.onPause()
        binding.adView.pause()
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
    }

    //функция запуска баннерной рекламы,скопировать на каждый 5 лайяут!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()

    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.adView != null) {
            binding.adView.destroy()
        }
    }
}

/////////////морской котик - межстраничный/////////////////////////////////////////////////////////////////////
class furseal : AppCompatActivity() {
    //закинуть в другие лайаяуты!!!!!!!!!!!!!!!!!!!!!!
    lateinit var binding: ActivityFurSealBinding
    private var mInterstitialAd: InterstitialAd? = null

    //воспроизведение звука животного
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        var music = false
        super.onCreate(savedInstanceState)
        binding = ActivityFurSealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.furseal)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        supportActionBar?.hide();
        //запуск рекламы баннерной!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        initAdMob()
        loadInterAd()
        //замена мультяшного животного на обычного и назад
        fun onClickAnimal() {
            binding.imageAnimal.setOnClickListener(View.OnClickListener {
                binding.imageAnimal.setImageResource(R.mipmap.furseals)
                binding.imageAnimal.setOnClickListener {
                    binding.imageAnimal.setImageResource(R.mipmap.furseal)
                    onClickAnimal()
                }
            })
        }
        onClickAnimal()
        //проигрываем и останавливаем музыку
        fun playMusic() {
            binding.imageZvyk.setOnClickListener {
                if (music) {
                    //mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(this, Uri.parse("android.resource://$packageName/${R.raw.furseal}"))
                    mediaPlayer.prepareAsync()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }
        }
        playMusic()
//следующая картинка
        binding.imageNext.setOnClickListener {
            findViewById<View>(R.id.imageNext).visibility = View.INVISIBLE
            //запуск рекламы при переходе на след страницу
            showInterAd()
            //nextImage()
        }
    }

    //передалал переход на следующюю картинку за счет функции
    private fun nextImage() {
        intent.extractActivities().startRandom(context = this)
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
        finish()
    }

    //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    override fun onPause() {
        super.onPause()
        binding.adView.pause()
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
    }

    //функция запуска баннерной рекламы,скопировать на каждый 5 лайяут!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
        loadInterAd()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.adView != null) {
            binding.adView.destroy()
        }
    }

    //скопировать на каждый 5 лайяут фулскрин реклама - с заменой кода!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun loadInterAd() {
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            this,
            "ca-app-pub-3971991853344828/4168444130",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adError?.toString()?.let { Log.d(TAG, it) }
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {

                    mInterstitialAd = interstitialAd
                }
            })
    }

    private fun showInterAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        Log.d(TAG, "Ad was dismissed.")

                        mInterstitialAd = null
                        loadInterAd()
                        nextImage()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        Log.d(TAG, "Ad failed to show.")
                        mInterstitialAd = null
                        nextImage()
                    }

                    override fun onAdShowedFullScreenContent() {
                        Log.d(TAG, "Ad showed fullscreen content.")
                    }
                }
            mInterstitialAd?.show(this)
        } else {
            loadInterAd()
        }
    }
}

////////////белка//////////////////////////////////////////////////////////////////////////////
class squirrel : AppCompatActivity() {
    //закинуть в другие лайаяуты!!!!!!!!!!!!!!!!!!!!!!
    lateinit var binding: ActivitySquirrelBinding
    private var interAd: InterstitialAd? = null

    //воспроизведение звука животного
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        var music = false
        super.onCreate(savedInstanceState)
        binding = ActivitySquirrelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.squirrel)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        supportActionBar?.hide();
        //запуск рекламы баннерной!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        initAdMob()
        //замена мультяшного животного на обычного и назад
        fun onClickAnimal() {
            binding.imageAnimal.setOnClickListener(View.OnClickListener {
                binding.imageAnimal.setImageResource(R.mipmap.squirrels)
                binding.imageAnimal.setOnClickListener {
                    binding.imageAnimal.setImageResource(R.mipmap.squirrel)
                    onClickAnimal()
                }
            })
        }
        onClickAnimal()
        //проигрываем и останавливаем музыку
        fun playMusic() {
            binding.imageZvyk.setOnClickListener {
                if (music) {
                    //mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(this, Uri.parse("android.resource://$packageName/${R.raw.squirrel}"))
                    mediaPlayer.prepareAsync()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }
        }
        playMusic()
//следующая картинка
        binding.imageNext.setOnClickListener {
            nextImage()
            //запуск рекламы при переходе на след страницу
            //showInterAd()
        }
    }

    //передалал переход на следующюю картинку за счет функции
    private fun nextImage() {
        intent.extractActivities().startRandom(context = this)
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
        finish()
    }

    //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    override fun onPause() {
        super.onPause()
        binding.adView.pause()
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
    }

    //функция запуска баннерной рекламы,скопировать на каждый 5 лайяут!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.adView != null) {
            binding.adView.destroy()
        }
    }
}


////////////курица-межстраничный/////////////////////////////////////////////////////////////////////////////
class chiken : AppCompatActivity() {
    //закинуть в другие лайаяуты!!!!!!!!!!!!!!!!!!!!!!
    lateinit var binding: ActivityChikenBinding
    private var mInterstitialAd: InterstitialAd? = null

    //воспроизведение звука животного
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        var music = false
        super.onCreate(savedInstanceState)
        binding = ActivityChikenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.chiken)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        supportActionBar?.hide();
        //запуск рекламы баннерной!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        initAdMob()
        loadInterAd()
        //замена мультяшного животного на обычного и назад
        fun onClickAnimal() {
            binding.imageAnimal.setOnClickListener(View.OnClickListener {
                binding.imageAnimal.setImageResource(R.mipmap.chikens)

                binding.imageAnimal.setOnClickListener {
                    binding.imageAnimal.setImageResource(R.mipmap.chiken)
                    onClickAnimal()
                }
            })
        }
        onClickAnimal()
        //проигрываем и останавливаем музыку
        fun playMusic() {
            binding.imageZvyk.setOnClickListener {
                if (music) {
                    //mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(this, Uri.parse("android.resource://$packageName/${R.raw.chiken}"))
                    mediaPlayer.prepareAsync()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }
        }
        playMusic()
//следующая картинка
        binding.imageNext.setOnClickListener {
            findViewById<View>(R.id.imageNext).visibility = View.INVISIBLE
            //запуск рекламы при переходе на след страницу
            showInterAd()


        }
    }

    //передалал переход на следующюю картинку за счет функции
    private fun nextImage() {
        intent.extractActivities().startRandom(context = this)
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
        finish()
    }

    //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    override fun onPause() {
        super.onPause()
        binding.adView.pause()
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
    }

    //функция запуска баннерной рекламы,скопировать на каждый 5 лайяут!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
        loadInterAd()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.adView != null) {
            binding.adView.destroy()
        }
    }

    //скопировать на каждый 5 лайяут фулскрин реклама - с заменой кода!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun loadInterAd() {
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            this,
            "ca-app-pub-3971991853344828/4168444130",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adError?.toString()?.let { Log.d(TAG, it) }
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {

                    mInterstitialAd = interstitialAd
                }
            })
    }

    private fun showInterAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        Log.d(TAG, "Ad was dismissed.")

                        mInterstitialAd = null
                        loadInterAd()
                        nextImage()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        Log.d(TAG, "Ad failed to show.")
                        mInterstitialAd = null
                        nextImage()
                    }

                    override fun onAdShowedFullScreenContent() {
                        Log.d(TAG, "Ad showed fullscreen content.")
                    }
                }
            mInterstitialAd?.show(this)
        } else {
            loadInterAd()
        }
    }
}

////////////петух///////////////////////////////////////////////////////////////////////////////
class rooster : AppCompatActivity() {
    //закинуть в другие лайаяуты!!!!!!!!!!!!!!!!!!!!!!
    lateinit var binding: ActivityRoosterBinding
    private var interAd: InterstitialAd? = null

    //воспроизведение звука животного
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        var music = false
        super.onCreate(savedInstanceState)
        binding = ActivityRoosterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.rooster)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        supportActionBar?.hide();
        //запуск рекламы баннерной!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        initAdMob()
        //замена мультяшного животного на обычного и назад
        fun onClickAnimal() {
            binding.imageAnimal.setOnClickListener(View.OnClickListener {
                binding.imageAnimal.setImageResource(R.mipmap.roosters)

                binding.imageAnimal.setOnClickListener {
                    binding.imageAnimal.setImageResource(R.mipmap.rooster)
                    onClickAnimal()
                }
            })
        }
        onClickAnimal()
        //проигрываем и останавливаем музыку
        fun playMusic() {
            binding.imageZvyk.setOnClickListener {
                if (music) {
                    //mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(this, Uri.parse("android.resource://$packageName/${R.raw.rooster}"))
                    mediaPlayer.prepareAsync()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }
        }
        playMusic()
//следующая картинка
        binding.imageNext.setOnClickListener {
            nextImage()
        }
    }

    //передалал переход на следующюю картинку за счет функции
    private fun nextImage() {
        intent.extractActivities().startRandom(context = this)
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
        finish()
    }

    //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    override fun onPause() {
        super.onPause()
        binding.adView.pause()
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
    }

    //функция запуска баннерной рекламы,скопировать на каждый 5 лайяут!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
        // loadInterAd()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.adView != null) {
            binding.adView.destroy()
        }
    }
}

////////////жаба///////////////////////////////////////////////////////////////////////////////
class frog : AppCompatActivity() {
    //закинуть в другие лайаяуты!!!!!!!!!!!!!!!!!!!!!!
    lateinit var binding: ActivityFrogBinding
    private var interAd: InterstitialAd? = null

    //воспроизведение звука животного
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        var music = false
        super.onCreate(savedInstanceState)
        binding = ActivityFrogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.frog)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        supportActionBar?.hide();
        //запуск рекламы баннерной!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        initAdMob()
        //замена мультяшного животного на обычного и назад
        fun onClickAnimal() {
            binding.imageAnimal.setOnClickListener(View.OnClickListener {
                binding.imageAnimal.setImageResource(R.mipmap.frogs)

                binding.imageAnimal.setOnClickListener {
                    binding.imageAnimal.setImageResource(R.mipmap.frog)
                    onClickAnimal()
                }
            })
        }
        onClickAnimal()
        //проигрываем и останавливаем музыку
        fun playMusic() {
            binding.imageZvyk.setOnClickListener {
                if (music) {
                    //mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(
                        this,
                        Uri.parse("android.resource://$packageName/${R.raw.frog}")
                    )
                    mediaPlayer.prepareAsync()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }
        }
        playMusic()
//следующая картинка
        binding.imageNext.setOnClickListener {
            nextImage()
        }
    }

    //передалал переход на следующюю картинку за счет функции
    private fun nextImage() {
        intent.extractActivities().startRandom(context = this)
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
        finish()
    }

    //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    override fun onPause() {
        super.onPause()
        binding.adView.pause()
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
    }

    //функция запуска баннерной рекламы,скопировать на каждый 5 лайяут!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.adView != null) {
            binding.adView.destroy()
        }
    }
}
////////////Пчела///////////////////////////////////////////////////////////////////////////////
class bee : AppCompatActivity() {
    //закинуть в другие лайаяуты!!!!!!!!!!!!!!!!!!!!!!
    lateinit var binding: ActivityBeeBinding
    private var interAd: InterstitialAd? = null

    //воспроизведение звука животного
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        var music = false
        super.onCreate(savedInstanceState)
        binding = ActivityBeeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.bee)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        supportActionBar?.hide();
        //запуск рекламы баннерной!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        initAdMob()
        //замена мультяшного животного на обычного и назад
        fun onClickAnimal() {
            binding.imageAnimal.setOnClickListener(View.OnClickListener {
                binding.imageAnimal.setImageResource(R.mipmap.bees)

                binding.imageAnimal.setOnClickListener {
                    binding.imageAnimal.setImageResource(R.mipmap.bee)
                    onClickAnimal()
                }
            })
        }
        onClickAnimal()
        //проигрываем и останавливаем музыку
        fun playMusic() {
            binding.imageZvyk.setOnClickListener {
                if (music) {
                    //mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(this, Uri.parse("android.resource://$packageName/${R.raw.bee}"))
                    mediaPlayer.prepareAsync()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }
        }

        playMusic()
//следующая картинка
        binding.imageNext.setOnClickListener {
            //запуск рекламы при переходе на след страницу
            //showInterAd()
            nextImage()
            finish()
        }
    }

    //передалал переход на следующюю картинку за счет функции
    private fun nextImage() {
        intent.extractActivities().startRandom(context = this)
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
        finish()
    }

    //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    override fun onPause() {
        super.onPause()
        binding.adView.pause()
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }

    }

    //функция запуска баннерной рекламы,скопировать на каждый 5 лайяут!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.adView != null) {
            binding.adView.destroy()
        }
    }
}

////////////зебра///////////////////////////////////////////////////////////////////////////////
class zebra : AppCompatActivity() {
    //закинуть в другие лайаяуты!!!!!!!!!!!!!!!!!!!!!!
    lateinit var binding: ActivityZebraBinding
    private var interAd: InterstitialAd? = null

    //воспроизведение звука животного
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        var music = false
        super.onCreate(savedInstanceState)
        binding = ActivityZebraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.zebra)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        supportActionBar?.hide();
        //запуск рекламы баннерной!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        initAdMob()
        //замена мультяшного животного на обычного и назад
        fun onClickAnimal() {
            binding.imageAnimal.setOnClickListener(View.OnClickListener {
                binding.imageAnimal.setImageResource(R.mipmap.zebras)

                binding.imageAnimal.setOnClickListener {
                    binding.imageAnimal.setImageResource(R.mipmap.zebra)
                    onClickAnimal()
                }
            })
        }
        onClickAnimal()
        //проигрываем и останавливаем музыку
        fun playMusic() {
            binding.imageZvyk.setOnClickListener {
                if (music) {
                    //mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(this, Uri.parse("android.resource://$packageName/${R.raw.zebra}"))
                    mediaPlayer.prepareAsync()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }
        }
        playMusic()
//следующая картинка
        binding.imageNext.setOnClickListener {
            nextImage()
        }
    }

    //передалал переход на следующюю картинку за счет функции
    private fun nextImage() {
        intent.extractActivities().startRandom(context = this)
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
        finish()
    }

    //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    override fun onPause() {
        super.onPause()
        binding.adView.pause()
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
    }

    //функция запуска баннерной рекламы,скопировать на каждый 5 лайяут!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
        //loadInterAd()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.adView != null) {
            binding.adView.destroy()
        }
    }
}

////////////свинья-межстраничный///////////////////////////////////////////////////////////////////////////////
class pig : AppCompatActivity() {
    //закинуть в другие лайаяуты!!!!!!!!!!!!!!!!!!!!!!
    lateinit var binding: ActivityPigBinding
    private var mInterstitialAd: InterstitialAd? = null

    //воспроизведение звука животного
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        var music = false
        super.onCreate(savedInstanceState)
        binding = ActivityPigBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.pig)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        supportActionBar?.hide();
        //запуск рекламы баннерной!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        initAdMob()
        loadInterAd()
        //замена мультяшного животного на обычного и назад
        fun onClickAnimal() {
            binding.imageAnimal.setOnClickListener(View.OnClickListener {
                binding.imageAnimal.setImageResource(R.mipmap.pigs)
                binding.imageAnimal.setOnClickListener {
                    binding.imageAnimal.setImageResource(R.mipmap.pig)
                    onClickAnimal()
                }
            })
        }
        onClickAnimal()
        //проигрываем и останавливаем музыку
        fun playMusic() {
            binding.imageZvyk.setOnClickListener {
                if (music) {
                    //mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(this, Uri.parse("android.resource://$packageName/${R.raw.pig}"))
                    mediaPlayer.prepareAsync()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }
        }
        playMusic()
//следующая картинка
        binding.imageNext.setOnClickListener {
            findViewById<View>(R.id.imageNext).visibility = View.INVISIBLE
            //запуск рекламы при переходе на след страницу
            showInterAd()

        }
    }

    //передалал переход на следующюю картинку за счет функции
    private fun nextImage() {
        intent.extractActivities().startRandom(context = this)
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
        finish()
    }

    //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    override fun onPause() {
        super.onPause()
        binding.adView.pause()
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
    }

    //функция запуска баннерной рекламы,скопировать на каждый 5 лайяут!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
        loadInterAd()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.adView != null) {
            binding.adView.destroy()
        }
    }

    //скопировать на каждый 5 лайяут фулскрин реклама - с заменой кода!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun loadInterAd() {
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            this,
            "ca-app-pub-3971991853344828/4168444130",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adError?.toString()?.let { Log.d(TAG, it) }
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {

                    mInterstitialAd = interstitialAd
                }
            })
    }

    private fun showInterAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        Log.d(TAG, "Ad was dismissed.")

                        mInterstitialAd = null
                        loadInterAd()
                        nextImage()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        Log.d(TAG, "Ad failed to show.")
                        mInterstitialAd = null
                        nextImage()
                    }

                    override fun onAdShowedFullScreenContent() {
                        Log.d(TAG, "Ad showed fullscreen content.")
                    }
                }
            mInterstitialAd?.show(this)
        } else {
            loadInterAd()
        }
    }
}

////////////лемур///////////////////////////////////////////////////////////////////////////////
class lemur : AppCompatActivity() {
    //закинуть в другие лайаяуты!!!!!!!!!!!!!!!!!!!!!!
    lateinit var binding: ActivityLemurBinding
    private var interAd: InterstitialAd? = null

    //воспроизведение звука животного
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        var music = false
        super.onCreate(savedInstanceState)
        binding = ActivityLemurBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.lemur)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        getSupportActionBar()?.hide();
        //запуск рекламы баннерной!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        initAdMob()
        //замена мультяшного животного на обычного и назад
        fun onClickAnimal() {
            binding.imageAnimal.setOnClickListener(View.OnClickListener {
                binding.imageAnimal.setImageResource(R.mipmap.lemurs)

                binding.imageAnimal.setOnClickListener {
                    binding.imageAnimal.setImageResource(R.mipmap.lemur)
                    onClickAnimal()
                }
            })
        }
        onClickAnimal()
        //проигрываем и останавливаем музыку
        fun playMusic() {
            binding.imageZvyk.setOnClickListener {
                if (music) {
                    //mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(this, Uri.parse("android.resource://$packageName/${R.raw.lemur}"))
                    mediaPlayer.prepareAsync()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }
        }
        playMusic()
//следующая картинка
        binding.imageNext.setOnClickListener {
            nextImage()
        }
    }

    //передалал переход на следующюю картинку за счет функции
    private fun nextImage() {
        intent.extractActivities().startRandom(context = this)
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
        finish()
    }

    //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    override fun onPause() {
        super.onPause()
        binding.adView.pause()
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
    }

    //функция запуска баннерной рекламы,скопировать на каждый 5 лайяут!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
        //loadInterAd()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.adView != null) {
            binding.adView.destroy()
        }
    }
}

////////////медведь///////////////////////////////////////////////////////////////////////////////
class teddy : AppCompatActivity() {
    //закинуть в другие лайаяуты!!!!!!!!!!!!!!!!!!!!!!
    lateinit var binding: ActivityTeddyBinding
    private var interAd: InterstitialAd? = null

    //воспроизведение звука животного
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        var music = false
        super.onCreate(savedInstanceState)
        binding = ActivityTeddyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.teddy)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        supportActionBar?.hide();
        //запуск рекламы баннерной!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        initAdMob()
        //замена мультяшного животного на обычного и назад
        fun onClickAnimal() {
            binding.imageAnimal.setOnClickListener(View.OnClickListener {
                binding.imageAnimal.setImageResource(R.mipmap.teddys)

                binding.imageAnimal.setOnClickListener {
                    binding.imageAnimal.setImageResource(R.mipmap.teddy)
                    onClickAnimal()
                }
            })
        }
        onClickAnimal()
        //проигрываем и останавливаем музыку
        fun playMusic() {
            binding.imageZvyk.setOnClickListener {
                if (music) {
                    //mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(this, Uri.parse("android.resource://$packageName/${R.raw.teddy}"))
                    mediaPlayer.prepareAsync()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }
        }
        playMusic()
//следующая картинка
        binding.imageNext.setOnClickListener {
            nextImage()
        }
    }

    //передалал переход на следующюю картинку за счет функции
    private fun nextImage() {
        intent.extractActivities().startRandom(context = this)
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
        finish()
    }

    //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    override fun onPause() {
        super.onPause()
        binding.adView.pause()
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
    }

    //функция запуска баннерной рекламы,скопировать на каждый 5 лайяут!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.adView != null) {
            binding.adView.destroy()
        }
    }
}

////////////кузнечик///////////////////////////////////////////////////////////////////////////////
class grasshopper : AppCompatActivity() {
    //закинуть в другие лайаяуты!!!!!!!!!!!!!!!!!!!!!!
    lateinit var binding: ActivityGrasshopperBinding
    private var interAd: InterstitialAd? = null

    //воспроизведение звука животного
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        var music = false
        super.onCreate(savedInstanceState)
        binding = ActivityGrasshopperBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.grasshopper)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        supportActionBar?.hide();
        //запуск рекламы баннерной!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        initAdMob()
        //замена мультяшного животного на обычного и назад
        fun onClickAnimal() {
            binding.imageAnimal.setOnClickListener(View.OnClickListener {
                binding.imageAnimal.setImageResource(R.mipmap.grasshoppers)

                binding.imageAnimal.setOnClickListener {
                    binding.imageAnimal.setImageResource(R.mipmap.grasshopper)
                    onClickAnimal()
                }
            })
        }
        onClickAnimal()
        //проигрываем и останавливаем музыку
        fun playMusic() {
            binding.imageZvyk.setOnClickListener {
                if (music) {
                    //mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(this, Uri.parse("android.resource://$packageName/${R.raw.grasshopper}"))
                    mediaPlayer.prepareAsync()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }
        }
        playMusic()
//следующая картинка
        binding.imageNext.setOnClickListener {
            nextImage()
        }
    }

    //передалал переход на следующюю картинку за счет функции
    private fun nextImage() {
        intent.extractActivities().startRandom(context = this)
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
        finish()
    }

    //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    override fun onPause() {
        super.onPause()
        binding.adView.pause()
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
    }

    //функция запуска баннерной рекламы,скопировать на каждый 5 лайяут!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.adView != null) {
            binding.adView.destroy()
        }
    }
}

////////////змея///////////////////////////////////////////////////////////////////////////////
class snake : AppCompatActivity() {
    //закинуть в другие лайаяуты!!!!!!!!!!!!!!!!!!!!!!
    lateinit var binding: ActivitySnakeBinding
    private var interAd: InterstitialAd? = null

    //воспроизведение звука животного
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        var music = false
        super.onCreate(savedInstanceState)
        binding = ActivitySnakeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.snake)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        supportActionBar?.hide();
        //запуск рекламы баннерной!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        initAdMob()
        //замена мультяшного животного на обычного и назад
        fun onClickAnimal() {
            binding.imageAnimal.setOnClickListener(View.OnClickListener {
                binding.imageAnimal.setImageResource(R.mipmap.snakes)

                binding.imageAnimal.setOnClickListener {
                    binding.imageAnimal.setImageResource(R.mipmap.snake)
                    onClickAnimal()
                }
            })
        }
        onClickAnimal()
        //проигрываем и останавливаем музыку
        fun playMusic() {
            binding.imageZvyk.setOnClickListener {
                if (music) {
                    //mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(this, Uri.parse("android.resource://$packageName/${R.raw.snake}"))
                    mediaPlayer.prepareAsync()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }
        }
        playMusic()
//следующая картинка
        binding.imageNext.setOnClickListener {
            nextImage()
        }
    }

    //передалал переход на следующюю картинку за счет функции
    private fun nextImage() {
        intent.extractActivities().startRandom(context = this)
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
        finish()
    }

    //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    override fun onPause() {
        super.onPause()
        binding.adView.pause()
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
    }

    //функция запуска баннерной рекламы,скопировать на каждый 5 лайяут!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.adView != null) {
            binding.adView.destroy()
        }
    }
}

////////////мышь - межстраничка////////////////////////////////////////////////////////////////////
class mouse : AppCompatActivity() {
    //закинуть в другие лайаяуты!!!!!!!!!!!!!!!!!!!!!!
    lateinit var binding: ActivityMouseBinding
    private var mInterstitialAd: InterstitialAd? = null

    //воспроизведение звука животного
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        var music = false
        super.onCreate(savedInstanceState)
        binding = ActivityMouseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.mouse)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        supportActionBar?.hide();
        //запуск рекламы баннерной!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        initAdMob()
        //замена мультяшного животного на обычного и назад
        fun onClickAnimal() {
            binding.imageAnimal.setOnClickListener(View.OnClickListener {
                binding.imageAnimal.setImageResource(R.mipmap.mouses)

                binding.imageAnimal.setOnClickListener {
                    binding.imageAnimal.setImageResource(R.mipmap.mouse)
                    onClickAnimal()
                }
            })
        }
        onClickAnimal()
        //проигрываем и останавливаем музыку
        fun playMusic() {
            binding.imageZvyk.setOnClickListener {
                if (music) {
                    //mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(this, Uri.parse("android.resource://$packageName/${R.raw.mouse}"))
                    mediaPlayer.prepareAsync()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }
        }
        playMusic()
//следующая картинка
        binding.imageNext.setOnClickListener {
            findViewById<View>(R.id.imageNext).visibility = View.INVISIBLE
            //запуск рекламы при переходе на след страницу
            showInterAd()

        }
    }

    //передалал переход на следующюю картинку за счет функции
    private fun nextImage() {
        intent.extractActivities().startRandom(context = this)
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
        finish()
    }

    //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    override fun onPause() {
        super.onPause()
        binding.adView.pause()
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
    }

    //функция запуска баннерной рекламы,скопировать на каждый 5 лайяут!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
        loadInterAd()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.adView != null) {
            binding.adView.destroy()
        }
    }

    //скопировать на каждый 5 лайяут фулскрин реклама - с заменой кода!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun loadInterAd() {
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            this,
            "ca-app-pub-3971991853344828/4168444130",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adError?.toString()?.let { Log.d(TAG, it) }
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {

                    mInterstitialAd = interstitialAd
                }
            })
    }

    private fun showInterAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        Log.d(TAG, "Ad was dismissed.")

                        mInterstitialAd = null
                        loadInterAd()
                        nextImage()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        Log.d(TAG, "Ad failed to show.")
                        mInterstitialAd = null
                        nextImage()
                    }

                    override fun onAdShowedFullScreenContent() {
                        Log.d(TAG, "Ad showed fullscreen content.")
                    }
                }
            mInterstitialAd?.show(this)
        } else {
            loadInterAd()
        }
    }
}

////////////elefant-межстраничный//////////////////////////////////////////////////////////////////
class elefant : AppCompatActivity() {
    //закинуть в другие лайаяуты!!!!!!!!!!!!!!!!!!!!!!
    lateinit var binding: ActivityElefantBinding
    private var mInterstitialAd: InterstitialAd? = null

    //воспроизведение звука животного
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        var music = false
        super.onCreate(savedInstanceState)
        binding = ActivityElefantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.elefant)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        supportActionBar?.hide();
        //запуск рекламы баннерной!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        initAdMob()
        //замена мультяшного животного на обычного и назад
        fun onClickAnimal() {
            binding.imageAnimal.setOnClickListener(View.OnClickListener {
                binding.imageAnimal.setImageResource(R.mipmap.elefants)

                binding.imageAnimal.setOnClickListener {
                    binding.imageAnimal.setImageResource(R.mipmap.elefant)
                    onClickAnimal()
                }
            })
        }
        onClickAnimal()
        //проигрываем и останавливаем музыку
        fun playMusic() {
            binding.imageZvyk.setOnClickListener {
                if (music) {
                    //mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(this, Uri.parse("android.resource://$packageName/${R.raw.elefant}"))
                    mediaPlayer.prepareAsync()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }
        }
        playMusic()
//следующая картинка
        binding.imageNext.setOnClickListener {
            findViewById<View>(R.id.imageNext).visibility = View.INVISIBLE
            //запуск рекламы при переходе на след страницу
            showInterAd()

        }

    }

    //передалал переход на следующюю картинку за счет функции
    private fun nextImage() {
        intent.extractActivities().startRandom(context = this)
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
        finish()
    }

    //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    override fun onPause() {
        super.onPause()
        binding.adView.pause()
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
    }

    //функция запуска баннерной рекламы,скопировать на каждый 5 лайяут!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
        loadInterAd()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.adView != null) {
            binding.adView.destroy()
        }
    }

    //скопировать на каждый 5 лайяут фулскрин реклама - с заменой кода!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun loadInterAd() {
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            this,
            "ca-app-pub-3971991853344828/4168444130",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adError?.toString()?.let { Log.d(TAG, it) }
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {

                    mInterstitialAd = interstitialAd
                }
            })
    }

    private fun showInterAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        Log.d(TAG, "Ad was dismissed.")

                        mInterstitialAd = null
                        loadInterAd()
                        nextImage()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        Log.d(TAG, "Ad failed to show.")
                        mInterstitialAd = null
                        nextImage()
                    }

                    override fun onAdShowedFullScreenContent() {
                        Log.d(TAG, "Ad showed fullscreen content.")
                    }
                }
            mInterstitialAd?.show(this)
        } else {
            loadInterAd()
        }
    }
}

////////////овечка////////////////////////////////////////////////////////////////////
class lamb : AppCompatActivity() {
    //закинуть в другие лайаяуты!!!!!!!!!!!!!!!!!!!!!!
    lateinit var binding: ActivityLambBinding
    private var interAd: InterstitialAd? = null

    //воспроизведение звука животного
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        var music = false
        super.onCreate(savedInstanceState)
        binding = ActivityLambBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.lamb)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        supportActionBar?.hide();
        //запуск рекламы баннерной!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        initAdMob()
        //замена мультяшного животного на обычного и назад
        fun onClickAnimal() {
            binding.imageAnimal.setOnClickListener(View.OnClickListener {
                binding.imageAnimal.setImageResource(R.mipmap.lambs)

                binding.imageAnimal.setOnClickListener {
                    binding.imageAnimal.setImageResource(R.mipmap.lamb)
                    onClickAnimal()
                }
            })
        }
        onClickAnimal()
        //проигрываем и останавливаем музыку
        fun playMusic() {
            binding.imageZvyk.setOnClickListener {
                if (music) {
                    //mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(this, Uri.parse("android.resource://$packageName/${R.raw.lamb}"))
                    mediaPlayer.prepareAsync()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }
        }
        playMusic()
//следующая картинка
        binding.imageNext.setOnClickListener {
            nextImage()
        }
    }

    //передалал переход на следующюю картинку за счет функции
    private fun nextImage() {
        intent.extractActivities().startRandom(context = this)
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
        finish()
    }

    //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    override fun onPause() {
        super.onPause()
        binding.adView.pause()
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
    }

    //функция запуска баннерной рекламы,скопировать на каждый 5 лайяут!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.adView != null) {
            binding.adView.destroy()
        }
    }
}

////////////сова////////////////////////////////////////////////////////////////////
class owl : AppCompatActivity() {
    //закинуть в другие лайаяуты!!!!!!!!!!!!!!!!!!!!!!
    lateinit var binding: ActivityOwlBinding
    private var interAd: InterstitialAd? = null

    //воспроизведение звука животного
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        var music = false
        super.onCreate(savedInstanceState)
        binding = ActivityOwlBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.owl)

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        getSupportActionBar()?.hide();
        //запуск рекламы баннерной!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        initAdMob()
        //замена мультяшного животного на обычного и назад
        fun onClickAnimal() {
            binding.imageAnimal.setOnClickListener(View.OnClickListener {
                binding.imageAnimal.setImageResource(R.mipmap.owls)
                binding.imageAnimal.setOnClickListener {
                    binding.imageAnimal.setImageResource(R.mipmap.owl)
                    onClickAnimal()
                }
            })
        }
        onClickAnimal()
        //проигрываем и останавливаем музыку
        fun playMusic() {
            binding.imageZvyk.setOnClickListener {
                if (music) {
                    //mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(this, Uri.parse("android.resource://$packageName/${R.raw.owl}"))
                    mediaPlayer.prepareAsync()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }
        }
        playMusic()
//следующая картинка
        binding.imageNext.setOnClickListener {
            nextImage()
        }
    }

    //передалал переход на следующюю картинку за счет функции
    private fun nextImage() {
        intent.extractActivities().startRandom(context = this)
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
        finish()
    }

    //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    override fun onPause() {
        super.onPause()
        binding.adView.pause()
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
    }

    //функция запуска баннерной рекламы,скопировать на каждый 5 лайяут!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.adView != null) {
            binding.adView.destroy()
        }
    }
}

////////////павлин-межстраничный////////////////////////////////////////////////////////////////////
class peacock : AppCompatActivity() {
    //закинуть в другие лайаяуты!!!!!!!!!!!!!!!!!!!!!!
    lateinit var binding: ActivityPeacockBinding
    private var mInterstitialAd: InterstitialAd? = null

    //воспроизведение звука животного
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        var music = false
        super.onCreate(savedInstanceState)
        binding = ActivityPeacockBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.peacock)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        supportActionBar?.hide();
        //запуск рекламы баннерной!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        initAdMob()
        //замена мультяшного животного на обычного и назад
        fun onClickAnimal() {
            binding.imageAnimal.setOnClickListener(View.OnClickListener {
                binding.imageAnimal.setImageResource(R.mipmap.peacocks)

                binding.imageAnimal.setOnClickListener {
                    binding.imageAnimal.setImageResource(R.mipmap.peacock)
                    onClickAnimal()
                }
            })
        }
        onClickAnimal()
        //проигрываем и останавливаем музыку
        fun playMusic() {
            binding.imageZvyk.setOnClickListener {
                if (music) {
                    //mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(this, Uri.parse("android.resource://$packageName/${R.raw.peacock}"))
                    mediaPlayer.prepareAsync()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }
        }
        playMusic()
//следующая картинка
        binding.imageNext.setOnClickListener {
            findViewById<View>(R.id.imageNext).visibility = View.INVISIBLE
            //запуск рекламы при переходе на след страницу
            showInterAd()

        }
    }

    //передалал переход на следующюю картинку за счет функции
    private fun nextImage() {
        intent.extractActivities().startRandom(context = this)
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
        finish()
    }

    //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    override fun onPause() {
        super.onPause()
        binding.adView.pause()
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
    }

    //функция запуска баннерной рекламы,скопировать на каждый 5 лайяут!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
        loadInterAd()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.adView != null) {
            binding.adView.destroy()
        }
    }

    //скопировать на каждый 5 лайяут фулскрин реклама - с заменой кода!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun loadInterAd() {
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            this,
            "ca-app-pub-3971991853344828/4168444130",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adError?.toString()?.let { Log.d(TAG, it) }
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {

                    mInterstitialAd = interstitialAd
                }
            })
    }

    private fun showInterAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        Log.d(TAG, "Ad was dismissed.")

                        mInterstitialAd = null
                        loadInterAd()
                        nextImage()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        Log.d(TAG, "Ad failed to show.")
                        mInterstitialAd = null
                        nextImage()
                    }

                    override fun onAdShowedFullScreenContent() {
                        Log.d(TAG, "Ad showed fullscreen content.")
                    }
                }
            mInterstitialAd?.show(this)
        } else {
            loadInterAd()
        }
    }
}

//////////////цыпленок-межстраничный//////////////////////////////////////////////
class chick : AppCompatActivity() {
    //закинуть в другие лайаяуты!!!!!!!!!!!!!!!!!!!!!!
    lateinit var binding: ActivityChickBinding
    private var mInterstitialAd: InterstitialAd? = null

    //воспроизведение звука животного
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        var adRequest = AdRequest.Builder().build()
        var music = false
        super.onCreate(savedInstanceState)
        binding = ActivityChickBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.chick)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        supportActionBar?.hide();
        //запуск рекламы баннерной!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        initAdMob()
        loadInterAd()
        //замена мультяшного животного на обычного и назад
        fun onClickAnimal() {
            binding.imageAnimal.setOnClickListener(View.OnClickListener {
                binding.imageAnimal.setImageResource(R.mipmap.chick2)

                binding.imageAnimal.setOnClickListener {
                    binding.imageAnimal.setImageResource(R.mipmap.chick)
                    onClickAnimal()
                }
            })
        }
        onClickAnimal()
        //проигрываем и останавливаем музыку
        fun playMusic() {
            binding.imageZvyk.setOnClickListener {
                if (music) {
                    //mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(this, Uri.parse("android.resource://$packageName/${R.raw.chick}"))
                    mediaPlayer.prepareAsync()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }
        }
        playMusic()
//следующая картинка
        binding.imageNext.setOnClickListener {
            findViewById<View>(R.id.imageNext).visibility = View.INVISIBLE
            //запуск рекламы при переходе на след страницу
            showInterAd()
            //nextImage()
        }
    }

    //передалал переход на следующюю картинку за счет функции
    private fun nextImage() {
        intent.extractActivities().startRandom(context = this)
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
        finish()
    }

    //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    override fun onPause() {
        super.onPause()
        binding.adView.pause()
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
    }

    //функция запуска баннерной рекламы,скопировать на каждый 5 лайяут!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
        loadInterAd()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.adView != null) {
            binding.adView.destroy()
        }
    }

    //  private var mInterstitialAd: InterstitialAd? = null
    //скопировать на каждый 5 лайяут фулскрин реклама - с заменой кода!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun loadInterAd() {
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            this,
            "ca-app-pub-3971991853344828/4168444130",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adError?.toString()?.let { Log.d(TAG, it) }
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {

                    mInterstitialAd = interstitialAd
                }
            })
    }

    private fun showInterAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        Log.d(TAG, "Ad was dismissed.")

                        mInterstitialAd = null
                        loadInterAd()
                        nextImage()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        Log.d(TAG, "Ad failed to show.")
                        mInterstitialAd = null
                        nextImage()
                    }

                    override fun onAdShowedFullScreenContent() {
                        Log.d(TAG, "Ad showed fullscreen content.")
                    }
                }
            mInterstitialAd?.show(this)
        } else {
            loadInterAd()
        }
    }
}


//////////////корова-межстраничный/////////////////////////////////////////////////
class cow : AppCompatActivity() {
    //закинуть в другие лайаяуты!!!!!!!!!!!!!!!!!!!!!!
    lateinit var binding: ActivityCowBinding
    private var mInterstitialAd: InterstitialAd? = null

    //воспроизведение звука животного
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        var music = false
        super.onCreate(savedInstanceState)
        binding = ActivityCowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.cow)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        supportActionBar?.hide();
        //запуск рекламы баннерной!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        initAdMob()
        //замена мультяшного животного на обычного и назад
        fun onClickAnimal() {
            binding.imageAnimal.setOnClickListener(View.OnClickListener {
                binding.imageAnimal.setImageResource(R.mipmap.cow2)

                binding.imageAnimal.setOnClickListener {
                    binding.imageAnimal.setImageResource(R.mipmap.cow)
                    onClickAnimal()
                }
            })
        }
        onClickAnimal()
        //проигрываем и останавливаем музыку
        fun playMusic() {
            binding.imageZvyk.setOnClickListener {
                if (music) {
                    //mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(this, Uri.parse("android.resource://$packageName/${R.raw.cow}"))
                    mediaPlayer.prepareAsync()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }
        }
        playMusic()
//следующая картинка
        binding.imageNext.setOnClickListener {
            findViewById<View>(R.id.imageNext).visibility = View.INVISIBLE
            //запуск рекламы при переходе на след страницу
            showInterAd()

        }
    }

    //передалал переход на следующюю картинку за счет функции
    private fun nextImage() {
        intent.extractActivities().startRandom(context = this)
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
        finish()
    }

    //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    override fun onPause() {
        super.onPause()
        binding.adView.pause()
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
    }

    //функция запуска баннерной рекламы,скопировать на каждый 5 лайяут!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
        loadInterAd()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.adView != null) {
            binding.adView.destroy()
        }
    }

    //скопировать на каждый 5 лайяут фулскрин реклама - с заменой кода!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun loadInterAd() {
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            this,
            "ca-app-pub-3971991853344828/4168444130",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adError?.toString()?.let { Log.d(TAG, it) }
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {

                    mInterstitialAd = interstitialAd
                }
            })
    }

    private fun showInterAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        Log.d(TAG, "Ad was dismissed.")

                        mInterstitialAd = null
                        loadInterAd()
                        nextImage()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        Log.d(TAG, "Ad failed to show.")
                        mInterstitialAd = null
                        nextImage()
                    }

                    override fun onAdShowedFullScreenContent() {
                        Log.d(TAG, "Ad showed fullscreen content.")
                    }
                }
            mInterstitialAd?.show(this)
        } else {
            loadInterAd()
        }
    }
}

//////////////собака-межстраничный/////////////////////////////////////////////////
class dog : AppCompatActivity() {
    //закинуть в другие лайаяуты!!!!!!!!!!!!!!!!!!!!!!
    lateinit var binding: ActivityDogBinding
    private var mInterstitialAd: InterstitialAd? = null

    //воспроизведение звука животного
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        var music = false
        super.onCreate(savedInstanceState)
        binding = ActivityDogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.dog)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        supportActionBar?.hide();
        //запуск рекламы баннерной!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        initAdMob()
        //замена мультяшного животного на обычного и назад
        fun onClickAnimal() {
            binding.imageAnimal.setOnClickListener(View.OnClickListener {
                binding.imageAnimal.setImageResource(R.mipmap.dog2)

                binding.imageAnimal.setOnClickListener {
                    binding.imageAnimal.setImageResource(R.mipmap.dog)
                    onClickAnimal()
                }
            })
        }
        onClickAnimal()
        //проигрываем и останавливаем музыку
        fun playMusic() {
            binding.imageZvyk.setOnClickListener {
                if (music) {
                    //mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(this, Uri.parse("android.resource://$packageName/${R.raw.dog}"))
                    mediaPlayer.prepareAsync()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }
        }
        playMusic()
//следующая картинка
        binding.imageNext.setOnClickListener {
            findViewById<View>(R.id.imageNext).visibility = View.INVISIBLE
            //запуск рекламы при переходе на след страницу
            showInterAd()

        }
    }

    //передалал переход на следующюю картинку за счет функции
    private fun nextImage() {
        intent.extractActivities().startRandom(context = this)
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
        finish()
    }

    //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    override fun onPause() {
        super.onPause()
        binding.adView.pause()
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
    }

    //функция запуска баннерной рекламы,скопировать на каждый 5 лайяут!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
        loadInterAd()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.adView != null) {
            binding.adView.destroy()
        }
    }

    //скопировать на каждый 5 лайяут фулскрин реклама - с заменой кода!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun loadInterAd() {
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            this,
            "ca-app-pub-3971991853344828/4168444130",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adError?.toString()?.let { Log.d(TAG, it) }
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {

                    mInterstitialAd = interstitialAd
                }
            })
    }

    private fun showInterAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        Log.d(TAG, "Ad was dismissed.")

                        mInterstitialAd = null
                        loadInterAd()
                        nextImage()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        Log.d(TAG, "Ad failed to show.")
                        mInterstitialAd = null
                        nextImage()
                    }

                    override fun onAdShowedFullScreenContent() {
                        Log.d(TAG, "Ad showed fullscreen content.")
                    }
                }
            mInterstitialAd?.show(this)
        } else {
            loadInterAd()
        }
    }
}


///////////утка////////////////////////////////////////////////////////////////////
class duck : AppCompatActivity() {
    //закинуть в другие лайаяуты!!!!!!!!!!!!!!!!!!!!!!
    lateinit var binding: ActivityDuckBinding
    private var interAd: InterstitialAd? = null

    //воспроизведение звука животного
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        var music = false
        super.onCreate(savedInstanceState)
        binding = ActivityDuckBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.duck)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        getSupportActionBar()?.hide();
        //запуск рекламы баннерной!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        initAdMob()
        //замена мультяшного животного на обычного и назад
        fun onClickAnimal() {
            binding.imageAnimal.setOnClickListener(View.OnClickListener {
                binding.imageAnimal.setImageResource(R.mipmap.duck2)
                binding.imageAnimal.setOnClickListener {
                    binding.imageAnimal.setImageResource(R.mipmap.duck)
                    onClickAnimal()
                }
            })
        }
        onClickAnimal()
        //проигрываем и останавливаем музыку
        fun playMusic() {
            binding.imageZvyk.setOnClickListener {
                if (music) {
                    //mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(this, Uri.parse("android.resource://$packageName/${R.raw.duck}"))
                    mediaPlayer.prepareAsync()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }
        }
        playMusic()
//следующая картинка
        binding.imageNext.setOnClickListener {
            nextImage()
        }
    }

    //передалал переход на следующюю картинку за счет функции
    private fun nextImage() {
        intent.extractActivities().startRandom(context = this)
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
        finish()
    }

    //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    override fun onPause() {
        super.onPause()
        binding.adView.pause()
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
    }

    //функция запуска баннерной рекламы,скопировать на каждый 5 лайяут!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.adView != null) {
            binding.adView.destroy()
        }
    }
}

///////////орел////////////////////////////////////////////////////////////////////
class eagle : AppCompatActivity() {
    //закинуть в другие лайаяуты!!!!!!!!!!!!!!!!!!!!!!
    lateinit var binding: ActivityEagleBinding
    private var interAd: InterstitialAd? = null

    //воспроизведение звука животного
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        var music = false
        super.onCreate(savedInstanceState)
        binding = ActivityEagleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.eagle)

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        getSupportActionBar()?.hide();
        //запуск рекламы баннерной!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        initAdMob()
        //замена мультяшного животного на обычного и назад
        fun onClickAnimal() {
            binding.imageAnimal.setOnClickListener(View.OnClickListener {
                binding.imageAnimal.setImageResource(R.mipmap.eagle2)
                binding.imageAnimal.setOnClickListener {
                    binding.imageAnimal.setImageResource(R.mipmap.eagle)
                    onClickAnimal()
                }
            })
        }
        onClickAnimal()
        //проигрываем и останавливаем музыку
        fun playMusic() {
            binding.imageZvyk.setOnClickListener {
                if (music) {
                    //mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(this, Uri.parse("android.resource://$packageName/${R.raw.eagle}"))
                    mediaPlayer.prepareAsync()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }
        }
        playMusic()
//следующая картинка
        binding.imageNext.setOnClickListener {
            nextImage()
        }
    }

    //передалал переход на следующюю картинку за счет функции
    private fun nextImage() {
        intent.extractActivities().startRandom(context = this)
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
        finish()
    }

    //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    override fun onPause() {
        super.onPause()
        binding.adView.pause()
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
    }

    //функция запуска баннерной рекламы,скопировать на каждый 5 лайяут!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.adView != null) {
            binding.adView.destroy()
        }
    }
}

///////////коза////////////////////////////////////////////////////////////////////
class goat : AppCompatActivity() {
    //закинуть в другие лайаяуты!!!!!!!!!!!!!!!!!!!!!!
    lateinit var binding: ActivityGoatBinding
    private var interAd: InterstitialAd? = null

    //воспроизведение звука животного
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        var music = false
        super.onCreate(savedInstanceState)
        binding = ActivityGoatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.goat)

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        getSupportActionBar()?.hide();
        //запуск рекламы баннерной!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        initAdMob()
        //замена мультяшного животного на обычного и назад
        fun onClickAnimal() {
            binding.imageAnimal.setOnClickListener(View.OnClickListener {
                binding.imageAnimal.setImageResource(R.mipmap.goat2)
                binding.imageAnimal.setOnClickListener {
                    binding.imageAnimal.setImageResource(R.mipmap.goat)
                    onClickAnimal()
                }
            })
        }
        onClickAnimal()
        //проигрываем и останавливаем музыку
        fun playMusic() {
            binding.imageZvyk.setOnClickListener {
                if (music) {
                    //mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(
                        this,
                        Uri.parse("android.resource://$packageName/${R.raw.goat}")
                    )
                    mediaPlayer.prepareAsync()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }
        }
        playMusic()
//следующая картинка
        binding.imageNext.setOnClickListener {
            nextImage()
        }
    }

    //передалал переход на следующюю картинку за счет функции
    private fun nextImage() {
        intent.extractActivities().startRandom(context = this)
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
        finish()
    }

    //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    override fun onPause() {
        super.onPause()
        binding.adView.pause()
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
    }

    //функция запуска баннерной рекламы,скопировать на каждый 5 лайяут!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.adView != null) {
            binding.adView.destroy()
        }
    }
}

///////////конь////////////////////////////////////////////////////////////////////
class horse : AppCompatActivity() {
    //закинуть в другие лайаяуты!!!!!!!!!!!!!!!!!!!!!!
    lateinit var binding: ActivityHorseBinding
    private var interAd: InterstitialAd? = null

    //воспроизведение звука животного
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        var music = false
        super.onCreate(savedInstanceState)
        binding = ActivityHorseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.horse)

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        getSupportActionBar()?.hide();
        //запуск рекламы баннерной!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        initAdMob()
        //замена мультяшного животного на обычного и назад
        fun onClickAnimal() {
            binding.imageAnimal.setOnClickListener(View.OnClickListener {
                binding.imageAnimal.setImageResource(R.mipmap.horse2)
                binding.imageAnimal.setOnClickListener {
                    binding.imageAnimal.setImageResource(R.mipmap.horse)
                    onClickAnimal()
                }
            })
        }
        onClickAnimal()
        //проигрываем и останавливаем музыку
        fun playMusic() {
            binding.imageZvyk.setOnClickListener {
                if (music) {
                    //mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(this, Uri.parse("android.resource://$packageName/${R.raw.horse}"))
                    mediaPlayer.prepareAsync()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }
        }
        playMusic()
//следующая картинка
        binding.imageNext.setOnClickListener {
            nextImage()
        }
    }

    //передалал переход на следующюю картинку за счет функции
    private fun nextImage() {
        intent.extractActivities().startRandom(context = this)
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
        finish()
    }

    //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    override fun onPause() {
        super.onPause()
        binding.adView.pause()
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
    }

    //функция запуска баннерной рекламы,скопировать на каждый 5 лайяут!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.adView != null) {
            binding.adView.destroy()
        }
    }
}

///////////панда////////////////////////////////////////////////////////////////////
class panda : AppCompatActivity() {
    //закинуть в другие лайаяуты!!!!!!!!!!!!!!!!!!!!!!
    lateinit var binding: ActivityPandaBinding
    private var interAd: InterstitialAd? = null

    //воспроизведение звука животного
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        var music = false
        super.onCreate(savedInstanceState)
        binding = ActivityPandaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.panda)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        getSupportActionBar()?.hide();
        //запуск рекламы баннерной!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        initAdMob()

        //замена мультяшного животного на обычного и назад
        fun onClickAnimal() {
            binding.imageAnimal.setOnClickListener(View.OnClickListener {
                binding.imageAnimal.setImageResource(R.mipmap.panda2)
                binding.imageAnimal.setOnClickListener {
                    binding.imageAnimal.setImageResource(R.mipmap.panda)
                    onClickAnimal()
                }
            })
        }
        onClickAnimal()
        //проигрываем и останавливаем музыку
        fun playMusic() {
            binding.imageZvyk.setOnClickListener {
                if (music) {
                    //mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(this, Uri.parse("android.resource://$packageName/${R.raw.panda}"))
                    mediaPlayer.prepareAsync()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }
        }
        playMusic()
//следующая картинка
        binding.imageNext.setOnClickListener {
            nextImage()
        }
    }

    //передалал переход на следующюю картинку за счет функции
    private fun nextImage() {
        intent.extractActivities().startRandom(context = this)
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
        finish()
    }

    //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    override fun onPause() {
        super.onPause()
        binding.adView.pause()
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
    }

    //функция запуска баннерной рекламы,скопировать на каждый 5 лайяут!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.adView != null) {
            binding.adView.destroy()
        }
    }
}

///////////индюк////////////////////////////////////////////////////////////////////
class turkey : AppCompatActivity() {

//    var GetInt = intent!!.getIntExtra("numberr",1)

    //закинуть в другие лайаяуты!!!!!!!!!!!!!!!!!!!!!!
    lateinit var binding: ActivityTurkeyBinding
    private var interAd: InterstitialAd? = null

    //воспроизведение звука животного
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        var music = false
        super.onCreate(savedInstanceState)
//        Log.d("Mylog","на входе в активити=$GetInt")
        binding = ActivityTurkeyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mediaPlayer = MediaPlayer.create(this, R.raw.turkey)

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        getSupportActionBar()?.hide();
        //запуск рекламы баннерной!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        initAdMob()
        //замена мультяшного животного на обычного и назад
        fun onClickAnimal() {
            binding.imageAnimal.setOnClickListener(View.OnClickListener {
                binding.imageAnimal.setImageResource(R.mipmap.turkey2)
                binding.imageAnimal.setOnClickListener {
                    binding.imageAnimal.setImageResource(R.mipmap.turkey)
                    onClickAnimal()
                }
            })
        }
        onClickAnimal()
        //проигрываем и останавливаем музыку
        fun playMusic() {
            binding.imageZvyk.setOnClickListener {
                if (music) {
                    //mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(this, Uri.parse("android.resource://$packageName/${R.raw.turkey}"))
                    mediaPlayer.prepareAsync()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }
        }
        playMusic()
//следующая картинка
        binding.imageNext.setOnClickListener {
            nextImage()
        }
    }

    //передалал переход на следующюю картинку за счет функции
    private fun nextImage() {
        intent.extractActivities().startRandom(context = this)
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
        finish()
    }

    //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    override fun onPause() {
        super.onPause()
        binding.adView.pause()
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
    }

    //функция запуска баннерной рекламы,скопировать на каждый 5 лайяут!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.adView != null) {
            binding.adView.destroy()
        }
    }
}

///////////волк////////////////////////////////////////////////////////////////////
class wolf : AppCompatActivity() {
    lateinit var binding: ActivityWolfBinding
    private var interAd: InterstitialAd? = null

    //воспроизведение звука животного
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
//        Log.d("Mylog","на входе в активити=$GetInt")
        var music = false
        super.onCreate(savedInstanceState)
        binding = ActivityWolfBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.wolf)


        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        getSupportActionBar()?.hide();
        //запуск рекламы баннерной!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        initAdMob()
        //замена мультяшного животного на обычного и назад
        fun onClickAnimal() {
            binding.imageAnimal.setOnClickListener(View.OnClickListener {
                binding.imageAnimal.setImageResource(R.mipmap.wolf2)
                binding.imageAnimal.setOnClickListener {
                    binding.imageAnimal.setImageResource(R.mipmap.wolf)
                    onClickAnimal()
                }
            })
        }
        onClickAnimal()
        //проигрываем и останавливаем музыку
        fun playMusic() {
            binding.imageZvyk.setOnClickListener {
                if (music) {
                    //mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(
                        this,
                        Uri.parse("android.resource://$packageName/${R.raw.wolf}")
                    )
                    mediaPlayer.prepareAsync()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }
        }
        playMusic()
//следующая картинка
        binding.imageNext.setOnClickListener {
            nextImage()
        }
    }

    //передалал переход на следующюю картинку за счет функции
    private fun nextImage() {
        intent.extractActivities().startRandom(context = this)
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
        finish()
    }

    //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    override fun onPause() {
        super.onPause()
        binding.adView.pause()
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
    }

    //функция запуска баннерной рекламы,скопировать на каждый 5 лайяут!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.adView != null) {
            binding.adView.destroy()
        }
    }
}
///////////олень////////////////////////////////////////////////////////////////////
class deer : AppCompatActivity() {
    lateinit var binding: ActivityDeerBinding
    private var interAd: InterstitialAd? = null

    //воспроизведение звука животного
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
//        Log.d("Mylog","на входе в активити=$GetInt")
        var music = false
        super.onCreate(savedInstanceState)
        binding = ActivityDeerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mediaPlayer = MediaPlayer.create(this, R.raw.deer)

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        getSupportActionBar()?.hide();
        //запуск рекламы баннерной!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        initAdMob()
        //замена мультяшного животного на обычного и назад
        fun onClickAnimal() {
            binding.imageAnimal.setOnClickListener(View.OnClickListener {
                binding.imageAnimal.setImageResource(R.mipmap.deers)
                binding.imageAnimal.setOnClickListener {
                    binding.imageAnimal.setImageResource(R.mipmap.deer)
                    onClickAnimal()
                }
            })
        }
        onClickAnimal()
        //проигрываем и останавливаем музыку
        fun playMusic() {
            binding.imageZvyk.setOnClickListener {
                if (music) {
                    //mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(
                        this,
                        Uri.parse("android.resource://$packageName/${R.raw.deer}")
                    )
                    mediaPlayer.prepareAsync()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }
        }
        playMusic()
//следующая картинка
        binding.imageNext.setOnClickListener {
            nextImage()
        }
    }

    //передалал переход на следующюю картинку за счет функции
    private fun nextImage() {
        intent.extractActivities().startRandom(context = this)
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
        finish()
    }

    //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    override fun onPause() {
        super.onPause()
        binding.adView.pause()
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
    }

    //функция запуска баннерной рекламы,скопировать на каждый 5 лайяут!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.adView != null) {
            binding.adView.destroy()
        }
    }
}
//////////////осел-межстраничный/////////////////////////////////////////////////
class donkey : AppCompatActivity() {
    //закинуть в другие лайаяуты!!!!!!!!!!!!!!!!!!!!!!
    lateinit var binding: ActivityDonkeyBinding
    private var mInterstitialAd: InterstitialAd? = null

    //воспроизведение звука животного
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        var music = false
        super.onCreate(savedInstanceState)
        binding = ActivityDonkeyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.donkey)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        supportActionBar?.hide();
        //запуск рекламы баннерной!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        initAdMob()
        //замена мультяшного животного на обычного и назад
        fun onClickAnimal() {
            binding.imageAnimal.setOnClickListener(View.OnClickListener {
                binding.imageAnimal.setImageResource(R.mipmap.donkeys)

                binding.imageAnimal.setOnClickListener {
                    binding.imageAnimal.setImageResource(R.mipmap.donkey)
                    onClickAnimal()
                }
            })
        }
        onClickAnimal()
        //проигрываем и останавливаем музыку
        fun playMusic() {
            binding.imageZvyk.setOnClickListener {
                if (music) {
                    //mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(this, Uri.parse("android.resource://$packageName/${R.raw.donkey}"))
                    mediaPlayer.prepareAsync()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }
        }
        playMusic()
//следующая картинка
        binding.imageNext.setOnClickListener {
            findViewById<View>(R.id.imageNext).visibility = View.INVISIBLE
            //запуск рекламы при переходе на след страницу
            showInterAd()

        }
    }

    //передалал переход на следующюю картинку за счет функции
    private fun nextImage() {
        intent.extractActivities().startRandom(context = this)
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
        finish()
    }

    //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    override fun onPause() {
        super.onPause()
        binding.adView.pause()
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
    }

    //функция запуска баннерной рекламы,скопировать на каждый 5 лайяут!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
        loadInterAd()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.adView != null) {
            binding.adView.destroy()
        }
    }

    //скопировать на каждый 5 лайяут фулскрин реклама - с заменой кода!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun loadInterAd() {
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            this,
            "ca-app-pub-3971991853344828/4168444130",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adError?.toString()?.let { Log.d(TAG, it) }
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {

                    mInterstitialAd = interstitialAd
                }
            })
    }

    private fun showInterAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        Log.d(TAG, "Ad was dismissed.")

                        mInterstitialAd = null
                        loadInterAd()
                        nextImage()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        Log.d(TAG, "Ad failed to show.")
                        mInterstitialAd = null
                        nextImage()
                    }

                    override fun onAdShowedFullScreenContent() {
                        Log.d(TAG, "Ad showed fullscreen content.")
                    }
                }
            mInterstitialAd?.show(this)
        } else {
            loadInterAd()
        }
    }
}

///////////гусь////////////////////////////////////////////////////////////////////
class Goose : AppCompatActivity() {
    lateinit var binding: ActivityGooseBinding
    private var interAd: InterstitialAd? = null

    //воспроизведение звука животного
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
//        Log.d("Mylog","на входе в активити=$GetInt")
        var music = false
        super.onCreate(savedInstanceState)
        binding = ActivityGooseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mediaPlayer = MediaPlayer.create(this, R.raw.goose)

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        getSupportActionBar()?.hide();
        //запуск рекламы баннерной!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        initAdMob()
        //замена мультяшного животного на обычного и назад
        fun onClickAnimal() {
            binding.imageAnimal.setOnClickListener(View.OnClickListener {
                binding.imageAnimal.setImageResource(R.mipmap.gooses)
                binding.imageAnimal.setOnClickListener {
                    binding.imageAnimal.setImageResource(R.mipmap.goose)
                    onClickAnimal()
                }
            })
        }
        onClickAnimal()
        //проигрываем и останавливаем музыку
        fun playMusic() {
            binding.imageZvyk.setOnClickListener {
                if (music) {
                    //mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(this, Uri.parse("android.resource://$packageName/${R.raw.goose}"))
                    mediaPlayer.prepareAsync()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }
        }
        playMusic()
//следующая картинка
        binding.imageNext.setOnClickListener {
            nextImage()
        }
    }

    //передалал переход на следующюю картинку за счет функции
    private fun nextImage() {
        intent.extractActivities().startRandom(context = this)
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
        finish()
    }

    //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    override fun onPause() {
        super.onPause()
        binding.adView.pause()
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
    }

    //функция запуска баннерной рекламы,скопировать на каждый 5 лайяут!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.adView != null) {
            binding.adView.destroy()
        }
    }

}

///////////Папугай////////////////////////////////////////////////////////////////////
class Parrot : AppCompatActivity() {
    lateinit var binding: ActivityParrotBinding
    private var interAd: InterstitialAd? = null

    //воспроизведение звука животного
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
//        Log.d("Mylog","на входе в активити=$GetInt")
        var music = false
        super.onCreate(savedInstanceState)
        binding = ActivityParrotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mediaPlayer = MediaPlayer.create(this, R.raw.parrot)

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        getSupportActionBar()?.hide();
        //запуск рекламы баннерной!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        initAdMob()
        //замена мультяшного животного на обычного и назад
        fun onClickAnimal() {
            binding.imageAnimal.setOnClickListener(View.OnClickListener {
                binding.imageAnimal.setImageResource(R.mipmap.parrots)
                binding.imageAnimal.setOnClickListener {
                    binding.imageAnimal.setImageResource(R.mipmap.parrot)
                    onClickAnimal()
                }
            })
        }
        onClickAnimal()
        //проигрываем и останавливаем музыку
        fun playMusic() {
            binding.imageZvyk.setOnClickListener {
                if (music) {
                    //mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(this, Uri.parse("android.resource://$packageName/${R.raw.parrot}"))
                    mediaPlayer.prepareAsync()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }
        }
        playMusic()
//следующая картинка
        binding.imageNext.setOnClickListener {
            nextImage()
        }
    }

    //передалал переход на следующюю картинку за счет функции
    private fun nextImage() {
        intent.extractActivities().startRandom(context = this)
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
        finish()
    }

    //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    override fun onPause() {
        super.onPause()
        binding.adView.pause()
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
    }

    //функция запуска баннерной рекламы,скопировать на каждый 5 лайяут!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.adView != null) {
            binding.adView.destroy()
        }
    }

}

///////////енот////////////////////////////////////////////////////////////////////
class Racoon : AppCompatActivity() {
    lateinit var binding: ActivityRacoonBinding
    private var interAd: InterstitialAd? = null

    //воспроизведение звука животного
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
//        Log.d("Mylog","на входе в активити=$GetInt")
        var music = false
        super.onCreate(savedInstanceState)
        binding = ActivityRacoonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.raccoon)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        getSupportActionBar()?.hide();
        //запуск рекламы баннерной!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        initAdMob()
        //замена мультяшного животного на обычного и назад
        fun onClickAnimal() {
            binding.imageAnimal.setOnClickListener(View.OnClickListener {
                binding.imageAnimal.setImageResource(R.mipmap.racoons)
                binding.imageAnimal.setOnClickListener {
                    binding.imageAnimal.setImageResource(R.mipmap.racoon)
                    onClickAnimal()
                }
            })
        }
        onClickAnimal()
        //проигрываем и останавливаем музыку
        fun playMusic() {
            binding.imageZvyk.setOnClickListener {
                if (music) {
                    //mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(this, Uri.parse("android.resource://$packageName/${R.raw.raccoon}"))
                    mediaPlayer.prepareAsync()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }
        }
        playMusic()
//следующая картинка
        binding.imageNext.setOnClickListener {
            nextImage()
        }
    }

    //передалал переход на следующюю картинку за счет функции
    private fun nextImage() {
        intent.extractActivities().startRandom(context = this)
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
        finish()
    }

    //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    override fun onPause() {
        super.onPause()
        binding.adView.pause()
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
    }

    //функция запуска баннерной рекламы,скопировать на каждый 5 лайяут!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.adView != null) {
            binding.adView.destroy()
        }
    }

}

////////////тукан-межстраничный///////////////////////////////////////////////////////////////////////////////
class Toucan : AppCompatActivity() {
    //закинуть в другие лайаяуты!!!!!!!!!!!!!!!!!!!!!!
    lateinit var binding: ActivityToucanBinding
    private var mInterstitialAd: InterstitialAd? = null

    //воспроизведение звука животного
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        var music = false
        super.onCreate(savedInstanceState)
        binding = ActivityToucanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.toukan)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        supportActionBar?.hide();
        //запуск рекламы баннерной!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        initAdMob()
        loadInterAd()
        //замена мультяшного животного на обычного и назад
        fun onClickAnimal() {
            binding.imageAnimal.setOnClickListener(View.OnClickListener {
                binding.imageAnimal.setImageResource(R.mipmap.toucans)
                binding.imageAnimal.setOnClickListener {
                    binding.imageAnimal.setImageResource(R.mipmap.toucan)
                    onClickAnimal()
                }
            })
        }
        onClickAnimal()
        //проигрываем и останавливаем музыку
        fun playMusic() {
            binding.imageZvyk.setOnClickListener {
                if (music) {
                    //mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(this, Uri.parse("android.resource://$packageName/${R.raw.toukan}"))
                    mediaPlayer.prepareAsync()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }
        }
        playMusic()
//следующая картинка
        binding.imageNext.setOnClickListener {
            findViewById<View>(R.id.imageNext).visibility = View.INVISIBLE
            //запуск рекламы при переходе на след страницу
            showInterAd()
            //nextImage()

        }
    }

    //передалал переход на следующюю картинку за счет функции
    private fun nextImage() {
        intent.extractActivities().startRandom(context = this)
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
        finish()
    }

    //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    override fun onPause() {
        super.onPause()
        binding.adView.pause()
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
    }

    //функция запуска баннерной рекламы,скопировать на каждый 5 лайяут!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
        loadInterAd()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.adView != null) {
            binding.adView.destroy()
        }
    }

    //скопировать на каждый 5 лайяут фулскрин реклама - с заменой кода!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun loadInterAd() {
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            this,
            "ca-app-pub-3971991853344828/4168444130",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adError?.toString()?.let { Log.d(TAG, it) }
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {

                    mInterstitialAd = interstitialAd
                }
            })
    }

    private fun showInterAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        Log.d(TAG, "Ad was dismissed.")

                        mInterstitialAd = null
                        loadInterAd()
                        nextImage()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        Log.d(TAG, "Ad failed to show.")
                        mInterstitialAd = null
                        nextImage()
                    }

                    override fun onAdShowedFullScreenContent() {
                        Log.d(TAG, "Ad showed fullscreen content.")

                    }
                }
            mInterstitialAd?.show(this)
        } else {
            loadInterAd()
        }
    }

}

