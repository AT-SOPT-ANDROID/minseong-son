package org.sopt.at.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
) : ViewModel() {
    private val _dialogState = MutableStateFlow(false)
    val dialogState: StateFlow<Boolean> = _dialogState.asStateFlow()

    fun openDialog() {
        _dialogState.value = true
    }

    fun closeDialog() {
        _dialogState.value = false
    }
}