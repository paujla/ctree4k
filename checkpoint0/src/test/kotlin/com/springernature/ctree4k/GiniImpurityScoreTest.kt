package com.springernature.ctree4k

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class GiniImpurityScoreTest {

    // we don't care at the moment about what the feature is or it's value
    val someFeature = listOf(Feature(0.0, FeatureId("feature-1")))

    val triangleClass = InstanceClass(0, "triangle")
    val circleClass = InstanceClass(0, "circle")
    val allClasses = InstanceClasses(setOf(triangleClass, circleClass))

    val triangle = createInstance(someFeature, triangleClass)
    val circle = createInstance(someFeature, circleClass)

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

private fun createInstance(features: List<Feature>, instanceClass: InstanceClass): Instance {
    return object : Instance {

        override val features: List<Feature>
            get() = features

        override val instanceClass: InstanceClass
            get() = instanceClass
    }
}