package com.example.hotrepo.ui.trendingRepos

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.hotrepo.data.room.entity.TrendingRepoEntity
import com.example.hotrepo.data.network.utils.Resource
import com.example.hotrepo.data.repository.TrendingRepository
import com.example.hotrepo.utility.Constants
import com.example.hotrepo.utility.SortUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Responsible for writing all logic to bind data in UI
 * Interact between UI layer and Data Layer
 * Update UI component with the help of LifeCycle aware Component as Live data
 * */
class TrendingRepoViewModel @ViewModelInject constructor(
    private val trendingRepository: TrendingRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    /**
     *Default Sorting order is Stars
     */
    private var sortOrder = SortUtils.Sort.STAR

    /**
     * Request a snackbar to display a string.
     */
    private val _snackbar = MutableLiveData<Boolean>()
    val snackbar: LiveData<Boolean>
        get() = _snackbar

    /**
     * Show a loading spinner if true
     */
    private val _spinner = MutableLiveData(false)
    val spinner: LiveData<Boolean>
        get() = _spinner

    /**
     * Hide a Pull to refresh Loader if data is refreshed
     * */
    private val _swipeLoader = MutableLiveData<Boolean>()
    val swipeLoader: LiveData<Boolean>
        get() = _swipeLoader

    /**
     * Show a Error Layout if Error occurred while fetching data
     * */
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?>
        get() = _error

    /**
     * Show a Error Layout if Error occurred while fetching data
     * */
    private val _emptyView = MutableLiveData<Boolean>()
    val emptyView: LiveData<Boolean>
        get() = _emptyView

    /**
     * Show a Error Layout if Error occurred while fetching data
     * */
    private val _hideZeroStateView = MutableLiveData<Boolean>()
    val hideZeroStateView: LiveData<Boolean>
        get() = _hideZeroStateView

    /**
     * Show Repo Listing on UI either from DB or Network
     */
    private val _repoList = MutableLiveData<List<RepoItemUIModel>>()
    val repoList: LiveData<List<RepoItemUIModel>>
        get() = _repoList

    /**
     * Logic to make UI reactive using LiveData after fetching data
     */
    @ExperimentalCoroutinesApi
    fun getRepoList() {
        viewModelScope.launch {
            trendingRepository.getTrendingRepoList()
                .onStart {
                    Log.d("Amit", "OnStart")
                    _spinner.value = true
                    _hideZeroStateView.value = true
                }
                .onEach {trendingRepoList->
                    Log.d("Amit", "OnEach")
                    if(trendingRepoList.isEmpty() || isStaleData(trendingRepoList[0])){
                        Log.d("Amit", "emptyStale")
                        doRefreshData(trendingRepoList.isEmpty())
                    }else {
                        Log.d("Amit", "sanitize")
                        sanitizeItemsForUI(trendingRepoList)
                    }
                }
                //onCompletion tells whether flow is completed successfully or with exception
                // It works upstream and downstream as well
                // can be used for removing resources after flow is completed
                .onCompletion { cause: Throwable? ->
                    Log.d("Amit", "OnComplete")
                    if(cause!=null)Log.d("Flow", "Exception")
                }
                // catch operator is applied till all upStream operator to handle exception,
                // so use onEach() operator instead of collect operator for handling response
                // catch operator must be applied before collect
                .catch { cause ->
                    Log.d("Flow", "Caught Exception ${cause.printStackTrace()}")
                    _error.value = cause.message
                    _spinner.value = false
                }
                .collect()
        }
    }

    /**
     * Refresh Repo Data by calling Network Api
     * Once Api response is updated into DB, it will reflect on UI using Room DB reactive Flow
     * This method will be called If Data is stale, swipe refreshed, or DB data is empty
     * */
    fun doRefreshData(isEmptyViewRequired: Boolean){
        viewModelScope.launch {
            val responseStatus = withContext(Dispatchers.IO){
                trendingRepository.doNetworkJob()
            }
            _swipeLoader.value = false
            _spinner.value = false
            when(responseStatus.status) {
                Resource.Status.OFFLINE -> {
                    _snackbar.value = true
                    if(isEmptyViewRequired) {_emptyView.value = true}
                }
                Resource.Status.SUCCESS -> {
                    if(responseStatus.data == null){
                        _emptyView.value = true
                    }
                    responseStatus.data?.let {
                        if(it.isEmpty())
                            _emptyView.value = true
                    }
                }
                Resource.Status.ERROR -> {
                    _error.value = responseStatus.message
                }
            }
        }
    }

    /**
     * Cache data is checked whether it is longer than 2 hrs or not
     */
    private fun isStaleData(repoEntity: TrendingRepoEntity): Boolean {
        return System.currentTimeMillis() - repoEntity.timeStamp > Constants.STALE_TIME
    }

    /**
     * parse Repo Items into UI Items and sort accordingly
     */
    private fun sanitizeItemsForUI(trendingRepoList : List<TrendingRepoEntity>){
        viewModelScope.launch {
            _repoList.value = withContext(Dispatchers.IO) {
                if(sortOrder == SortUtils.Sort.NAME) {
                    trendingRepoList.map { RepoItemUIModel.parseToUIData(it) }
                        .sortedBy { it.name }
                }else{
                    trendingRepoList.map { RepoItemUIModel.parseToUIData(it) }
                        .sortedByDescending { it.stars }
                }
            }
            _spinner.value = false
            _hideZeroStateView.value = true
        }

    }

    @ExperimentalCoroutinesApi
    fun setSortOrder(order: SortUtils.Sort){
        if(sortOrder != order){
            sortOrder = order
            getRepoList()
        }

    }
}
