package com.tomorrow.weatherapp.domain.interactors.base

import kotlinx.coroutines.flow.Flow

abstract class FlowParameterizedUseCase<P, T> : BaseUseCase<T>() {
    abstract fun execute(params: P): Flow<T>
}