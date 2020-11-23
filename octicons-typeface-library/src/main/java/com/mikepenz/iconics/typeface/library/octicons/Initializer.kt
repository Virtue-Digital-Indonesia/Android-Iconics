package com.mikepenz.iconics.typeface.library.octicons

import android.content.Context
import com.mikepenz.iconics.typeface.ITypeface
import com.mikepenz.iconics.typeface.IconicsHolder
import com.mikepenz.iconics.typeface.IconicsInitializer

class Initializer : androidx.startup.Initializer<ITypeface> {
    override fun create(context: Context): ITypeface {
        IconicsHolder.registerFont(Octicons)
        return Octicons
    }

    override fun dependencies(): List<Class<out androidx.startup.Initializer<*>>> {
        return listOf(IconicsInitializer::class.java)
    }
}
