package com.ken.taipeitourtestproject.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ken.taipeitourtestproject.screen.home.data.LanguageType
import com.ken.taipeitourtestproject.tools.sharedpreference.MySharedPreferencesManager
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val sharedPreferencesManager: MySharedPreferencesManager
): ViewModel() {

    private val _changeLanguageSharedFlow = MutableSharedFlow<Unit>()
    val changeLanguageSharedFlow = _changeLanguageSharedFlow.asSharedFlow()

    fun onChangeLanguage(type: LanguageType) {
        viewModelScope.launch {
            if (type.showText != sharedPreferencesManager.languageTypeTag) {
                sharedPreferencesManager.languageTypeTag = type.showText
                _changeLanguageSharedFlow.emit(Unit)
            }
        }
    }
}