package com.bar42.botcui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bar42.botcui.databinding.FragmentFirstBinding
import com.bar42.botcui.fetcher.BaseFetcher
import com.bar42.botcui.model.enums.GameStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        val adapter = ArrayAdapter<String>(requireContext(), R.layout.textview_layout, mutableListOf())
        binding.gamesList.adapter = adapter
        lifecycleScope.launch (Dispatchers.IO) {
            val gamesAvailable = BaseFetcher.gameInterface.getAll().body()!!
                .filter { it.status != GameStatus.FINISHED }
                .map { "${it.id} : ${it.status}" }
            withContext(Dispatchers.Main) {
                adapter.clear()
                adapter.addAll(gamesAvailable)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}