package com.robodk.api.model

/**
 *  Projection types (for AddCurve).
 */
enum class ProjectionType : NumberedEnum<Int, ProjectionType> {

    /**
     * No curve projection.
     */
    NONE,

    /**
     * The projection will the closest point on the surface.
     */
    COLSEST,

    /**
     * The projection will be done along the normal.
     */
    ALONG_NORMAL,

    /**
     * The projection will be done along the normal.
     * Furthermore, the normal will be recalculated according to the surface normal.
     */
    ALONG_NORMAL_RECALC,

    /**
     * The projection will be the closest point on the surface and the normals will be recalculated.
     */
    CLOSEST_RECALC_NORMAL,

    /**
     * The normals are recalculated according to the surface normal of the closest projection.
     * The points are not changed.
     */
    RECALC_NORMAL;

    override val value get() = ordinal
    override val values get() = values().toList()

}
