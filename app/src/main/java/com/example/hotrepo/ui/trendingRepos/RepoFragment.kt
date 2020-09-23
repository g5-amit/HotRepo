package com.example.hotrepo.ui.trendingRepos

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotrepo.R
import com.example.hotrepo.base.BaseFragment
import com.example.hotrepo.data.repository.TrendingRepository
import com.example.hotrepo.databinding.RepoFragBinding
import com.example.hotrepo.utility.SortUtils
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Fragment which will show Trending Repo listing
 */
@AndroidEntryPoint
class RepoFragment : BaseFragment() {

    private lateinit var binding: RepoFragBinding

    @Inject  lateinit var repoAdapter: TrendingRepoAdapter

    @Inject  lateinit var trendingRepository: TrendingRepository

    /**
     * Need to pass repository Explicitly to support FakeRepository for test cases
     * means Repository Dependency should be injected from outside to view model
     * */
    private val viewModel by viewModels<TrendingRepoViewModel> {
        TrendingViewModelFactory(trendingRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RepoFragBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = lifecycleOwner
            viewModel = viewModel
        }
        return binding.root
    }

    override fun onViewCreated(root: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.swipeRefreshLayout.setOnRefreshListener(this::swipeRefresh)
        binding.recyclerView.layoutManager= LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = repoAdapter
        setUpObservers()
        binding.layoutEmpty.emptyRetry.setOnClickListener {
            fetchTrendingRepo()
        }

        binding.layoutError.errorRetry.setOnClickListener {
            fetchTrendingRepo()
        }
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
            binding.swipeRefreshLayout.isRefreshing = false
        })

        viewModel.snackbar.observe(viewLifecycleOwner, {
            val msg: CharSequence = getString(R.string.offline_data)
            Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
        })

    }
    private fun fetchTrendingRepo(){
        viewModel.getTrendingRepoList()
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
        if (isVisible) binding.layoutLoading.visibility = View.VISIBLE
        else binding.layoutLoading.visibility = View.GONE
    }

    private fun showEmptyView() {
        binding.layoutEmpty.root.visibility= View.VISIBLE
    }

    private fun showError() {
        binding.layoutError.root.visibility = View.VISIBLE
    }

    private fun hideZeroStateViews() {
        binding.layoutEmpty.root.visibility= View.GONE
        binding.layoutError.root.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sort_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_star -> {
                viewModel.setSortOrder(SortUtils.Sort.STAR)
                true
            }
            R.id.menu_name -> {
                viewModel.setSortOrder(SortUtils.Sort.NAME)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}