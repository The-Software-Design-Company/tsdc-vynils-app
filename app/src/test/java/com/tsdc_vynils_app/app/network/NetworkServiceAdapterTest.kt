//package com.tsdc_vynils_app.app.network
//
//import com.android.volley.VolleyError
//import com.tsdc_vynils_app.app.models.Album
//import com.tsdc_vynils_app.app.models.Comment
//import com.tsdc_vynils_app.app.models.Musician
//import com.tsdc_vynils_app.app.models.Performer
//import com.tsdc_vynils_app.app.models.Track
//import com.tsdc_vynils_app.app.repositories.AlbumRepository
//import com.tsdc_vynils_app.app.repositories.MusicianRepository
//import io.mockk.MockKAnnotations
//import io.mockk.coEvery
//import io.mockk.coVerify
//import io.mockk.impl.annotations.RelaxedMockK
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.runBlocking
//import org.junit.Assert.assertEquals
//import org.junit.Assert.assertNotNull
//import org.junit.Before
//import org.junit.Test
//import java.util.Date
//
//class NetworkServiceAdapterTest {
//    @RelaxedMockK
//    lateinit var networkServiceAdapter: NetworkServiceAdapter
//
//    @RelaxedMockK
//    lateinit var albumRepository: AlbumRepository
//
//    @RelaxedMockK
//    lateinit var musicianRepository: MusicianRepository
//
//
//    @Before
//    fun onBefore() {
//        MockKAnnotations.init(this)
//    }
//
//    @Test
//    fun `when The Api  Return Something Then Get Albums from api`() = runBlocking {
//        val expectedComments = listOf<Comment>()
//        val expectedTrack = listOf<Track>()
//        val expectedPerformers = listOf<Performer>()
//
//        // Given
//        var expectedAlbums = listOf(
//            Album(
//                id = 1,
//                name = "Buscando América",
//                cover = "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg",
//                releaseDate = "1984-08-01T00:00:00-05:00",
//                description = "Buscando América es el primer álbum de la banda de Rubén Blades y Seis del Solar lanzado en 1984. La producción, bajo el sello Elektra, fusiona diferentes ritmos musicales tales como la salsa, reggae, rock, y el jazz latino. El disco fue grabado en Eurosound Studios en Nueva York entre mayo y agosto de 1983.",
//                genre = "Salsa",
//                recordLabel = "Elektra",
//                comments = expectedComments,
//                tracks = expectedTrack,
//                performers = expectedPerformers
//            )
//        )
//
//
//        expectedAlbums=networkServiceAdapter.getAlbums()
//
//        var expectedAlbumsRep=albumRepository.refreshData()
//
//        // Then
//        assertNotNull(expectedAlbums)
//        assertEquals(expectedAlbums, expectedAlbumsRep)
//    }
//
//    @Test
//    fun `when The Api  Return Something Then Get Album from api`() = runBlocking {
//        // Given
//        val expectedAlbumId = 1
//        val expectedAlbum = Album(
//            id = expectedAlbumId,
//            name = "Buscando América",
//            cover = "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg",
//            releaseDate = "1984-08-01T00:00:00-05:00",
//            description = "Buscando América es el primer álbum de la banda de Rubén Blades y Seis del Solar lanzado en 1984...",
//            genre = "Salsa",
//            recordLabel = "Elektra",
//            comments = listOf<Comment>(),
//            tracks = listOf<Track>(),
//            performers = listOf<Performer>()
//        )
//
//        coEvery { networkServiceAdapter.getAlbum(any(), any(), any()) } coAnswers {
//            val albumId = arg<Int>(0)
//            val onComplete: (Album) -> Unit = arg(1)
//            val onError: (VolleyError) -> Unit = arg(2)
//
//            if (albumId == expectedAlbumId) {
//                onComplete.invoke(expectedAlbum)
//            } else {
//                onError.invoke(VolleyError("Simulated API error"))
//            }
//        }
//
//        // When
//        var result: Album? = null
//        networkServiceAdapter.getAlbum(
//            albumId = expectedAlbumId,
//            onComplete = { album ->
//                result = album
//            },
//            onError = { /* maneja el error, si es necesario */ }
//        )
//
//        delay(1000)
//
//        // Then
//        coVerify(exactly = 1) { networkServiceAdapter.getAlbum(expectedAlbumId, any(), any()) }
//        assertNotNull(result)
//        assertEquals(expectedAlbum, result)
//    }
//    @Test
//    fun `when The Api Doesnt Return an Album Then Handle Error`() = runBlocking {
//        // Given
//        val unexpectedAlbumId = 999
//
//        coEvery { networkServiceAdapter.getAlbum(any(), any(), any()) } coAnswers {
//            val albumId = arg<Int>(0)
//            val onComplete: (Album) -> Unit = arg(1)
//            val onError: (VolleyError) -> Unit = arg(2)
//
//            if (albumId == unexpectedAlbumId) {
//                onError.invoke(VolleyError("Simulated API error"))
//            } else {
//                // Esto simula que la API no retornó un álbum
//            }
//        }
//
//        // When
//        var error: VolleyError? = null
//        networkServiceAdapter.getAlbum(
//            albumId = unexpectedAlbumId,
//            onComplete = { /* maneja el álbum, si es necesario */ },
//            onError = { e ->
//                error = e
//            }
//        )
//
//        delay(1000)
//
//        // Then
//        coVerify(exactly = 1) { networkServiceAdapter.getAlbum(unexpectedAlbumId, any(), any()) }
//        assertNotNull(error)
//    }
//
//    @Test
//    fun `when The Api  Return Something Then Get musicians from api`() = runBlocking {
//        val expectedAlbums = listOf<Album>()
//        val expectedPerformerPrizes = listOf<Performer>()
//
//        // Given
//        var expectedMusicians = listOf(
//            Musician(
//                id = 1,
//                name="Paola Jara",
//                description="Songer",
//                birthDate= Date(),
//                image="SomeImage",
//                albums= emptyList(),
//                performerPrizes = emptyList(),
//                imagenResId = 0
//            )
//
//        )
//
//        expectedMusicians=networkServiceAdapter.getMusicians()
//
//        var expectedMusiciansRep=musicianRepository.refreshData()
//
//        // Then
//        assertNotNull(expectedMusicians)
//        assertEquals(expectedMusicians, expectedMusiciansRep)
//    }
//
//    @Test
//    fun `when The Api  Return Something Then Get bands for musicians from api`() = runBlocking {
//        val expectedAlbums = listOf<Album>()
//        val expectedPerformerPrizes = listOf<Performer>()
//
//        // Given
//        var expectedMusicians = listOf(
//            Musician(
//                id = 1,
//                name="Paola Jara",
//                description="Songer",
//                birthDate= Date(),
//                image="SomeImage",
//                albums=expectedAlbums,
//                performerPrizes = expectedPerformerPrizes,
//                imagenResId = 0
//            )
//
//        )
//
//        expectedMusicians=networkServiceAdapter.getMusicians()
//
//        var expectedMusiciansRep=musicianRepository.refreshData()
//
//        // Then
//        assertNotNull(expectedMusicians)
//        assertEquals(expectedMusicians, expectedMusiciansRep)
//    }
//}
