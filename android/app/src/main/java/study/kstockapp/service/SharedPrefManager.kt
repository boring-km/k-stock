package study.kstockapp.service

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager(
    context: Context,
    name: String
) {
    private var prefs: SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    fun findStringSet(key: String): MutableSet<String>? {
        return prefs.getStringSet(key, HashSet<String>())
    }

    fun saveStringSet(key: String, name: String) {
        val tempSet: MutableSet<String>? = prefs.getStringSet(key, HashSet<String>())
        tempSet!!.add(name)
        val editor = prefs.edit()
        editor.putStringSet("data", tempSet)
        editor.apply()
    }
}