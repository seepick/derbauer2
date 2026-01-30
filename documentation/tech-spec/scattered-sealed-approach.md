# Sealed objects without placement limitation

* Gain the benefit of having exhaustive `when`s (compile time safety), as provided by sealed interfaces/classes.
    * This has the limitation that all implementations must be in the same file...
* Gain the benefit from being able to place implementations anywhere (scattered).

Here a sample implementation of this pattern:

```kotlin
interface Foo {
    val discriminator: Discriminator<out Foo>

    sealed class Discriminator<FOO>(private val foo: FOO) {
        fun <T> asRuntimeType(code: (FOO) -> T) = code(foo)
        class A(foo: FooA) : Discriminator<FooA>(foo)
        class B(foo: FooB) : Discriminator<FooB>(foo)
    }
}

class FooA : Foo {
    override val discriminator = Foo.Discriminator.A(this)
    fun sayA() {
        println("A")
    }
}
class FooB : Foo {
    override val discriminator = Foo.Discriminator.B(this)
    fun sayB() {
        println("B")
    }
}

fun main() {
    val someFoo: Foo = FooA()
    when (val discriminator = someFoo.discriminator) {
        is Foo.Discriminator.A -> {
            discriminator.asRuntimeType { fooA ->
                fooA.sayA()
            }
        }

        is Foo.Discriminator.B -> {
            discriminator.asRuntimeType { fooB ->
                fooB.sayB()
            }
        }
    }
}

```