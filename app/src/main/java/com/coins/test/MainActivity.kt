package com.coins.test

import CoinsAdapter
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.coins.test.activity.fragment.DetailedFragment
import com.coins.test.models.data_classes.Coin
import com.coins.test.network_services.ResponseData
import com.coins.test.view_models.CoinsViewModel
import com.coins.test.databinding.ActivityMainBinding
import com.coins.test.view_models.view_model_factory.CoinsViewModelFactory


import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.properties.Delegates


class MainActivity : AppCompatActivity() {
  lateinit var coinsViewModel: CoinsViewModel
    private lateinit var adapter: CoinsAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var loadingDialog: Dialog
    private var coinsListSize by Delegates.notNull<Int>()
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)


        println("sha1key:- ${getSHA1(this)}")
        val repository = (application as CoinsApplication).coinsRepository


        coinsViewModel = ViewModelProvider(
            this,
            CoinsViewModelFactory(repository)
        ).get(CoinsViewModel::class.java)


        loadingDialog = Dialog(this)
        loadingDialog.setContentView(ProgressBar(this))


        adapter = CoinsAdapter {
                coins -> onCoinsClicked(coins)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Load data initially
        loadData()

        // Set up swipe to refresh
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.setColorSchemeResources(
                R.color.purple,
                R.color.orange,
                R.color.blue
            )
            binding.swipeRefreshLayout.isRefreshing = true
            loadData()
            binding.swipeRefreshLayout.isRefreshing = false
        }


        coinsViewModel.coinData.observe(this, Observer { coins ->
            when(coins){
                is ResponseData.Loading-> {
                    loadingDialog.show()
                }
                is ResponseData.Success-> {
                    loadingDialog.cancel()
                    coins.data?.let {
                        coinsListSize = coins.data!!.size
                          print("coinsTitle:- "+ coins.data!!.size.toString())
                          print("coinsName:- "+ coins.data!![1].toString())
                        Toast.makeText(this@MainActivity,coins.data!!.size.toString(),Toast.LENGTH_SHORT).show()
                        adapter.updateCoins(it)
                    }
                }
                is ResponseData.Error-> {
                    loadingDialog.cancel()
                }
            }
        })



        // Set up search functionality
        binding.searchCoins.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { adapter.SearchCoins(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { adapter.SearchCoins(it) }
                return true
            }
        })
    }

    private fun onCoinsClicked(coin: Coin) {
        val id = coin.id
        val name = coin.name
        val type = coin.type
        val symbol = coin.symbol

        val fragment = DetailedFragment.newInstance(id,name,type,symbol)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun loadData() {
        loadingDialog.show()
        coinsViewModel.getCoinsData()
    }

    fun getSHA1(context: Context): String {
        try {
            val packageName = context.packageName
            val packageInfo = context.packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)

            // Get signatures from package info
            val signatures = packageInfo.signatures

            // Iterate over signatures and generate SHA-1 fingerprint
            for (signature in signatures) {
                val md = MessageDigest.getInstance("SHA-1")
                md.update(signature.toByteArray())
                val sha1 = md.digest()

                // Convert the byte to hex format
                val hexString = StringBuilder()
                for (aSha1 in sha1) {
                    val appendString = Integer.toHexString(aSha1.toInt() and 0xff)
                    if (appendString.length == 1)
                        hexString.append("0")
                    hexString.append(appendString)
                }
                return hexString.toString()
            }
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e("SHA1", "Package name not found", e)
        } catch (e: NoSuchAlgorithmException) {
            Log.e("SHA1", "Algorithm not found", e)
        }
        return ""
    }
}