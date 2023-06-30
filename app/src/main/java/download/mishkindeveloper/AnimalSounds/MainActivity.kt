package download.mishkindeveloper.AnimalSounds

import android.content.Intent
import android.content.IntentSender
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.MobileAds
import com.google.android.ump.*
import com.google.firebase.analytics.FirebaseAnalytics
import download.mishkindeveloper.AnimalSounds.databinding.ActivityMainBinding
import com.google.android.ump.ConsentForm

import com.google.android.play.core.appupdate.*
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.InstallStateUpdatedListener

class MainActivity : AppCompatActivity() {
    private lateinit var consentInformation: ConsentInformation
    private lateinit var consentForm: ConsentForm
    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    lateinit var binding: ActivityMainBinding
    lateinit var mAppUpdateManager: AppUpdateManager
    private val RC_APP_UPDATE = 100
    private var updateCanceled: String? = null
    private var newAppIsReady: String? = null
    private var updateInstall: String? = null

    private lateinit var outAnimation: Animation

    private lateinit var textReview : String
    private lateinit var laiterReview : String
    private lateinit var leaveReview : String
    private lateinit var okReview : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        getSupportActionBar()?.hide();

        updateCanceled = getString(R.string.update_canceled)
        newAppIsReady = getString(R.string.new_app_is_ready)
        updateInstall = getString(R.string.update_install)

        mAppUpdateManager = AppUpdateManagerFactory.create(this)
        mAppUpdateManager.registerListener(installStateUpdatedListener)

        mAppUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
            ) {
                try {
                    mAppUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo, AppUpdateType.FLEXIBLE, this, RC_APP_UPDATE
                    )
                } catch (e: IntentSender.SendIntentException) {
                    e.printStackTrace()
                }
            }
        }

outAnimation = AnimationUtils.loadAnimation(this, R.anim.alfa_out)
        // Set tag for under age of consent. false means users are not under
        // age.
        val params = ConsentRequestParameters
            .Builder()
            .setTagForUnderAgeOfConsent(false)
            .build()
        consentInformation = UserMessagingPlatform.getConsentInformation(this)
        consentInformation.requestConsentInfoUpdate(
            this,
            params,
            ConsentInformation.OnConsentInfoUpdateSuccessListener {
                // The consent information state was updated.
                // You are now ready to check if a form is available.
                if (consentInformation.isConsentFormAvailable) {
                    loadForm()
                }
            },
            ConsentInformation.OnConsentInfoUpdateFailureListener {
                // Handle the error.
            })

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

        textReview = getString(R.string.text_review)
        laiterReview = getString(R.string.laiter_review)
        leaveReview = getString(R.string.leave_review)
        okReview = getString(R.string.ok_review)

        ReviewManager(this).checkAndPromptForReview(textReview, laiterReview, leaveReview,okReview)



    }
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        @Nullable data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_APP_UPDATE && resultCode != RESULT_OK) {
            // Handle the update cancellation
            Toast.makeText(this, updateCanceled, Toast.LENGTH_SHORT).show()
        }
    }
    override fun onStop() {
        super.onStop()
        mAppUpdateManager.unregisterListener(installStateUpdatedListener);
    }

    override fun onResume() {
        super.onResume()

        // Проверка состояния обновления
        mAppUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                // Обновление было загружено, отображаем сообщение
                showCompletedUpdate()
            } else if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                // Процесс обновления был приостановлен, возобновляем его
                try {
                    mAppUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.FLEXIBLE,
                        this,
                        RC_APP_UPDATE
                    )
                } catch (e: IntentSender.SendIntentException) {
                    e.printStackTrace()
                }
            }
        }
    }
    private fun loadForm() {
        UserMessagingPlatform.loadConsentForm(
            this,
            UserMessagingPlatform.OnConsentFormLoadSuccessListener {
                this.consentForm = it
                if (consentInformation.consentStatus == ConsentInformation.ConsentStatus.REQUIRED) {
                    consentForm.show(
                        this,
                        ConsentForm.OnConsentFormDismissedListener {
                            if (consentInformation.consentStatus == ConsentInformation.ConsentStatus.OBTAINED) {
                                // App can start requesting ads.
                            }
                            loadForm()
                        }
                    )
                }
            },
            UserMessagingPlatform.OnConsentFormLoadFailureListener { errorCode ->
                // Handle the error.

            }
        )
    }

    private val installStateUpdatedListener =
        InstallStateUpdatedListener { state ->
            if (state.installStatus() == InstallStatus.DOWNLOADED) {
                // Show the update completion message
                showCompletedUpdate()
            }
        }
    private fun showCompletedUpdate() {
        val snackbar = newAppIsReady?.let {
            Snackbar.make(
                findViewById(android.R.id.content), it,
                Snackbar.LENGTH_INDEFINITE
            )
        }
        snackbar?.setAction(
            updateInstall
        ) { mAppUpdateManager.completeUpdate() }
        snackbar?.show()
    }

}



