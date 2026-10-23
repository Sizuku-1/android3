package cn.edu.sicnu.cs.txp.android3

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cn.edu.sicnu.cs.txp.android3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 按钮1：正向传参启动 Activity1
        binding.btnActivity1.setOnClickListener {
            val intent = Intent(this, ActivityOne::class.java)
            intent.putExtra(EXTRA_MESSAGE, getString(R.string.message_to_activity_one))
            startActivity(intent)
        }

        // 按钮2：启动 Activity2，等待其返回结果
        binding.btnActivity2.setOnClickListener {
            startActivityForResult(Intent(this, ActivityTwo::class.java), REQUEST_CODE_TWO)
        }

        // 按钮3：启动 Activity3（隐式调用系统相机拍照）
        binding.btnActivity3.setOnClickListener {
            startActivity(Intent(this, ActivityThree::class.java))
        }

        // 按钮4：启动 Activity4（StopWatch 秒表）
        binding.btnActivity4.setOnClickListener {
            startActivity(Intent(this, ActivityFour::class.java))
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_TWO && resultCode == RESULT_OK) {
            val result = data?.getStringExtra(ActivityTwo.EXTRA_RESULT)
            binding.tvResult.text = result ?: getString(R.string.no_result)
        }
    }

    companion object {
        const val EXTRA_MESSAGE = "extra_message"
        const val REQUEST_CODE_TWO = 1001
    }
}
