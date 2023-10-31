package com.imdvlpr.expensetracker.activity.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.InputType
import androidx.activity.result.contract.ActivityResultContracts
import com.imdvlpr.expensetracker.R
import com.imdvlpr.expensetracker.databinding.ActivityUserDataBinding
import com.imdvlpr.expensetracker.helper.base.BaseActivity
import com.imdvlpr.expensetracker.helper.ui.CustomInputView
import com.imdvlpr.expensetracker.helper.ui.CustomInsertImage
import com.imdvlpr.expensetracker.helper.ui.CustomToolbar
import com.imdvlpr.expensetracker.helper.utils.DatePickerListener
import com.imdvlpr.expensetracker.helper.utils.encodeImage
import com.imdvlpr.expensetracker.helper.utils.getParcelable
import com.imdvlpr.expensetracker.helper.utils.getStatusBarHeight
import com.imdvlpr.expensetracker.helper.utils.showDatePicker
import com.imdvlpr.expensetracker.model.Register
import java.io.InputStream

class UserDataView : BaseActivity() {

    companion object {

        private const val REGISTER_DATA = "register_data"

        fun newIntent(context: Context, data: Register): Intent {
            val intent = Intent(context, UserDataView::class.java)
            intent.putExtra(REGISTER_DATA, data)
            return intent
        }
    }

    private lateinit var binding: ActivityUserDataBinding
    private var register = Register()
    private var encodedImage: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBundle()
        initView()
    }

    private fun initBundle() {
        register = intent.getParcelable(REGISTER_DATA, Register::class.java) ?: Register()
    }

    private fun initView() {
        binding.customToolbar.apply {
            setTitle(getString(R.string.register_title))
            setPadding(0, getStatusBarHeight(), 0, 0)
            setListener(object : CustomToolbar.ToolbarListener {
                override fun onBackButtonClick() {
                    onBackPressedDispatcher.onBackPressed()
                    finish()
                }
            })
        }

        binding.insertImage.apply {
            setListener(object : CustomInsertImage.InsertImageListener {
                override fun onCameraClicked() {
                    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    pickImages.launch(intent)
                }
            })
        }

        binding.fullName.apply {
            setTitle(getString(R.string.register_full_name))
            setHint(getString(R.string.phone_hint))
            setInputType(InputType.TYPE_CLASS_TEXT)
            setListener(object : CustomInputView.InputViewListener {
                override fun afterTextChanged(s: Editable?) {
                    register.fullName = s.toString()
                    validateInput()
                }
            })
        }

        binding.userName.apply {
            setTitle(getString(R.string.register_user_name))
            setHint(getString(R.string.register_user_name_hint))
            setInputType(InputType.TYPE_CLASS_TEXT)
            setListener(object : CustomInputView.InputViewListener {
                override fun afterTextChanged(s: Editable?) {
                    register.userName = s.toString()
                    validateInput()
                }
            })
        }

        binding.email.apply {
            setTitle(getString(R.string.register_email))
            setHint(getString(R.string.register_email_hint))
            setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
            setListener(object : CustomInputView.InputViewListener {
                override fun afterTextChanged(s: Editable?) {
                    register.email = s.toString()
                    validateInput()
                }
            })
        }

        binding.dateOfBirth.apply {
            setTitle(getString(R.string.register_date_of_birth))
            setHint(getString(R.string.register_date_of_birth_hint))
            setInputType(InputType.TYPE_NULL)
            setRightIndicatorIcon(R.drawable.ic_calendar, true)
            setListener(object : CustomInputView.InputViewListener {
                override fun onInputClicked() {
                    showDatePicker(supportFragmentManager, object : DatePickerListener {
                        override fun onDateSelected(s: String) {
                            register.dateOfBirth = s
                            binding.dateOfBirth.setText(s)
                            validateInput()
                        }
                    })
                }
            })
        }

        binding.registerBtn.setOnClickListener {
            startActivity(OtpView.intentRegister(this, register, OtpView.Companion.TYPE.REGISTER))
        }
    }

    private fun validateInput() {
        binding.registerBtn.isEnabled = binding.fullName.getText().isNotEmpty() &&
                binding.userName.getText().isNotEmpty() &&
                binding.email.getText().isNotEmpty() &&
                binding.dateOfBirth.getText().isNotEmpty() &&
                encodedImage != null
    }

    private var pickImages = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            if (result.data != null) {
                val imageUri: Uri? = result.data!!.data
                val inputStream: InputStream? = imageUri?.let { contentResolver.openInputStream(it) }
                val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
                binding.insertImage.setImage(bitmap)
                encodedImage = encodeImage(bitmap)
                register.userImage = encodedImage as String
                validateInput()
            }
        }
    }
}