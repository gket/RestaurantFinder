package com.gketdev.restaurantfinder.source

import app.cash.turbine.test
import com.gketdev.restaurantfinder.data.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.time.ExperimentalTime

@ExperimentalTime
@RunWith(MockitoJUnitRunner::class)
class LocalDataSourceTest {

    lateinit var SUT: LocalDataSource

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
        SUT = LocalDataSource()
        pair = 0.0 to 0.0
    }


    @Test
    fun `when list empty should return success state with empty list `() = runBlocking {
        SUT.addRestaurant(emptyList())
        val restaurants = SUT.getRestaurants(pair, pair)

        restaurants.test {
            val item = awaitItem()
            assert((item as DataResultState.Success).data.isEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when restaurantList filled should return success state with filled list `() = runBlocking {
        SUT.addRestaurant(listOf(item, item.copy(fsqId = "222")))
        val restaurants = SUT.getRestaurants(pair, pair)
        restaurants.test {
            val item = awaitItem()
            assert((item as DataResultState.Success).data.isNotEmpty())
            assert(item.data.size == 2)
            cancelAndIgnoreRemainingEvents()
        }
    }

}