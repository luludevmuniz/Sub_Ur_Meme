package com.alpaca.telalogin.extensions

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.alpaca.telalogin.R

private val navOptions = NavOptions.Builder()
    .setEnterAnim(R.anim.slide_in_right)
    .setExitAnim(R.anim.slide_out_left)
    .setPopEnterAnim(R.anim.slide_in_left)
    .setPopExitAnim(R.anim.slide_out_right)
    .build()

fun NavController.navigateWithAnimations(destinationId: Int, args: Bundle?) {
    this.navigate(destinationId, args, navOptions)
}