package com.example.mewfeline.screen.felineDetailScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mewfeline.models.catDetails.CatDetailResponse
import com.example.mewfeline.screen.components.ErrorScreen
import com.example.mewfeline.screen.components.LoadingScreen
import com.example.mewfeline.screen.screensModel.FelineDetailScreenState
import com.example.mewfeline.screen.screensModel.FelineDetailsModel

data class FelineDetailScreen(val imageId: String) : Screen{
    @Composable
    override fun Content() {

        val felineDetailModel = rememberScreenModel {
            FelineDetailsModel()
        }

        val navigator = LocalNavigator.currentOrThrow
        FelineDetailsScreen(
            screenModel = felineDetailModel,
            imageId = imageId,
            navigator = navigator
        )

    }

}

@Composable
fun FelineDetailsScreen(
    screenModel : FelineDetailsModel,
    imageId: String,
    navigator: Navigator
){
    LaunchedEffect(
        key1 = Unit,
        block = {
            screenModel.getFelineDetails(imageId)
        },
    )

    when(val state = screenModel.mewFelineDetailsScreenState.collectAsState().value){
        is FelineDetailScreenState.Error -> {
            ErrorScreen(errorMessage = state.message)

        }
        FelineDetailScreenState.Loading -> {
            LoadingScreen()

        }
        is FelineDetailScreenState.Success -> {

            FelineDetailScreenContent(cat = state.cats!!,
                onBackPressed = {
                    navigator.pop()
                }
                )
        }
    }
}

@Composable
fun FelineDetailScreenContent(
    modifier: Modifier = Modifier,
    cat: CatDetailResponse,
    onBackPressed: () -> Unit,
) {
    val context = LocalContext.current
    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) { paddingValues ->
        val catInfo = cat.breeds[0]
        Column(
            modifier =
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xFF7D7D7D)),
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                AsyncImage(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(392.dp)
                        .align(Alignment.TopCenter),
                    model =
                    ImageRequest.Builder(context)
                        .data(cat.url)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
                FloatingActionButton(
                    shape = CircleShape,
                    containerColor = Color(0xFF7D7D7D),
                    modifier =
                    Modifier.align(Alignment.TopStart)
                        .padding(20.dp),
                    onClick = onBackPressed,
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "navigate back",
                        tint = Color.White,
                    )
                }
                Surface(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(35.dp)
                        .align(Alignment.BottomCenter),
                    color = Color(0xFF7D7D7D),
                    shape =
                    RoundedCornerShape(
                        topStart = 20.dp,
                        topEnd = 20.dp,
                    ),
                ) {}
            }
            Column(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
            ) {
                Text(
                    text = catInfo.name,
                    fontSize = 32.sp,
                    color = Color.White,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Origin: " + catInfo.origin,
                    fontSize = 12.sp,
                    color = Color.White,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = catInfo.temperament,
                    fontSize = 12.sp,
                    color = Color.White,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = catInfo.description,
                    fontSize = 12.sp,
                    color = Color.White,
                )
            }
        }
    }
}

