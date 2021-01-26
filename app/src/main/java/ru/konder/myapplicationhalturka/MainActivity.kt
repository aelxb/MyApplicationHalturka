package ru.konder.myapplicationhalturka

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.io.IOException
import java.net.Socket

class MainActivity : AppCompatActivity() {
    val LOG_TAG = "MyApplicationHalturka"
    val mServerName = "127.0.0.1"
    val mServerPort = 8888
    var  mSocket: Socket? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun openConnection() {
        closeConnection()
        try {
            mSocket = Socket(mServerName, mServerPort)
        }
        catch (e: IOException) {
            throw  Exception("Невозможно создать сокет: "+ e.localizedMessage);
        }
    }
    fun closeConnection(){
        if(!mSocket!!.isClosed()){
            try {
                mSocket!!.close();
            }
            catch (e: IOException) {
                Log.e(LOG_TAG, "Невозможно закрыть сокет: " + e.localizedMessage);
            }
            finally {
                mSocket = null;
            }
        }
    }
}