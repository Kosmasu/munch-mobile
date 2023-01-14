package com.example.munch.fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.munch.activities.CustomerHomeActivity
import com.example.munch.adapter.ProviderMenuAdapter
import com.example.munch.api.Retrofit
import com.example.munch.api.menu.MenuStore
import com.example.munch.api.user.UserStore
import com.example.munch.databinding.FragmentCustomerMenuProviderBinding
import com.example.munch.helpers.FragmentUtils.isSafeFragment
import com.example.munch.model.Menu
import com.example.munch.model.User
import kotlinx.coroutines.launch

class CustomerMenuProviderFragment(val provider_id : ULong) : Fragment() {
    private var _binding: FragmentCustomerMenuProviderBinding? = null
    val binding get() = _binding!!

    val reqMap : Map<String, String> = mapOf("provider_id" to provider_id.toString(), "menu_status" to "tersedia", "sort_column" to "menu_nama", "sort_type" to "asc")
    lateinit var userStore : UserStore
    lateinit var menuStore : MenuStore
    lateinit var menuAdapter : ProviderMenuAdapter
    lateinit var provider : User
    var listMenu : ArrayList<Menu> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userStore = UserStore.getInstance(requireContext())
        menuStore = MenuStore.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomerMenuProviderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Retrofit.coroutine.launch {
            try {
                provider = userStore.fetch(provider_id).body()?.data!!
                val res = menuStore.fetchUnpaginated(reqMap).body()?.data!!
                listMenu.clear()
                listMenu.addAll(res)

                if (isSafeFragment()) {
                    (context as Activity).runOnUiThread {
                        binding.tvPeekProviderName.text = provider.users_nama

                        menuAdapter = ProviderMenuAdapter(requireContext(), listMenu)
                        binding.rvListMenuProvider.adapter = menuAdapter
                        binding.rvListMenuProvider.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
                        menuAdapter.notifyDataSetChanged()
                    }
                }
            } catch (e: Exception) {
                if (isSafeFragment()) {
                    (context as Activity).runOnUiThread {
                        Toast.makeText(requireContext(), "Error fetching menu", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.btnPesanSekarang.setOnClickListener {
            (activity as CustomerHomeActivity).toPesan(provider_id)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

 }