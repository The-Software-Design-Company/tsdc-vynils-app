package com.tsdc_vynils_app.app.repositories
import android.app.Application
import com.github.javafaker.Faker
import com.tsdc_vynils_app.app.models.Album
import com.tsdc_vynils_app.app.network.NetworkServiceAdapter
import com.tsdc_vynils_app.app.viewModels.HomeViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class HomeViewModelTest {


    private val faker = Faker()

    @RelaxedMockK
    private lateinit var albumRepository: AlbumRepository

    @RelaxedMockK
    private lateinit var application: Application

    @RelaxedMockK
    private lateinit var networkServiceAdapter: NetworkServiceAdapter

    @RelaxedMockK
    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun testRefreshDataSuccess()= runBlocking {
        var albums = listOf(
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

        albums=networkServiceAdapter.getAlbums()

        var expectedAlbumsRep=albumRepository.refreshData()

        // Then
        Assert.assertNotNull(albums)
        Assert.assertEquals(albums, expectedAlbumsRep)

        homeViewModel.refreshDataFromNetwork()




    }


}
