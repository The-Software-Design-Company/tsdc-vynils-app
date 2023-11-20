package com.tsdc_vynils_app.app.repositories

import android.app.Application
import com.github.javafaker.Faker
import com.tsdc_vynils_app.app.models.Album
import com.tsdc_vynils_app.app.models.Musician
import com.tsdc_vynils_app.app.network.NetworkServiceAdapter
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.Date


class MusicianRepositoryTest {


    private val faker = Faker()

    @RelaxedMockK
    private lateinit var musicianRepository: MusicianRepository

    @RelaxedMockK
    private lateinit var application: Application

    @RelaxedMockK
    private lateinit var networkServiceAdapter: NetworkServiceAdapter

    @Before
    fun setup() {
        MockKAnnotations.init(this)

    }

    @Test
    fun testRefreshDataSuccess() = runBlocking  {
        var musicians = listOf(
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

        musicians=networkServiceAdapter.getMusicians()



        var expectedMusiciansRep=musicianRepository.refreshData()

        // Then
        Assert.assertNotNull(musicians)
        Assert.assertEquals(musicians, expectedMusiciansRep)
    }


}