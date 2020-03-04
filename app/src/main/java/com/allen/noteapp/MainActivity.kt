package com.allen.noteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tickets.view.*

class MainActivity : AppCompatActivity() {

    var listOfNotes = ArrayList<Notes>();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //add list of notes
        listOfNotes.add(Notes(1,"Dear Pastor","But, as most pastors will tell you, creating patterns from scratch can be a time-consuming process, especially if your goal is to create a seamless pattern. Luckily, the web offers plenty of resources to come to your aid and save you time on design projects."));
        listOfNotes.add(Notes(2,"Dear Doctor","But, as most doctor will tell you, creating patterns from scratch can be a time-consuming process, especially if your goal is to create a seamless pattern. Luckily, the web offers plenty of resources to come to your aid and save you time on design projects."));
        listOfNotes.add(Notes(3,"Dear Student","But, as most students will tell you, creating patterns from scratch can be a time-consuming process, especially if your goal is to create a seamless pattern. Luckily, the web offers plenty of resources to come to your aid and save you time on design projects."));


        //adding your list of notes to the adapter
        var myNotesAdapter = MyNotesBaseAdapter(listOfNotes)

        //connecting adapter with the activity layout
        ivNotes.adapter = myNotesAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item != null) {
            when(item.itemId){
                R.id.addNote -> {
                    //Go to add Page -- Here we will use intent which facilitates InterProcess Commun` btn activities
                    var intent = Intent(this,AddNotes::class.java)
                    startActivity(intent)

                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //Displaying listOfNotes to Activity
    inner class MyNotesBaseAdapter:BaseAdapter {
        var listOfNotesAdapter = ArrayList<Notes>()
        constructor(listOfNotesAdapter: ArrayList<Notes>):super(){
            this.listOfNotesAdapter = listOfNotesAdapter
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            //To connect myView & my code. i should use the following
            var myView = layoutInflater.inflate(R.layout.tickets,null);
            //Get the item that you wanna display
            var myItem = listOfNotesAdapter[position]

            //connecting the view components with the items in the array
            myView.tvTitle.text = myItem.noteName
            myView.tvDes.text = myItem.noteDes

            return myView
        }

        override fun getItem(position: Int): Any {
            return listOfNotesAdapter[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return listOfNotesAdapter.size
        }
    }


}
