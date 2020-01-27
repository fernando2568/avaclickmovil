package com.proyect.avaclick.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import com.joanzapata.pdfview.PDFView
import com.proyect.avaclick.R
import java.io.File

class PdfActivity : AppCompatActivity() {
    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pdf_viewer)

        val pdfFile: String = getIntent().getStringExtra("pdfFile")
        val file = File(Environment.getExternalStorageDirectory(), "/Reportes/" + pdfFile)
        val pdf = findViewById<PDFView>(R.id.pdf)
        pdf.fromFile(file).load()
    }

}