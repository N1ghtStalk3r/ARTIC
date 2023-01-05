package com.nightstalker.artic.features.audio.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nightstalker.artic.R
import com.nightstalker.artic.core.presentation.model.ContentResultState
import com.nightstalker.artic.core.presentation.model.handleContents
import com.nightstalker.artic.databinding.FragmentAudioPlayerBottomBinding
import com.nightstalker.artic.features.audio.domain.model.AudioFile
import com.nightstalker.artic.features.audio.presentation.viewmodel.AudioViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * @author Tamerlan Mamukhov
 * @created 2022-11-23
 */
class AudioPlayerBottomFragment : Fragment() {
    private var _binding: FragmentAudioPlayerBottomBinding? = null
    private val binding get() = _binding
    private val audioViewModel by sharedViewModel<AudioViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_audio_player_bottom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAudioPlayerBottomBinding.bind(view)
        setupPlaying()
        initObserver()
    }

    private fun initObserver() {
        audioViewModel.audioFileContentState.observe(viewLifecycleOwner, ::handleAudio)
    }

    private fun setupPlaying() {
        binding?.play?.setOnClickListener {
            findNavController().navigate(R.id.audioPlayerBottomSheetDialog)
        }
    }

    private fun handleAudio(contentResultState: ContentResultState) =
        contentResultState.handleContents(
            onStateSuccess = {
                binding?.audioTitle?.text = (it as AudioFile).title
            },
            onStateError = {
                Log.d(TAG, "handle: $it")
            }
        )

    companion object {
        private const val TAG = "AudioPlayerBottom"
    }
}