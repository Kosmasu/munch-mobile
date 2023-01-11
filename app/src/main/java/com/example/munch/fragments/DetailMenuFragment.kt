package com.example.munch.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.munch.R
import com.example.munch.api.Retrofit
import com.example.munch.api.menu.MenuStore
import com.example.munch.databinding.FragmentDetailMenuBinding
import com.example.munch.helpers.CurrencyUtils.toRupiah
import com.example.munch.model.Menu
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch


class DetailMenuFragment : Fragment() {
    private val TAG = "DetailMenuFragment"
    private var _binding: FragmentDetailMenuBinding? = null
    val binding get() = _binding!!

    private var menuId: Long? = null
    private lateinit var menuStore: MenuStore
    private var menu : Menu? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            menuId = it.getLong("menu_id")
        }
        menuStore = MenuStore.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        getMenu()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnMenuDelete.setOnClickListener {
            Retrofit.coroutine.launch {
                try {
                    menuStore.delete(menuId!!.toULong())

                    requireActivity().runOnUiThread {
                        Toast.makeText(context, "menu successfully deleted", Toast.LENGTH_SHORT).show()
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

        binding.btnMenuEdit.setOnClickListener {
            // edit
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.flFragmentProvider, ProviderModifyMenuFragment.newInstance(menu!!.menu_id) , TAG)
                setReorderingAllowed(true)
                addToBackStack(TAG)
                commit()
            }
        }

        getMenu()
    }

    private fun getMenu() {
        Retrofit.coroutine.launch {
            try {
                menu = menuStore.fetch(menuId!!.toULong()).body()?.data
                Log.d(TAG, "onViewCreated: menu = $menu")
            } catch (e: Exception) {
                Log.e(TAG, "onViewCreated: API Server Error", e)
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Server error", Toast.LENGTH_SHORT).show()
                }
            }
            if (activity != null) {
                requireActivity().runOnUiThread {
                    binding.tvDetailMenuNama.text = menu?.menu_nama
                    binding.tvDetailMenuHarga.text = menu?.menu_harga?.toRupiah() ?: "Rp. 0"
                    binding.tvDetailMenuStatus.text = menu?.menu_status

                    val url = Retrofit.hostUrl + "/storage/" + (menu?.menu_foto ?: "")
                    Picasso.get()
                        .load(url)
//            .placeholder(R.drawable.user_placeholder)
//            .error(R.drawable.user_placeholder_error)
                        .resize(480,360)
                        .centerCrop()
                        .into(binding.imageViewMenu)
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
            DetailMenuFragment().apply {
                arguments = Bundle().apply {
                    putLong("menu_id", menu_id.toLong())
                }
            }
    }
}