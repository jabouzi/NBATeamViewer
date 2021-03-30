package com.skanderjabouzi.nbateamviewer

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class KotlinStandardScopingFunctionsTest {

    @Test
    fun letTest() {
        // provide argument it
        // return a result (can be Any)
        val name: String? = "hello"
        val result1 = name?.let {
            it.toUpperCase()
        }
        assertEquals(result1, "HELLO")

        val result2 = name?.let {
            it.toUpperCase()
            100
        }
        assertEquals(result2, 100)

        val result3 = name?.let {}
        assertEquals(result3, Unit)

        data class Person(var name: String, var age: Int)
        val person = Person("skander", 46)
        val meNextYear = person.let {
            it.age + 1
            it.name.toUpperCase()
        }
        assertEquals(person, Person("skander", 46))
        assertEquals(meNextYear, "SKANDER")

        val meNext2Years = person.let {
            it.age = 48
            it.name = "Sir skander"
        }
        assertEquals(person, Person("Sir skander", 48))
        assertEquals(meNext2Years, Unit)
    }

    @Test
    fun alsoTest() {
        // provide argument it
        // doesn’t return a result / same object
        val name: String? = "hello"
        val result1 = name?.also {
            it.toUpperCase()
        }
        assertEquals(result1, "hello")

        val result2 = name?.also {
            it.toUpperCase()
            100
        }
        assertEquals(result2, "hello")

        val result3 = name?.also {}
        assertEquals(result3, "hello")

        data class Person(var name: String, var age: Int)
        val person = Person("skander", 46)
        val meNextYear = person.also {
            it.age + 1
            it.name.toUpperCase()
        }
        assertEquals(person, Person("skander", 46))
        assertEquals(meNextYear, Person("skander", 46))

        val meNext2Years = person.also {
            it.age = 48
            it.name = "Sir skander"
        }
        assertEquals(person, Person("Sir skander", 48))
        assertEquals(meNext2Years, Person("Sir skander", 48))
    }

    @Test
    fun runTest() {
        // change argument this
        // return a result (can be Any)
        val name: String? = "hello"
        val result1 = name?.run {
            this.toUpperCase()
        }
        assertEquals(result1, "HELLO")

        val result2 = name?.run {
            this.toUpperCase()
            100
        }
        assertEquals(result2, 100)

        val result3 = name?.run {}
        assertEquals(result3, Unit)

        data class Person(var name: String, var age: Int)
        val person = Person("skander", 46)
        val meNextYear = person.apply {
            age + 1
            this.name.toUpperCase()
        }
        assertEquals(person, Person("skander", 46))
        assertEquals(meNextYear, Person("skander", 46))

        val meNext2Years = person.apply {
            age = 48
            this.name = "Sir skander"
        }
        assertEquals(person, Person("Sir skander", 48))
        assertEquals(meNext2Years, Person("Sir skander", 48))
    }

    @Test
    fun applyTest() {
        // change argument this
        // doesn’t return a result / same object
        val name: String? = "hello"
        val result1 = name?.apply {
            this.toUpperCase()
        }
        assertEquals(result1, "hello")

        val result2 = name?.apply {
            this.toUpperCase()
            100
        }
        assertEquals(result2, "hello")

        val result3 = name?.apply {}
        assertEquals(result3, "hello")

        data class Person(var name: String, var age: Int)
        val person = Person("skander", 46)
        val meNextYear = person.apply {
            age + 1
            this.name.toUpperCase()
        }
        assertEquals(person, Person("skander", 46))
        assertEquals(meNextYear, Person("skander", 46))

        val meNext2Years = person.apply {
            age = 48
            this.name = "Sir skander"
        }
        assertEquals(person, Person("Sir skander", 48))
        assertEquals(meNext2Years, Person("Sir skander", 48))
    }

    @Test
    fun withTest() {
        // change argument this
        // return a result (can be Any)
        // not extension function
        // not suited for null checking or call chaining
        val name: String? = "hello"
        val result1 = with(name) {
            this?.toUpperCase()
        }
        assertEquals(result1, "HELLO")

        val result2 = with(name) {
            this?.toUpperCase()
            100
        }
        assertEquals(result2, 100)

        val result3 = with(name) {}
        assertEquals(result3, Unit)

        data class Person(var name: String, var age: Int)
        val person = Person("skander", 46)
        val meNextYear = with(person) {
            age + 1
            this.name?.toUpperCase()
        }
        assertEquals(person, Person("skander", 46))
        assertEquals(meNextYear,"SKANDER")

        val meNext2Years = with(person) {
            age = 48
            this.name = "Sir skander"
        }
        assertEquals(person, Person("Sir skander", 48))
        assertEquals(meNext2Years, Unit)
    }

}