package com.martynov.service


import com.martynov.dto.MediaResponseDto
import com.martynov.model.MediaType
import io.ktor.features.BadRequestException
import io.ktor.features.UnsupportedMediaTypeException
import io.ktor.http.ContentType
import io.ktor.http.content.MultiPartData
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.http.content.streamProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

class FileService(private val uploadPath: String) {
    private val images = listOf(ContentType.Image.JPEG, ContentType.Image.PNG)

    init {
        println(Paths.get(uploadPath).toAbsolutePath().toString())
        if (Files.notExists(Paths.get(uploadPath))) {
            Files.createDirectory(Paths.get(uploadPath))
        }
    }

    suspend fun save(multipart: MultiPartData): MediaResponseDto {
        var response: MediaResponseDto? = null
        multipart.forEachPart { part ->
            when (part) {
                is PartData.FileItem -> {
                    if (part.name == "file") {
                        // TODO: use Apache Tika for content detection
                        if (!images.contains(part.contentType)) {
                            throw UnsupportedMediaTypeException(part.contentType ?: ContentType.Any)
                        }
                        val ext = when (part.contentType) {
                            ContentType.Image.JPEG -> "jpg"
                            ContentType.Image.PNG -> "png"
                            else -> throw UnsupportedMediaTypeException(part.contentType!!)
                        }
                        val name = "${UUID.randomUUID()}.$ext"
                        val path = Paths.get(uploadPath, name)
                        part.streamProvider().use {
                            withContext(Dispatchers.IO) {
                                Files.copy(it, path)
                            }
                        }
                        part.dispose()
                        response = MediaResponseDto(name, MediaType.IMAGE)
                        return@forEachPart
                    }
                }
            }

            part.dispose()
        }
        return response ?: throw BadRequestException("No file field in request")
    }
}

