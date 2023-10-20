package com.murtuza.popularmovielist.util
/**
 * Created by murtuza khalid saleem 03060824762 on 10/17/2023.
 */

import android.content.Context
import android.widget.Toast

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}