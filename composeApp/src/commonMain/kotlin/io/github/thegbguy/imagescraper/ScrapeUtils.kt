package io.github.thegbguy.imagescraper

import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.Platform
import com.fleeksoft.ksoup.PlatformType
import com.fleeksoft.ksoup.network.parseGetRequest
import com.fleeksoft.ksoup.select.Evaluator

suspend fun scrapeImages(url: String): List<String> {
    return Ksoup.parseGetRequest(url)
        .select(Evaluator.Tag("img"))
        .map { element ->
            val src = element.attr("src")
            if (Platform.current == PlatformType.WASM_JS || Platform.current == PlatformType.JS) {
                "${url.removeSuffix("/")}${src}"
            } else src
        }
}