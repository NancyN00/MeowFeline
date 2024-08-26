package com.example.mewfeline.screen.screensModel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.mewfeline.data.MewFelineService
import com.example.mewfeline.models.CatResponse
import com.example.mewfeline.repository.MewFelineRepository
import com.example.mewfeline.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FelineListModel : ScreenModel {

    private val repository =
        MewFelineRepository(
            mewFelineService = MewFelineService.getClient(),
        )

    private val _felineListScreenState =
        MutableStateFlow<FelineListScreenState>(FelineListScreenState.Loading)
    val felineListScreenState = _felineListScreenState.asStateFlow()

  init {
      getFeline()
  }

    private fun getFeline() {
        screenModelScope.launch {
            repository.getAllFeline().collectLatest { result ->
                when(result){
                    is Resource.Error -> {
                        _felineListScreenState.value =
                            FelineListScreenState.Error(result.message ?: "Unknown error occured")
                    }
                    is Resource.Success -> {
                        _felineListScreenState.value =
                        FelineListScreenState.Success(result.data ?: emptyList())
                    }
                }

            }
        }
    }
}

sealed interface FelineListScreenState {
    data object Loading : FelineListScreenState

    data class Success(val feline: List<CatResponse>) : FelineListScreenState

    data class Error(val message: String) : FelineListScreenState
}