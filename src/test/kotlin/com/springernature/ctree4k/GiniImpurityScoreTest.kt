package com.springernature.ctree4k

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class GiniImpurityScoreTest {

    // we don't care at the moment about what the feature is or it's value
    val someFeature = listOf(Feature(0.0, FeatureId("feature-1")))

    val triangleClass = InstanceClass(0, "triangle")
    val circleClass = InstanceClass(0, "triangle")
    val allClasses = InstanceClasses(setOf(triangleClass, circleClass))

    val triangleOne = createInstance(someFeature, triangleClass)
    val triangleTwo = createInstance(someFeature, triangleClass)
    val circleOne = createInstance(someFeature, circleClass)
    val circleTwo = createInstance(someFeature, circleClass)

    val giniScore = GiniImpurityScore()

    @Test fun `returns 0,5 for the worst split`() {

        val leftSplit = SplitHalf(listOf(triangleOne, circleOne))
        val rightSplit = SplitHalf(listOf(triangleTwo, circleTwo))

        assertThat(giniScore(Pair(leftSplit, rightSplit), allClasses), equalTo(GiniScore(0.5)))
    }

    @Test fun `returns 0 for the best split`() {

        val leftSplit = SplitHalf(listOf(triangleOne, triangleTwo))
        val rightSplit = SplitHalf(listOf(circleOne, circleTwo))

        assertThat(giniScore(Pair(leftSplit, rightSplit), allClasses), equalTo(GiniScore(0.5)))
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