package com.nightstalker.artic.features.audio.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nightstalker.artic.R
import com.nightstalker.artic.core.domain.ContentResultState
import com.nightstalker.artic.core.presentation.onDone
import com.nightstalker.artic.databinding.FragmentAudioLookupBinding
import com.nightstalker.artic.features.audio.domain.model.AudioFileModel
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
        audioFileContentState.observe(viewLifecycleOwner, ::handle)
    }

    private fun handle(contentResultState: ContentResultState) {
        when (contentResultState) {
            is ContentResultState.Content -> contentResultState.handle()
            is ContentResultState.Error -> contentResultState.handle()
            else -> {}
        }
    }

    private fun ContentResultState.Content.handle() {
        Log.d(TAG, "handle: ${contentSingle as AudioFileModel}")
    }

    private fun ContentResultState.Error.handle() {
        Log.d(TAG, "handle: $error")

        binding?.audioNumber?.error = "Не найдено!"
    }

    private fun setupView() {
        with(_binding) {
            val tv = this?.audioNumber?.editText
            tv?.onDone { execSearch(tv.text.trim().toString().toInt()) }

            this?.audioNumber?.setStartIconOnClickListener {

                var soundId = 0
                if (tv?.text?.toString()?.isNotEmpty() == true) {
                    soundId = tv.text?.toString()?.toInt()!!
                }

                Bundle().apply {
                    if (soundId != null) {
                        putInt("mob_id", soundId)
                    }
                }.run {
                    findNavController().navigate(R.id.audioPlayerBottomSheetDialog,
                        args = this)
                }
            }

        }
    }

    private fun execSearch(sequence: Int? = 0) {
        val query = MutableStateFlow(0)
        if (sequence != null) {
            query.value = sequence
            audioViewModel.getSoundById(query.value)
        }
        Log.d(TAG, "execSearch: $sequence")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "AudioLookupFragment"
    }
}