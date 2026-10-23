package cn.edu.sicnu.cs.txp.android3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.edu.sicnu.cs.txp.android3.databinding.ActivityOneBinding

/** 第1个二级Activity：显示MainActivity正向传递的参数 */
class ActivityOne : AppCompatActivity() {

    private lateinit var binding: ActivityOneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 接收MainActivity传来的参数并显示
        val message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE)
        binding.tvReceived.text = message ?: getString(R.string.no_message)
    }
}
