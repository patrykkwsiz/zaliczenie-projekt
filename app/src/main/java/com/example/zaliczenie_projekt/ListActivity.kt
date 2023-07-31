package com.example.zaliczenie_projekt
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ListActivity : AppCompatActivity() {

    private lateinit var favorites: MutableSet<String>
    private lateinit var sharedPreferences: SharedPreferences
    private var shoppingLists = mutableListOf<MutableList<String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        sharedPreferences = getSharedPreferences("ShoppingApp", Context.MODE_PRIVATE)

        val shoppingListsTextView = findViewById<TextView>(R.id.shoppingListsTextView)

        // Odczytaj listy zakupów z pamięci aplikacji i wyświetl je w TextView
        loadListsFromMemory()
        updateShoppingListTextView()

        // Odczytaj listę ulubionych z pamięci aplikacji
        favorites = sharedPreferences.getStringSet("Favorites", mutableSetOf()) ?: mutableSetOf()

    }

    private fun updateShoppingListTextView() {
        val shoppingListTextView = findViewById<TextView>(R.id.shoppingListsTextView)
        val stringBuilder = StringBuilder()

        shoppingLists.forEach { list ->
            stringBuilder.append("lista:\n\n")
            stringBuilder.append(list.joinToString("\n"))
            stringBuilder.append("\n\n") // Dodajemy pusty wiersz między kolejnymi listami
        }

        shoppingListTextView.text = stringBuilder.toString()
    }

    private fun saveFavoritesToMemory() {
        val editor = sharedPreferences.edit()
        editor.putStringSet("Favorites", favorites)
        editor.apply()
    }

    private fun loadListsFromMemory() {
        val savedLists = sharedPreferences.getStringSet("ShoppingLists", setOf())
        shoppingLists.clear()

        if (savedLists != null) {
            savedLists.forEach { itemList ->
                val items = itemList.split("\n").toMutableList()
                shoppingLists.add(items)
            }
        }
    }
}