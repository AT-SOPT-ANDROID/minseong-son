package org.sopt.at.repository.home

import org.sopt.at.models.home.HomeBannerEntity
import org.sopt.at.models.home.HomeTabEntity
import org.sopt.at.models.home.HomeTopAndCurrentEntity

interface HomeRepository {
    fun getHomeTopList(tabEntity: HomeTabEntity): List<HomeTopAndCurrentEntity>
    fun getHomeCurrentList(tabEntity: HomeTabEntity): List<HomeTopAndCurrentEntity>
    fun getTabList(route: String): List<HomeTabEntity>
    fun getHomeBannerDataForTab(tabEntity: HomeTabEntity): List<HomeBannerEntity>
}
