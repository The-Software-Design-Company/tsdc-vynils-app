package com.tsdc_vynils_app.app.viewModels

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.javafaker.Faker
import com.tsdc_vynils_app.app.models.Album
import com.tsdc_vynils_app.app.network.NetworkServiceAdapter
import com.tsdc_vynils_app.app.repositories.AlbumRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class HomeViewModelTest {

    private val faker = Faker()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @RelaxedMockK
    lateinit var application: Application

    @RelaxedMockK
    private lateinit var albumRepository: AlbumRepository


    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        homeViewModel = HomeViewModel(application)
        homeViewModel.albumsRepository = albumRepository
    }

    @Test
    fun testRefreshDataFromNetworkSuccess() {
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
                emptyList() )
        )



        coEvery { albumRepository.refreshData(any(), any()) } coAnswers {
            val onComplete: (List<Album>) -> Unit = arg(0)

            onComplete.invoke(albums)
        }

        homeViewModel.refreshDataFromNetwork()

        val observedAlbums = homeViewModel.albums.value
        assert(observedAlbums != null && observedAlbums.size == 2)
        assert(observedAlbums?.get(0)?.id == 1)
        assert(observedAlbums?.get(1)?.id == 2)
    }
}