package com.davidmag.bitcointracker.domain.usecase

import com.davidmag.bitcointracker.domain.repo.StatsRepository
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetStatsUseCaseTest {

    @Mock
    lateinit var statsRepository: StatsRepository

    lateinit var SUT : GetStatsUseCase

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)

        SUT = GetStatsUseCase(
            statsRepository
        )
    }

}