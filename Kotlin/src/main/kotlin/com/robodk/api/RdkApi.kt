package com.robodk.api

object RdkApi {
    object Commands {
        const val START = "CMD_START"
        object Get {
            const val ITEM_ANY = "G_Item"
            const val ITEM = "G_Item2"
            const val NAME = "G_Name"
            const val THETAS = "G_Thetas"
            const val HOME = "G_Home"
        }

        object Set {
            const val NAME = "S_Name"
        }

    }

    object Responses {
        const val READY = "READY"
    }
}