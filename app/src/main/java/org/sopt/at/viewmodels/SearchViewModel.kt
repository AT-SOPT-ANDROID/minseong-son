package org.sopt.at.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import org.sopt.at.core.common.CommonConstants
import org.sopt.at.core.common.CommonConstants.SEARCH_TIME_DELAY
import org.sopt.at.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: UserRepository
) : ViewModel() {
    private val _searchKeyword = MutableStateFlow(CommonConstants.EMPTY_STRING)
    val searchKeyword: StateFlow<String> = _searchKeyword.asStateFlow()

    private val _searchUserList = MutableStateFlow<List<String>>(emptyList())
    val searchUserList: StateFlow<List<String>> = _searchUserList.asStateFlow()

    fun onTextChange(text: String) {
        _searchKeyword.value = text
    }

    private fun getNicknameList(keyword: String) {
        viewModelScope.launch {
            val nicknameList = searchRepository.getNicknameList(keyword)
            if (nicknameList != null) {
                _searchUserList.value = nicknameList
            }
        }
    }

    //자동 검색 기능 DebouncedSearch
    @OptIn(FlowPreview::class)
    val debouncedSearchQuery: Flow<String?> by lazy {
        searchKeyword
            .debounce(SEARCH_TIME_DELAY)
            .filter { it.isNotEmpty() }
            .distinctUntilChanged() //이전과 같은 입력은 무시하여 중복요청을 방지하도록
    }

    private fun observeDebouncedSearch() {
        viewModelScope.launch {
            debouncedSearchQuery.collect { keyword ->
                keyword?.let {
                    getNicknameList(it)
                }
            }
        }
    }

    init {
        observeDebouncedSearch()
    }
}