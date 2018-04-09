package net.blockchaind.smack.Controllers

import android.app.Application
import net.blockchaind.smack.Utilities.SharedPrefs

/**
 * Created by shiva on 2/18/2018.
 */
class App : Application() {

    companion object {
        lateinit var prefs : SharedPrefs
    }

    override fun onCreate() {
        prefs = SharedPrefs(applicationContext)
        super.onCreate()
    }

}