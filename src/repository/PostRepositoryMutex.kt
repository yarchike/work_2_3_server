package com.martynov.repository

import com.google.gson.Gson
import com.martynov.PostData
import com.martynov.exception.ActionProhibitedException
import com.martynov.model.PostModel
import com.martynov.model.PostTypes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.File
import kotlin.coroutines.EmptyCoroutineContext

class PostRepositoryMutex : PostRepository {

    private var nextId = 1L
    private val items = PostData.getDataBase()
    private val mutex = Mutex()

    override suspend fun getAll(): List<PostModel> =
            mutex.withLock {
                for (post in items) {
                    val index = items.indexOf(post)
                    val copy = post.copy(viewsPost = post.viewsPost + 1)
                    items[index] = copy

                }
                items.reversed()
            }

    override suspend fun getById(id: Long): PostModel? =
            mutex.withLock {
                items.find { it.id == id }
            }

    override suspend fun save(item: PostModel): PostModel =
            mutex.withLock {
                when (val index = items.indexOfFirst { it.id == item.id }) {
                    -1 -> {
                        val copy = item.copy(id = nextId++)
                        items.add(copy)
                        copy
                    }
                    else -> {
                        items[index] = item
                        item
                    }
                }
            }

    override suspend fun removeById(id: Long) {
        mutex.withLock {
            items.removeIf { it.id == id }
        }
    }

    override suspend fun likeById(id: Long, userId: Long?): PostModel? =
            mutex.withLock {
                val index = items.indexOfFirst { it.id == id }
                if (index < 0) {
                    return@withLock null
                }
                val post = items[index]
                if (post.postIsLike.contains(userId)) {
                    return throw ActionProhibitedException("действие запрешено")
                }

                val newPost = post.copy(like = post.like.inc(), isLike = true)

                items[index] = newPost

                newPost
            }

    override suspend fun dislikeById(id: Long, userId: Long?): PostModel? =
            mutex.withLock {
                val index = items.indexOfFirst { it.id == id }
                if (index < 0) {
                    return@withLock null
                }

                val post = items[index]
                if (!post.postIsLike.contains(userId)) {
                    return throw ActionProhibitedException("действие запрешено")
                }

                val newPost = post.copy(like = post.like.dec(), isLike = false)

                items[index] = newPost

                newPost
            }

    override suspend fun repost(item: PostModel): PostModel? =
            mutex.withLock {
                val index = items.indexOfFirst { it.id == item.id }
                println(index)
                if (index < 0) {
                    return@withLock null
                }

                val post = items[index]

                val newPost = post.copy(id = items.size.toLong(), type = PostTypes.Reposts)

                items.add(newPost)

                newPost
            }

    override suspend fun getfive(): List<PostModel> =
            mutex.withLock {
                items.takeLast(5).reversed()
            }

    override suspend fun getOld(id: Long): List<PostModel> =
            mutex.withLock {
                items.filter {
                    it.id < id
                }.reversed()
            }

    override suspend fun newPost(postResurse: String): List<PostModel> =
            mutex.withLock {


                val newPost = PostModel(id = items.size.toLong(), postResurse = postResurse)

                items.add(newPost)

                items
            }


}

fun main() {
    repeat(10) {
        val repo = PostRepositoryMutex()

        val scope = CoroutineScope(EmptyCoroutineContext + SupervisorJob())

        repeat(10_000) {
            scope.launch {
                repo.save(PostModel(id = 0, autor = "Test"))
            }
        }

        Thread.sleep(1000)

        with(CoroutineScope(EmptyCoroutineContext + SupervisorJob())) {
            repeat(100_000) {
                launch {
                    repo.likeById(1L, 1L)
                }
            }
        }

        with(CoroutineScope(EmptyCoroutineContext + SupervisorJob())) {
            launch {
                println(repo.getById(1L))
                repo.removeById(1L)
                println("After remove ${repo.getById(1L)}")
            }
        }

        Thread.sleep(2500)

        runBlocking {
            val all = repo.getAll()
            println(all.size)
            File("result.json").writeText(Gson().toJson(all))
        }
    }
}