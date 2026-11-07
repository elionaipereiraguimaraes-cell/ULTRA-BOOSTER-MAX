package com.ultrabooster.max

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import android.view.Gravity
import android.widget.*

class MainActivity : Activity() {

    private val verde = Color.rgb(0, 255, 100)
    private val fundo = Color.rgb(10, 12, 15)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tela = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 30, 24, 30)
            setBackgroundColor(fundo)
        }

        val titulo = TextView(this).apply {
            text = "ULTRA BOOSTER MAX"
            textSize = 27f
            setTextColor(verde)
            gravity = Gravity.CENTER
        }
        tela.addView(titulo)

        val subtitulo = TextView(this).apply {
            text = "PAINEL GAMER • FREE FIRE MAX"
            textSize = 13f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }
        tela.addView(subtitulo)

        val nomes = listOf(
            "Geral", "Ponto vermelho", "Mira 2x",
            "Mira 4x", "Mira AWM", "Olhadinha"
        )

        val preferencias = getSharedPreferences("perfis", MODE_PRIVATE)

        nomes.forEach { nome ->
            val valor = preferencias.getInt(nome, 150)

            val texto = TextView(this).apply {
                text = "$nome: $valor"
                textSize = 17f
                setTextColor(Color.WHITE)
            }
            tela.addView(texto)

            val barra = SeekBar(this).apply {
                max = 200
                progress = valor
                progressTintList =
                    android.content.res.ColorStateList.valueOf(verde)
                thumbTintList =
                    android.content.res.ColorStateList.valueOf(verde)
            }

            barra.setOnSeekBarChangeListener(
                object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(
                        seekBar: SeekBar?, progress: Int, fromUser: Boolean
                    ) {
                        texto.text = "$nome: $progress"
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
                }
            )

            tela.addView(barra)
        }

        val salvar = Button(this).apply {
            text = "SALVAR PERFIL"
            setOnClickListener {
                val editor = preferencias.edit()
                val barras = mutableListOf<SeekBar>()
                for (i in 0 until tela.childCount) {
                    val item = tela.getChildAt(i)
                    if (item is SeekBar) barras.add(item)
                }
                nomes.forEachIndexed { i, nome ->
                    editor.putInt(nome, barras[i].progress)
                }
                editor.apply()
                Toast.makeText(
                    this@MainActivity,
                    "Perfil salvo!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        tela.addView(salvar)

        val abrir = Button(this).apply {
            text = "ABRIR FREE FIRE MAX"
            setOnClickListener {
                val jogo = packageManager.getLaunchIntentForPackage(
                    "com.dts.freefiremax"
                )
                if (jogo != null) {
                    startActivity(jogo)
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Free Fire MAX não encontrado.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        tela.addView(abrir)

        val aviso = TextView(this).apply {
            text = "As sensibilidades são referências para configurar manualmente no jogo."
            setTextColor(Color.LTGRAY)
            textSize = 12f
            gravity = Gravity.CENTER
        }
        tela.addView(aviso)

        setContentView(
            ScrollView(this).apply {
                addView(tela)
            }
        )
    }
}
