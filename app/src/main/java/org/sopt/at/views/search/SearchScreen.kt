package org.sopt.at.views.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.sopt.at.viewmodels.SearchViewModel
import org.sopt.at.views.search.components.SearchLazyColumn
import org.sopt.at.views.search.components.top.SearchTopBar

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val searchDataList by searchViewModel.searchUserList.collectAsState()
    val searchKeyword by searchViewModel.searchKeyword.collectAsState()

    //false가 검색창으로 변경, true = 최근검색어창
    var isRecentQuery by remember {
        mutableStateOf(true)
    }

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        SearchTopBar(
            searchKeyword = searchKeyword,
            focusRequester = focusRequester,
            onRecentQuery = {
                isRecentQuery = it
            },
            onTextChange = {
                searchViewModel.onTextChange(it)
            },
            onClear = {
                searchViewModel.onTextChange(it)
            }
        )

        SearchLazyColumn(
            searchDataList = searchDataList
        )
    }
}

