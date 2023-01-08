package parse

fun<T> ListIterator<T>.visitNext(): T = next().apply { previous() }

fun<T> ListIterator<T>.expect(value: T) = next().also { if (this != value) error("Expected $value but got $this") }