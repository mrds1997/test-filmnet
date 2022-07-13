package com.example.filmnettest.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.colbeh.virtualphone.views.AppViewModelProviderFactory
import com.example.filmnettest.R
import com.example.filmnettest.data.network.ApiClient
import com.example.filmnettest.data.network.ApiService
import com.example.filmnettest.data.network.Repository
import com.example.filmnettest.data.network.Resource
import com.example.filmnettest.data.network.models.Video
import com.example.filmnettest.data.network.models.response.GetVideosResponse
import com.example.filmnettest.ui.AppViewModel
import com.example.filmnettest.ui.activity.VideoDetailsActivity
import com.example.filmnettest.ui.adapter.VideoAdapter
import com.example.filmnettest.ui.customs.Loading
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_videos.view.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class VideosFragment : Fragment() {
    private var adapter: VideoAdapter? = null
    private val videos = ArrayList<Video>()
    lateinit var viewModel: AppViewModel
    var loading: Loading? = null
    private var isLoading = false
    private var endOfList = false
    private var searchQueryWord: String? = null
    var job: Job? = null

    companion object {
        @JvmStatic
        fun newInstance(): VideosFragment {
            return VideosFragment()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_videos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModel()

        setUpObserver()

        initRecyclerAdapter()



        view.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                job?.cancel()
                s?.let {
                    if (it.toString().isNotEmpty()) {
                        searchQueryWord = it.toString()
                        getVideosList()

                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }
        })

    }

    private fun getVideosList() {
        //isLoading = true
        job = viewLifecycleOwner.lifecycleScope.launch {
            delay(2000L)
            viewModel.getMoviesList(searchQueryWord)
        }

    }

    private fun setUpViewModel() {
        val repository = Repository(ApiClient.getClient().create(ApiService::class.java))
        val viewModelProviderFactory = AppViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(AppViewModel::class.java)

        /* val db = DataBaseClient.getInstance(requireContext())?.getAppDatabase()
         val dbRepository = DBRepository(db)
         val dbViewModelProviderFactory = DbViewModelProviderFactory(dbRepository)
         dbViewModel = ViewModelProvider(this, dbViewModelProviderFactory).get(DbViewModel::class.java)*/
    }

    private fun setUpObserver() {
        viewModel.moviesList.observe(viewLifecycleOwner, object : Observer<Resource<GetVideosResponse>> {
                override fun onChanged(t: Resource<GetVideosResponse>?) {
                    //loading?.dismiss()
                    //isLoading = false
                    when (t) {
                        is Resource.Success -> {
                            loading?.dismiss()
                            loadDetails(t.data)
                        }
                        is Resource.Error -> {
                            loading?.dismiss()
                            Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                        }
                        is Resource.Loading -> {
                            loading = Loading.newInstance(view?.layoutRoot)
                        }

                    }
                }
        })
    }

    private fun initRecyclerAdapter() {
        adapter = VideoAdapter(videos, object : VideoAdapter.OnItemSelected {
            override fun onSelect(id: String?) {
                showVideoDetailsActivity(id)
            }

        })

        view?.rcMoviesList?.adapter = adapter

        /*val lm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        view?.rcMoviesList?.layoutManager = lm
        view?.rcMoviesList?.adapter = adapter
        view?.rcMoviesList?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val visibleItemCount: Int = lm.childCount
                    val totalItemCount: Int = lm.itemCount
                    val pastVisibleItems: Int = lm.findFirstVisibleItemPosition()

                    if (!isLoading && !endOfList) {
                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                            loading?.startLazy()
                            getVideosList()
                        }
                    }
                }
            }
        })*/

        //loading = Loading.newInstance(view?.layoutRoot)
        /*  calls.clear()
          getCallsList()*/
        //videos.clear()
    }

    private fun showVideoDetailsActivity(id: String?) {
        val intent = Intent(activity, VideoDetailsActivity::class.java)
        intent.putExtra("videosJson", Gson().toJson(videos))
        intent.putExtra("id", id)
        startActivity(intent)
    }

    private fun loadDetails(response: GetVideosResponse?) {
        if (response != null) {
            if (response.data != null) {
                if (response.data.videos != null && response.data.videos.isNotEmpty()) {
                    videos.clear()
                    videos.addAll(response.data.videos)
                    adapter?.notifyDataSetChanged()
                }
            }
            /*if(response.meta.remainingItemsCount == 0){
                endOfList = true
            }*/
        }
    }

}