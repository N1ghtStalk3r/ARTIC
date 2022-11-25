package com.nightstalker.artic.features.audio.presentation.ui.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nightstalker.artic.R
import com.nightstalker.artic.core.domain.ContentResultState
import com.nightstalker.artic.core.presentation.filterHtmlEncodedText
import com.nightstalker.artic.databinding.FragmentAudioPlayerBottomSheetDialogBinding
import com.nightstalker.artic.features.audio.domain.model.AudioFileModel
import com.nightstalker.artic.features.audio.player.AudioPlayerService
import com.nightstalker.artic.features.audio.presentation.ui.AudioPlayerBottomFragment
import com.nightstalker.artic.features.audio.presentation.viewmodel.AudioViewModel
import kotlinx.android.synthetic.main.fragment_audio_lookup.audioNumber
import kotlinx.android.synthetic.main.fragment_audio_player_bottom_sheet_dialog.audioDescription
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Аудио плеер в виде диалога
 *
 * @author Tamerlan Mamukhov
 * @created 2022-11-20
 */
class AudioPlayerBottomSheetDialog : BottomSheetDialogFragment() {
    private var _binding: FragmentAudioPlayerBottomSheetDialogBinding? = null
    private val binding get() = _binding
    private lateinit var audioPlayerService: AudioPlayerService
    private val audioViewModel by sharedViewModel<AudioViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(
            R.layout.fragment_audio_player_bottom_sheet_dialog,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAudioPlayerBottomSheetDialogBinding.bind(view)

        arguments?.getInt("mob_id")?.also {
            Log.d(TAG, "onViewCreated: $it")
            audioViewModel.getSoundById(it)
        }

        audioPlayerService = AudioPlayerService(requireContext())

        initObserver()
    }

    private fun initObserver() {
        audioViewModel.audioFileContentState.observe(viewLifecycleOwner, ::handle)
    }

    private fun handle(contentResultState: ContentResultState) {
        when (contentResultState) {
            is ContentResultState.Content -> contentResultState.handleContent()
            is ContentResultState.Error -> contentResultState.handleError()
            else -> {}
        }
    }

    private fun ContentResultState.Content.handleContent() {
        when (contentSingle) {
            is AudioFileModel -> {
                Log.d(TAG, "handleContent: ${contentSingle as AudioFileModel} ")
                audioPlayerService.setAudio(contentSingle as AudioFileModel)

                binding?.audioDescription?.text = (contentSingle as AudioFileModel)?.transcript?.filterHtmlEncodedText()
                audioPlayerService.initPlayer()

                binding?.audioPlayer?.player = audioPlayerService.player
            }
        }
    }

    private fun ContentResultState.Error.handleError() {
        Log.d(TAG, "handle: $error")
    }

    companion object {
        private const val TAG = "AudioPlayerBottomSheet"
    }
}