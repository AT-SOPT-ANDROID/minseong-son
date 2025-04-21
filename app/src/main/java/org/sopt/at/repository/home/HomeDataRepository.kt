package org.sopt.at.repository.home

import android.util.Log
import org.sopt.at.R
import org.sopt.at.core.common.CommonConstants
import org.sopt.at.models.home.HomeBannerEntity
import org.sopt.at.models.home.HomeTabEntity
import org.sopt.at.models.home.HomeTopAndCurrentEntity
import org.sopt.at.views.home.common.HomeItemTypeDescription
import org.sopt.at.views.home.common.HomeTabData
import org.sopt.at.views.navigation.Route
import javax.inject.Inject

class HomeDataRepository @Inject constructor(): HomeRepository {
    //init 데이터들
    private val homeBannerSampleDataList = listOf(
        HomeBannerEntity(
            id = 0,
            imageUrl = R.drawable.series_recruit1.toString(),
            title = "신병1",
            subtitle = "~~~~~~~~~~~~~~~~"
        ),
        HomeBannerEntity(
            id = 1,
            imageUrl = R.drawable.series_recruit2.toString(),
            title = "신병2",
            subtitle = "~~~~~~~~~~~~~~~~"
        ),
        HomeBannerEntity(
            id = 2,
            imageUrl = R.drawable.series_recruit3.toString(),
            title = "신병3",
            subtitle = "~~~~~~~~~~~~~~~~"
        )
    )

    val homeTopSampleDataList = listOf(
        HomeTopAndCurrentEntity(
            id = 0,
            imageUrl = R.drawable.series_recruit3.toString(),
            title = "신병3",
            subtitle = HomeItemTypeDescription.TYPE_ONLY,
            rank = 1,
            isNewData = true,
        ),
        HomeTopAndCurrentEntity(
            id = 1,
            imageUrl = R.drawable.drama_wise.toString(),
            title = "슬기로운 전공의 생활",
            subtitle = HomeItemTypeDescription.TYPE_ONLY,
            rank = 2,
            isNewData = true,
        ),
        HomeTopAndCurrentEntity(
            id = 2,
            imageUrl = R.drawable.drama_crushology_101.toString(),
            title = "바니와 오빠들",
            subtitle = HomeItemTypeDescription.TYPE_ORIGINAL,
            rank = 3,
            isNewData = false,
        ),
    )

    val homeCurrentSampleDataList = listOf(
        HomeTopAndCurrentEntity(
            id = 0,
            imageUrl = R.drawable.series_recruit3.toString(),
            title = "신병3",
            subtitle = HomeItemTypeDescription.TYPE_ONLY,
            rank = 1,
            isNewData = true,
        ),
        HomeTopAndCurrentEntity(
            id = 1,
            imageUrl = R.drawable.drama_undercover.toString(),
            title = "언더커버 하이스쿨",
            subtitle = CommonConstants.EMPTY_STRING,
            rank = 2,
            isNewData = true,
        ),
        HomeTopAndCurrentEntity(
            id = 2,
            imageUrl = R.drawable.entertainment_25.toString(),
            title = "특파원 25시",
            subtitle = HomeItemTypeDescription.TYPE_ORIGINAL,
            rank = 3,
            isNewData = true,
        )
    )

    val homeTabSampleList = listOf(
        HomeTabEntity (
            tab = "드라마"
        ),
        HomeTabEntity (
            tab = "예능"
        ),
        HomeTabEntity (
            tab = "영화"
        ),
        HomeTabEntity (
            tab = "스포츠"
        ),
        HomeTabEntity (
            tab = "애니"
        ),
        HomeTabEntity (
            tab = "뉴스"
        )
    )

    override fun getHomeTopList(tabEntity: HomeTabEntity) = homeTopSampleDataList

    override fun getHomeCurrentList(tabEntity: HomeTabEntity) = homeCurrentSampleDataList

    override fun getTabList(route: String): List<HomeTabEntity> {
        return when (route) {
            Route.HOME -> {
                homeTabSampleList
            }

            else -> homeTabSampleList
        }
    }

    override fun getHomeBannerDataForTab(tabEntity: HomeTabEntity): List<HomeBannerEntity> {
        return when (tabEntity.tab) {
            HomeTabData.DRAMA -> listOf(
                HomeBannerEntity(
                    0,
                    R.drawable.drama_wise.toString(),
                    "슬기로운 전공의 생활",
                    "설명1"
                ),

                HomeBannerEntity(
                    1,
                    R.drawable.drama_undercover.toString(),
                    "언더커버 하이스쿨",
                    "설명2"
                ),

                HomeBannerEntity(
                    2,
                    R.drawable.drama_crushology_101.toString(),
                    "바니의 오빠들",
                    "설명3"
                ),
            )

            HomeTabData.ENTERTAINMENT -> listOf(
                HomeBannerEntity(
                    0,
                    R.drawable.entertainment_25.toString(),
                    "특파원 25시",
                    "설명1"
                ),
            )

            HomeTabData.MOVIE -> listOf(
                HomeBannerEntity(
                    0,
                    R.drawable.movie_shinchan.toString(),
                    "우리들의 공룡일기",
                    "설명1"
                ),

                HomeBannerEntity(
                    1,
                    R.drawable.movie_miss_fortune.toString(),
                    "화사한 그녀",
                    "설명2"
                ),

                HomeBannerEntity(
                    2,
                    R.drawable.movie_pororo.toString(),
                    "바닷속 대모험",
                    "설명3"
                ),
            )

            HomeTabData.SPORTS -> listOf(
                HomeBannerEntity(
                    0,
                    R.drawable.sports_baseball.toString(),
                    "베이스볼",
                    "설명1"
                )
            )

            HomeTabData.ANI -> listOf(
                HomeBannerEntity(
                    0,
                    R.drawable.ani_giant.toString(),
                    "진격의 거인",
                    "설명1"
                )
            )

            HomeTabData.NEWS -> listOf(
                HomeBannerEntity(
                    0,
                    R.drawable.news.toString(),
                    "",
                    "설명1"
                )
            )

            else -> homeBannerSampleDataList
        }
    }
}