package core.model

import android.view.View

data class ToolbarData(
    val visibility:Int = View.VISIBLE,
    val title: String = "",
    val weatherVisibility:Int = View.GONE,
    val temperature: String = "",
    val city: String = "",
    val weatherDescription: String = "",
    val weatherIcon: String? = null
)