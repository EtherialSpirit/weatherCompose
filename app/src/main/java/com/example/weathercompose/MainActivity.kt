package com.example.weathercompose

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weathercompose.api.WeatherApi
import com.example.weathercompose.ui.theme.WeatherComposeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val API_KEY = "1febfd481cb0d27c7d7ea9b49148e7e7"
private const val BASE_URL_WEATHER = "https://api.openweathermap.org/data/2.5/"
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Greeting("Android")

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String,) {

    val state = remember {
        mutableStateOf("Unknown")
    }
    Column ( modifier = Modifier
        .fillMaxSize()){
        Box(modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth(),
            contentAlignment = Alignment.Center){
            Text(text = "Temp in $name = ${state.value}")
        }
        Box(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter){
            Button(onClick = {
                //getResult()
            }, modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
            ) {
                Text(text = "Refresh")
            }
        }

    }

}

 fun getResult(city:String, state: MutableState<String>){
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_WEATHER)
        .client(createClient())
        .addConverterFactory(GsonConverterFactory.create()).build()
    val weatherApi = retrofit.create(WeatherApi::class.java)


    }

fun createClient(): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor { chain ->
            val url = chain
                .request()
                .url
                .newBuilder()
                .addQueryParameter("appid", API_KEY)
                .build()
            val request = chain
                .request()
                .newBuilder()
                .url(url)
                .build()
            chain.proceed(request)
        }
        .addInterceptor(HttpLoggingInterceptor().apply{
            level = if(BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        })
        .build()




