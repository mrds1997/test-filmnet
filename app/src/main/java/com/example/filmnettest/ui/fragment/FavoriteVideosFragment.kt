package com.example.filmnettest.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.filmnettest.data.db.DbViewModelProviderFactory
import com.example.filmnettest.R
import com.example.filmnettest.data.db.DBRepository
import com.example.filmnettest.data.db.DataBaseClient
import com.example.filmnettest.data.db.DbViewModel
import com.example.filmnettest.data.network.models.Video
import com.example.filmnettest.ui.AppViewModel
import com.example.filmnettest.ui.activity.VideoDetailsActivity
import com.example.filmnettest.ui.adapter.FavoriteVideoAdapter
import com.example.filmnettest.ui.customs.Loading
import com.example.filmnettest.ui.customs.NoItem
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_favorite_videos.*
import kotlinx.android.synthetic.main.fragment_favorite_videos.view.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FavoriteVideosFragment : Fragment() {
    private var adapter: FavoriteVideoAdapter? = null
    private val videos = ArrayList<com.example.filmnettest.data.db.Video>()
    private val remoteServerVideos = ArrayList<Video>()
    lateinit var viewModel: AppViewModel
    var loading: Loading? = null
    private var isLoading = false
    private var endOfList = false
    private var searchQueryWord: String? = null
    var job: Job? = null
    lateinit var dbViewModel: DbViewModel
    private var noMore: NoItem? = null

    companion object {
        @JvmStatic
        fun newInstance(): FavoriteVideosFragment {
            return FavoriteVideosFragment()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_videos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModel()

        setUpObserver()

        initRecyclerAdapter()

        handleDeleteVideo()

    }

    private fun handleDeleteVideo() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val video = videos[position]
                videos.remove(video)
                viewLifecycleOwner.lifecycleScope.launch {
                    dbViewModel.deleteVideoFromDb(video)
                    adapter?.notifyDataSetChanged()
                }
                Snackbar.make(
                    layoutRoot,
                    getString(R.string.deleted_from_favorite),
                    Snackbar.LENGTH_LONG
                ).apply {
                    setAction(getString(R.string.cancell_deleting)) {
                        viewLifecycleOwner.lifecycleScope.launch {
                            dbViewModel.saveVideo(video)
                            adapter?.notifyDataSetChanged()
                        }
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(view?.rcFavoriteVideosList)
        }
    }


    private fun setUpViewModel() {
        val db = DataBaseClient.getInstance(requireContext())?.getAppDatabase()
        val dbRepository = DBRepository(db)
        val dbViewModelProviderFactory = DbViewModelProviderFactory(dbRepository)
        dbViewModel =
            ViewModelProvider(this, dbViewModelProviderFactory).get(DbViewModel::class.java)
    }

    private fun setUpObserver() {
        dbViewModel.getVideos()?.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                noMore?.dismiss()
                videos.clear()
                videos.addAll(it)
                adapter?.notifyDataSetChanged()
            } else {
                showNoFavoriteVideo()
            }
        })
    }

    private fun showNoFavoriteVideo() {
        noMore = NoItem.newInstance(view?.layoutRoot)
        noMore!!.setImage(R.drawable.pic_video)
            .setTitle(getString(R.string.no_favorite))
            .show()

    }

    private fun initRecyclerAdapter() {
        adapter = FavoriteVideoAdapter(videos, object : FavoriteVideoAdapter.OnItemSelected{
            override fun onSelect(id: String?) {
                showVideoDetailsActivity(id)
            }

        })
        view?.rcFavoriteVideosList?.adapter = adapter
    }


    private fun showVideoDetailsActivity(id: String?) {
        val intent = Intent(activity, VideoDetailsActivity::class.java)
        videos.forEach {
            remoteServerVideos.add(
                Video(id = it.videoId,title = it.title, summary = it.summary, rate = it.rate, categories = it.categories, coverImage = it.coverImage)
            )
        }
        intent.putExtra("videosJson", Gson().toJson(remoteServerVideos))
        intent.putExtra("id", id)
        intent.putExtra("forFavorite", 1)
        startActivity(intent)
    }

}