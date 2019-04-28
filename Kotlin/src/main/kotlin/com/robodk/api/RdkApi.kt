package com.robodk.api

typealias RdkCommand = RdkApi.Commands
typealias RdkSet = RdkApi.Commands.Set
typealias RdkGet = RdkApi.Commands.Get

/** Structure containing RoboDk API commands/responses. */
object RdkApi {
    /** Commands used to instruct RoboDk to carry out certain functions. */
    object Commands {
        const val START = "CMD_START"
        const val MOVE_X = "MoveX"
        const val WAIT_MOVE = "WaitMove"
        const val PICK_ITEM = "PickItem"
        const val ADD_WIRE = "AddWire"

        /** Commands used to instruct RoboDk to send data. */
        object Get {
            const val ITEM_ANY = "G_Item"
            const val ITEM = "G_Item2"
            const val NAME = "G_Name"
            const val THETAS = "G_Thetas"
            const val THETAS_CONFIG = "G_Thetas_Config"
            const val HOME = "G_Home"
            const val FK = "G_FK"
            const val IK = "G_IK"
            const val IK_JOINTS = "G_IK_jnts"
        }

        /** Commands used to instruct RobotDk to set data. */
        object Set {
            const val NAME = "S_Name"
            const val THETAS = "S_Thetas"
        }
    }

    /** Responses expected to be sent by RoboDk during communication. */
    object Responses {
        const val READY = "READY"
    }
}
