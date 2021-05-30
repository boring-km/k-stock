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
        val inputSet = HashSet<String>()
        tempSet!!.forEach { item -> inputSet.add(item) }
        inputSet.add(name)
        val editor = prefs.edit()
        editor.putStringSet(key, inputSet)
        editor.apply()
        editor.commit()
    }
}