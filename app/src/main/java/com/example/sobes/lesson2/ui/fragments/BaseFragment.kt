package com.example.sobes.lesson2.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.example.sobes.lesson2.ui.BaseState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment<VBinding : ViewBinding?, VViewModel : ViewModel>: Fragment(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + SupervisorJob()

    protected lateinit var viewModel: VViewModel
    protected abstract fun getViewModelClass(): Class<VViewModel>
    protected abstract fun initViewModel()

    protected var binding: VBinding? = null

    protected abstract fun getViewBinding(): VBinding?
    protected abstract fun renderData(data: BaseState)
    protected abstract fun isProgressVisible(isVisible: Boolean)
    open fun observeData() {}
    open fun setupViews() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initBindingAndViewModel()

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        observeData()
    }

    private fun initBindingAndViewModel() {
        binding = getViewBinding()
        initViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        coroutineContext.cancelChildren()
        binding = null
    }
}