package com.ddu.icore.util.algorithm

/**
 * Created by yzbzz on 2019-08-10.
 */
object ContainerHelpers {

    // This is Arrays.binarySearch(), but doesn't do any argument validation.
    fun binarySearch(array: IntArray, size: Int, value: Int): Int {
        var lo = 0
        var hi = size - 1

        while (lo <= hi) {
            val mid = (lo + hi).ushr(1)
            val midVal = array[mid]

            when {
                midVal < value -> lo = mid + 1
                midVal > value -> hi = mid - 1
                else -> return mid  // value found
            }
        }
        return lo.inv()  // value not present
    }

    fun binarySearch(array: LongArray, size: Int, value: Long): Int {
        var lo = 0
        var hi = size - 1

        while (lo <= hi) {
            val mid = (lo + hi).ushr(1)
            val midVal = array[mid]

            when {
                midVal < value -> lo = mid + 1
                midVal > value -> hi = mid - 1
                else -> return mid  // value found
            }
        }
        return lo.inv()  // value not present
    }
}