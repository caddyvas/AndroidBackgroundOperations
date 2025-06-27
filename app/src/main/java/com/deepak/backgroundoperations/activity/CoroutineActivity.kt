package com.deepak.backgroundoperations.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.deepak.backgroundoperations.adapter.CoroutineFragmentAdapter
import com.deepak.backgroundoperations.databinding.CoroutineActivityBinding
import com.deepak.backgroundoperations.fragments.CoroutineScopes
import com.deepak.backgroundoperations.fragments.CoroutineDispatchers
import com.deepak.backgroundoperations.fragments.CoroutineDownload
import com.google.android.material.tabs.TabLayoutMediator

class CoroutineActivity : AppCompatActivity() {

    private lateinit var binding: CoroutineActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CoroutineActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // add action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        val fragments =
            listOf(CoroutineDownload(), CoroutineScopes(), CoroutineDispatchers())
        val adapter = CoroutineFragmentAdapter(fragments, supportFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "BUILDERS"
                1 -> "SCOPES"
                else -> "DISPATCHERS"
            }
        }.attach()
    }
}