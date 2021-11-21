package com.example.sobes.lesson2.data.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import java.io.IOException

const val MAX_ITEMS = 25
const val FIRST_INDEX_ITEM = 1

abstract class BasePagingSource<T: Any>: PagingSource<Int, T>() {
    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val pageIndex = params.key ?: FIRST_INDEX_ITEM
        return try {
            getData(pageIndex, params)
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    abstract suspend fun getData(pageIndex: Int, params: LoadParams<Int>): LoadResult.Page<Int, T>
}