package com.chandilsachin.notely.util

import io.reactivex.schedulers.Schedulers
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins


class TestUtilFunctions{

    companion object {
        @JvmStatic
        fun overrideRxJavaPlugins() {
            RxJavaPlugins.setIoSchedulerHandler { scheduler -> Schedulers.trampoline() }
            RxJavaPlugins.setComputationSchedulerHandler { scheduler -> Schedulers.trampoline() }
            RxJavaPlugins.setNewThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }
        }
    }

}
