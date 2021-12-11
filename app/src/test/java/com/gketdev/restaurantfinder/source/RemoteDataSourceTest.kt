package com.gketdev.restaurantfinder.source

import app.cash.turbine.test
import com.gketdev.restaurantfinder.api.FoursquareApiService
import com.gketdev.restaurantfinder.data.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import kotlin.time.ExperimentalTime

@ExperimentalTime
@RunWith(MockitoJUnitRunner::class)
class RemoteDataSourceTest {

    lateinit var SUT: RemoteDataSource

    @Mock
    lateinit var apiService: FoursquareApiService

    lateinit var pair: Pair<Double, Double>

    private val item = Restaurant(
        distance = 0,
        fsqId = "00",
        Geocodes(Main(0.0, 0.0)),
        Location("", "", "", "", emptyList(), "", ""),
        "",
        ""
    )

    @Before
    fun setUp() {
        SUT = RemoteDataSource(apiService)
        pair = 0.0 to 0.0
    }

    @Test
    fun `when response null should return error state`() = runBlocking {
        Mockito.`when`(
            apiService.getRestaurants(
                anyString(), anyString(), anyInt()
            )
        ).thenReturn(null)

        val restaurants = SUT.getRestaurants(pair, pair)

        restaurants.test {
            val item = awaitItem()
            assert(item is DataResultState.Error)
            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `when list empty should return success state with empty list `() = runBlocking {
        Mockito.`when`(
            apiService.getRestaurants(
                anyString(), anyString(), anyInt()
            )
        ).thenReturn(ResultResponse(emptyList()))

        val restaurants = SUT.getRestaurants(pair, pair)

        restaurants.test {
            val item = awaitItem()
            assert((item as DataResultState.Success).data.isEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when restaurantList filled should return success state with filled list `() = runBlocking {
        Mockito.`when`(
            apiService.getRestaurants(
                anyString(), anyString(), anyInt()
            )
        ).thenReturn(ResultResponse(listOf(item, item)))

        val restaurants = SUT.getRestaurants(pair, pair)

        restaurants.test {
            val item = awaitItem()
            assert((item as DataResultState.Success).data.isNotEmpty())
            assert(item.data.size == 2)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when response push error should return error state`() = runBlocking {
        Mockito.`when`(
            apiService.getRestaurants(
                anyString(), anyString(), anyInt()
            )
        ).thenThrow(RuntimeException())

        val restaurants = SUT.getRestaurants(pair, pair)

        restaurants.test {
            val item = awaitItem()
            assert(item is DataResultState.Error)
            cancelAndIgnoreRemainingEvents()
        }
    }

}