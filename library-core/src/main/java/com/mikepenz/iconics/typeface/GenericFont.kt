/*
 * Copyright 2019 Mike Penz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mikepenz.iconics.typeface

import android.graphics.Typeface
import androidx.annotation.FontRes
import com.mikepenz.iconics.Iconics
import java.util.HashMap

/**
 * Created by mikepenz on 01.11.14.
 */
open class GenericFont : ITypeface {
    override val fontName: String
    override val author: String
    override val mappingPrefix: String
    override val fontRes: Int

    private val fontFile: String

    private val chars = HashMap<String, Char>()

    override val rawTypeface: Typeface
        get() = try {
            Typeface.createFromAsset(Iconics.applicationContext.assets, fontFile)
        } catch (ignored: Exception) {
            super.rawTypeface
        }

    override val characters: HashMap<String, Char>
        get() = chars

    override val version: String
        get() = "1.0.0"

    override val iconCount: Int
        get() = characters.size

    override val icons: List<String>
        get() = characters.keys.toList()

    override val url: String
        get() = ""

    override val description: String
        get() = ""

    override val license: String
        get() = ""

    override val licenseUrl: String
        get() = ""

    protected constructor() : this("GenericFont", "GenericAuthor", "", "")

    constructor(
        mappingPrefix: String,
        fontFile: String
    ) : this("GenericFont", "GenericAuthor", mappingPrefix, fontFile)

    constructor(
        mappingPrefix: String,
        @FontRes fontRes: Int
    ) : this("GenericFont", "GenericAuthor", mappingPrefix, fontRes)

    @Suppress("LeakingThis")
    constructor(fontName: String, author: String, mappingPrefix: String, fontFile: String) {
        if (mappingPrefix.length != 3) {
            throw IllegalArgumentException("MappingPrefix must be 3 char long")
        }
        this.fontName = fontName
        this.author = author
        this.mappingPrefix = mappingPrefix
        this.fontFile = fontFile
        this.fontRes = -1
    }

    @Suppress("LeakingThis")
    constructor(fontName: String, author: String, mappingPrefix: String, @FontRes fontRes: Int) {
        if (mappingPrefix.length != 3) {
            throw IllegalArgumentException("MappingPrefix must be 3 char long")
        }
        this.fontName = fontName
        this.author = author
        this.mappingPrefix = mappingPrefix
        this.fontFile = ""
        this.fontRes = fontRes
    }

    fun registerIcon(name: String, char: Char) {
        chars[mappingPrefix + "_" + name] = char
    }

    override fun getIcon(key: String): IIcon = Icon(chars[key]!!).withTypeface(this)

    inner class Icon : IIcon {

        private val customName: String?
        override var character: Char = ' '
            private set
        private var customTypeface: ITypeface? = null

        override val name: String
            get() = customName ?: character.toString()

        override val typeface: ITypeface
            get() = customTypeface ?: this@GenericFont

        constructor(c: Char) {
            this.customName = null
            this.character = c
        }

        constructor(name: String, c: Char) {
            this.customName = name
            this.character = c
        }

        fun withTypeface(typeface: ITypeface): Icon {
            this.customTypeface = typeface
            return this
        }
    }
}
