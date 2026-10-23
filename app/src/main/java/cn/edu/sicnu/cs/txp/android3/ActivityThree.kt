package cn.edu.sicnu.cs.txp.android3

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import cn.edu.sicnu.cs.txp.android3.databinding.ActivityThreeBinding
import java.io.File

/**
 * 第3个二级Activity：隐式Intent调用系统相机拍照，拍完在本Activity中显示照片
 * 参考《采用隐式Intent调用相机拍照功能.pdf》
 */
class ActivityThree : AppCompatActivity() {

    private lateinit var binding: ActivityThreeBinding
    private lateinit var photoFile: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThreeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        photoFile = File(getExternalFilesDir(null), PHOTO_FILE_NAME)

        binding.btnTakePhoto.setOnClickListener { takePhoto() }
    }

    private fun takePhoto() {
        // 通过FileProvider生成content:// Uri，供相机应用写入照片
        val photoUri: Uri = FileProvider.getUriForFile(
            this, FILE_PROVIDER_AUTHORITY, photoFile
        )
        // 隐式Intent：调用系统相机
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        // 如果没有相机应用，给出提示
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_TAKE_PHOTO)
        } else {
            Toast.makeText(this, R.string.no_camera_app, Toast.LENGTH_SHORT).show()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            // 拍照成功，把照片显示在ImageView上
            val photoUri = FileProvider.getUriForFile(
                this, FILE_PROVIDER_AUTHORITY, photoFile
            )
            binding.ivPhoto.setImageURI(photoUri)
        }
    }

    companion object {
        private const val PHOTO_FILE_NAME = "photo.jpg"
        private const val FILE_PROVIDER_AUTHORITY = "cn.edu.sicnu.cs.txp.android3.fileprovider"
        private const val REQUEST_TAKE_PHOTO = 1002
    }
}
