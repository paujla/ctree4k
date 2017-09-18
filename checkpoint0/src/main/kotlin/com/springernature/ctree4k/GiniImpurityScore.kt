package com.springernature.ctree4k

data class SplitHalf(val instances: List<Instance>)

class GiniImpurityScore: (Pair<SplitHalf, SplitHalf>, InstanceClasses) -> GiniScore {

    override fun invoke(bothSplits: Pair<SplitHalf, SplitHalf>, allClasses: InstanceClasses): GiniScore {
        return GiniScore(-1.0)
    }
}

data class GiniScore(val value: Double)