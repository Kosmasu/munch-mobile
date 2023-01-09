package com.example.munch.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.munch.api.Retrofit
import com.example.munch.api.menu.MenuStore
import com.example.munch.databinding.FragmentProviderModifyMenuBinding
import com.example.munch.model.Menu
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import okhttp3.FormBody


class ProviderModifyMenuFragment : Fragment() {
    private val TAG = "ProviderModeivyMenuFragment"
    private var _binding: FragmentProviderModifyMenuBinding? = null
    val binding get() = _binding!!

    private var menu_id: Long? = null
    private lateinit var menuStore: MenuStore
    private var menu : Menu? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            menu_id = it.getLong("menu_id")
        }
        menuStore = MenuStore.getInstance(requireContext())
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
            // TODO: upload image
        }

        binding.btnModifySubmit.setOnClickListener {
            val body = FormBody.Builder()
                .add("menu_nama", binding.etAddNama.toString())
                .add("menu_foto", "123")
                .add("menu_harga", binding.etAddHarga.toString())
                .add("menu_status", if(binding.rbTersedia.isChecked) "tersedia" else "tidak tersedia")
                .build()

            Retrofit.coroutine.launch {
                try {
                    menuStore.create(body)
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

        if (menu_id != (0).toLong()) {
            // edit mode
            Retrofit.coroutine.launch {
                try {
                    menu = menuStore.fetch(menu_id!!.toULong()).data
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
//            .placeholder(R.drawable.user_placeholder)
//            .error(R.drawable.user_placeholder_error)
                            .resize(480,360)
                            .centerCrop()
                            .into(binding.imageView5)
                    }
                }
            }
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