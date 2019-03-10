package com.robodk.api

typealias RdkCommand = RdkApi.Commands
typealias RdkSet = RdkApi.Commands.Set
typealias RdkGet = RdkApi.Commands.Get

object RdkApi {
    object Commands {
        const val START = "CMD_START"
        const val MOVE_X = "MoveX"
        const val WAIT_MOVE = "WaitMove"
        const val PICK_ITEM = "PickItem"
        const val ADD_WIRE = "AddWire"

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

        object Set {
            const val NAME = "S_Name"
            const val THETAS = "S_Thetas"
        }
    }

    object Responses {
        const val READY = "READY"
    }
}