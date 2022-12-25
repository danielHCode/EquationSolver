package parse

fun<T> ListIterator<T>.visitNext(): T = next().apply { previous() }