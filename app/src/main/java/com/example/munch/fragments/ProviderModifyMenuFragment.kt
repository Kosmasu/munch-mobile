package com.example.munch.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.munch.R
import com.example.munch.api.Retrofit
import com.example.munch.api.menu.MenuStore
import com.example.munch.databinding.FragmentProviderModifyMenuBinding
import com.example.munch.helpers.FileUtils.getFilePath
import com.example.munch.model.Menu
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class ProviderModifyMenuFragment : Fragment() {
    private val TAG = "ProviderModifyMenuFragment"
    private var _binding: FragmentProviderModifyMenuBinding? = null
    val binding get() = _binding!!

    private var menuId: Long? = null
    private lateinit var menuStore: MenuStore
    private var menu : Menu? = null
    private var uri : Uri? = null
    private lateinit var imageLauncher : ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            menuId = it.getLong("menu_id")
        }
        menuStore = MenuStore.getInstance(requireContext())

        imageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { res: ActivityResult ->
            if (res.resultCode == Activity.RESULT_OK) {
                val data = res.data
                uri = data?.data
                binding.imageView5.setImageURI(uri)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProviderModifyMenuBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnUploadImg.setOnClickListener {
            // upload image
            val getIntent = Intent(Intent.ACTION_GET_CONTENT)
            getIntent.type = "image/*"

            val pickIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")

            val chooserIntent = Intent.createChooser(getIntent, "Select Image")
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))

            imageLauncher.launch(chooserIntent)
        }

        binding.btnModifySubmit.setOnClickListener {
            if (binding.etAddNama.text.length > 255) {
                Toast.makeText(
                    context,
                    "Name cannot be more than 255 characters",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            } else if (binding.etAddHarga.text.toString().length > 12) {
                Toast.makeText(
                    context,
                    "price cannot be more than 999.999.999.999",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            } else {
                if (uri != null) {
                    val file = File(uri!!.getFilePath(requireContext()).toString())

                    Log.d(TAG, "onViewCreated: filename = $file")
                    val fileBody = FormBody.create(MediaType.parse("image/*"), file)
                    val foto = MultipartBody.Part.createFormData("menu_foto",file.name,fileBody)

                    val body: HashMap<String, RequestBody> = hashMapOf()
                    body["menu_nama"] = RequestBody.create(MultipartBody.FORM,binding.etAddNama.text.toString())
                    body["menu_harga"] = RequestBody.create(MultipartBody.FORM,binding.etAddHarga.text.toString())
                    body["menu_status"] = RequestBody.create(MultipartBody.FORM, if(binding.rbTersedia.isChecked) "tersedia" else "tidak tersedia")

//                    val body = MultipartBody.Builder()
//                        .addFormDataPart("menu_nama", binding.etAddNama.toString())
//                        .addPart(filePart)
//                        .addFormDataPart("menu_harga", binding.etAddHarga.toString())
//                        .addFormDataPart("menu_status", if(binding.rbTersedia.isChecked) "tersedia" else "tidak tersedia")
//                        .build()

                    Retrofit.coroutine.launch {
                        try {
//                            menuStore.create(
//                                RequestBody.create(MultipartBody.FORM,binding.etAddNama.text.toString()),
//                                foto,
//                                RequestBody.create(MultipartBody.FORM,binding.etAddHarga.text.toString()),
//                                RequestBody.create(MultipartBody.FORM, if(binding.rbTersedia.isChecked) "tersedia" else "tidak tersedia")
//                            )

//                            menuStore.create(
//                                binding.etAddNama.toString(),
//                                FormBody.create(MediaType.parse("image/*"), file),
////                                binding.etAddHarga.toString().toInt(),
//                                11,
//                                if(binding.rbTersedia.isChecked) "tersedia" else "tidak tersedia"
//                            )

                            menuStore.create(body,foto)
                            requireActivity().runOnUiThread{
                                Toast.makeText(context, "successfully created menu", Toast.LENGTH_SHORT).show()
                                parentFragmentManager.popBackStack()
                            }
                        } catch (e: Exception) {
                            Log.e(TAG, "onViewCreated: API Server Error", e)
                            requireActivity().runOnUiThread {
                                Toast.makeText(requireContext(), "Server error", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }

        if (menuId != (0).toLong()) {
            // edit mode
            binding.tvModifyMenuTitle.text = resources.getText(R.string.edit_menu)
            Retrofit.coroutine.launch {
                try {
                    menu = menuStore.fetch(menuId!!.toULong()).response.body()?.data
                    Log.d(TAG, "onViewCreated: menu = $menu")
                } catch (e: Exception) {
                    Log.e(TAG, "onViewCreated: API Server Error", e)
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "Server error", Toast.LENGTH_SHORT).show()
                    }
                }

                if (_binding != null) {
                    requireActivity().runOnUiThread {
                        binding.etAddNama.setText(menu?.menu_nama ?: "")
                        binding.etAddHarga.setText(menu?.menu_harga.toString())
                        binding.rbTersedia.isChecked = (menu?.menu_status == "tersedia")

                        val url = Retrofit.hostUrl + "/storage/" + (menu?.menu_foto ?: "")
                        Picasso.get()
                            .load(url)
                            .placeholder(R.drawable.samplefood)
                            .error(R.drawable.samplefood)
                            .resize(480,360)
                            .centerCrop()
                            .into(binding.imageView5)
                    }
                }
            }
        } else {
            // add mode
            binding.tvModifyMenuTitle.text = resources.getText(R.string.add_menu)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @param menu_id id history pemesanan
         *
         * @return A new instance of fragment ProfileFragment.
         */
        @JvmStatic
        fun newInstance(menu_id: ULong) =
            ProviderModifyMenuFragment().apply {
                arguments = Bundle().apply {
                    putLong("menu_id", menu_id.toLong())
                }
            }

        @JvmStatic
        fun newInstance() =
            ProviderModifyMenuFragment().apply {
                arguments = Bundle().apply {
                    putLong("menu_id", 0)
                }
            }
    }
}