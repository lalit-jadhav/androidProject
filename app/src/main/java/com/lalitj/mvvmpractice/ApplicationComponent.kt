package com.lalitj.mvvmpractice

import dagger.Component

interface ApplicationComponent {
    fun inject(activity: MainActivity)
}