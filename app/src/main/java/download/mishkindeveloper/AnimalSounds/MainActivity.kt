package download.mishkindeveloper.AnimalSounds

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.google.android.ump.ConsentForm
import com.google.android.ump.ConsentInformation
import com.google.firebase.analytics.FirebaseAnalytics
import download.mishkindeveloper.AnimalSounds.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val consentInformation: ConsentInformation? = null
    private val consentForm: ConsentForm? = null
    private var mFirebaseAnalytics: FirebaseAnalytics? = null
       lateinit var binding: ActivityMainBinding
//lateinit var binding :  ActivityMainBinding
    private lateinit var outAnimation: Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        getSupportActionBar()?.hide();
outAnimation = AnimationUtils.loadAnimation(this, R.anim.alfa_out)

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        // гугл разработкик - разрешение для малолеток о просмотре рекламы
//        val params = ConsentRequestParameters.Builder()
//            .setTagForUnderAgeOfConsent(false)
//            .build()
//        consentInformation = UserMessagingPlatform.getConsentInformation(this);
//        consentInformation.requestConsentInfoUpdate(
//            this,
//            params,
//            new ConsentInformation.OnConsentInfoUpdateSuccessListener() {
//                @Override
//                public void onConsentInfoUpdateSuccess() {
//                    // The consent information state was updated.
//                    // You are now ready to check if a form is available.
//                }
//            },
//            new ConsentInformation.OnConsentInfoUpdateFailureListener() {
//                @Override
//                public void onConsentInfoUpdateFailure(FormError formError) {
//                    // Handle the error.
//                }
//            });
//
//        consentInformation.requestConsentInfoUpdate(
//            this,
//            params,
//            new ConsentInformation.OnConsentInfoUpdateSuccessListener() {
//                @Override
//                public void onConsentInfoUpdateSuccess() {
//                    // The consent information state was updated.
//                    // You are now ready to check if a form is available.
//                    if (consentInformation.isConsentFormAvailable()) {
//                        loadForm();
//                    }
//                }
//            },
//            new ConsentInformation.OnConsentInfoUpdateFailureListener() {
//                @Override
//                public void onConsentInfoUpdateFailure(FormError formError) {
//                    // Handle the error.
//
//                }
//            });
//
//
//    public void loadForm() {
//        UserMessagingPlatform.loadConsentForm(
//            this,
//            new UserMessagingPlatform.OnConsentFormLoadSuccessListener() {
//                @Override
//                public void onConsentFormLoadSuccess(ConsentForm consentForm) {
//                    MainActivity.this.consentForm = consentForm;
//                    if(consentInformation.getConsentStatus() == ConsentInformation.ConsentStatus.REQUIRED) {
//                        consentForm.show(
//                            MainActivity.this,
//                            new ConsentForm.OnConsentFormDismissedListener() {
//                                @Override
//                                public void onConsentFormDismissed(@Nullable FormError formError) {
//                                    // Handle dismissal by reloading form.
//                                    loadForm();
//                                }
//                            });
//
//                    }
//
//                }
//            },
//            new UserMessagingPlatform.OnConsentFormLoadFailureListener() {
//                @Override
//                public void onConsentFormLoadFailure(FormError formError) {
//                    /// Handle Error.
//    }

//2я попытка по политике






// конец - гугл разработкик - разрешение для малолеток о просмотре рекламы

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
}



