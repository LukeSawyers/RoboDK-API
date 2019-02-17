package com.robodk.api.model

enum class VisibleRefType(val value: Int) {
    /**
     * Default behavior: for objects, the reference is visible if the object is visible. For robots it does not alter the display state of the robot links.
     */
    DEFAULT(-1),

    /**
     * Visible.
     */
    ON(1),

    /**
     * Not visible.
     */
    OFF(0),

    /**
     * Do not show robot links or reference frames.
     */
    ROBOT_NONE(0),

    /**
     * Display the robot tool flange (reference frame). The robot flange can be used to drag the robot from the tool flange.
     */
    ROBOT_FLANGE(0x01),

    /**
     * Display the 3D geometry attached to the robot base.
     */
    ROBOT_AXIS_BASE_3D(0x01 shl 1),

    /**
     * Display the reference frame attached to the robot base. The reference frame is only visible when the geometry is visible.
     */
    ROBOT_AXIS_BASE_REF(0x01 shl 2),

    /**
     * Display the 3D geometry attached to the robot axis 1.
     */
    ROBOT_AXIS_1_3D(0x01 shl 3),

    /**
     * Display the reference frame attached to the robot axis 1. The reference frame is only visible when the geometry is visible.
     */
    ROBOT_AXIS_1_REF(0x01 shl 4),

    /**
     * Display the 3D geometry attached to the robot axis 2.
     */
    ROBOT_AXIS_2_3D(0x01 shl 5),

    /**
     * Display the reference frame attached to the robot axis 2. The reference frame is only visible when the geometry is visible.
     */
    ROBOT_AXIS_2_REF(0x01 shl 6),

    /**
     * Display the 3D geometry attached to the robot axis 3.
     */
    ROBOT_AXIS_3_3D(0x01 shl 7),

    /**
     * Display the reference frame attached to the robot axis 3. The reference frame is only visible when the geometry is visible.
     */
    ROBOT_AXIS_3_REF(0x01 shl 8),

    /**
     * Display the 3D geometry attached to the robot axis 4.
     */
    ROBOT_AXIS_4_3D(0x01 shl 9),

    /**
     * Display the reference frame attached to the robot axis 4. The reference frame is only visible when the geometry is visible.
     */
    ROBOT_AXIS_4_REF(0x01 shl 10),

    /**
     * Display the 3D geometry attached to the robot axis 5.
     */
    ROBOT_AXIS_5_3D(0x01 shl 11),

    /**
     * Display the reference frame attached to the robot axis 5. The reference frame is only visible when the geometry is visible.
     */
    ROBOT_AXIS_5_REF(0x01 shl 12),

    /**
     * Display the 3D geometry attached to the robot axis 6.
     */
    ROBOT_AXIS_6_3D(0x01 shl 13),

    /**
     * Display the reference frame attached to the robot axis 6. The reference frame is only visible when the geometry is visible.
     */
    ROBOT_AXIS_6_REF(0x01 shl 14),

    /**
     * Display the 3D geometry attached to the robot axis 7.
     */
    ROBOT_AXIS_7_3D(0x01 shl 15),

    /**
     * Display the reference frame attached to the robot axis 7. The reference frame is only visible when the geometry is visible.
     */
    ROBOT_AXIS_7_REF(0x01 shl 16),

    /**
     * Set the robot to be displayed in the default state (show the geometry, hide the internal links).
     */
    ROBOT_DEFAULT(0x2AAAAAAB),

    /**
     * Display all robot links and references.
     */
    ROBOT_ALL(0x7FFFFFFF),

    /**
     * Display all robot references. Important: The references are only displayed if the geometry is visible. Add apropriate flags to display the geometry and the reference frame.
     */
    ROBOT_ALL_REFS(0x15555555)
}