package org.firstinspires.ftc.teamcode.common

class ActionQueue : ArrayList<ScheduledRunnable?>() {
    fun tick() {
        var i = 0
        while (i < size) {
            val action = get(i)
            if (action!!.isDue) {
                action.run()
                removeAt(i)
                i--
            }
            i++
        }
    }

    fun clear(type: String?) {
        var i = 0
        while (i < size) {
            if (get(i)?.type.equals(type)) {
                removeAt(i)
                i--
            }
            i++
        }
    }
}

