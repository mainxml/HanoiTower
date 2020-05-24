package xyz.okxy.hanoitower

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import java.util.LinkedList

/**
 * 汉诺塔视图
 * @author zcp 2020/5/23
 */
class HanoiTower @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
                                           defStyleAttr: Int = 0, defStyleRes: Int = 0) :
        ViewGroup(context, attrs, defStyleAttr, defStyleRes) {

    private val pillarA = PillarStack<DiskView>("A")
    private val pillarB = PillarStack<DiskView>("B")
    private val pillarC = PillarStack<DiskView>("C")
    private var diskCount = 4

    private val animatorList = LinkedList<ValueAnimator>()

    private var paint: Paint

    init {
        // ViewGroup默认不执行onDraw, 而我们需要绘画
        setWillNotDraw(false)

        paint = Paint().apply {
            color = Color.GRAY
            style = Paint.Style.FILL
            isAntiAlias = true
        }

        setDiskCount(diskCount)
    }

    /**
     * 设置初始盘子数
     */
    fun setDiskCount(count: Int) {
        diskCount = count

        removeAllViews()
        pillarA.clear()
        pillarB.clear()
        pillarC.clear()
        animatorList.forEach { it.cancel() }
        animatorList.clear()

        for (i in 0 until diskCount) {
            val diskView = DiskView(context)
            addView(diskView)
            pillarA.add(diskView)
        }

        requestLayout()
    }

    fun start() {
        setDiskCount(diskCount)
        // 使用post让页面重建后再开始
        post {
            hanoiAlg(diskCount, pillarA, pillarB, pillarC)
        }
    }

    /** 汉诺塔算法  */
    private fun hanoiAlg(n: Int, a: PillarStack<DiskView>, b: PillarStack<DiskView>, c: PillarStack<DiskView>) {
        if (n > 0) {
            hanoiAlg(n - 1, a, c, b)
            move(a, c)
            hanoiAlg(n - 1, b, a, c)
        }
    }

    /** 移动圆盘  */
    private fun move(source: PillarStack<DiskView>, target: PillarStack<DiskView>) {
        val diskView = source.pop()
        animationMove(diskView, source.name, target.name)
        target.push(diskView)
    }

    private fun animationMove(child: View, source: String, target: String) {
        Log.i("move", "$source -> $target")

        up(child)
        leftOrRight(child, target[0].toInt() - 'A'.toInt())
        down(child, target)
    }

    private fun up(child: View) {
        val endValue = height / 3f
        val animator = ObjectAnimator.ofFloat(child, "Y", endValue)
        animator.addUpdateListener {
            if (it.animatedValue as Float == endValue) {
                animatorList.remove(it)
                animatorList.first.start()
            }
        }
        animatorList.addLast(animator)
    }

    private fun leftOrRight(child: View, moveCount: Int) {
        val endValue = child.x + width / 4f * moveCount
        val animator = ObjectAnimator.ofFloat(child, "X", endValue)
        animator.addUpdateListener {
            if (it.animatedValue as Float == endValue) {
                animatorList.remove(it)
                animatorList.first.start()
            }
        }
        animatorList.addLast(animator)
    }

    private fun down(child: View, target: String) {
        var offsetBottom = 0f

        when(target) { // 根据目标柱子上盘子的数量算偏移
            "A" -> offsetBottom = pillarA.count() * (child.height + dp2px(2))
            "B" -> offsetBottom = pillarB.count() * (child.height + dp2px(2))
            "C" -> offsetBottom = pillarC.count() * (child.height + dp2px(2))
        }

        val endValue = height - dp2px(34) - offsetBottom
        val animator = ObjectAnimator.ofFloat(child, "Y", endValue)
        animator.addUpdateListener {
            if (it.animatedValue as Float == endValue) {
                animatorList.remove(it)
                if (animatorList.size > 0) {
                    animatorList.first.start()
                }
            }
        }
        animatorList.addLast(animator)

        // 开始
        animatorList.first.start()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 汉诺塔的大小不用根据它的盘子的大小来决定,所以直接测量所有子View(DiskView)
        children.forEach {
            measureChild(it, widthMeasureSpec, heightMeasureSpec)
        }

        val defaultWidth = dp2px(300).toInt()
        val defaultHeight = dp2px(300).toInt()
        val measuredWidth = View.resolveSize(defaultWidth, widthMeasureSpec)
        val measuredHeight = View.resolveSize(defaultHeight, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    /**
     * 布局: 把全部子View排布到第一根柱子上
     */
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        // 获取此ViewGroup去掉Padding后的高度
        val h = height - paddingTop - paddingBottom
        // 第一根柱子的X轴中点
        val pillarCenter = width / 4
        // 圆盘底部开始布局的位置
        var bottomStart = h - dp2px(34) // h - dp2px(24) 为底盘开始的地方

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.layout(
                    pillarCenter - child.measuredWidth / 2,
                    bottomStart.toInt(),
                    pillarCenter - child.measuredWidth / 2 + child.measuredWidth,
                    bottomStart.toInt() + child.measuredHeight
            )
            bottomStart -= child.measuredHeight + dp2px(2)
            child.scaleX = 1 - 0.2f * i
        }
    }

    override fun onDraw(canvas: Canvas?) {
        val pl = paddingLeft.toFloat()
        val pt = paddingTop.toFloat()
        val pr = paddingRight.toFloat()
        val pb = paddingBottom.toFloat()
        val w = width - pl - pr
        val h = height - pt - pb

        // 第一根柱子的X轴中点
        val pillarDistance = w / 4
        // 柱子的宽度
        val pillarWidth = dp2px(4)

        canvas?.apply {
            // 画第一根柱子
            drawRect(pillarDistance - pillarWidth / 2, h / 2,
                    pillarDistance + pillarWidth / 2, h, paint)
            // 画第二根柱子
            drawRect(pillarDistance * 2 - pillarWidth / 2, h / 2,
                    pillarDistance * 2 + pillarWidth / 2, h, paint)
            // 画第三根柱子
            drawRect(pillarDistance * 3 - pillarWidth / 2, h / 2,
                    pillarDistance * 3 + pillarWidth / 2, h, paint)
            // 画底盘
            drawRect(pl, h - dp2px(24), w, h, paint)
        }
    }

    private fun dp2px(dp: Int): Float {
        val scale = context.resources.displayMetrics.density
        return dp * scale + 0.5f
    }
}