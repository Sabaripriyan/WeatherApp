package base

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import core.di.DaggerAppComponent


class ListingApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .setContext(this)
            .build()
    }
}

