package com.anandmali.upstox.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.anandmali.upstox.databinding.FragmentHomeBinding
import com.anandmali.upstox.remote.Status
import com.anandmali.upstox.remote.data.UiStockModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val stockAdapter = StockListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        homeViewModel.getStocksList()

        obesrveStocksList()
    }

    private fun obesrveStocksList() {
        homeViewModel.getStockResponse().observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it) {
                    is Status.Failure -> Log.e("Error => ", it.error)
                    is Status.Success -> updateStocksList(it.response)
                }
            }
        })
    }

    private fun updateStocksList(response: List<UiStockModel>) {
        _binding?.let {
            it.stocksList.apply {
                adapter = stockAdapter
                layoutManager = LinearLayoutManager(activity)
            }
        }

        stockAdapter.submitList(response)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}