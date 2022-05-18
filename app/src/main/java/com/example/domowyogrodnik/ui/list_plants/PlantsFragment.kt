package com.example.domowyogrodnik.ui.list_plants


import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.domowyogrodnik.AddPlantActivity
import com.example.domowyogrodnik.Model
import com.example.domowyogrodnik.MyListAdapter
import com.example.domowyogrodnik.R
import com.example.domowyogrodnik.db.ClientDB
import com.example.domowyogrodnik.db.PlantsDB

class PlantsFragment : Fragment() {
    var button: Button? = null
    lateinit var listView : ListView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_plants, container, false)

        button = rootView.findViewById(R.id.button)

        button?.setOnClickListener{
            val intent = Intent (activity, AddPlantActivity::class.java)
            activity?.startActivity(intent)
        }

        listView = rootView.findViewById(R.id.listView)
        val list = mutableListOf<Model>()

        class LoadFromDB : AsyncTask<Void?, Void?, List<PlantsDB>?>() {
            override fun doInBackground(vararg p0: Void?): List<PlantsDB>? {
                //retrieve data from DB
                return ClientDB.getInstance(requireContext())?.appDatabase?.plantsDAO()?.allDesc()
            }

            override fun onPostExecute(db: List<PlantsDB>?) {
                super.onPostExecute(db)

                if (db != null) {
                    for (element in db) {
                        list.add(Model(element.name, "opisik", element.path!!, element))  //TODO
                    }

                    listView.adapter = MyListAdapter(requireContext(), R.layout.single_item, list)
                }
            }
        }

        LoadFromDB().execute()

        return rootView
    }
}
