package com.chandilsachin.notely.util

/**
 * Created by sachin on 31/5/17.
 */
fun Double.toDecimal(digits: Int) = java.lang.String.format("%.${digits}f", this)