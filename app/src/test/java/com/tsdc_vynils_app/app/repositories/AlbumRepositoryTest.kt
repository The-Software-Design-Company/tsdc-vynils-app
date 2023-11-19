package com.tsdc_vynils_app.app.repositories


import android.app.Application
import com.github.javafaker.Faker
import com.tsdc_vynils_app.app.models.Album
import com.tsdc_vynils_app.app.network.NetworkServiceAdapter
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class AlbumRepositoryTest {


    private val faker = Faker()

    @RelaxedMockK
    private lateinit var albumRepository: AlbumRepository

    @RelaxedMockK
    private lateinit var application: Application

    @RelaxedMockK
    private lateinit var networkServiceAdapter: NetworkServiceAdapter

    @Before
    fun setup() {
        MockKAnnotations.init(this)

    }

    @Test
    fun testRefreshDataSuccess() = runBlocking {
        val albums = listOf(
            Album(1,faker.name().toString(),
                faker.name().toString(),
                faker.internet().url().toString(),
                faker.name().toString(),faker.music().genre(),faker.music().instrument().toString(),
                emptyList(),
                emptyList(),
                emptyList() ),
            Album(2,faker.name().toString(),
                faker.name().toString(),
                faker.internet().url().toString(),
                faker.name().toString(),faker.music().genre(),faker.music().instrument().toString(),
                emptyList(),
                emptyList(),
                emptyList() ))


        val result=albumRepository.refreshData()

        Assert.assertNotNull(result)

    }


}
