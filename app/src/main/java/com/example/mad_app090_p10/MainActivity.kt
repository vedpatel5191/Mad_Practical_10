package com.example.mad_app090_p10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mad_app090_p10.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val data = HttpRequest().makeServiceCall(
                        "https://api.json-generator.com/templates/EQ-I7-g55etR/data",
                        "c6v0zd5hcrtbhztoqfq22836w3buikp5fzusw92c")
                    withContext(Dispatchers.Main) {
                        try {
                            if(data != null){
                                getPersonDetailsFromJson(data)
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
    private fun getPersonDetailsFromJson(data: String) {
        val personList = ArrayList<PersonListCardModel>()
        try{
            val jsonArray = JSONArray(data)
            for(i in 0 until jsonArray.length()){
                val jsonObject = jsonArray[i] as JSONObject
                val person = PersonListCardModel(jsonObject)
                personList.add(person)
            }
        }catch (e: Exception) {
            e.printStackTrace()
        }
        val adapter = RecyclerPersonlistAdapter(this, personList)
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.adapter = adapter
    }
}

