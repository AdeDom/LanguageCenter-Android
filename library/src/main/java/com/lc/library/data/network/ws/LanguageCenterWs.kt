package com.lc.library.data.network.ws

import com.google.gson.Gson
import com.lc.library.sharedpreference.pref.PreferenceAuth
import com.lc.server.models.model.TalkSendMessageWebSocket
import com.lc.server.util.LanguageCenterConstant
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import io.ktor.util.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow

@KtorExperimentalAPI
class LanguageCenterWs(
    private val client: HttpClient,
    private val pref: PreferenceAuth,
) {

    private var sendMessageSocket: WebSocketSession? = null

    suspend fun incomingSendMessageSocket(listener: suspend (TalkSendMessageWebSocket) -> Unit) {
        client.wss(
            method = HttpMethod.Get,
            host = "languagecenter.herokuapp.com",
            port = DEFAULT_PORT,
            path = "/ws/chats/send-message",
            request = {
                header(LanguageCenterConstant.ACCESS_TOKEN, pref.accessToken)
            }
        ) {
            sendMessageSocket = this
            try {
                incoming
                    .receiveAsFlow()
                    .catch {
                    }
                    .collect { frame ->
                        val text = (frame as Frame.Text).readText()
                        val fromJson = Gson().fromJson(text, TalkSendMessageWebSocket::class.java)
                        listener.invoke(fromJson)
                    }
            } catch (e: Throwable) {
            } finally {
                sendMessageSocket = null
            }
        }
    }

    suspend fun outgoingSendMessageSocket(talkSendMessageWebSocket: TalkSendMessageWebSocket) {
        val toJson = Gson().toJson(talkSendMessageWebSocket)
        sendMessageSocket?.outgoing?.send(Frame.Text(toJson))
    }

}
