package io.github.thegbguy.imagescraper

import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.network.parseGetRequest
import com.fleeksoft.ksoup.select.Evaluator

suspend fun scrapeImages(url: String): List<String> {
    return Ksoup.parseGetRequest(url)
        .select(Evaluator.Tag("img"))
        .map { element ->
            element.attr("src")
        }
}