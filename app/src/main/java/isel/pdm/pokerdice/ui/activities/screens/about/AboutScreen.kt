package isel.pdm.pokerdice.ui.activities.screens.about

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.ui.components.card.PageIndicator
import isel.pdm.pokerdice.ui.components.card.SimpleCard
import isel.pdm.pokerdice.ui.layouts.screens.DefaultBackScreen
import isel.pdm.pokerdice.ui.viewmodels.about.AboutState

@Composable
fun AboutScreen(
    state: AboutState,
    onBackClick: () -> Unit,
    onMailClick: (List<String>, String) -> Unit,
    onWebRequest: (String) -> Unit,
    ) {
    val pagerState = rememberPagerState(pageCount = {state.pageCount})
    DefaultBackScreen(
        title = stringResource(R.string.about_title),
        onClick=onBackClick,
        content = {
            SimpleCard {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { pageIdx ->
                    when (pageIdx) {
                        0 -> ProjectPage(onMailClick)
                        1 -> GameplayPage(onWebRequest)
                    }
                }
            }
            PageIndicator(state.pageCount, pagerState)
        }
    )
}