package ru.mangotest.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import ru.mangotest.presentation.screen.app.MangoApplication
import ru.mangotest.presentation.screen.auth.navigation.AuthenticationNavigation
import ru.mangotest.presentation.theme.MangoTestTheme
import ru.mangotest.presentation.viewmodel.AuthViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MangoTestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val authState by authViewModel.authState.collectAsState(initial = null)

                    when (authState?.isAuthorized) {
                        true -> {
                            MangoApplication()
                        }
                        false -> {
                            AuthenticationNavigation(authViewModel)
                        }
                        null -> {}
                    }
                }
            }
        }
    }
}