package com.example.mewfeline.screen.screensModel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.mewfeline.data.MewFelineService
import com.example.mewfeline.models.catDetails.CatDetailResponse
import com.example.mewfeline.repository.MewFelineRepository
import com.example.mewfeline.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FelineDetailsModel : ScreenModel {
    private val repository =
        MewFelineRepository(
            mewFelineService = MewFelineService.getClient(),
        )

    private val _mewDetailScreenState =
        MutableStateFlow<FelineDetailScreenState>(FelineDetailScreenState.Loading)
    val mewFelineDetailsScreenState = _mewDetailScreenState.asStateFlow()

    fun getFelineDetails(imageId: String) {

        screenModelScope.launch {
            repository.getFelineDetails(imageId)
                .collectLatest { result ->
                    when (result) {
                        is Resource.Error -> {
                            _mewDetailScreenState.value = FelineDetailScreenState.Error(result.message ?: "Unknown error occurred")
                        }
                        is Resource.Success -> {
                            _mewDetailScreenState.value = FelineDetailScreenState.Success(result.data)

                        }
                    }
                }

        }

    }

}

sealed interface FelineDetailScreenState {
    data object Loading : FelineDetailScreenState

    data class Success(val cats: CatDetailResponse?) : FelineDetailScreenState

    data class Error(val message: String) : FelineDetailScreenState
}