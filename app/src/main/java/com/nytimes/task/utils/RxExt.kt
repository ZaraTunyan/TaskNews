package com.nytimes.task.utils

import io.reactivex.*
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.throwingSubscribe(onNext: Consumer<T>): Disposable {
    return subscribe(onNext, Consumer { t -> throw t })
}

fun <T> Observable<T>.throwingSubscribe(onNext: (T) -> Unit): Disposable {
    return subscribe(onNext) { t -> throw t }
}

fun <T> Single<T>.throwingSubscribe(onSuccess: Consumer<T>): Disposable {
    return subscribe(onSuccess, Consumer { t -> throw t })
}

fun <T> Single<T>.throwingSubscribe(onSuccess: (T) -> Unit): Disposable {
    return subscribe(onSuccess) { t -> throw t }
}

fun <T> Maybe<T>.throwingSubscribe(onSuccess: (T) -> Unit): Disposable {
    return subscribe(onSuccess) { t -> throw t }
}

fun <T> Flowable<T>.throwingSubscribe(onNext: Consumer<T>): Disposable {
    return subscribe(onNext, Consumer { t -> throw t })
}

fun <T> Flowable<T>.throwingSubscribe(onNext: (T) -> Unit): Disposable {
    return subscribe(onNext) { t -> throw t }
}

fun Completable.throwingSubscribe(onComplete: Action): Disposable {
    return subscribe(onComplete, Consumer { t -> throw t })
}

fun Completable.throwingSubscribe(onComplete: () -> Unit): Disposable {
    return subscribe(onComplete) { t -> throw t }
}

fun <T> Observable<T>.replayingShare(): Observable<T> {
    return replay(1)
        .refCount()
}

fun <T> Observable<T>.uncancelableDefaults(
    scheduler: Scheduler,
    disposableGetter: (Disposable) -> Unit
): Observable<T> {
    return subscribeOn(scheduler)
        .publish()
        .autoConnect(1, disposableGetter)
}

fun <T> Single<T>.cancelableDefaults(): Observable<T> {
    return toObservable()
        .subscribeOn(Schedulers.io())
}

fun <T> cancelableObservable(function: () -> T): Observable<T> {
    return Single.fromCallable(function)
        .cancelableDefaults()
}

sealed class Event<out S, out E> {
    object Started : Event<Nothing, Nothing>()
    data class Success<S>(val value: S) : Event<S, Nothing>()
    data class Error<E>(val value: E) : Event<Nothing, E>()
}

sealed class Event2<out S, out E> {
    object Started : Event2<Nothing, Nothing>()
    data class Success<S>(val value: S) : Event2<S, Nothing>()
    data class Error<E>(val value: E) : Event2<Nothing, E>()
    object Idle : Event2<Nothing, Nothing>()
}

private val throwableIdentity: (Throwable) -> Throwable = { it }

fun <T> Observable<T>.asEvents(): Observable<Event<T, Throwable>> {
    return asEvents(throwableIdentity)
}

fun <T, R> Observable<T>.asEvents(errorTransformer: (Throwable) -> R): Observable<Event<T, R>> {
    return asEvents(
        started = { Event.Started },
        success = { Event.Success(it) },
        error = { Event.Error(errorTransformer(it)) }
    )
}

fun <T, R> Observable<T>.asEvents(
    started: () -> R,
    success: (T) -> R,
    error: (Throwable) -> R
): Observable<R> {
    return map {
        success(it)
    }.onErrorReturn { t ->
        error(t)
    }.startWith(started())

}
