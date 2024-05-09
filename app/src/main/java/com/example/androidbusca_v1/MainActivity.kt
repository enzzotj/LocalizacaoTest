package com.example.androidbusca_v1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
    val petShops = petShopViewModel.listPetShop.observeAsState().value!!
    DisposableEffect(petShops) {
        petShopViewModel.getPetShop()
        onDispose { }
    }
    LazyColumn {
        items(items = petShops.toList()){
            Row {
                Text(text = it.distance.toString())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidBusca_v1Theme {
        Tela(PetShopViewModel())
    }
}