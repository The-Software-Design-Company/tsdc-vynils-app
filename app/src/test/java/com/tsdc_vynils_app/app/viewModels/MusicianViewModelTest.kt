package com.tsdc_vynils_app.app.repositories
import android.app.Application
import com.github.javafaker.Faker
import com.tsdc_vynils_app.app.models.Album
import com.tsdc_vynils_app.app.models.Musician
import com.tsdc_vynils_app.app.network.NetworkServiceAdapter
import com.tsdc_vynils_app.app.viewModels.HomeViewModel
import com.tsdc_vynils_app.app.viewModels.MusicianViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Test
import java.util.Date


class MusicianViewModelTest {


    private val faker = Faker()

    @RelaxedMockK
    private lateinit var musicianRepository: MusicianRepository

    @RelaxedMockK
    private lateinit var application: Application

    @RelaxedMockK
    private lateinit var networkServiceAdapter: NetworkServiceAdapter

    @RelaxedMockK
    private lateinit var musicianViewModel: MusicianViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)

    }

    @Test
    fun testRefreshDataSuccess() {
        val musicians = listOf(
            Musician(
                id = 1,
                name=faker.name().toString(),
                description=faker.name().toString(),
                birthDate= Date(),
                image=faker.internet().url().toString(),
                albums=emptyList(),
                performerPrizes = emptyList(),
                imagenResId = 0
            )

        )

        coEvery { networkServiceAdapter.getMusicians(any(), any()) } coAnswers {
            val onComplete: (List<Musician>) -> Unit = arg(0)
            onComplete(musicians)
        }



        coEvery { musicianRepository.refreshData(any(), any()) } coAnswers {
            val onComplete: (List<Musician>) -> Unit = arg(0)

            onComplete.invoke(musicians)
        }

        musicianRepository.refreshData(
            { result ->
                assert(result.size == 1)
                assert(result[0].id == 1)
            },
            { error ->
                assert(false)
            }
        )

        coEvery { musicianViewModel.refreshDataFromNetwork() } coAnswers {
            val onComplete: (List<Musician>) -> Unit = arg(0)

            onComplete.invoke(musicians)
        }
    }


}
