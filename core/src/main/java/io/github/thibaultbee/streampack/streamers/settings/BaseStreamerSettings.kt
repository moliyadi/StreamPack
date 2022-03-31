package io.github.thibaultbee.streampack.streamers.settings

import io.github.thibaultbee.streampack.internal.encoders.AudioMediaCodecEncoder
import io.github.thibaultbee.streampack.internal.encoders.VideoMediaCodecEncoder
import io.github.thibaultbee.streampack.internal.sources.IAudioCapture
import io.github.thibaultbee.streampack.streamers.interfaces.settings.IAudioSettings
import io.github.thibaultbee.streampack.streamers.interfaces.settings.IBaseStreamerSettings
import io.github.thibaultbee.streampack.streamers.interfaces.settings.IVideoSettings

open class BaseStreamerSettings(
    audioCapture: IAudioCapture?,
    audioEncoder: AudioMediaCodecEncoder?,
    videoEncoder: VideoMediaCodecEncoder?
) : IBaseStreamerSettings {
    /**
     * Get audio settings
     */
    override val audio = BaseStreamerAudioSettings(audioCapture, audioEncoder)

    /**
     * Get video settings
     */
    override val video = BaseStreamerVideoSettings(videoEncoder)
}

class BaseStreamerVideoSettings(private val videoEncoder: VideoMediaCodecEncoder?) :
    IVideoSettings {
    /**
     * Get/set video bitrate.
     * Do not set this value if you are using a bitrate regulator.
     */
    override var bitrate: Int
        /**
         * @return video bitrate in bps
         */
        get() = videoEncoder?.bitrate ?: 0
        /**
         * @param value video bitrate in bps
         */
        set(value) {
            videoEncoder?.let { it.bitrate = value }
        }
}

class BaseStreamerAudioSettings(
    private val audioCapture: IAudioCapture?,
    private val audioEncoder: AudioMediaCodecEncoder?
) :
    IAudioSettings {
    /**
     * Get/set audio bitrate.
     * Do not set this value if you are using a bitrate regulator.
     */
    override var bitrate: Int
        /**
         * @return audio bitrate in bps
         */
        get() = audioEncoder?.bitrate ?: 0
        /**
         * @param value audio bitrate in bps
         */
        set(value) {
            audioEncoder?.let { it.bitrate = value }
        }

    /**
     * Get/set audio mute
     */
    override var isMuted: Boolean
        /**
         * @return [Boolean.true] if audio is muted, [Boolean.false] if audio is running.
         */
        get() = audioCapture?.isMuted ?: true
        /**
         * @param value [Boolean.true] to mute audio, [Boolean.false]to unmute audio.
         */
        set(value) {
            audioCapture?.isMuted = value
        }
}