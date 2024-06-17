package com.example.listhotels.data.network

import com.example.listhotels.util.Resource
import com.example.listhotels.data.Response

interface NetworkClient {

    suspend fun doRequest(dto: Request): Resource<Response>
}