package com.example.hotrepo.di

import com.example.hotrepo.navigator.AppNavigator
import com.example.hotrepo.navigator.AppNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * Inject Dependency for Navigator Interface
 * */
@InstallIn(ActivityComponent::class)
@Module
abstract class NavigationModule {

    /**
     * If AppNavigator is an interface, then you cannot constructor-inject it.
     * Instead, provide Hilt with the binding information by creating an abstract function annotated
     * with @Binds inside a Hilt module.
     * The function return type tells Hilt what interface the function provides instances of.
     * The function parameter tells Hilt which implementation to provide.
     */
    @Binds
    abstract fun bindNavigator(impl: AppNavigatorImpl): AppNavigator
}