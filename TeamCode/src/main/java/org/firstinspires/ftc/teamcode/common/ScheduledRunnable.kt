package org.firstinspires.ftc.teamcode.common

class ScheduledRunnable {
    private val runnable: Runnable
    val delay: Long
    @kotlin.jvm.JvmField
    val type: String
    val startTime: Long

    constructor(runnable: Runnable, delay: Long, type: String) {
        this.runnable = runnable
        this.delay = delay
        this.type = type
        this.startTime = System.currentTimeMillis()
    }

    constructor(runnable: Runnable, delay: Long) {
        this.runnable = runnable
        this.delay = delay
        this.type = ""
        this.startTime = System.currentTimeMillis()
    }

    fun run() {
        runnable.run()
    }

    val isDue: Boolean
        get() = System.currentTimeMillis() - startTime >= delay
}
