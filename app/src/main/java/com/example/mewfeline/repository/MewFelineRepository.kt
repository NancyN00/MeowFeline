package com.example.mewfeline.repository

import com.example.mewfeline.data.MewFelineService
import com.example.mewfeline.models.CatResponse
import com.example.mewfeline.models.catDetails.CatDetailResponse
import com.example.mewfeline.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MewFelineRepository(
    private val mewFelineService: MewFelineService,
) {

    fun getAllFeline() : Flow<Resource<List<CatResponse>>> =
        flow {
            try {
                val feline = mewFelineService.getFeline()
               emit(Resource.Success(feline))
            }catch (e: Exception){
                emit(Resource.Error(e.localizedMessage))
            }
        }

    fun getFelineDetails(imageId : String) : Flow<Resource<CatDetailResponse>> =

        flow {
            try {
                val felineDetails = mewFelineService.getFelineDetails(imageId)
                emit(Resource.Success(felineDetails))
            }catch (e:Exception) {
                emit(Resource.Error(e.localizedMessage))
            }

        }
    }

