package com.springernature.ctree4k

data class SplitHalf(val instances: List<Instance>)

class GiniImpurityScore : (Pair<SplitHalf, SplitHalf>, InstanceClasses) -> GiniScore {

    override fun invoke(bothSplits: Pair<SplitHalf, SplitHalf>, allClasses: InstanceClasses): GiniScore {
//        (1 - (C0LS^2 + C1LS^2)) * LSC/TC
        val leftSideCount = bothSplits.first.instances.size.toDouble()
        val rightSideCount= bothSplits.second.instances.size.toDouble()
        val totalCount = leftSideCount + rightSideCount

        val class0OnLeftSide = bothSplits.first.instances.filter { it: Instance ->
            it.instanceClass == allClasses.value.elementAt(0) }.count().toDouble()
        val C0LS: Double = class0OnLeftSide/leftSideCount.toDouble()

        val class1OnLeftSide = bothSplits.first.instances.filter { it: Instance ->
            it.instanceClass == allClasses.value.elementAt(1) }.count().toDouble()
        val C1LS = class1OnLeftSide/leftSideCount.toDouble()

        val class0OnRightSide = bothSplits.second.instances.filter { it: Instance ->
            it.instanceClass == allClasses.value.elementAt(0) }.count().toDouble()
        val C0RS = class0OnRightSide/rightSideCount.toDouble()

        val class1OnRightSide = bothSplits.second.instances.filter { it: Instance ->
            it.instanceClass == allClasses.value.elementAt(1) }.count().toDouble()
        val C1RS = class1OnRightSide/rightSideCount.toDouble()

        val scoreForLeft = ((1.0 - (C0LS*C0LS + C1LS*C1LS)) * leftSideCount/totalCount)
        val scoreForRight = ((1.0 - (C0RS*C0RS + C1RS*C1RS)) * rightSideCount/totalCount)

        val score = scoreForLeft + scoreForRight

        return GiniScore(score)
    }
}

data class GiniScore(val value: Double)