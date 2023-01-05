package com.nightstalker.artic.features.audio.presentation.ui.dialog

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nightstalker.artic.R
import com.nightstalker.artic.core.presentation.filterHtmlEncodedText
import com.nightstalker.artic.core.presentation.model.ContentResultState
import com.nightstalker.artic.core.presentation.model.handleContents
import com.nightstalker.artic.databinding.FragmentAudioPlayerBottomSheetDialogBinding
import com.nightstalker.artic.features.ApiConstants.EXTRA_AUDIO_URL
import com.nightstalker.artic.features.audio.domain.model.AudioFileModel
import com.nightstalker.artic.features.audio.player.NewAudioPlayerService
import com.nightstalker.artic.features.audio.presentation.viewmodel.AudioViewModel
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
    private val audioViewModel by sharedViewModel<AudioViewModel>()
    private var boundService: NewAudioPlayerService? = null

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

        val serviceIntent = Intent(activity, NewAudioPlayerService::class.java)
        serviceIntent.putExtra(EXTRA_AUDIO_URL, "HFHF")
        activity?.startService(serviceIntent)

        initObserver()
    }

    private fun initObserver() {
        audioViewModel.audioFileContentState.observe(viewLifecycleOwner, ::handleAudio)
    }

    private fun handleAudio(contentResultState: ContentResultState) =
        contentResultState.handleContents(
            onStateSuccess = { content ->
                binding?.audioDescription?.text =
                    (content as AudioFileModel).transcript?.filterHtmlEncodedText()

                binding?.audioDescription?.setOnClickListener {
                    val serviceIntent = Intent(activity, NewAudioPlayerService::class.java)
                    activity?.stopService(serviceIntent)
                }
            },
            onStateError = {
                Log.d(TAG, "handle: $it")
            }
        )

    companion object {
        private const val TAG = "AudioPlayerBottomSheet"
    }
}