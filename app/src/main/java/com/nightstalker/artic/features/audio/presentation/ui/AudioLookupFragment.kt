package com.nightstalker.artic.features.audio.presentation.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.nightstalker.artic.R
import com.nightstalker.artic.core.domain.ContentResultState
import com.nightstalker.artic.databinding.FragmentAudioLookupBinding
import com.nightstalker.artic.features.audio.domain.model.AudioFileModel
import com.nightstalker.artic.features.audio.presentation.viewmodel.AudioLookupViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Фрагмент для поиска аудио
 *
 * @author Tamerlan Mamukhov on 2022-11-01
 */
class AudioLookupFragment : Fragment() {

    private val audioViewModel: AudioLookupViewModel by viewModel()

    private var binding: FragmentAudioLookupBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_audio_lookup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAudioLookupBinding.bind(view)

        initObservers()

        audioViewModel.getSoundById(1605)
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
        Log.d("AudioFragment", "handle: ${contentSingle as AudioFileModel}")
        Log.d("AudioFragment", "handle: ${contentsList as List<AudioFileModel>}")
    }

    private fun ContentResultState.Error.handle() {
        with(binding) {
        }
    }

    private fun setupView() {
        with(binding) {
            val tv = this?.number?.editText
            tv?.textChanges()
                ?.filterNot { it.isNullOrBlank() }
                ?.debounce(300)
                ?.distinctUntilChanged()
                ?.flatMapLatest { execSearch(it?.toString()?.toInt()) }
                ?.onEach { updateView(it) }
                ?.launchIn(lifecycleScope)
        }
    }

    private fun updateView(it: Any) {
    }

    private fun execSearch(sequence: Int?): Flow<Int> {
        val _searchQuery = MutableStateFlow(0)
        if (sequence != null) {
            _searchQuery.value = sequence

            audioViewModel.getSoundById(_searchQuery.value)
        }
        Log.d("fds", "execSearch: $sequence")
        return _searchQuery
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}

fun EditText.textChanges(): Flow<CharSequence?> {
    return callbackFlow<CharSequence?> {
        val listener = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                trySend(s)
            }
        }
        addTextChangedListener(listener)
        awaitClose { removeTextChangedListener(listener) }
    }.onStart { emit(text) }
}