package download.mishkindeveloper.AnimalSounds

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.MobileAds
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentForm
import com.google.android.ump.ConsentInformation
import com.google.android.ump.UserMessagingPlatform
import com.google.firebase.analytics.FirebaseAnalytics
import download.mishkindeveloper.AnimalSounds.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var consentInformation: ConsentInformation
    private lateinit var consentForm: ConsentForm
    private var mFirebaseAnalytics: FirebaseAnalytics? = null
       lateinit var binding: ActivityMainBinding

    private lateinit var outAnimation: Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        getSupportActionBar()?.hide();
outAnimation = AnimationUtils.loadAnimation(this, R.anim.alfa_out)

        //getConsentStatus()

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        MobileAds.initialize(this) { }

        //политика конфиденциальности
        binding.policy.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://mishkindeveloper.download/pages-Privacy-Policy.html"))
            startActivity(browserIntent)
        }

        binding.imageStart.setOnClickListener {

            var intent = Intent(this, download.mishkindeveloper.AnimalSounds.learn::class.java)
            startActivity(intent)
            finish()
        }
        binding.zatemnenie.setOnClickListener {

            var intent = Intent(this, download.mishkindeveloper.AnimalSounds.learn::class.java)
            startActivity(intent)
            finish()
        }
    }
//    fun getConsentStatus(){
//        consentInformation = UserMessagingPlatform.getConsentInformation(this)
//
//        ConsentDebugSettings debugSettings = new ConsentDebugSettings.Builder()
//            .setDebugGeography(ConsentDebugSettings.)
//    }
}



