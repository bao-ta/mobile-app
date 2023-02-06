package com.example.drawingapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.get
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_brush_size.*

class MainActivity : AppCompatActivity() {
    private var mImageButtonCurrentPaint: ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mImageButtonCurrentPaint = ll_brush_color[5] as ImageButton
        mImageButtonCurrentPaint!!.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pallet_pressed))
        drawingView?.setSizeForBrush(10.toFloat())
        ib_brush.setOnClickListener{
            showBrushSizeChooserDialog()
        }

    }

    private fun showBrushSizeChooserDialog() {
        val brushDialog = Dialog(this)
        brushDialog.setContentView(R.layout.dialog_brush_size)
        brushDialog.setTitle("Brush size: ")
        val smallBursh = brushDialog.ib_small_brush
        smallBursh.setOnClickListener{
            drawingView.setSizeForBrush(5.toFloat())
            brushDialog.dismiss()
        }
        val mediumBrush = brushDialog.ib_medium_brush
        mediumBrush.setOnClickListener{
            drawingView.setSizeForBrush(10.toFloat())
            brushDialog.dismiss()
        }
        val largeBrush = brushDialog.ib_large_brush
        largeBrush.setOnClickListener{
            drawingView.setSizeForBrush(20.toFloat())
            brushDialog.dismiss()
        }
        brushDialog.show()
    }

    fun paintClicked(view: View) {
        if(view != mImageButtonCurrentPaint) {
            val imageButton = view as ImageButton
            val colorTag = imageButton.tag.toString()
            drawingView.setColor(colorTag)

            imageButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pallet_pressed))
            mImageButtonCurrentPaint!!.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pallet_normal))
            mImageButtonCurrentPaint = imageButton
        }
    }
}