package com.whisker.revolutconverter.view.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.whisker.revolutconverter.di.Injectable
import com.whisker.revolutconverter.di.ViewModelFactory
import javax.inject.Inject

abstract class BaseFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }
}