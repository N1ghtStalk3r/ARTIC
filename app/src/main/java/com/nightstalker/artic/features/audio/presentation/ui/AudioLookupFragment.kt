package com.nightstalker.artic.features.audio.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nightstalker.artic.R
import com.nightstalker.artic.databinding.FragmentAudioLookupBinding

/**
 * Фрагмент для поиска аудио
 *
 * @author Tamerlan Mamukhov on 2022-11-01
 */
class AudioLookupFragment : Fragment() {

    private var binding: FragmentAudioLookupBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_audio_lookup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAudioLookupBinding.bind(view)

        setupView()
    }

    private fun setupView() {
        with(binding) {
            this?.number?.error = "До 4 символов"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}