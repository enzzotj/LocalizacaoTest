package com.example.androidbusca_v1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidbusca_v1.ui.theme.AndroidBusca_v1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidBusca_v1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Tela(PetShopViewModel())
                }
            }
        }
    }
}

@Composable
fun Tela(petShopViewModel: PetShopViewModel = PetShopViewModel(), modifier: Modifier = Modifier) {
    val test = petShopViewModel.localizacao.observeAsState().value?.location
    val tt = petShopViewModel.erroApi.observeAsState().value!!
    Column {
        Text(text = test?.lng.toString())
        Text(text = tt)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidBusca_v1Theme {
        Tela(PetShopViewModel())
    }
}