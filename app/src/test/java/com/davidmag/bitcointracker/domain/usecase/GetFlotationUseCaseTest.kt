package com.davidmag.bitcointracker.domain.usecase

import com.davidmag.bitcointracker.domain.model.Flotation
import com.davidmag.bitcointracker.domain.repo.FlotationRepository
import io.reactivex.Flowable
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
class GetFlotationUseCaseTest {

    @Mock
    lateinit var flotationRepository: FlotationRepository

    lateinit var SUT : GetFlotationUseCase

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)

        SUT = GetFlotationUseCase(
            flotationRepository
        )
    }

    @Test
    fun execute_withdata_returnData() {
        val flotationList = listOf(Flotation(
            date = OffsetDateTime.now(),
            price = BigDecimal(0)
        ))

        `when`(flotationRepository.get()).thenReturn(
            Flowable.just(flotationList)
        )

        val testObserver = TestSubscriber<List<Flotation>>()

        SUT.execute()
            .subscribeOn(Schedulers.trampoline())
            .subscribe(testObserver)

        testObserver
            .await()
            .assertNoTimeout()
            .assertComplete()
            .assertValueCount(1)
            .assertResult(flotationList)
    }

    @Test
    fun execute_nodata_returnEmptyList() {
        val flotationList = listOf<Flotation>()

        `when`(flotationRepository.get()).thenReturn(
            Flowable.just(flotationList)
        )

        val testObserver = TestSubscriber<List<Flotation>>()

        SUT.execute()
            .subscribeOn(Schedulers.trampoline())
            .subscribe(testObserver)

        testObserver
            .await()
            .assertNoTimeout()
            .assertComplete()
            .assertValue {
                it.isEmpty()
            }
    }
}