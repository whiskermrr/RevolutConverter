package com.whisker.revolutconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.whisker.revolutconverter.di.Injectable
import com.whisker.revolutconverter.view.currency.CurrencyConverterFragment
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), Injectable, HasSupportFragmentInjector {

    @Inject
    @SuppressWarnings("WeakerAccess")
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(mainContainer.id, CurrencyConverterFragment())
                .commit()
        }
    }

    override fun supportFragmentInjector() = fragmentDispatchingAndroidInjector
}
