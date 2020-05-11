package com.allen.noteapp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.SearchView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tickets.view.*

class MainActivity : AppCompatActivity() {

    var listOfNotes = ArrayList<Note>();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //add list of notes
        listOfNotes.add(Note(1,"Dear Pastor","But, as most pastors will tell you, creating patterns from scratch can be a time-consuming process, especially if your goal is to create a seamless pattern. Luckily, the web offers plenty of resources to come to your aid and save you time on design projects."));
        listOfNotes.add(Note(2,"Dear Doctor","But, as most doctor will tell you, creating patterns from scratch can be a time-consuming process, especially if your goal is to create a seamless pattern. Luckily, the web offers plenty of resources to come to your aid and save you time on design projects."));
        listOfNotes.add(Note(3,"Dear Student","But, as most students will tell you, creating patterns from scratch can be a time-consuming process, especially if your goal is to create a seamless pattern. Luckily, the web offers plenty of resources to come to your aid and save you time on design projects."));



        Toast.makeText(this,"onCreate",Toast.LENGTH_LONG).show()
        LoadQuery("%")

    }

    override fun onResume() {
        super.onResume()
        LoadQuery("%")
        Toast.makeText(this,"onResume",Toast.LENGTH_LONG).show()
    }

    override fun onStart() {
        super.onStart()
        Toast.makeText(this,"onStart",Toast.LENGTH_LONG).show()
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(this,"onPause",Toast.LENGTH_LONG).show()
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(this,"onStop",Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this,"onDestroy",Toast.LENGTH_LONG).show()
    }


    fun LoadQuery(title:String){

        //Load from the databse
        var dbManager= DbManager(this)
        val projections = arrayOf("ID","Title","Description")

        val selectionArgs = arrayOf(title)

        val cursor = dbManager.Query(projections,"Title like ?",selectionArgs,"Title")

        listOfNotes.clear()
        if(cursor.moveToFirst()){

            do{
                val ID=cursor.getInt(cursor.getColumnIndex("ID"))
                val Title=cursor.getString(cursor.getColumnIndex("Title"))
                val Description=cursor.getString((cursor.getColumnIndex("Description")))
                listOfNotes.add(Note(ID,Title,Description))

            }while (cursor.moveToNext())
            //adding your list of notes to the adapter
            var myNotesAdapter = MyNotesBaseAdapter(this,listOfNotes)

            //connecting adapter with the activity layout
            ivNotes.adapter = myNotesAdapter
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)

        //finding the item to search
        val sv = menu!!.findItem(R.id.app_bar_search).actionView as SearchView

        //To Get Searched Item and show it on screen
        val sm = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        sv.setSearchableInfo(sm.getSearchableInfo(componentName))
        sv.setOnQueryTextListener(object: SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(applicationContext, query, Toast.LENGTH_LONG).show()
                //TODO: Search Database
                LoadQuery("%"+ query + "%")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
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
        var listOfNotesAdapter = ArrayList<Note>()
        var context:Context?=null;
        constructor(context:Context,listOfNotesAdapter: ArrayList<Note>):super(){
            this.listOfNotesAdapter = listOfNotesAdapter
            this.context=context
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            //To connect myView & my code. i should use the following
            var myView = layoutInflater.inflate(R.layout.tickets,null);
            //Get the item that you wanna display
            var myItem = listOfNotesAdapter[position]

            //connecting the view components with the items in the array
            myView.tvTitle.text = myItem.noteName
            myView.tvDes.text = myItem.noteDes
            myView.IvDelete.setOnClickListener(View.OnClickListener {
               var dbManager=DbManager(this.context!!)
                val selectionArgs = arrayOf(myItem.noteID.toString())
                dbManager.Delete("ID=?",selectionArgs)
                //call loadQuery
                LoadQuery("%")
            })
            myView.IvEdit.setOnClickListener(View.OnClickListener {
                GoToUpdate(myItem)
            })

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

    fun GoToUpdate(note:Note){
        var intent = Intent(this,AddNotes::class.java)
        intent.putExtra("ID",note.noteID)
        intent.putExtra("Name",note.noteName)
        intent.putExtra("Des",note.noteDes)
        startActivity(intent)
    }


}
