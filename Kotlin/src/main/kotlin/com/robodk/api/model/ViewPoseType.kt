package com.robodk.api.model

/** Type of the view pose: Isometric, Top, Front, Back, ... */
enum class ViewPoseType : NumberedEnum<Int, ViewPoseType> {

    /** Currently active view pose (do not calculate) */
    ACTIVE_VIEW,

    /** Isometric View pose */
    ISOMETRIC,

    /** Front view pose */
    FRONT,

    /** Top view pose */
    TOP,

    /** Right view pose */
    RIGHT,

    /** Left view pose */
    LEFT,

    /** Back view pose */
    BACK,

    /** Fit all pose (do not change rotation) */
    FIT_ALL;

    override val value: Int get() = ordinal
    override val values get() = values().toList()
}