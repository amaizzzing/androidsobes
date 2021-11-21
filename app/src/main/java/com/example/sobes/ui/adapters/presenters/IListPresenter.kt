package com.example.sobes.ui.adapters.presenters

interface IListPresenter<V> {
    var itemClickListener: ((V) -> Unit)?

    fun bindView(view: V)

    fun getCount(): Int
}