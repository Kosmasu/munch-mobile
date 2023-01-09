package com.example.munch.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.munch.adapter.DetailPemesananAdapter
import com.example.munch.api.Retrofit
import com.example.munch.api.pesanan.PesananStore

import com.example.munch.databinding.FragmentDetailPemesananBinding
import com.example.munch.helpers.CurrencyUtils.toRupiah
import com.example.munch.model.DetailPemesanan
import com.example.munch.model.HistoryPemesanan
import kotlinx.coroutines.launch


class DetailPemesananFragment : Fragment() {
    private val TAG = "DetailPemesananFragment"
    private var _binding: FragmentDetailPemesananBinding? = null
    val binding get() = _binding!!

    private var pemesanan_id: Long? = null
    private lateinit var pesananStore: PesananStore
    private var pesanan : HistoryPemesanan? = null
    private lateinit var detailPemesananAdapter: DetailPemesananAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pemesanan_id = it.getLong("pemesanan_id")
        }
        pesananStore = PesananStore.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailPemesananBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailPemesananAdapter = DetailPemesananAdapter(requireActivity(), arrayListOf())
        binding.rvDetailPemesanan.adapter = detailPemesananAdapter
        binding.rvDetailPemesanan.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        Retrofit.coroutine.launch {
            try {
                pesanan = pesananStore.fetch(pemesanan_id!!.toULong()).data
                Log.d(TAG, "onViewCreated: pesanan = $pesanan")
            } catch (e : Exception){
                Log.e(TAG, "onViewCreated: API Server Error", e)
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Server error", Toast.LENGTH_SHORT).show()
                }
            }

            if (_binding != null) {
                requireActivity().runOnUiThread {
                    binding.tvOrderDetailsTanggal.text = pesanan?.created_at ?: ""
                    binding.tvOrderDetailsProvider.text = pesanan?.users_provider?.users_nama ?: ""
                    binding.tvOrderDetailsCustomer.text = pesanan?.users_customer?.users_nama ?: ""
                    binding.tvOrderDetailsAlamat.text = pesanan?.users_customer?.users_alamat ?: ""
                    binding.tvOrderDetailsTelepon.text = pesanan?.users_customer?.users_telepon ?: ""
                    binding.tvOrderDetailsJumlah.text = pesanan?.pemesanan_jumlah.toString()
                    binding.tvOrderDetailsTotal.text = pesanan?.pemesanan_total?.toRupiah() ?: "Rp. 0"
                    binding.tvOrderDetailsStatus.text = pesanan?.pemesanan_status.toString()

                    val detailPemesanan : ArrayList<DetailPemesanan?>? = pesanan?.detail_pemesanan as ArrayList<DetailPemesanan?>?
                    Log.d(TAG, "onViewCreated: detailpemesanan = $detailPemesanan")
                    detailPemesananAdapter.detailList.clear()
                    if (detailPemesanan != null) {
                        detailPemesananAdapter.detailList.addAll(detailPemesanan)
                    }
                    detailPemesananAdapter.notifyDataSetChanged()
                }
            }

        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @param pemesanan_id id history pemesanan
         *
         * @return A new instance of fragment ProfileFragment.
         */
        @JvmStatic
        fun newInstance(pemesanan_id: ULong) =
            DetailPemesananFragment().apply {
                arguments = Bundle().apply {
                    putLong("pemesanan_id", pemesanan_id.toLong())
                }
            }
    }
}