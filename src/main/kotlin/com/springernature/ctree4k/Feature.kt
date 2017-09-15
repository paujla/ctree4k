package com.springernature.ctree4k

data class FeatureId(val value: String)
data class Feature(val value: Double, val id: FeatureId)

data class InstanceClass(val value: Int, val name: String)
data class InstanceClasses(val value: Set<InstanceClass>)

interface Instance {

    val features: List<Feature>

    val instanceClass: InstanceClass

    fun get(featureId: FeatureId): Feature {
        val feature = features.find { it.id == featureId }

        if(feature != null) {
            return feature
        } else {

            throw IllegalArgumentException("Could not find feature in Instance[$this] with id[$featureId]")
        }
    }
}