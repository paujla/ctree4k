package com.springernature.ctree4k

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class GiniImpurityScoreTest {

    val triangleClass = InstanceClass(0, "triangle")
    val circleClass = InstanceClass(1, "circle")
    val allClasses = InstanceClasses(setOf(triangleClass, circleClass))

    val triangle = createInstance(triangleClass)
    val circle = createInstance(circleClass)

    val giniScore = GiniImpurityScore()

    @Test fun `returns 0,5`() {

        val leftSplit = SplitHalf(listOf(triangle, circle))
        val rightSplit = SplitHalf(listOf(triangle, circle))

        assertThat(giniScore(Pair(leftSplit, rightSplit), allClasses), equalTo(GiniScore(0.5)))
    }

    @Test fun `returns 0`() {

        val leftSplit = SplitHalf(listOf(triangle, triangle))
        val rightSplit = SplitHalf(listOf(circle, circle))

        assertThat(giniScore(Pair(leftSplit, rightSplit), allClasses), equalTo(GiniScore(0.0)))
    }

    @Test fun `returns 0,4`() {

        val leftSplit = SplitHalf(listOf(triangle, triangle))
        val rightSplit = SplitHalf(listOf(circle, circle, circle, circle, triangle, triangle, triangle, triangle))

        assertThat(giniScore(Pair(leftSplit, rightSplit), allClasses), equalTo(GiniScore(0.4)))
    }
}

private fun createInstance(instanceClass: InstanceClass): Instance {
    return object : Instance {

        override val features: List<Feature>
            get() = emptyList()

        override val instanceClass: InstanceClass
            get() = instanceClass
    }
}