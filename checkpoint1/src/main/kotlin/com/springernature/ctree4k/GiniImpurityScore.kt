package com.springernature.ctree4k

data class SplitHalf(val instances: List<Instance>)

class GiniImpurityScore : (Pair<SplitHalf, SplitHalf>, InstanceClasses) -> GiniScore {

    override fun invoke(bothSplits: Pair<SplitHalf, SplitHalf>, allClasses: InstanceClasses): GiniScore {

        val eachScore = bothSplits.toList().map { half: List<Instance> ->
            val currentSplitSize = half.size

            val eachClassScoreBySplit = allClasses.value.map { featureSetClass: InstanceClass ->
                val ratio = half.ratioThatAre(featureSetClass)
                ratio * ratio
            }

            val score = eachClassScoreBySplit.sum()

            val scoreBySplit = (1.0 - score) * (currentSplitSize.toDouble().div(bothSplits.count()))
            scoreBySplit
        }
        val score = eachScore.sum()

        return GiniScore(score)
    }
}

data class GiniScore(val value: Double)

private fun List<Instance>.ratioThatAre(instanceClass: InstanceClass): Double {
    val count = count {
        it.instanceClass == instanceClass
    }

    return count.toDouble().div(size.toDouble())
}

private fun Pair<SplitHalf, SplitHalf>.toList(): List<List<Instance>> {
    return listOf(first.instances, second.instances)
}

private fun Pair<SplitHalf, SplitHalf>.count(): Int = first.instances.count().plus(second.instances.count())