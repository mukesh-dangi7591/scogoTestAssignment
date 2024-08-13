import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.coins.test.R
import com.coins.test.models.data_classes.Coin

class CoinsAdapter(private val onItemClick: (Coin) -> Unit) :
    RecyclerView.Adapter<CoinsAdapter.CoinsViewHolder>() {

    private var coins = listOf<Coin>()
    private var searchedList = listOf<Coin>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_coins, parent, false)
        return CoinsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinsViewHolder, position: Int) {

        if (position < searchedList.size) {
            val coins = searchedList [position]
            holder.bind(coins)
            holder.itemView.setOnClickListener { onItemClick(coins) }
        }
    }

    override fun getItemCount(): Int = coins.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateCoins(newCoins: List<Coin>) {
        coins = newCoins
        searchedList  = newCoins
        notifyDataSetChanged()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun SearchCoins(query: String) {
        searchedList = if (query.isEmpty()) {

            coins
        } else {
            coins.filter { it.name.contains(query, ignoreCase = true) }
        }

        notifyDataSetChanged()
    }
    class CoinsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(coins: Coin) {
            itemView.findViewById<TextView>(R.id.coinId).text = coins.id
            itemView.findViewById<TextView>(R.id.coinName).text = coins.name
            itemView.findViewById<TextView>(R.id.coinType).text = coins.type
            itemView.findViewById<TextView>(R.id.coinSymbol).text = coins.symbol
        }
    }
}