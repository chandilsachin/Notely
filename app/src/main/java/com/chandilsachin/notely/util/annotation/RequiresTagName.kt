package com.ace.diettracker.util.annotation

/**
 * Created by sachin on 28/5/17.
 */
@Target(AnnotationTarget.CLASS)
@MustBeDocumented
annotation class RequiresTagName(val tagName:String)