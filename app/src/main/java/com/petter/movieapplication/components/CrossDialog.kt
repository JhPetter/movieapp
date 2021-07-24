package com.petter.movieapplication.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.petter.movieapplication.R
import com.petter.movieapplication.databinding.DialogErrorBinding

class CrossDialog(private val message: String) : DialogFragment() {

    private lateinit var binding: DialogErrorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CrossDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogErrorBinding.inflate(inflater, container, false)
        binding.dialogTitleText.text = message
        binding.dialogButton.setOnClickListener {
            this.dismiss()
        }
        return binding.root
    }
}