package com.davidmag.bitcointracker.domain.usecase

import com.davidmag.bitcointracker.domain.repo.FlotationRepository
import com.davidmag.bitcointracker.domain.repo.StatsRepository
import io.reactivex.Maybe
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class LoadDataUseCaseTest {

    @Mock
    lateinit var flotationRepository: FlotationRepository

    @Mock
    lateinit var statsRepository : StatsRepository

    lateinit var SUT : LoadDataUseCase

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)

        SUT = LoadDataUseCase(
            flotationRepository,
            statsRepository
        )
    }

    @Test
    fun execute_fetcherror_fails(){
        val error = RuntimeException()
        `when`(flotationRepository.fetch()).thenReturn(Maybe.error(error))

        val testObserver = TestObserver<Any>()

        SUT.execute()
            .subscribeOn(Schedulers.trampoline())
            .subscribe(testObserver)

        testObserver.assertTerminated().assertError(error)

        verify(statsRepository, times(0)).fetch()

        testObserver.awaitTerminalEvent()

        testObserver.dispose()
    }

    @Test
    fun execute_fetchsuccess_success(){
        `when`(flotationRepository.fetch()).thenReturn(Maybe.just(Any()))
        `when`(statsRepository.fetch()).thenReturn(Maybe.just(Any()))

        val testObserver = TestObserver<Any>()

        SUT.execute()
            .subscribeOn(Schedulers.trampoline())
            .subscribe(testObserver)

        testObserver.assertTerminated().assertComplete()

        testObserver.awaitTerminalEvent()

        testObserver.dispose()
    }
}