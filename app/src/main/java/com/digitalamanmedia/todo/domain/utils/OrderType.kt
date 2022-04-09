package com.digitalamanmedia.todo.domain.utils

sealed class OrderType{
    object Ascending: OrderType()
    object Descending: OrderType()
}
