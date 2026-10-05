package com.ute.studenprofilemanager

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.ute.studenprofilemanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "TAG_LIFECYCLE"
    }

    private lateinit var binding: ActivityMainBinding
    private var student = Student(
        id = "22505120005",
        name = "Huỳnh Ngọc Anh",
        className = "22CT1",
        email = "anhhn.22ct@ute.udn.vn",
        gpa = 3.8
    )

    // 1. Launcher chinh sua ho so
    private val editLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val updatedStudent = result.data?.getSerializableExtra("UPDATED_STUDENT") as? Student
            updatedStudent?.let {
                student = it
                bindData(student)
                Toast.makeText(this, "Đã lưu thông tin mới của ${it.name}!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 2. Launcher chon anh tu thu vien (Gallery)
    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            binding.imgAvatar.setImageURI(it)
            Toast.makeText(this, "Đã thay đổi ảnh đại diện thành công!", Toast.LENGTH_SHORT).show()
        }
    }

    // 3. Launcher xin quyen Camera luc runtime
    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this, "Đã cấp quyền Camera! Có thể chụp ảnh ngay.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Bạn đã từ chối quyền Camera. Tính năng này bị khóa!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindData(student)

        // Nut 1: Chinh sua ho so (Result API)
        binding.btnEditProfile.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java).apply {
                putExtra("STUDENT", student)
            }
            editLauncher.launch(intent)
        }

        // Nut 2: Doi avatar tu thu vien anh (GetContent)
        binding.btnChangeAvatar.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

        // Nut 3: Goi Co van hoc tap (Implicit Intent ACTION_DIAL)
        binding.btnCallHotline.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:0905123456")
            }
            try {
                startActivity(dialIntent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Không tìm thấy ứng dụng gọi điện!", Toast.LENGTH_SHORT).show()
            }
        }

        // Nut 4: Kiem tra va xin quyen Camera (RequestPermission)
        binding.btnRequestCamera.setOnClickListener {
            cameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }

        // Nut 5: Xem ban do truong hoc UTE (Implicit Intent ACTION_VIEW)
        binding.btnOpenMap.setOnClickListener {
            val mapIntent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("geo:16.0768,108.2141?q=Đại+học+Sư+phạm+Kỹ+thuật+Đà+Nẵng")
            }
            try {
                startActivity(mapIntent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Không tìm thấy ứng dụng bản đồ!", Toast.LENGTH_SHORT).show()
            }
        }

        Log.d(TAG, "onCreate: Activity đang được khởi tạo và nạp layout")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: Activity đã hiển thị trên màn hình")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: Activity sẵn sàng tương tác")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: Activity bị che khuất một phần")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: Activity bị ẩn hoàn toàn")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: Người dùng mở lại Activity từ Stop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: Activity bị hủy hoàn toàn")
    }

    private fun bindData(student: Student) {
        binding.tvName.text = student.name
        binding.tvDetails.text = "MSSV: ${student.id} | Lớp: ${student.className}"
        val rank = when {
            student.gpa >= 3.6 -> "Xuất sắc"
            student.gpa >= 3.2 -> "Giỏi"
            student.gpa >= 2.5 -> "Khá"
            else -> "Trung bình"
        }
        binding.tvGpaBadge.text = "GPA: ${student.gpa} ($rank)"
    }
}