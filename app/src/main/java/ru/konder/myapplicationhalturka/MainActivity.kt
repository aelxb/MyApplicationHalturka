package ru.konder.myapplicationhalturka

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import java.io.DataOutput
import java.io.IOException
import java.net.Socket

class MainActivity : AppCompatActivity() {
    val LOG_TAG = "MyApplicationHalturka"
    val mServerName = "127.0.0.1"
    val mServerPort = 8888
    var mSocket: Socket? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var mButtonOpen = findViewById<Button>(R.id.bOpen)
        mButtonOpen.setOnClickListener() {
            var mServer: LaptopSever? = LaptopSever()
            try {
                mServer.openConnection()
            } catch (e: IOException) {
                throw Exception(e.localizedMessage)
                mServer = null;
            }
        }
    }
}
class LaptopSever{
    val LOG_TAG = "MyApplicationHalturka"
    val mServerName = "127.0.0.1"
    val mServerPort = 8888
    var  mSocket: Socket? = null
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
    fun sendData(data:ByteArray)  {
        if (mSocket!!.isClosed()) {
            throw Exception("Невозможно отправить данные. Сокет не создан или закрыт");
        }
        try {
            mSocket!!.getOutputStream().write(data);
            mSocket!!.getOutputStream().flush();
        }
        catch (e: IOException) {
            throw Exception("Невозможно отправить данные: "+e.localizedMessage);
        }
    }
    fun finalize() {
        super.finish();
        closeConnection();
    }
}