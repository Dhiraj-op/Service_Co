import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.mess.service_co.PostAdapter
import com.mess.service_co.R
import com.mess.service_co.Posts

class Dashboard : Fragment() {

    private lateinit var userRecyclerview: RecyclerView
    private lateinit var userArrayList: ArrayList<Posts>
    private lateinit var dbref: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view1 = inflater.inflate(R.layout.fragment_dashboard, container, false)

        userRecyclerview = view1.findViewById(R.id.postlist)
        userRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        userRecyclerview.setHasFixedSize(true)

        userArrayList = arrayListOf()
        getUserData()

        return view1
    }

    private fun getUserData() {
        dbref = FirebaseDatabase.getInstance().getReference("Posts")

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    userArrayList.clear() // Clear the list before adding new data
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(Posts::class.java)
                        user?.let { userArrayList.add(it) }
                    }
                    userRecyclerview.adapter = PostAdapter(userArrayList, requireContext())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled if needed
            }
        })
    }
}
