package org.sopt.at.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import org.sopt.at.views.history.tab.HistoryTabData
import org.sopt.at.core.common.CommonConstants
import org.sopt.at.models.history.HistoryEmptyEntity
import org.sopt.at.models.history.HistoryEntity
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
): ViewModel() {
    private val historyEmptyDataMap = mapOf(
        HistoryTabData.TAB_VIEW to HistoryEmptyEntity(
            id = 0,
            title = "시청 내역이 \n없어요",
            description = "재미있는 시리즈를 시청해보세요. \n지금 볼 만한 시리즈를 추천해드릴까요?"
        ),
        HistoryTabData.TAB_PURCHASE to HistoryEmptyEntity(
            id = 1,
            title = "구매 내역이 \n없어요",
            description = "재미있는 콘텐츠를 추천해드릴게요. \n오늘 영화 한 편 보는 건 어떠세요?"
        ),
        HistoryTabData.TAB_SERIES to HistoryEmptyEntity(
            id = 2,
            title = "찜한 시리즈가 없어요",
            description = "좋아하는 시리즈를 찜하면\n시리즈 소식을 알려드려요."
        ),
        HistoryTabData.TAB_MOVIES to HistoryEmptyEntity(
            id = 3,
            title = "찜한 영화가 \n없어요",
            description = "보고 싶은 영화를 찜하여 \n잊지말고 시청하세요!"
        )
    )

    fun getHistoryEmptyData(): Map<String, HistoryEmptyEntity> {
        return historyEmptyDataMap
    }

    private val _historyTab = MutableStateFlow(HistoryTabData.TAB_VIEW)
    val historyTab = _historyTab.asStateFlow()

    fun fetchHistoryTab(tab : String) {
        _historyTab.value = tab
    }

    private val _historyDataList = MutableStateFlow(emptyList<HistoryEntity>())
    val historyDataList = _historyDataList.asStateFlow()

    private val _historyDeleteData = MutableStateFlow<HistoryEntity?>(null)
    val historyDeleteData = _historyDeleteData.asStateFlow()

    fun fetchHistoryData(historyEntity: HistoryEntity) {
        _historyDeleteData.value = historyEntity
    }

    private val _historyTitle = MutableStateFlow<String?>(null)
    val historyTitle = _historyTitle.asStateFlow()

    private val _historyDialogState = MutableStateFlow(Pair(false, CommonConstants.EMPTY_STRING))
    val historyDialogState = _historyDialogState.asStateFlow()

    fun openHistoryDialog(dialogType: String) {
        _historyDialogState.value = _historyDialogState.value.copy(
            first = true,
            second = dialogType
        )
    }

    fun closeHistoryDialog(dialogType: String) {
        _historyDialogState.value = _historyDialogState.value.copy(
            first = false,
            second = dialogType
        )
    }

    fun setHistoryTitle(title: String?) {
        _historyTitle.value = title
    }

    fun clearHistoryTitle() {
        _historyTitle.value = null
    }

    fun addHistoryData(historyEntity: HistoryEntity) {
        _historyDataList.value = _historyDataList.value.toMutableList().apply {
            add(historyEntity)
        }
    }

    fun deleteHistoryData(historyEntity: HistoryEntity) {
        _historyDataList.value = _historyDataList.value.toMutableList().apply {
            remove(historyEntity)
        }
    }
}