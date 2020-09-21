package com.example.hotrepo.ui.trendingRepos

import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.hotrepo.R
import com.example.hotrepo.ui.BaseFragment
import com.example.hotrepo.utility.SortUtils
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

/**
 * Fragment which will show Trending Repo listing
 */
@AndroidEntryPoint
class RepoFragment : BaseFragment() {

    private lateinit var toolbarView: Toolbar
    private lateinit var layoutError: ConstraintLayout
    private lateinit var layoutEmpty: ConstraintLayout
    private lateinit var layoutLoading: ConstraintLayout
    private lateinit var emptyRetry: Button
    private lateinit var errorRetry: Button

    private lateinit var root: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    @Inject lateinit var repoAdapter: TrendingRepoAdapter
    private val viewModel: TrendingRepoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.repo_frag, container, false)
        return root
    }

    override fun onViewCreated(root: View, savedInstanceState: Bundle?) {
        toolbarView = root.findViewById(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbarView)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        layoutError = root.findViewById(R.id.layout_error)
        layoutEmpty = root.findViewById(R.id.layout_empty)
        emptyRetry = layoutEmpty.findViewById(R.id.empty_retry)
        errorRetry = layoutError.findViewById(R.id.error_retry)
        layoutLoading = root.findViewById(R.id.layout_loading)
        recyclerView = root.findViewById(R.id.recycler_view)
        swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener(this::swipeRefresh)
        recyclerView.layoutManager= LinearLayoutManager(requireContext())
        recyclerView.adapter = repoAdapter

        emptyRetry.setOnClickListener {
            fetchTrendingRepo()
        }

        errorRetry.setOnClickListener {
            fetchTrendingRepo()
        }

        setUpObservers()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fetchTrendingRepo()
    }

    private fun setUpObservers() {
        viewModel.spinner.observe(viewLifecycleOwner, {
            showHideLoader(it)
        })
        viewModel.repoList.observe(viewLifecycleOwner, {
            inflateData(it)
        })

        viewModel.error.observe(viewLifecycleOwner, {
            showError()
        })

        viewModel.emptyView.observe(viewLifecycleOwner, {
            showEmptyView()
        })

        viewModel.hideZeroStateView.observe(viewLifecycleOwner, {
            hideZeroStateViews()
        })

        viewModel.swipeLoader.observe(viewLifecycleOwner, {
            swipeRefreshLayout.isRefreshing = false
        })

        viewModel.snackbar.observe(viewLifecycleOwner, {
            val msg: CharSequence = getString(R.string.offline_data)
            Snackbar.make(root, msg, Snackbar.LENGTH_SHORT).show()
        })

    }

    @ExperimentalCoroutinesApi
    private fun fetchTrendingRepo(){
        viewModel.getRepoList()
    }

    private fun swipeRefresh() {
        viewModel.doRefreshData(false)
    }

    private fun inflateData(list: List<RepoItemUIModel>) {
        if(list.isNotEmpty()){
            repoAdapter.setItems(list)
            return
        }
    }

    private fun showHideLoader(isVisible: Boolean) {
        if (isVisible) layoutLoading.visibility = View.VISIBLE
        else layoutLoading.visibility = View.GONE
    }

    private fun showEmptyView() {
        layoutEmpty.visibility= View.VISIBLE
    }

    private fun showError() {
        layoutError.visibility = View.VISIBLE
    }

    private fun hideZeroStateViews() {
        layoutError.visibility = View.GONE
        layoutEmpty.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sort_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.star -> {
                viewModel.setSortOrder(SortUtils.Sort.STAR)
                true
            }
            R.id.name -> {
                viewModel.setSortOrder(SortUtils.Sort.NAME)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}