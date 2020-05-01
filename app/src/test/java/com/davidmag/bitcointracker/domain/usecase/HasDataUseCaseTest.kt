package com.davidmag.bitcointracker.domain.usecase

import com.davidmag.bitcointracker.domain.model.Flotation
import com.davidmag.bitcointracker.domain.repo.FlotationRepository
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.threeten.bp.OffsetDateTime
import java.math.BigDecimal

@RunWith(JUnit4::class)
class HasDataUseCaseTest {

    @Mock
    lateinit var flotationRepository: FlotationRepository

    lateinit var SUT : HasDataUseCase

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)

        SUT = HasDataUseCase(
            flotationRepository
        )
    }

    @Test
    fun execute_hasdata_returnsTrue(){
        val flotationList = listOf(
            Flotation(
                date = OffsetDateTime.now(),
                price = BigDecimal(0)
            )
        )

        `when`(flotationRepository.get()).thenReturn(
            Flowable.just(flotationList)
        )
        `when`(flotationRepository.count()).thenReturn(
            Maybe.just(flotationList.size)
        )

        val testObservable = TestObserver<Boolean>()

        SUT.execute()
            .subscribeOn(Schedulers.trampoline())
            .subscribe(testObservable)

        testObservable
            .await()
            .assertNoTimeout()
            .assertComplete()
            .assertResult(true)
    }

    @Test
    fun execute_nodata_returnsFalse(){
        val flotationList = listOf<Flotation>()

        `when`(flotationRepository.get()).thenReturn(
            Flowable.just(flotationList)
        )
        `when`(flotationRepository.count()).thenReturn(
            Maybe.just(flotationList.size)
        )

        val testObservable = TestObserver<Boolean>()

        SUT.execute()
            .subscribeOn(Schedulers.trampoline())
            .subscribe(testObservable)

        testObservable
            .await()
            .assertNoTimeout()
            .assertComplete()
            .assertResult(false)
    }
}