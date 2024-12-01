package io.github.thegbguy.imagescraper

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import imagescraper.composeapp.generated.resources.Res
import imagescraper.composeapp.generated.resources.compose_multiplatform
import imagescraper.composeapp.generated.resources.placeholder
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@Composable
fun ImagesScraperApp() {
    val scope = rememberCoroutineScope()
    var urlTxtFieldValue by remember {
        mutableStateOf(TextFieldValue("https://www.pexels.com/search/nature/"))
    }
    var imageUrls by remember {
        mutableStateOf<List<String>>(emptyList())
    }
    var loading by remember { mutableStateOf<Boolean?>(null) }

    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = urlTxtFieldValue,
                onValueChange = {
                    urlTxtFieldValue = it
                }
            )

            Button(
                onClick = {
                    scope.launch {
                        loading = true
                        imageUrls = scrapeImages(urlTxtFieldValue.text)
                        loading = false
                    }
                }
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Scrape it")
                        if (loading == true) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = Color.White
                            )
                        }
                    }
                }
            }

            if (loading == false) {
                Text(
                    "${imageUrls.size} images found",
                    style = MaterialTheme.typography.subtitle2
                )
            }

            LazyVerticalStaggeredGrid(
                modifier = Modifier.fillMaxWidth(),
                columns = StaggeredGridCells.Adaptive(100.dp),
                verticalItemSpacing = 8.dp,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                content = {
                    items(imageUrls) { url ->
                        AsyncImage(
                            modifier = Modifier.fillMaxWidth(),
                            model = url,
                            placeholder = painterResource(Res.drawable.placeholder),
                            contentScale = ContentScale.Crop,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    }
}