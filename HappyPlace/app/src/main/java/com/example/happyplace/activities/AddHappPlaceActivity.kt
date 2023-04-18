package com.example.happyplace.activities

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.happyplace.database.DatabaseHandler
import com.example.happyplace.databinding.ActivityAddHappPlaceBinding
import com.example.happyplace.models.HappyPlaceModel
import com.infinum.dbinspector.DbInspector
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

class AddHappPlaceActivity : AppCompatActivity() {

    private val getImageFromGallery = registerForActivityResult(ActivityResultContracts.GetContent(), {
        binding?.ivPlaceImage?.setImageURI(it)
        saveImageTOInternalStorage = it
    })

    private val pickImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val bitmap = it?.data?.extras?.get("data") as Bitmap
        binding?.ivPlaceImage?.setImageBitmap(bitmap)
        saveImageTOInternalStorage = saveImageToInternalStorage(bitmap)
        Log.i("Save image", "Path:: $saveImageTOInternalStorage")

    }

    private var saveImageTOInternalStorage: Uri? = null
    private var mLongitude: Double = 0.0
    private var maAtitude: Double = 0.0


    // Để code đc date pick cần 2 object: Calendar.instance: để lưu trữ thời gian và datepickerdialog để
    // chọn thời gian
    private var cal = Calendar.getInstance()
    private lateinit var datePicker: DatePickerDialog.OnDateSetListener

    var binding: ActivityAddHappPlaceBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityAddHappPlaceBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarAddPlace)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.toolbarAddPlace?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding?.etDate?.setOnClickListener {
            DatePickerDialog(
                this, datePicker, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(
                    Calendar.DAY_OF_MONTH
                )
            ).show()
        }

        // Tạo ra 1 date picker và connect Calendar cal với datepickẻ để cho calender lắng nghe
        datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDate(cal)
        }

        binding?.tvAddImage?.setOnClickListener {
            val pictureDialog = AlertDialog.Builder(this)
            pictureDialog.setTitle("Choose action")
            pictureDialog.setItems(arrayOf("Select from gallery", "Capture from camera")) { dialog, which ->
                when (which) {
                    0 -> chooseFromGallery()
                    1 -> takePhotoFromCamera()
                }
            }
            pictureDialog.show()
        }

        binding?.btnSave?.setOnClickListener {
            when {
                binding?.etTitle?.text?.isNullOrEmpty() == true -> {
                    Toast.makeText(this, "Please Enter Title", Toast.LENGTH_SHORT).show()
                }
                binding?.etDescription?.text?.isNullOrEmpty() == true -> {
                    Toast.makeText(this, "Please Enter Description", Toast.LENGTH_SHORT).show()
                }
                binding?.etLocation?.text?.isNullOrEmpty() == true -> {
                    Toast.makeText(this, "Please Enter Location", Toast.LENGTH_SHORT).show()
                }
                binding?.etDate?.text?.isNullOrEmpty() == true -> {
                    Toast.makeText(this, "Please Enter Date", Toast.LENGTH_SHORT).show()
                }
                saveImageTOInternalStorage == null -> {
                    Toast.makeText(this, "Please Select Image", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val happyPlaceModel = HappyPlaceModel(
                        0, binding?.etTitle?.text.toString(),
                        saveImageTOInternalStorage.toString(),
                        binding?.etDescription?.text.toString(), binding?.etLocation?.text.toString(),
                        binding?.etDate?.text.toString(), maAtitude, mLongitude
                    )
                    val dbHandler = DatabaseHandler(this)
                    val addHappyPlace = dbHandler.addHappyPlace(happyPlaceModel)
                    if(addHappyPlace > 0) {
                        setResult(Activity.RESULT_OK)
                        finish()

                    }
                }
            }


        }


    }

    private fun takePhotoFromCamera() {
        Dexter.withContext(this).withPermissions(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        ).withListener(object : MultiplePermissionsListener {

            override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                try {
                    val intentt = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    if (intent.resolveActivity(packageManager) != null) {
                        pickImage.launch(intentt)
                    }


                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onPermissionRationaleShouldBeShown(p0: MutableList<PermissionRequest>?, p1: PermissionToken?) {
                showRationalDialogForPermissions()
            }

        }).onSameThread().check()
    }

    private fun chooseFromGallery() {
        Dexter.withContext(this).withPermissions(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        ).withListener(object : MultiplePermissionsListener {


            override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {

                getImageFromGallery.launch("image/*")
            }

            override fun onPermissionRationaleShouldBeShown(p0: MutableList<PermissionRequest>?, p1: PermissionToken?) {
                showRationalDialogForPermissions()
            }

        }).onSameThread().check()
    }

    private fun showRationalDialogForPermissions() {
        AlertDialog.Builder(this).setMessage("Bạn đã từ chối quyền truy cập, cấp quyền để có thể sử dụng ứng ")
            .setPositiveButton("Go to setting") { _, _ ->
                try {
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        val uri = Uri.fromParts("package", packageName, null)
                        this.data = uri
                        startActivity(this)
                    }
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun updateDate(cal: Calendar) {
        val format = "dd-MM-yyyy"
        val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
        binding?.etDate?.setText(simpleDateFormat.format(cal.time).toString())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 2) {
                val thumbnails = data!!.extras!!.get("data") as Bitmap
                binding?.ivPlaceImage?.setImageBitmap(thumbnails)
            }
        }
    }

    private fun saveImageToInternalStorage(bitmap: Bitmap): Uri {
        val wrapper = ContextWrapper(applicationContext)
        var file = wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")
        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return Uri.parse(file.absolutePath)
    }

    companion object {
        private const val IMAGE_DIRECTORY = "HappyPlacesImages"
    }


}


