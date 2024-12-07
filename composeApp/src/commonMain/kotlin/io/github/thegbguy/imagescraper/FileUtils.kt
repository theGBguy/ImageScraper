package io.github.thegbguy.imagescraper

import co.touchlab.kermit.Logger
import io.github.vinceglb.filekit.core.FileKit
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.readRawBytes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.invoke
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
suspend fun saveImages(client: HttpClient, urls: List<String>) =
    (Dispatchers.Default) {
        urls.forEach { url ->
            Logger.d { "Saving image $url" }
            val fullName = url.split("/").lastOrNull()
            val baseName = fullName?.split(".")?.firstOrNull() ?: "image-${Uuid.random()}"
            val extension = fullName?.split(".")?.lastOrNull() ?: "jpg"
            val imageByteArray = client.get(url).readRawBytes()

            FileKit.saveFile(
                bytes = imageByteArray,
                initialDirectory = null,
                baseName = baseName,
                extension = extension
            )
        }
    }

