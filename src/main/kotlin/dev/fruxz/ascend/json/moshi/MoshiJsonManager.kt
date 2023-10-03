package dev.fruxz.ascend.json.moshi

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object MoshiJsonManager {

    val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()



}