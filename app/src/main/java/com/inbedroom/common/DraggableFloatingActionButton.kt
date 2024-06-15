package com.inbedroom.common

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.math.abs

class DraggableFloatingActionButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): FloatingActionButton(context, attrs, defStyleAttr), View.OnTouchListener {
    companion object {
        const val CLICK_DRAG_TOLERANCE: Float = 10f
    }
    private var downRawX: Float = 0f
    private var downRawY: Float = 0f
    private var dX: Float = 0f
    private var dY: Float = 0f
    private var isSnapEdge: Boolean = false

    init {
        setOnTouchListener(this)
    }
    fun setSnapToEdge(isSnapEdge: Boolean) {
        this.isSnapEdge = isSnapEdge
    }
    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        return when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                downRawX = motionEvent.rawX
                downRawY = motionEvent.rawY
                dX = view.x - downRawX
                dY = view.y - downRawY

                true // Consumed
            }
            MotionEvent.ACTION_MOVE -> {
                val viewWidth = view.width
                val viewHeight = view.height

                val viewParent = view.parent as View
                val parentWidth = viewParent.width
                val parentHeight = viewParent.height

                var newX = motionEvent.rawX + dX
                newX = 0f.coerceAtLeast(newX) // Don't allow the FAB past the left hand side of the parent

                newX = (parentWidth - viewWidth).toFloat()
                    .coerceAtMost(newX) // Don't allow the FAB past the right hand side of the parent

                var newY = motionEvent.rawY + dY
                newY = 0f.coerceAtLeast(newY) // Don't allow the FAB past the top of the parent

                newY = (parentHeight - viewHeight).toFloat()
                    .coerceAtMost(newY) // Don't allow the FAB past the bottom of the parent


                view.animate()
                    .x(newX)
                    .y(newY)
                    .setDuration(0)
                    .start()

                return true // Consumed

            }
            MotionEvent.ACTION_UP -> {
                val upRawX = motionEvent.rawX
                val upRawY = motionEvent.rawY

                val upDX = upRawX - downRawX
                val upDY = upRawY - downRawY

                return if (abs(upDX) < CLICK_DRAG_TOLERANCE && abs(upDY) < CLICK_DRAG_TOLERANCE) { // A click
                    performClick()
                } else { // A drag
                    if (isSnapEdge) {
                        val viewParent = view.parent as View
                        val fiftyPercentWidth = (viewParent.width / 2)
                        val newX = if (view.x < fiftyPercentWidth) -((view.width) * 0.5f) else (viewParent.width - view.width).toFloat()
                        view.animate()
                            .x(newX)
                            .setDuration(20)
                            .start()
                    }
                    true // Consumed
                }
            }
            else -> {
                super.onTouchEvent(motionEvent)
            }
        }
    }
}