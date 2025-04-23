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
import org.sopt.at.core.common.CommonConstants
import org.sopt.at.models.home.HomeBannerEntity
import org.sopt.at.models.home.HomeTabEntity
import org.sopt.at.models.home.HomeTopAndCurrentEntity
import org.sopt.at.repository.home.HomeDataRepository
import org.sopt.at.views.navigation.Screen
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeDataRepository
) : ViewModel() {
    private val _homeTab = MutableStateFlow(HomeTabEntity(tab = CommonConstants.EMPTY_STRING))
    val homeTab = _homeTab.asStateFlow()

    private val _route = MutableStateFlow(Screen.Home.route)
    val route = _route.asStateFlow()

    fun fetchHomeTab(tab : HomeTabEntity) {
        _homeTab.value = tab
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val homeBannerDataList: StateFlow<List<HomeBannerEntity>> = _homeTab
        .flatMapLatest { tab ->
            flow {
                emit(homeRepository.getHomeBannerDataForTab(tab))
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(1000),
            initialValue = emptyList()
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val tabDataList: StateFlow<List<HomeTabEntity>> = _route
        .flatMapLatest { route ->
            flow {
                emit(homeRepository.getTabList(route))
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(1000),
            initialValue = emptyList()
        )


    fun getHomeTopTwentyItemList(): List<HomeTopAndCurrentEntity> {
        return homeRepository.homeTopSampleDataList
    }

    fun getHomeCurrentContentsItemList(): List<HomeTopAndCurrentEntity> {
        return homeRepository.homeCurrentSampleDataList
    }
}