package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class SecondActivity : AppCompatActivity() {

    private lateinit var resultTextView: TextView
    private lateinit var nextButton: Button
    private lateinit var likeButton: ImageButton
    private var isLiked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        resultTextView = findViewById(R.id.activity_second_result_text_view)
        nextButton = findViewById(R.id.next_button)
        likeButton = findViewById(R.id.like_button)

        val result = intent.getStringExtra("result")
        resultTextView.text = result

        nextButton.setOnClickListener {
            finishAffinity()
        }

        likeButton.setOnClickListener {
            isLiked = !isLiked
            if (isLiked) {
                likeButton.setColorFilter(ContextCompat.getColor(this, R.color.red))
            } else {
                likeButton.setColorFilter(ContextCompat.getColor(this, R.color.white))
            }
        }
    }
}