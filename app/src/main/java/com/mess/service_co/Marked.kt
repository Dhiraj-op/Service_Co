import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.mess.service_co.*
import com.mess.service_co.R

class Marked : Fragment() {

    private lateinit var userRecyclerview: RecyclerView
    private lateinit var userArrayList: ArrayList<Persons>
    private lateinit var dbref: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view1 = inflater.inflate(R.layout.fragment_marked, container, false)

        userRecyclerview = view1.findViewById(R.id.personlist)
        userRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        userRecyclerview.setHasFixedSize(true)

        userArrayList = arrayListOf()
        getUserData()

        return view1
    }

    private fun getUserData() {
        dbref = FirebaseDatabase.getInstance().getReference("Worker List")

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    userArrayList.clear() // Clear the list before adding new data
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(Persons::class.java)
                        user?.let { userArrayList.add(it) }
                    }
                    userRecyclerview.adapter = PersonAdapter(userArrayList, requireContext())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled if needed
            }
        })
    }
}
