//для каждого

package download.mishkindeveloper.AnimalSounds
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import download.mishkindeveloper.AnimalSounds.databinding.ActivityLearnBinding


class learn : AppCompatActivity() {
    //закинуть в другие лайаяуты!!!!!!!!!!!!!!!!!!!!!!
    lateinit var binding: ActivityLearnBinding
    private var interAd: InterstitialAd? = null

    //воспроизведение звука животного
    val mediaPlayer by lazy { MediaPlayer.create(this, R.raw.toukan) }

    override fun onCreate(savedInstanceState: Bundle?) {

        var music = false

        super.onCreate(savedInstanceState)
        binding = ActivityLearnBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        getSupportActionBar()?.hide();

//переход сразу к приложению
        binding.iKnow.setOnClickListener {
            startActivity(Intent(this, IntroActivity::class.java))
            //IntroActivity().intent.extractActivities().startRandom(context = this)
            finish()
        }
        //политика конфиденциальности
        binding.policy.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW,
                Uri.parse("http://mishkindeveloper.download/pages-Privacy-Policy.html"))
            startActivity(browserIntent)
        }

        //название животного
        binding.startLearn.setOnClickListener {
            binding.iKnow.isVisible = false
            binding.textView.isVisible = true
            binding.infoName.setText(R.string.name_animal)
            binding.startLearn.setText(R.string.next)

            //картинка
            binding.startLearn.setOnClickListener {
                binding.textView.isVisible = false
                binding.imageAnimal.isVisible = true
                binding.infoName.isVisible = false
                binding.infoName2.setText(R.string.next2)
                binding.infoName2.textSize = 20F

//                //кнопка назад
//                binding.startLearn.setOnClickListener {
//                    binding.imageAnimal.isVisible = false
//                    binding.imageBack.isVisible = true
//                    binding.infoName2.isVisible = false
//                    binding.infoName3.setText(R.string.next3)

                //кнопка вперед
                binding.startLearn.setOnClickListener {
                    binding.imageAnimal.isVisible = false
                    binding.imageBack.isVisible = false
                    binding.imageNext.isVisible = true
                    binding.infoName2.isVisible = false
                    binding.infoName3.setText(R.string.next4)

                    //кнопка звука
                    binding.startLearn.setOnClickListener {

                        binding.imageNext.isVisible = false
                        binding.infoName2.isVisible = false
                        binding.imageZvyk.isVisible = true
                        binding.infoName3.setText(R.string.next5)
                        binding.infoName3.textSize = 25F
                        binding.startLearn.setText(R.string.go)


                        //вход в программу
                        binding.startLearn.setOnClickListener {
                            startActivity(Intent(this, IntroActivity::class.java))
                            finish()

                        }
                    }
                }
            }
        }

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
                    mediaPlayer.stop()
                    mediaPlayer.prepare()
                } else {
                    mediaPlayer.start()
                }
                music = !music
            }
        }
        playMusic()

//следующая картинка
        //binding.imageNext.setOnClickListener
//    {
//        //  nextImage()
//        // finish()
//        mediaPlayer.stop()
//
//
//    }
//    //преведущая картинка
//    binding.imageBack.setOnClickListener
//    {
//        //  var intent = Intent(this,lev::class.java)
//        //  startActivity(intent)
//        //   finish()
//        mediaPlayer.stop()
//    }
    }

        //начиная от сюда поставить во все лайяуты!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        override fun onPause() {
            super.onPause()
            mediaPlayer.stop()

        }


        override fun onResume() {
            super.onResume()

        }

        override fun onDestroy() {
            super.onDestroy()

        }

}




























