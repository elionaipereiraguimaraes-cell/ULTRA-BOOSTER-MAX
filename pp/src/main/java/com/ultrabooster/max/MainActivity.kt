package com.ultrabooster.max

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.*

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = Gravity.CENTER
        layout.setPadding(24, 24, 24, 24)
        layout.setBackgroundColor(Color.rgb(8, 12, 10))

        val titulo = TextView(this)
        titulo.text = "ULTRA BOOSTER MAX"
        titulo.textSize = 28f
        titulo.gravity = Gravity.CENTER
        titulo.setTextColor(Color.GREEN)
        layout.addView(titulo)

        val texto = TextView(this)
        texto.text = "PAINEL GAMER FREE FIRE MAX"
        texto.setTextColor(Color.WHITE)
        texto.textSize = 18f
        layout.addView(texto)

        val nomes = listOf(
            "Geral", "Ponto vermelho", "Mira 2x",
            "Mira 4x", "Mira AWM", "Olhadinha"
        )

        val prefs = getSharedPreferences("perfil", MODE_PRIVATE)

        nomes.forEach { nome ->
            val label = TextView(this)
            label.text = nome
            label.setTextColor(Color.WHITE)
            layout.addView(label)

            val barra = SeekBar(this)
            barra.max = 200
            barra.progress = prefs.getInt(nome, 150)
            layout.addView(barra)

            barra.setOnSeekBarChangeListener(
                object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(
                        seekBar: SeekBar?, progress: Int, fromUser: Boolean
                    ) {
                        label.text = "$nome: $progress"
                    }
                    override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
                }
            )
        }

        val salvar = Button(this)
        salvar.text = "SALVAR PERFIL"
        salvar.setOnClickListener {
            val editor = prefs.edit()
            val barras = (0 until layout.childCount)
                .map { layout.getChildAt(it) }
                .filterIsInstance<SeekBar>()
            nomes.forEachIndexed { i, nome ->
                editor.putInt(nome, barras[i].progress)
            }
            editor.apply()
            Toast.makeText(this, "Perfil salvo!", Toast.LENGTH_SHORT).show()
        }
        layout.addView(salvar)

        setContentView(ScrollView(this).apply {
            addView(layout)
        })
    }
}
