package com.reign.vectorimage

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.util.Xml
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException

/**
 * 给 Vector 图指定 path 修改颜色
 * @date: 2021/8/24
 * @author: Reign9201
 */
class VectorImage(
    private val context: Context,
    @DrawableRes private val vectorResId: Int
) {

    private val vectorPathParams = mutableListOf<Triple<String, Int, Boolean>>()
    private var tintColor: Int? = null


    fun setTint(@ColorInt tint: Int): VectorImage {
        this.tintColor = tint
        return this
    }

    fun setPartPathColor(
        path: String,
        @ColorInt color: Int,
        fillMode: Boolean = false
    ): VectorImage {
        vectorPathParams.add(Triple(path, color, fillMode))
        return this
    }

    fun create(): Drawable? {
        return when {
            tintColor != null -> {
                ContextCompat.getDrawable(context, vectorResId)?.apply {
                    setTint(tintColor!!)
                }
            }
            vectorPathParams.isEmpty() -> {
                ContextCompat.getDrawable(context, vectorResId)
            }
            else -> {
                val drawable = createDrawable(context, vectorResId)
                if (drawable != null) {
                    try {
                        val clazz = drawable.javaClass
                        val allow =
                            clazz.getDeclaredMethod(
                                "setAllowCaching",
                                Boolean::class.javaPrimitiveType
                            )
                        allow.isAccessible = true
                        allow.invoke(drawable, false)

                        val getTargetByName =
                            clazz.getDeclaredMethod("getTargetByName", java.lang.String::class.java)
                        getTargetByName.isAccessible = true
                        vectorPathParams.forEach {
                            val fullPath = getTargetByName.invoke(drawable, it.first)
                            val setColorMethod = fullPath.javaClass.getDeclaredMethod(
                                if (it.third) "setFillColor" else "setStrokeColor",
                                Int::class.javaPrimitiveType
                            )
                            setColorMethod.isAccessible = true
                            setColorMethod.invoke(fullPath, it.second)
                        }

                    } catch (e: Exception) {
                        Log.e("Reign", e.toString())
                    }
                }

                return drawable
            }
        }


    }

    @SuppressLint("ResourceType")
    private fun createDrawable(
        context: Context,
        @DrawableRes vectorResId: Int
    ): VectorDrawableCompat? {
        try {
            val parser: XmlPullParser = context.resources.getXml(vectorResId)
            val attrs = Xml.asAttributeSet(parser)
            var type: Int
            while (parser.next().also { type = it } != 2 && type != 1) {
            }
            if (type != 2) {
                throw XmlPullParserException("No start tag found")
            }
            return VectorDrawableCompat.createFromXmlInner(context.resources, parser, attrs, null)
        } catch (var6: XmlPullParserException) {
            Log.e("VectorDrawableCompat", "parser error", var6)
        } catch (var7: IOException) {
            Log.e("VectorDrawableCompat", "parser error", var7)
        }
        return null
    }

}


