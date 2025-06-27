package com.deepak.backgroundoperations.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepak.backgroundoperations.model.PhoneTypesResponse
import com.deepak.backgroundoperations.networkServices.PhoneTypesAPI
import kotlinx.coroutines.launch

class PhoneNameViewModel : ViewModel() {

    companion object {
         val TAG: String = Companion::class.java.toString()
    }

    var phoneNameListResponse: List<PhoneTypesResponse> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")

    fun getPhoneTypeList() {
        viewModelScope.launch {
            Log.i(
                TAG, "Launching Dispatchers IO in:" +
                        " ${Thread.currentThread().name}"
            )
            val response = PhoneTypesAPI().getPhoneTypes()
            phoneNameListResponse = response.body() as ArrayList<PhoneTypesResponse>

            if (response.isSuccessful) {
                Log.i(TAG, "Coroutine scope successful")
                for (phoneType in response.body()!!) {
                    Log.i(TAG, phoneType.name)
                }
            } else {
                Log.i(TAG, "Coroutine scope Failed")
            }
        }
    }
}