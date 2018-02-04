package com.chandilsachin.notely.util

import io.reactivex.schedulers.Schedulers
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins


class TestUtilFunctions{

    companion object {
        @JvmStatic
        fun overrideRxJavaPlugins() {
            RxJavaPlugins.setIoSchedulerHandler { _ -> Schedulers.trampoline() }
            RxJavaPlugins.setComputationSchedulerHandler { _ -> Schedulers.trampoline() }
            RxJavaPlugins.setNewThreadSchedulerHandler { _ -> Schedulers.trampoline() }
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { _ -> Schedulers.trampoline() }
        }
    }

}
