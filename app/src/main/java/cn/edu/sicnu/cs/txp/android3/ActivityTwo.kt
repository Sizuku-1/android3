package cn.edu.sicnu.cs.txp.android3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.edu.sicnu.cs.txp.android3.databinding.ActivityTwoBinding

/** 第2个二级Activity：输入内容后确定，将内容返回MainActivity */
class ActivityTwo : AppCompatActivity() {

    private lateinit var binding: ActivityTwoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 点击确定：把输入内容通过 setResult 返回，并结束自身
        binding.btnOk.setOnClickListener {
            val input = binding.etInput.text.toString().trim()
            val intent = Intent()
            intent.putExtra(EXTRA_RESULT, input)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    companion object {
        const val EXTRA_RESULT = "extra_result"
    }
}
