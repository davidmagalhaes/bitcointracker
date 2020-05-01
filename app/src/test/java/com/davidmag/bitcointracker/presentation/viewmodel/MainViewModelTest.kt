package com.davidmag.bitcointracker.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.runner.screenshot.Screenshot.capture
import com.davidmag.bitcointracker.domain.model.Flotation
import com.davidmag.bitcointracker.domain.model.Stats
import com.davidmag.bitcointracker.domain.usecase.GetFlotationUseCase
import com.davidmag.bitcointracker.domain.usecase.GetStatsUseCase
import com.davidmag.bitcointracker.domain.usecase.HasDataUseCase
import com.davidmag.bitcointracker.domain.usecase.LoadDataUseCase
import io.reactivex.Flowable
import io.reactivex.Maybe
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import com.davidmag.bitcointracker.presentation.common.Result
import junit.framework.Assert.assertEquals
import org.junit.After
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var flotationObserver : Observer<Result<List<Flotation>>>

    @Mock
    lateinit var statsObserver : Observer<Result<Stats>>

    @Mock
    lateinit var getFlotationUseCase : GetFlotationUseCase

    @Mock
    lateinit var getStatsUseCase: GetStatsUseCase

    @Mock
    lateinit var hasDataUseCase: HasDataUseCase

    @Mock
    lateinit var loadDataUseCase: LoadDataUseCase

    lateinit var SUT : MainViewModel

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)

        SUT = MainViewModel(
            getFlotationUseCase = getFlotationUseCase,
            getStatsUseCase = getStatsUseCase,
            hasDataUseCase = hasDataUseCase,
            loadDataUseCase = loadDataUseCase
        )

        SUT.flotations.observeForever(flotationObserver)
        SUT.stats.observeForever(statsObserver)
    }

    @After
    fun tearDown(){
        SUT.flotations.removeObserver(flotationObserver)
        SUT.stats.removeObserver(statsObserver)
    }

    @Test
    fun init_executes_completes(){
        `when`(getFlotationUseCase.execute()).thenReturn(Flowable.just(listOf()))
        `when`(getStatsUseCase.execute()).thenReturn(Flowable.just(listOf()))
        `when`(hasDataUseCase.execute()).thenReturn(Maybe.just(true))
        `when`(loadDataUseCase.execute()).thenReturn(Maybe.just(Any()))

        assert(SUT.flotations.value == null)
        assert(SUT.stats.value == null)

        SUT.init()

        val captor = ArgumentCaptor.forClass(Result::class.java)
        captor.run {
            verify(flotationObserver, times(1)).onChanged(capture() as Result<List<Flotation>>)
        }

        val captor2 = ArgumentCaptor.forClass(Result::class.java)
        captor2.run {
            verify(statsObserver, times(1)).onChanged(capture() as Result<Stats>)
        }
    }
}