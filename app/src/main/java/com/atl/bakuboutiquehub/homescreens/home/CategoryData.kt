package com.atl.bakuboutiquehub.homescreens.home

data class Category(
    val id: Int,
    val name: String,
    val count: String,
    val imageRes: Int
)

data class Product(
    val name: String,
    val price: String,
    val stock: String,
    val reviews: String,
    val imageRes: Int
)