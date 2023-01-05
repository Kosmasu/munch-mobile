package com.example.munch.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.munch.R
import com.example.munch.adapter.ProviderMenuAdapter
import com.example.munch.api.Retrofit
import com.example.munch.api.menu.MenuStore
import com.example.munch.databinding.FragmentProviderMenusBinding
import com.example.munch.model.Menu
import kotlinx.coroutines.launch


class ProviderMenusFragment : Fragment() {
    private val TAG = "ProviderMenusFragment"
    private var _binding: FragmentProviderMenusBinding? = null
    val binding get() = _binding!!
    private lateinit var menuStore: MenuStore
    lateinit var menuAdapter: ProviderMenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        menuStore = MenuStore.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProviderMenusBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init layout
        getMenu()

        binding.btnAddMenu.setOnClickListener {
            // TODO add menu
        }
        binding.btnSearchMenus.setOnClickListener {
            getMenu()
        }
    }

    fun getMenu() {
        val term = binding.etSearchMenus.text.toString()
        Retrofit.coroutine.launch {
            try {
                val menus = menuStore.fetchUnpaginated(mapOf("menu_nama" to term)).data
                print(menus)

                if (_binding != null) {
                    requireActivity().runOnUiThread {
                        menuAdapter = ProviderMenuAdapter(requireContext(),menus as ArrayList<Menu>)
                        binding.rvListMenu.adapter = menuAdapter
                        binding.rvListMenu.layoutManager = GridLayoutManager(requireContext(),2, GridLayoutManager.VERTICAL, false)
                    }
                }
            } catch (e : Exception){
                Log.e(TAG, "onViewCreated: API Server Error", e)
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Server error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ProfileFragment.
         */
        @JvmStatic
        fun newInstance() =
            ProviderMenusFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}