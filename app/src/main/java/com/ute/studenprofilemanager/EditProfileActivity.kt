package com.ute.studenprofilemanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ute.studenprofilemanager.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private var originalStudent: Student? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Nhan du lieu sinh vien cu tu MainActivity
        originalStudent = intent.getSerializableExtra("STUDENT") as? Student
        originalStudent?.let {
            binding.edtName.setText(it.name)
            binding.edtClass.setText(it.className)
            binding.edtGpa.setText(it.gpa.toString())
        }

        // Xu ly nut Luu va Phan hoi
        binding.btnSave.setOnClickListener {
            val name = binding.edtName.text.toString().trim()
            val className = binding.edtClass.text.toString().trim()
            val gpa = binding.edtGpa.text.toString().toDoubleOrNull()

            if (name.isEmpty() || className.isEmpty() || gpa == null || gpa !in 0.0..4.0) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin và GPA hợp lệ (0.0 - 4.0)!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Cap nhat du lieu
            val updated = originalStudent?.copy(name = name, className = className, gpa = gpa)
                ?: return@setOnClickListener

            // Dong goi ket qua va tra ve MainActivity
            val resIntent = Intent().apply {
                putExtra("UPDATED_STUDENT", updated)
            }
            setResult(Activity.RESULT_OK, resIntent)
            finish()
        }

        // Xu ly nut Huy bo
        binding.btnCancel.setOnClickListener {
            finish()
        }
    }
}
