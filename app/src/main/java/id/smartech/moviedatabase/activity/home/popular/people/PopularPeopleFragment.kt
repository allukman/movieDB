package id.smartech.moviedatabase.activity.home.popular.people

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.smartech.moviedatabase.R
import id.smartech.moviedatabase.activity.detailperson.DetailPersonActivity
import id.smartech.moviedatabase.base.BaseFragment
import id.smartech.moviedatabase.databinding.FragmentPopularPeopleBinding
import id.smartech.moviedatabase.model.PeopleModel
import id.smartech.moviedatabase.util.adapter.PeopleAdapter


class PopularPeopleFragment : BaseFragment<FragmentPopularPeopleBinding>() {
    private lateinit var viewModel: PopularPeopleViewModel
    private lateinit var adapter: PeopleAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private val listPeople = ArrayList<PeopleModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_popular_people
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        setViewModel()
        subscribeLiveData()
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this).get(PopularPeopleViewModel::class.java)
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
                Log.d("anu", list.toString())
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
        layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        bind.rvPopularPeople.layoutManager = layoutManager
        adapter =
                PeopleAdapter(listPeople)
        bind.rvPopularPeople.adapter = adapter


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