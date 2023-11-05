package com.tsdc_vynils_app.app.repositories


import android.app.Application
import com.github.javafaker.Faker
import com.tsdc_vynils_app.app.models.Album
import com.tsdc_vynils_app.app.network.NetworkServiceAdapter
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
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
    fun testRefreshDataSuccess() {
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

        coEvery { networkServiceAdapter.getAlbums(any(), any()) } coAnswers {
            val onComplete: (List<Album>) -> Unit = arg(0)
            onComplete(albums)
        }


        // Mockear el resultado de la función refreshData
        coEvery { albumRepository.refreshData(any(), any()) } coAnswers {
            val onComplete: (List<Album>) -> Unit = arg(0)

            onComplete.invoke(albums)
        }

        albumRepository.refreshData(
            { result ->
                assert(result.size == 2)
                assert(result[0].id == 1)
                assert(result[1].id == 2)
            },
            { error ->
                assert(false)
            }
        )
    }


}
