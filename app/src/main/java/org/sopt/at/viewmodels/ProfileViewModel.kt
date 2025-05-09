package org.sopt.at.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.at.core.common.CommonConstants
import org.sopt.at.models.dto.request.NicknameRequestDto
import org.sopt.at.models.history.DialogState
import org.sopt.at.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _profileNickname = MutableStateFlow<String?>(null)
    val profileNickname : StateFlow<String?> = _profileNickname.asStateFlow()

    private val _profileChangedNickname = MutableStateFlow<String?>(null)
    val profileChangedNickname : StateFlow<String?> = _profileChangedNickname.asStateFlow()


    fun onTextChangedProfileNickname(newText : String) {
        _profileChangedNickname.value = newText
    }

    private val _patchNicknameMessage = MutableStateFlow<String?>(null)
    val patchNicknameMessage : StateFlow<String?> = _patchNicknameMessage.asStateFlow()

    private val _nicknameDialogState = MutableStateFlow(DialogState())
    val nicknameDialogState = _nicknameDialogState.asStateFlow()

    fun openDialog(dialogType: String) {
        _nicknameDialogState.value = DialogState(true, dialogType)
    }

    fun closeDialog() {
        _nicknameDialogState.value = DialogState(false, CommonConstants.EMPTY_STRING)
    }

    fun getProfileNickname(userId: Long) {
        viewModelScope.launch {
            val nickname = userRepository.getNickname(userId)
            _profileNickname.value = nickname
        }
    }

    fun patchNickname(userId: Long, nickname: String) {
        viewModelScope.launch {
            val newNickname = NicknameRequestDto(nickname)
            val result = userRepository.patchNickname(userId, newNickname)
            _patchNicknameMessage.value = result
            getProfileNickname(userId)
        }
    }
}