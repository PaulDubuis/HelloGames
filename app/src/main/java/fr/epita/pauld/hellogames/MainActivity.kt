package fr.epita.pauld.hellogames

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import fr.epita.pauld.hellogames.ui.main.MainFragment
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.main_fragment.*
import java.util.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), View.OnClickListener {

    fun IntRange.random() = Random().nextInt((endInclusive + 1) - start) + start

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        val retrofitClient = Retrofit.Builder()
            .baseUrl("https://androidlessonsapi.herokuapp.com/game/details/?id=1")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()

        val service = retrofitClient.create(WSInterface::class.java)

        val callback : Callback<List<Objects>> = object : Callback<List<Objects>> {

            override fun onFailure(call: Call<List<Objects>>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Objects>>, response: Response<List<Objects>>) {
                if (response.code() == 200)
                {
                    val data : List<Objects> = response.body()!!
                    Toast.makeText(this@MainActivity, "Id : " + data[0].toString() + ", name : " + data[1], LENGTH_SHORT).show()
                }
            }
        }


        imgBt1.setOnClickListener(this@MainActivity)
        imgBt2.setOnClickListener(this@MainActivity)
        imgBt3.setOnClickListener(this@MainActivity)
        imgBt4.setOnClickListener(this@MainActivity)

    }

    override fun onClick(v: View?) {
        if (v != null)
        {
            when (v.id)
            {
                R.id.imgBt1 -> {
                    val retrofitClient = Retrofit.Builder()
                        .baseUrl("https://jsonplaceholder.typicode.com/ /game/list")
                        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                        .build()

                    val service = retrofitClient.create(WSInterface::class.java)

                    val callback : Callback<List<Objects>> = object : Callback<List<Objects>> {

                        override fun onFailure(call: Call<List<Objects>>, t: Throwable) {
                            Toast.makeText(this@MainActivity, t.message, LENGTH_SHORT).show()
                        }

                        override fun onResponse(call: Call<List<Objects>>, response: Response<List<Objects>>) {
                            if (response.code() == 200)
                            {
                                val data : List<Objects> = response.body()!!
                                Toast.makeText(this@MainActivity, "Size is " + data.size, LENGTH_SHORT).show()
                            }
                        }

                    }

                    service.get4RandomGames().enqueue(callback)
                }
            }
        }
    }

}
