package com.example.homoma

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.database.*


class HomeFragment : Fragment(){
    lateinit var textView: TextView
    lateinit var transactionsRef: DatabaseReference
    lateinit var membersRef: DatabaseReference
    lateinit var categoriesRef: DatabaseReference
    lateinit var mainTable: TableLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView = view.findViewById(R.id.home_frag_text)
        textView.text  = "Home"
        mainTable = view.findViewById(R.id.HomeTable)

        val database = FirebaseDatabase.getInstance()

        transactionsRef = database.getReference("Transactions")
        membersRef = database.getReference("Members")
        categoriesRef = database.getReference("Categories")

        ReadLastData(view)
    }

    private fun ReadLastData(view: View){
        val lastQuery: Query = transactionsRef.orderByChild("beginDate").limitToLast(5)



        var snapMembers: DataSnapshot? = null
        var snapCategories: DataSnapshot? = null

        //Listener for table Members
        membersRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshotM: DataSnapshot) {
                snapMembers = dataSnapshotM

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        //Listener for table Categories
        categoriesRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshotC: DataSnapshot) {
                snapCategories = dataSnapshotC

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        //Listener for table Transactions
        lastQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) { // This method is called once with the initial value and again
                // whenever data at this location is updated.

                var row: TableRow

                var tv1: TextView
                var tv2: TextView
                var tv3: TextView
                var tv4: TextView
                var tv5: TextView
                var tv6: TextView

                var memberID: String
                var categoryID: String
                var bDate: String
                var memberFirstname: String
                var category: String
                var transactName: String
                var amount: String
                var i = 0

                for(data in dataSnapshot.children){

                    row = TableRow(view.context)

                    tv1 = TextView(view.context)
                    tv2 = TextView(view.context)
                    tv3 = TextView(view.context)
                    tv4 = TextView(view.context)
                    tv5 = TextView(view.context)
                    tv6 = TextView(view.context)

                    row.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    tv1.apply { width = 200 }
                    tv2.apply { width = 200 }
                    tv3.apply { width = 200 }
                    tv4.apply { width = 200 }
                    tv5.apply { width = 80 }
                    tv6.apply { width = 80 }

                    if(i == 0){
                        row.setBackgroundColor(Color.LTGRAY);
                        i = 1
                    }
                    else{
                        row.setBackgroundColor(Color.GRAY);
                        i=0
                    }

                    memberID = data.child("id_Member").value.toString()
                    categoryID = data.child("id_Category").value.toString()

                    bDate = data.child("beginDate").value.toString()

                    if(snapMembers!=null){
                        memberFirstname = snapMembers!!.child(memberID).child("firstname").value.toString()
                    }else{
                        memberFirstname = "not loaded yet"

                        RefreshFrag()
                    }
                    if(snapCategories!=null){
                        category = snapCategories!!.child(categoryID).child("name").value.toString()
                    }else{
                        category = "not loaded yet"

                        RefreshFrag()
                    }





                    transactName = data.child("name").value.toString()
                    amount = data.child("amount").value.toString()


                    tv1.text = bDate
                    tv2.text = memberFirstname
                    tv3.text = category
                    tv4.text = transactName

                    if(data.child("income").value!!.equals(true)){
                        tv5.text = ""
                        tv6.text = amount
                    }
                    else{
                        tv5.text = amount
                        tv6.text = ""
                    }

                    row.addView(tv1)
                    row.addView(tv2)
                    row.addView(tv3)
                    row.addView(tv4)
                    row.addView(tv5)
                    row.addView(tv6)

                    mainTable.addView(row)
                }

            }



            override fun onCancelled(error: DatabaseError) { // Failed to read value

            }
        })

    }

    private fun RefreshFrag(){
        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.myFragment, HomeFragment())
        fragmentTransaction.commit()
    }

}