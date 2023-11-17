package gomoku.variant.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import gomoku.LoadState
import gomoku.idle
import gomoku.loaded
import gomoku.loading
import gomoku.variant.VariantService
import gomoku.variant.domain.VariantConfig
import kotlinx.coroutines.launch

class VariantScreenViewModel(
    private val service: VariantService
) : ViewModel() {

    companion object {
        fun factory(service: VariantService) = viewModelFactory {
            initializer { VariantScreenViewModel(service) }
        }
    }

    var variants by mutableStateOf<LoadState<List<VariantConfig>>>(idle())
        private set

    fun fetchVariants() {
        viewModelScope.launch {
            Log.v("ViewModelVariants", "fetching variants in view model...")
            variants = loading()
            val result: Result<List<VariantConfig>> = runCatching { service.fetchVariants() }
            Log.v("ViewModelVariants", "fetched variants in view model")
            variants = loaded(result)
        }
    }

}
