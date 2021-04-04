package com.github.thibaultbee.streampack.endpoints

import com.github.thibaultbee.srtdroid.Srt
import com.github.thibaultbee.srtdroid.enums.SockOpt
import com.github.thibaultbee.srtdroid.enums.Transtype
import com.github.thibaultbee.srtdroid.models.Socket
import com.github.thibaultbee.streampack.data.Packet
import com.github.thibaultbee.streampack.utils.Logger
import java.net.ConnectException

class SrtProducer(val logger: Logger) : IEndpoint {
    private var socket = Socket()
    private var bitrate = 0

    companion object {
        private const val PAYLOAD_SIZE = 1316
    }

    override fun configure(startBitrate: Int) {
        this.bitrate = startBitrate
    }

    fun connect(ip: String, port: Int) {
        socket = Socket()
        socket.setSockFlag(SockOpt.PAYLOADSIZE, PAYLOAD_SIZE)
        socket.setSockFlag(SockOpt.TRANSTYPE, Transtype.LIVE)
        socket.connect(ip, port)
    }

    fun disconnect() {
        socket.close()
    }

    override fun write(packet: Packet) {
        socket.send(packet.buffer)
    }

    override fun startStream() {
        // socket.setSockFlag(SockOpt.INPUTBW, bitrate)
        if (!socket.isConnected)
            throw ConnectException("SrtEndpoint should be connected at this point")
    }

    override fun stopStream() {

    }

    override fun release() {
        Srt.cleanUp()
    }
}