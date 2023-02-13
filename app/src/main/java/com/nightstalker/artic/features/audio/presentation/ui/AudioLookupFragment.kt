package com.nightstalker.artic.features.audio.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nightstalker.artic.R
import com.nightstalker.artic.core.presentation.ext.ui.onDone
import com.nightstalker.artic.core.presentation.model.ContentResultState
import com.nightstalker.artic.core.presentation.model.handleContents
import com.nightstalker.artic.databinding.FragmentAudioLookupBinding
import com.nightstalker.artic.features.ApiConstants
import com.nightstalker.artic.features.audio.domain.model.AudioFile
import com.nightstalker.artic.features.audio.player.NewAudioPlayerService
import com.nightstalker.artic.features.audio.presentation.viewmodel.AudioViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Фрагмент для поиска аудио
 *
 * @author Tamerlan Mamukhov on 2022-11-01
 */
class AudioLookupFragment : Fragment() {

    private val audioViewModel: AudioViewModel by viewModel()
    private var _binding: FragmentAudioLookupBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_audio_lookup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAudioLookupBinding.bind(view)
        setupView()
        initObservers()
    }

    private fun initObservers() = with(audioViewModel) {
        audioFileContentState.observe(viewLifecycleOwner, ::handleAudioSearch)
    }

    private fun handleAudioSearch(contentResultState: ContentResultState) =
        contentResultState.handleContents(
            onStateSuccess = {
                val serviceIntent = Intent(activity, NewAudioPlayerService::class.java)
                serviceIntent.putExtra(ApiConstants.EXTRA_AUDIO_URL, (it as AudioFile).url)
                activity?.startService(serviceIntent)
            },
            onStateError = {
                binding?.audioNumber?.error = "Не найдено!"
            }
        )

    private fun setupView() {
        with(_binding) {
            val inputLayout = this?.audioNumber
            val tv = inputLayout?.editText

            tv?.onDone {
                if (execSearch(tv.text.trim().toString().toInt())) {
                    inputLayout?.setStartIconOnClickListener {

                        var soundId = 0
                        if (tv?.text?.toString()?.isNotEmpty() == true) {
                            soundId = tv.text?.toString()?.toInt()!!
                        }

                        Bundle().apply {
                            if (soundId != null) {
                                putInt("mob_id", soundId)
                            }
                        }.run {
                            findNavController().navigate(
                                R.id.audioPlayerBottomSheetDialog,
                                args = this
                            )
                        }
                    }
                }
            }


        }
    }

    private fun execSearch(sequence: Int = 0): Boolean =
        when (sequence) {
            null -> {
                false
            }
            else -> {
                val query = MutableStateFlow(0)
                query.value = sequence
                audioViewModel.getSoundById(query.value)
                true
            }
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "AudioLookupFragment"
    }
}