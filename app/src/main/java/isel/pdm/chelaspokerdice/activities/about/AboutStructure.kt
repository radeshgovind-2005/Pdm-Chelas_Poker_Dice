package isel.pdm.chelaspokerdice.activities.about

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import isel.pdm.chelaspokerdice.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutStructure(
    code: @Composable () -> Unit,
){
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "About",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        color = Color.White
                    )
                },
                actions ={
                    IconButton(onClick = { }) {
                        Icon(
                            Icons.Rounded.Home,
                            contentDescription = "Home",
                            tint = Color.White,
                            modifier = Modifier.size(35.dp)
                        )
                    }
                },
                colors = TopAppBarColors(
                    Color.Black,
                    Color.Blue,
                    Color.Red,
                    Color.White,
                    Color.Black
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
            ,
            contentAlignment = Alignment.Center
        ){
            Image(
                painter = painterResource(R.drawable.simple_background),
                contentDescription = "Background",
                modifier = Modifier
                    .fillMaxHeight(),
                contentScale = ContentScale.Crop
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AboutContent()
                code()
            }

        }
    }
}
@PreviewScreenSizes
@Composable
private fun Preview() {
    val map = mapOf(
        { } to R.string.title_screen,
    )
    AboutScreen(map).Render(Modifier)
}







@Composable
fun AboutContent() {
    val pagerState = rememberPagerState(pageCount = { 2 })
    val tabTitles = listOf("Rules", "Creators")
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            TabRow(selectedTabIndex = pagerState.currentPage) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = { /* Handled by pager */ },
                        text = { Text(title) }
                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp) // Adjust height as needed
            ) { page ->
                when (page) {
                    0 -> RulesPage(context)
                    1 -> CreatorsPage(context)
                }
            }
        }
    }
}

@Composable
fun RulesPage(context: android.content.Context) {
    val scrollState = rememberScrollState()
    val videoUrl = "https://www.youtube.com/watch?v=9InZ9eEnXzs"
    val annotatedString = buildAnnotatedString {
        append("Poker Dice is played with five special dice marked with Ace, King, Queen, Jack, 10, and 9. The objective is to form the best poker hand possible.\n\n")
        append("Rules:\n")
        append("1. Each player takes turns rolling the five dice up to three times.\n")
        append("2. After each roll, you can choose to keep some dice and re-roll others.\n")
        append("3. The poker hands rank as follows: Five of a kind, Four of a kind, Full house, Straight, Three of a kind, Two pairs, One pair.\n\n")
        append("Watch this video for a tutorial: ")
        pushStringAnnotation(tag = "URL", annotation = videoUrl)
        withStyle(style = SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline)) {
            append("Poker Dice Tutorial")
        }
        pop()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.Start
    ) {
        ClickableText(
            text = annotatedString,
            onClick = { offset ->
                annotatedString.getStringAnnotations(tag = "URL", start = offset, end = offset)
                    .firstOrNull()?.let { annotation ->
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(annotation.item))
                        context.startActivity(intent)
                    }
            }
        )
    }
}

@Composable
fun CreatorsPage(context: android.content.Context) {
    val scrollState = rememberScrollState()
    val emails = "creator1@example.com,creator2@example.com,creator3@example.com" // Replace with actual emails
    val annotatedString = buildAnnotatedString {
        append("This app was created by:\n\n")
        append("Creator 1: Tubarão\n")
        append("Creator 2: Gay do Chico\n")
        append("Creator 3: Boiola do martim\n\n")
        append("Contact us at: ")
        pushStringAnnotation(tag = "EMAIL", annotation = "mailto:$emails")
        withStyle(style = SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline)) {
            append("Send Email")
        }
        pop()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.Start
    ) {
        ClickableText(
            text = annotatedString,
            onClick = { offset ->
                annotatedString.getStringAnnotations(tag = "EMAIL", start = offset, end = offset)
                    .firstOrNull()?.let { annotation ->
                        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse(annotation.item))
                        context.startActivity(intent)
                    }
            }
        )
    }
}
