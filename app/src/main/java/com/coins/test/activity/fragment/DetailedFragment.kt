package com.coins.test.activity.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.coins.test.R


class DetailedFragment : Fragment() {
    companion object {
        private const val coin_id = "id"
        private const val name = "name"
        private const val type = "type"
        private const val symbol = "symbol"

        fun newInstance(id: String,name: String,type: String,symbol: String,): DetailedFragment {
            val fragment = DetailedFragment()
            val args = Bundle()
            args.putString(coin_id, id)
            args.putString(this.name, name)
            args.putString(this.type, type)
            args.putString(this.symbol, symbol)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detailed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val overlayView = View(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            isClickable = true
            isFocusable = true
        }
        (activity?.findViewById<ViewGroup>(android.R.id.content) ?: view as ViewGroup).addView(overlayView)

        var idText = view.findViewById<TextView>(R.id.coinId)
        var nameText = view.findViewById<TextView>(R.id.coinName)
        var typeText = view.findViewById<TextView>(R.id.coinType)
        var symbolText = view.findViewById<TextView>(R.id.coinSymbol)

        idText.text = "Coin Id :- "+arguments?.getString(coin_id);
        nameText.text = "Coin name :- "+arguments?.getString(name);
        typeText.text = "Coin Type :- "+arguments?.getString(type);
        symbolText.text = "Coin symbol :- "+arguments?.getString(symbol);
    }

    override fun onDestroyView() {
        super.onDestroyView()

        (activity?.findViewById<ViewGroup>(android.R.id.content) ?: view?.parent as ViewGroup).removeViewAt(1)
    }
}