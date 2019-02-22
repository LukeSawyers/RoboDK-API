package com.robodk.api.model

enum class DisplayRefType(override val value: Int) : NumberedEnum<Int, DisplayRefType> {

    /** Set the default behavior */
    DEFAULT(-1),

    /** No axis to display */
    NONE(0),

    /** Display all reference frames */
    ALL(0x1FF), /*0b111111111),*/

    /** Display the translation/rotation along the XY plane (holds Z axis the same) */
    TXY_RZ(0x63), /*0b001100011),*/

    /** Display the translation X axis */
    TX(0x1), /*0b001,*/

    /** Display the translation Y axis */
    TY(0x2), /*0b010,*/

    /** Display the translation Z axis */
    TZ(0x4), /*0b100,*/

    /** Display the rotation X axis */
    RX(0x8), /*0b001000,*/

    /** Display the rotation Y axis */
    RY(0x10), /*0b010000,*/

    /** Display the rotation Z axis */
    RZ(0x20), /*0b100000,*/

    /** Display the plane translation along XY plane */
    PXY(0x40), /*0b001000000,*/

    /** Display the plane translation along XZ plane */
    PXZ(0x80), /*0b010000000,*/

    /** Display the plane translation along YZ plane */
    PYZ(0x100); /*0b100000000*/

    override val values get() = values().toList()
}