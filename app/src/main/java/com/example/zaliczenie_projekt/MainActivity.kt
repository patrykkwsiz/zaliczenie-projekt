package com.example.zaliczenie_projekt
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val shoppingList = mutableListOf<String>()
    private val shoppingLists = mutableListOf<List<String>>()
    private lateinit var shoppingListEditText: EditText
    private lateinit var addToListButton: Button
    private lateinit var saveListButton: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("ShoppingApp", Context.MODE_PRIVATE)
        shoppingListEditText = findViewById(R.id.shoppingListEditText)
        addToListButton = findViewById(R.id.addToListButton)
        saveListButton = findViewById(R.id.saveListButton)

        addToListButton.setOnClickListener {
            val item = shoppingListEditText.text.toString()
            if (item.isNotBlank()) {
                shoppingList.add(item)
                updateShoppingListTextView()
                shoppingListEditText.text.clear()
            }
        }

        saveListButton.setOnClickListener {
            if (shoppingList.isNotEmpty()) {
                shoppingLists.add(shoppingList.toList()) // Dodajemy kopię listy zakupów do listy list
                saveListToMemory()
            }
            clearShoppingList()
        }

        val goToNextScreenButton = findViewById<Button>(R.id.goToSecondActivityButton)
        goToNextScreenButton.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }

        // Dodaj pustą listę zakupów do początkowej listy list
        shoppingList.add("")
    }

    private fun updateShoppingListTextView() {
        val shoppingListTextView = findViewById<TextView>(R.id.shoppingListTextView)
        shoppingListTextView.text = shoppingList.joinToString("\n")
    }

    private fun saveListToMemory() {
        val editor = sharedPreferences.edit()
        val savedLists = shoppingLists.map { list -> list.joinToString("\n") }
        editor.putStringSet("ShoppingLists", savedLists.toSet())
        editor.apply()
    }

    private fun clearShoppingList() {
        shoppingList.clear()
        shoppingList.add("")
        updateShoppingListTextView()
    }
}
