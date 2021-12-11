package com.gketdev.restaurantfinder.repository

import app.cash.turbine.test
import com.gketdev.restaurantfinder.data.*
import com.gketdev.restaurantfinder.source.LocalDataSource
import com.gketdev.restaurantfinder.source.RemoteDataSource
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import kotlin.time.ExperimentalTime

@ExperimentalTime
@RunWith(MockitoJUnitRunner::class)
class HomeRepositoryTest {

    lateinit var SUT: HomeRepository

    @Mock
    lateinit var localDataSource: LocalDataSource

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

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
        SUT = HomeRepository(remoteDataSource, localDataSource)
        pair = 0.0 to 0.0
    }

    @Test
    fun `when sources are error should return error state`() = runBlocking {

        Mockito.`when`(
            localDataSource.getRestaurants(pair, pair)
        ).thenReturn(
            flow { emit(DataResultState.Error(null, null, null)) }
        )

        Mockito.`when`(
            remoteDataSource.getRestaurants(pair, pair)
        ).thenReturn(
            flow { emit(DataResultState.Error(null, null, null)) }
        )

        val restaurants = SUT.getRestaurants(pair, pair)

        restaurants.test {
            val item = awaitItem()
            assert(item is DataResultState.Error)
            val secondaryItem = awaitItem()
            assert(secondaryItem is DataResultState.Error)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when local is error remote is success should call addItem function`() = runBlocking {

        Mockito.`when`(localDataSource.getRestaurants(pair, pair))
            .thenReturn(flow {
                emit(DataResultState.Error(null, null, null))
            }
            )

        Mockito.`when`(remoteDataSource.getRestaurants(pair, pair))
            .thenReturn(flow {
                emit(DataResultState.Success(data = listOf(item)))
            }
            )

        val restaurants = SUT.getRestaurants(pair, pair)

        restaurants.test {
            val item = awaitItem()
            assert(item is DataResultState.Error)
            val secondaryItem = awaitItem()
            assert(secondaryItem is DataResultState.Success)
            secondaryItem as DataResultState.Success
            verify(localDataSource).addRestaurant(secondaryItem.data)
            cancelAndIgnoreRemainingEvents()
        }

    }

    @Test
    fun `when local is success remote is error should not call addItem function`() = runBlocking {

        Mockito.`when`(localDataSource.getRestaurants(pair, pair))
            .thenReturn(flow {
                emit(DataResultState.Success(data = listOf(item)))
            }
            )

        Mockito.`when`(remoteDataSource.getRestaurants(pair, pair))
            .thenReturn(flow {
                emit(DataResultState.Error(null, null, null))
            }
            )

        val restaurants = SUT.getRestaurants(pair, pair)

        restaurants.test {
            val item = awaitItem()
            assert(item is DataResultState.Success)
            val secondaryItem = awaitItem()
            assert(secondaryItem is DataResultState.Error)
            verify(localDataSource, never()).addRestaurant(listOf())
            cancelAndIgnoreRemainingEvents()
        }

    }

    @Test
    fun `when local is success remote is success should return success`() = runBlocking {

        Mockito.`when`(localDataSource.getRestaurants(pair, pair))
            .thenReturn(flow {
                emit(DataResultState.Success(data = listOf(item)))
            }
            )

        Mockito.`when`(remoteDataSource.getRestaurants(pair, pair))
            .thenReturn(flow {
                emit(DataResultState.Success(data = listOf(item)))
            }
            )

        val restaurants = SUT.getRestaurants(pair, pair)

        restaurants.test {
            val item = awaitItem()
            assert(item is DataResultState.Success)
            val secondaryItem = awaitItem()
            assert(secondaryItem is DataResultState.Success)
            cancelAndIgnoreRemainingEvents()
        }

    }


}