package com.prashant.weatherreportapp.repositories

import androidx.lifecycle.MutableLiveData
import com.prashant.weatherreportapp.utils.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * @Author: Prshant G. Gupta
 * @Date: 18-07-2021
 */
open class BaseRepository : CoroutineScope {

    var state: MutableLiveData<State> = MutableLiveData()

    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
}
