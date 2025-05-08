package org.sopt.at.views.search.components.top

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import org.sopt.at.core.common.CommonConstants
import org.sopt.at.views.search.components.edittext.SearchEditText

@Composable
fun SearchTopBar(
    searchKeyword: String,
    focusRequester: FocusRequester,
    onRecentQuery: (Boolean) -> Unit,
    onTextChange: (String) -> Unit,
    onClear: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        SearchEditText(
            searchValue = searchKeyword,
            focusRequester = focusRequester,
            onTextChange = {
                val query = it.trim()
                onTextChange(query)
            },
            onDone = {
                onRecentQuery(it)
            },
            onClear = {
                onRecentQuery(true)
                onClear(CommonConstants.EMPTY_STRING)
            }
        )
    }
}