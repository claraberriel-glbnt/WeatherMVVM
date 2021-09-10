package com.claraberriel.weathermvvm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.claraberriel.domain.entities.Weather
import com.claraberriel.domain.usecases.GetWeatherUseCase
import com.claraberriel.domain.utils.Result
import com.claraberriel.weathermvvm.utils.Status
import com.claraberriel.weathermvvm.viewmodel.WeatherViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.Assert.assertEquals
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class WeatherViewModelTest {

    class TestObserver<T> : Observer<T> {
        val observedValues = mutableListOf<T?>()
        override fun onChanged(value: T?) {
            observedValues.add(value)
        }
    }

    private fun <T> LiveData<T>.testObserver() = TestObserver<T>().also {
        observeForever(it)
    }

    @ObsoleteCoroutinesApi
    private var mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: WeatherViewModel
    @Mock lateinit var getWeatherUseCase: GetWeatherUseCase
    @Mock lateinit var weatherSuccessResult: Result.Success<List<Weather>>
    @Mock lateinit var weatherFailureResult: Result.Failure
    private var weatherReport: List<Weather> =
        listOf(Weather(111, 00.0, 00.0, 00.0, 00.0, "", "", ""))
    @Mock lateinit var exception: Exception

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        MockitoAnnotations.openMocks(this)
        viewModel = WeatherViewModel(getWeatherUseCase)
    }

    @After
    fun after() {
        mainThreadSurrogate.close()
        Dispatchers.resetMain()
    }

    @Test
    fun getWeatherSuccess() {
        val liveDataUnderTest = viewModel.oneCallResp.testObserver()
        whenever(getWeatherUseCase.invoke()).thenReturn(weatherSuccessResult)
        whenever(weatherSuccessResult.data).thenReturn(weatherReport)

        runBlocking {
            viewModel.getWeather().join()
        }

        liveDataUnderTest.observedValues.run {
            assertEquals(Status.LOADING, first()?.peekContent()?.responseType)
            Assert.assertNotNull(last()?.peekContent())
            last()?.peekContent()?.run {
                assertEquals(Status.SUCCESSFUL, responseType)
                Assert.assertEquals(weatherReport, data)
            }
        }
    }

    @Test
    fun getWeatherFailure() {
        val liveDataUnderTest = viewModel.oneCallResp.testObserver()
        whenever(getWeatherUseCase.invoke()).thenReturn(weatherFailureResult)
        whenever(weatherFailureResult.exception).thenReturn(exception)

        runBlocking {
            viewModel.getWeather().join()
        }

        liveDataUnderTest.observedValues.run {
            assertEquals(Status.LOADING, first()?.peekContent()?.responseType)
            Assert.assertNotNull(last()?.peekContent())
            last()?.peekContent()?.run {
                assertEquals(Status.ERROR, responseType)
                Assert.assertEquals(exception, error)
            }
        }
    }
}