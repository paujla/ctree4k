package com.springernature.ctree4k

data class SplitHalf(val instances: List<Instance>)

class GiniImpurityScore : (Pair<SplitHalf, SplitHalf>, InstanceClasses) -> GiniScore {

    override fun invoke(bothSplits: Pair<SplitHalf, SplitHalf>, allClasses: InstanceClasses): GiniScore {

        val eachScoreBySplit = bothSplits.toList().map { half: List<Instance> ->
            val currentSplitSize = half.size

            val eachScoreByClassAndSplit = allClasses.value.map { featureSetClass: InstanceClass ->
                val ratio = half.ratioThatAre(featureSetClass)
                ratio * ratio
            }

            val scoreBySplit = (1.0 - eachScoreByClassAndSplit.sum()) * (currentSplitSize.toDouble().div(bothSplits.totalInstancesCount()))
            scoreBySplit
        }
        val score = eachScoreBySplit.sum()

        return GiniScore(score)
    }
}

data class GiniScore(val value: Double)

private fun List<Instance>.ratioThatAre(instanceClass: InstanceClass): Double {
    val count = count {
        it.instanceClass == instanceClass
    }

    return count.toDouble().div(size)
}

private fun Pair<SplitHalf, SplitHalf>.toList(): List<List<Instance>> {
    return listOf(first.instances, second.instances)
}

private fun Pair<SplitHalf, SplitHalf>.totalInstancesCount(): Int = first.instances.count().plus(second.instances.count())