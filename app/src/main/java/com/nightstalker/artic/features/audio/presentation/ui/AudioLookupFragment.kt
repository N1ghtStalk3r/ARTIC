package com.nightstalker.artic.features.audio.presentation.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nightstalker.artic.R
import com.nightstalker.artic.core.domain.ContentResultState
import com.nightstalker.artic.core.presentation.onDone
import com.nightstalker.artic.databinding.FragmentAudioLookupBinding
import com.nightstalker.artic.features.audio.domain.model.AudioFileModel
import com.nightstalker.artic.features.audio.presentation.viewmodel.AudioViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart
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
        initObservers()
        setupView()
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
                findNavController().navigate(R.id.audioPlayerBottomSheetDialog)
            }
        }
    }

    private fun execSearch(sequence: Int? = 0): Flow<Int> {
        val query = MutableStateFlow(0)
        if (sequence != null) {
            query.value = sequence
            audioViewModel.getSoundById(query.value)
        }
        Log.d(TAG, "execSearch: $sequence")
        return query
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "AudioLookupFragment"
    }
}