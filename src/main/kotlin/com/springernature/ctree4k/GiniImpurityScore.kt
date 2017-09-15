package com.springernature.ctree4k

import com.springernature.ctree4k.Instance
import com.springernature.ctree4k.InstanceClasses

data class SplitHalf(val instances: List<Instance>)

class GiniImpurityScore: (Pair<SplitHalf, SplitHalf>, InstanceClasses) -> GiniScore {

    override fun invoke(bothSplits: Pair<SplitHalf, SplitHalf>, allClasses: InstanceClasses): GiniScore {
        return GiniScore(-1.0)
    }
}

data class GiniScore(val value: Double)