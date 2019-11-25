package com.davidmag.bitcointracker.presentation.di

import com.davidmag.bitcointracker.infrastructure.di.ApplicationComponent
import com.davidmag.bitcointracker.presentation.activity.MainActivity
import dagger.Binds
import dagger.BindsInstance
import dagger.Component

@PresentationScope
@Component(
    modules = [ViewModelModule::class],
    dependencies = [ApplicationComponent::class]
)
interface PresentationComponent {

    @Component.Builder
    interface Builder {
        fun applicationComponent(applicationComponent: ApplicationComponent) : Builder

        fun build(): PresentationComponent
    }

    fun inject(mainActivity: MainActivity)
}