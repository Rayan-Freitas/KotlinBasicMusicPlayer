package com.example.services_musicplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageButton
import android.widget.SeekBar

class MainActivity : AppCompatActivity() {

    //Agora para criar uma seekbar progressiva que acompanha o tempo da música
    //Precisamos de um 'Runnable object' e um 'Handler'

    lateinit var runnable: Runnable
    private var handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var seekbar = findViewById<SeekBar>(R.id.seekbar)
        var play_btn = findViewById<ImageButton>(R.id.play_btn)

        //Criando um objeto MediaPlayer
        val mediaplayer : MediaPlayer = MediaPlayer.create(this, R.raw.musica)

        //Configurando SeekBar Barra de Progresso
        seekbar.progress = 0
        //E adicionar o valor máximo de duração da música
        seekbar.max = mediaplayer.duration

        //Criando evento do botão Play de música
        play_btn.setOnClickListener {
            //Primeiro vamos verificar se o botão já está tocando
            if(!mediaplayer.isPlaying){
                mediaplayer.start()

                //E mudar a imagem do botão
                play_btn.setImageResource(R.drawable.ic_baseline_pause_24)

            }else { //A media está rodando e podemos pausa-la
                mediaplayer.pause()
                play_btn.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            }
        }

        //Adicionar o evento da seekBar
        //Quando mudar nossa seekbar o tempo da música também deverá mudar

        seekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, pos: Int, changed: Boolean) {
                //Agora quando mudarmos a posição da SeekBar a música também mudará de tempo
                if(changed){
                    mediaplayer.seekTo(pos)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })
        //Código da seekbar Progressiva
        runnable = Runnable {
            seekbar.progress = mediaplayer.currentPosition
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
        //Código para quando a música terminar a barrinha resetar e mudar a imagem do botão
        mediaplayer.setOnCompletionListener {
            play_btn.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            seekbar.progress = 0
        }


    }
}