package com.example.photopicker

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.photopicker.ui.theme.PhotoPickerTheme

class SubActivity : ComponentActivity() {
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
        Column(modifier = Modifier.fillMaxSize()) {
            var selectedUriList: List<Uri?> by remember { mutableStateOf(emptyList()) }
            val pickMultipleMedia =
                rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(5)) { uris ->
                    selectedUriList = uris
                }
            var imageNumber by remember { mutableStateOf(0) }
            Spacer(modifier = Modifier.height(160.dp))
            // 가운데 이미지
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (selectedUriList.isNotEmpty() && selectedUriList.size < 3) {
                    Image(
                        painter = rememberAsyncImagePainter(model = selectedUriList[0]),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(300.dp),
                        contentDescription = null
                    )
                } else if (selectedUriList.size > 2) {
                    Image(
                        painter = rememberAsyncImagePainter(model = selectedUriList[imageNumber + 1]),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(300.dp),
                        contentDescription = null
                    )
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.weight(0.5f)) {
                    Text(text = "이전")
                }
                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.weight(0.5f)) {
                    Text(text = "다음")
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // 왼쪽 이미지
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.weight(0.5f)
                ) {
                    if (selectedUriList.size > 2 && imageNumber >= 0) {
                        Image(
                            painter = rememberAsyncImagePainter(model = selectedUriList[imageNumber]),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(100.dp)
                                .clickable { imageNumber-- },
                            contentDescription = null
                        )
                    }
                }
                // 오른쪽 이미지
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.weight(0.5f)
                ) {
                    if (selectedUriList.size == 2) {
                        Image(
                            painter = rememberAsyncImagePainter(model = selectedUriList[1]),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(100.dp),
                            contentDescription = null
                        )
                    } else if (selectedUriList.size > 2 && imageNumber + 2 < selectedUriList.size) {
                        Image(
                            painter = rememberAsyncImagePainter(model = selectedUriList[imageNumber + 2]),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(100.dp)
                                .clickable { imageNumber++ },
                            contentDescription = null
                        )
                    }
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth(1f)
            ) {
                Spacer(modifier = Modifier.height(100.dp))
                Button(onClick = {
                    pickMultipleMedia.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageAndVideo
                        )
                    )
                }) {
                    Text(text = "선택", modifier = Modifier.padding(horizontal = 30.dp))
                }
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