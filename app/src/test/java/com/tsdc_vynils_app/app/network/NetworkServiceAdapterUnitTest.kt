package com.tsdc_vynils_app.app.network

import com.android.volley.VolleyError
import com.tsdc_vynils_app.app.models.Album
import com.tsdc_vynils_app.app.models.Comment
import com.tsdc_vynils_app.app.models.Performer
import com.tsdc_vynils_app.app.models.Track
import com.tsdc_vynils_app.app.repositories.AlbumRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NetworkServiceAdapterUnitTest {
    @RelaxedMockK
    lateinit var networkServiceAdapter: NetworkServiceAdapter

    @RelaxedMockK
    lateinit var albumRepository: AlbumRepository

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `when The Api  Return Something Then Get from api`() = runBlocking {
        val expectedComments = listOf<Comment>()
        val expectedTrack = listOf<Track>()
        val expectedPerformers = listOf<Performer>()

        // Given
        val expectedAlbums = listOf(
            Album(
                id = 1,
                name = "Buscando América",
                cover = "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg",
                releaseDate = "1984-08-01T00:00:00-05:00",
                description = "Buscando América es el primer álbum de la banda de Rubén Blades y Seis del Solar lanzado en 1984. La producción, bajo el sello Elektra, fusiona diferentes ritmos musicales tales como la salsa, reggae, rock, y el jazz latino. El disco fue grabado en Eurosound Studios en Nueva York entre mayo y agosto de 1983.",
                genre = "Salsa",
                recordLabel = "Elektra",
                comments = expectedComments,
                tracks = expectedTrack,
                performers = expectedPerformers
            )
        )

        coEvery { networkServiceAdapter.getAlbums(any(), any()) } coAnswers {
            val onComplete: (List<Album>) -> Unit = arg(0)
            onComplete(expectedAlbums)
        }

        // Mockear el resultado de la función refreshData
        coEvery { albumRepository.refreshData(any(), any()) } coAnswers {
            val onComplete: (List<Album>) -> Unit = arg(0)

            onComplete.invoke(expectedAlbums)
        }
        // When
        var result: List<Album>? = null
        albumRepository.refreshData(
            callback = { albums ->
                result = albums
            },
            onError = { /* maneja el error, si es necesario */ }
        )

        // Then
        coVerify(exactly = 1) { albumRepository.refreshData(any(), any()) }
        assertEquals(expectedAlbums, result)
    }

    @Test
    fun `given an album id when call the album with this id then return the specific album`() = runBlocking {
        // Given
        val expectedComments = listOf<Comment>()
        val expectedTrack = listOf<Track>()
        val expectedPerformers = listOf<Performer>()
        val albumId = 1
        val expectedAlbum = Album(
            id = 1,
            name = "Buscando América",
            cover = "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg",
            releaseDate = "1984-08-01T00:00:00-05:00",
            description = "Buscando América es el primer álbum de la banda de Rubén Blades y Seis del Solar lanzado en 1984. La producción, bajo el sello Elektra, fusiona diferentes ritmos musicales tales como la salsa, reggae, rock, y el jazz latino. El disco fue grabado en Eurosound Studios en Nueva York entre mayo y agosto de 1983.",
            genre = "Salsa",
            recordLabel = "Elektra",
            comments = expectedComments,
            tracks = expectedTrack,
            performers = expectedPerformers
        )
        val errorCallback: (VolleyError) -> Unit = mockk(relaxed = true)

        coEvery { networkServiceAdapter.getAlbum(albumId, any(), any()) } coAnswers {
            val onComplete: (Album) -> Unit = arg(0)
            onComplete(expectedAlbum)
        }

        // When
        var result: Album? = null
        albumRepository.refreshDataById(
            albumId,
            { album ->
                result = album
            },
           errorCallback
        )

        // Then
        assertEquals(expectedAlbum, result)
        coVerify(exactly = 1) { albumRepository.refreshDataById(albumId, any(), any()) }

    }


}
