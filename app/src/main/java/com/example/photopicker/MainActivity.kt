package com.example.photopicker

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.photopicker.ui.theme.PhotoPickerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PhotoPickerTheme {
                MainScreen()
            }
        }
    }

    @Composable
    fun MainScreen() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
        ) {
            var imageId by remember {
                mutableStateOf(R.drawable.ic_android_black_24dp)
            }
            var imageUri by remember {
                mutableStateOf<Uri?>(null)
            }
            var selectedUriList: List<Uri?> by remember {
                mutableStateOf(emptyList())
            }
            var clicked by remember {
                mutableStateOf(true)
            }
            // save
            val pickMedia =
                rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                    imageUri = uri
                }
            val pickMultipleMedia =
                rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(5)) { uris ->
                    selectedUriList = uris
                }

            if (clicked) {


                SubcomposeAsyncImage(
                    model = "https://newsimg-hams.hankookilbo.com/2023/11/23/f5f38ca5-8ed4-49b2-b64c-9943cccfc55c.jpg",
                    contentDescription = null,
                    loading = {
                        CircularProgressIndicator()
                    },
                    modifier = Modifier
                        .size(500.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .border(
                            BorderStroke(4.dp, Color.Yellow),
                            RoundedCornerShape(15.dp)
                        ),
                    contentScale = ContentScale.FillHeight
                )


            }
            if (!clicked) {
                LazyColumn {
                    items(selectedUriList) { uri ->
                        Image(
                            painter = rememberAsyncImagePainter(uri),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,

                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(15.dp))
                                .border(
                                    BorderStroke(4.dp, Color.Yellow),
                                    RoundedCornerShape(15.dp)
                                ),
                            colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply {
                                setToSaturation(0f)
                            })
                        )
                    }
                }
            }
            Button(
                onClick = {
                    clicked = !clicked
                    if (!clicked) {
                        pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
                    }
//                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
//                    if (imageId == R.drawable.ic_android_black_24dp) {
//                        imageId = R.drawable.ic_launcher_foreground
//                    } else {
//                        imageId = R.drawable.ic_android_black_24dp
//                    }
                },
                shape = RectangleShape,

                modifier = Modifier
                    .padding(bottom = 40.dp)
            ) {
                Text(
                    text = "변경",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(horizontal = 30.dp)
                )
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun MainScreenPreview() {
        PhotoPickerTheme {
            MainScreen()
        }
    }
}


