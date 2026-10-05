package com.ute.studenprofilemanager

import android.os.Bundle
import android.util.Log
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindData(student)
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