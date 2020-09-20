package com.example.hotrepo.ui.trendingRepos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hotrepo.R
import com.example.hotrepo.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment which will show Trending Repo listing
 */
@AndroidEntryPoint
class RepoFragment : BaseFragment() {

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.repo_frag, container, false)
        return root
    }


}