package fr.epita.pauld.hellogames

import retrofit2.Call
import retrofit2.http.GET
import java.util.*

interface  WSInterface {
    @GET ("games")
    fun get4RandomGames() : Call<List<Objects>>
}
