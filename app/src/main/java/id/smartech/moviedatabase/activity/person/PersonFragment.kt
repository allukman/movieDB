package id.smartech.moviedatabase.activity.person

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import id.smartech.moviedatabase.R
import id.smartech.moviedatabase.activity.detailperson.DetailPersonActivity
import id.smartech.moviedatabase.util.adapter.PeopleAdapter
import id.smartech.moviedatabase.base.BaseFragment
import id.smartech.moviedatabase.databinding.FragmentPersonBinding
import id.smartech.moviedatabase.model.PeopleModel

class PersonFragment : BaseFragment<FragmentPersonBinding>() {
    private lateinit var viewModel: PersonViewModel
    private lateinit var adapter: PeopleAdapter
    private val listPeople = ArrayList<PeopleModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_person
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        setViewModel()
        subscribeLiveData()
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this).get(PersonViewModel::class.java)
        viewModel.setService(createApi(activity))
        viewModel.getPopularPeople()
    }

    private fun subscribeLiveData() {
        activity?.let {
            viewModel.isLoadingLiveData.observe(it) { isLoading ->
                if (isLoading) {
                    bind.progressBar.visibility = View.VISIBLE
                } else {
                    bind.progressBar.visibility = View.GONE
                }
            }
        }

        activity?.let {
            viewModel.onSuccessLiveData.observe(it) { list ->
                adapter.addList(list)
            }
        }

        activity?.let {
            viewModel.onFailLiveData.observe(it) { message ->
                noticeToast(message)
            }
        }
    }

    private fun setRecyclerView() {
        bind.rvPopularPeople.isNestedScrollingEnabled = false
        adapter = PeopleAdapter(listPeople)
        bind.rvPopularPeople.adapter = adapter

        val layoutManager = FlexboxLayoutManager(activity)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        bind.rvPopularPeople.layoutManager = layoutManager

        adapter.setOnItemClickCallback(object :
            PeopleAdapter.OnItemClickCallback {
            override fun onClickItem(data: PeopleModel) {
                val intent = Intent(activity, DetailPersonActivity::class.java)
                intent.putExtra("person_id", data.id)
                startActivity(intent)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.getPopularPeople()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getPopularPeople()
    }

}