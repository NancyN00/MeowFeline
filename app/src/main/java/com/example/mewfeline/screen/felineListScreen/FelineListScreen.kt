package com.example.mewfeline.screen.felineListScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.mewfeline.models.CatResponse
import com.example.mewfeline.screen.components.CatImageCard
import com.example.mewfeline.screen.felineDetailScreen.FelineDetailScreen
import com.example.mewfeline.screen.screensModel.FelineListModel
import com.example.mewfeline.screen.screensModel.FelineListScreenState

class FelineListScreen : Screen {
    @Composable
    override fun Content() {
        val felineScreenModel = rememberScreenModel {
            FelineListModel()
        }

        val navigator = LocalNavigator.currentOrThrow
        CatScreenList(
            screenModel = felineScreenModel,
            navigator = navigator
        )
    }
}

@Composable
fun CatScreenList(
    screenModel: FelineListModel,
    navigator: Navigator,
) {

    when (val state = screenModel.felineListScreenState.collectAsState().value){
        is FelineListScreenState.Error -> {
            ErrorScreen(errorMessage = state.message)
        }

        FelineListScreenState.Loading -> {
            LoadingScreen()
        }
        is FelineListScreenState.Success -> {
            FelineListScreenContent(
                felines = state.feline,
                onFelineClicked = { imageId ->
                     navigator.push(FelineDetailScreen(imageId))
                }
            )


        }
    }
}


@Composable
fun FelineListScreenContent(
    modifier: Modifier = Modifier,
    felines: List<CatResponse>,
    onFelineClicked: (String) -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                content = {
                    item {
                        Column(
                            modifier = Modifier.padding(top = 22.dp, start = 20.dp),
                        ) {
                            Text(
                                text = "MewFeline",
                                fontSize = 36.sp,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }
                    }
                    items(felines) {
                        CatImageCard(
                            url = it.url,
                            onClick = {
                                onFelineClicked.invoke(it.id)
                            },
                        )
                    }
                },
            )
        }
    }
}


@Composable
private fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorScreen(
    modifier: Modifier = Modifier,
    errorMessage: String,
) {
    Box(
        modifier = modifier.fillMaxSize()
            .padding(start = 8.dp, end = 8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = errorMessage)
    }
}


