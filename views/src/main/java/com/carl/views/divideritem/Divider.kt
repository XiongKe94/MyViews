package com.carl.views.divideritem

/**
 * @author xiongke
 * @date 2018/8/9
 */
class Divider(var leftSideLine: SideLine?, var topSideLine: SideLine?, var rightSideLine: SideLine?, var bottomSideLine: SideLine?) {
    class Builder {
        var leftSideLine: SideLine? = null
        var topSideLine: SideLine? = null
        var rightSideLine: SideLine? = null
        var bottomSideLine: SideLine? = null

        fun setLeftSideLine(leftSideLine: SideLine): Builder {
            this.leftSideLine = leftSideLine
            return this
        }

        fun setTopSideLine(topSideLine: SideLine): Builder {
            this.topSideLine = topSideLine
            return this
        }

        fun setRightSideLine(rightSideLine: SideLine): Builder {
            this.rightSideLine = rightSideLine
            return this
        }

        fun setBottomSideLine(bottomSideLine: SideLine): Builder {
            this.bottomSideLine = bottomSideLine
            return this
        }

        fun builder(): Divider {
            return Divider(leftSideLine, topSideLine, rightSideLine, bottomSideLine)
        }
    }
}



