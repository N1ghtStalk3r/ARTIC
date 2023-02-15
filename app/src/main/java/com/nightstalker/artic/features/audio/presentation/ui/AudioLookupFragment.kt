package com.nightstalker.artic.features.audio.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
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
class AudioLookupFragment : Fragment(R.layout.fragment_audio_lookup) {

    private val audioViewModel: AudioViewModel by viewModel()
    private val binding: FragmentAudioLookupBinding by viewBinding(FragmentAudioLookupBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        with(binding) {
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

    companion object {
        private const val TAG = "AudioLookupFragment"
    }
}