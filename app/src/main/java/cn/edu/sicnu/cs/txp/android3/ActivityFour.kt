package cn.edu.sicnu.cs.txp.android3

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import cn.edu.sicnu.cs.txp.android3.databinding.ActivityFourBinding

/**
 * 第4个二级Activity：StopWatch 秒表
 * 用 Handler 每 30ms 刷新一次显示。
 * 计时状态通过 onSaveInstanceState 保存，横竖屏切换时 Activity 重建，
 * 状态被恢复，因此计时器始终连续计时。
 */
class ActivityFour : AppCompatActivity() {

    private lateinit var binding: ActivityFourBinding
    private val handler = Handler(Looper.getMainLooper())

    private var isRunning = false
    private var startTime = 0L // 本次开始计时的时刻（开机毫秒数）
    private var pausedElapsed = 0L // 暂停时累计的毫秒数

    /** 每30ms刷新一次显示 */
    private val ticker = object : Runnable {
        override fun run() {
            if (isRunning) {
                val current = SystemClock.elapsedRealtime() - startTime + pausedElapsed
                binding.tvTime.text = formatTime(current)
                handler.postDelayed(this, TICK_INTERVAL)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFourBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 恢复旋转屏幕前保存的计时状态
        savedInstanceState?.let {
            isRunning = it.getBoolean(KEY_IS_RUNNING, false)
            startTime = it.getLong(KEY_START_TIME, 0L)
            pausedElapsed = it.getLong(KEY_PAUSED_ELAPSED, 0L)
        }

        binding.tvTime.text = formatTime(pausedElapsed)

        binding.btnStart.setOnClickListener {
            if (!isRunning) {
                isRunning = true
                startTime = SystemClock.elapsedRealtime()
                handler.post(ticker)
            }
        }

        binding.btnPause.setOnClickListener {
            if (isRunning) {
                pausedElapsed += SystemClock.elapsedRealtime() - startTime
                isRunning = false
                handler.removeCallbacks(ticker)
                binding.tvTime.text = formatTime(pausedElapsed)
            }
        }

        binding.btnReset.setOnClickListener {
            isRunning = false
            pausedElapsed = 0L
            startTime = 0L
            handler.removeCallbacks(ticker)
            binding.tvTime.text = getString(R.string.time_zero)
        }

        // 若旋转前正在计时，恢复刷新
        if (isRunning) {
            startTime = SystemClock.elapsedRealtime()
            handler.post(ticker)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_IS_RUNNING, isRunning)
        outState.putLong(KEY_START_TIME, startTime)
        outState.putLong(KEY_PAUSED_ELAPSED, pausedElapsed)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(ticker)
    }

    private fun formatTime(ms: Long): String {
        val totalSec = ms / 1000
        val sec = totalSec % 60
        val min = totalSec / 60
        val milli = (ms % 1000) / 10
        return getString(
            R.string.time_format,
            String.format("%02d", min),
            String.format("%02d", sec),
            String.format("%02d", milli)
        )
    }

    companion object {
        private const val TICK_INTERVAL = 30L
        private const val KEY_IS_RUNNING = "key_is_running"
        private const val KEY_START_TIME = "key_start_time"
        private const val KEY_PAUSED_ELAPSED = "key_paused_elapsed"
    }
}
