package life.knowledge4.videotrimmer.utils;


import android.content.Context;
import android.net.Uri;
import android.util.Log;

import org.m4m.android.AndroidMediaObjectFactory;
import org.m4m.android.AudioFormatAndroid;
import org.m4m.android.VideoFormatAndroid;
import org.m4m.domain.MediaCodecInfo;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

public class TranscodeVideoUtils {

    protected static String srcMediaName1 = null;
    protected static String srcMediaName2 = null;
    protected static String dstMediaPath = null;
    protected static org.m4m.Uri mediaUri1;
    protected static org.m4m.Uri mediaUri2;

    protected static org.m4m.MediaFileInfo mediaFileInfo = null;

    protected static long duration = 0;


    ///////////////////////////////////////////////////////////////////////////

    protected static org.m4m.AudioFormat audioFormat = null;
    protected static org.m4m.VideoFormat videoFormat = null;

    // Transcode parameters

    // Video
    public static int videoWidthOut;
    public static int videoHeightOut;

    protected static int videoWidthIn = 640;
    protected static int videoHeightIn = 480;

    protected static final String videoMimeType = "video/avc";
    public static int videoBitRateInKBytes = 5000;
    public static float videoBitRateInKBytesOut = 5000;
    protected static final int videoFrameRate = 30;

    protected static final int videoIFrameInterval = 1;
    // Audio
    protected static final String audioMimeType = "audio/mp4a-latm";
    protected static final int audioSampleRate = 44100;
    protected static final int audioChannelCount = 2;

    protected static final int audioBitRate = 96 * 1024;


    ///////////////////////////////////////////////////////////////////////////

    // Media Composer parameters and logic

    protected static org.m4m.MediaComposer mediaComposer;

    private static boolean isStopped = false;

    protected static AndroidMediaObjectFactory factory;

    public static void setResolutionAndQuality(int outQuality) {
        if (outQuality == 1) {
            if (videoHeightIn < videoWidthIn) {
                if (videoHeightIn > 300) {
                    videoWidthOut = (int) ((videoWidthIn) / round((videoHeightIn / 300f), 2));
                    videoHeightOut = 300;
                } else {
                    videoHeightOut = (int) ((videoHeightIn) / round((videoWidthIn / 300f), 2));
                    videoWidthOut = 300;
                }
            } else if (videoWidthIn > 300) {
                videoHeightOut = (int) ((videoHeightIn) / round((videoWidthIn / 300f), 2));
                videoWidthOut = 300;
            } else {
                videoWidthOut = (int) ((videoWidthIn) / round((videoHeightIn / 300f), 2));
                videoHeightOut = 300;
            }
        }

        if (outQuality == 2) {
            if (videoHeightIn < videoWidthIn) {
                if (videoHeightIn > 360) {
                    videoWidthOut = (int) ((videoWidthIn) / round((videoHeightIn / 360f), 2));
                    videoHeightOut = 360;
                } else {
                    videoHeightOut = (int) ((videoHeightIn) / round((videoWidthIn / 360f), 2));
                    videoWidthOut = 360;
                }
            } else if (videoWidthIn > 360) {
                videoHeightOut = (int) ((videoHeightIn) / round((videoWidthIn / 360f), 2));
                videoWidthOut = 360;
            } else {
                videoWidthOut = (int) ((videoWidthIn) / round((videoHeightIn / 360f), 2));
                videoHeightOut = 360;
            }
        }

        if (outQuality == 3) {
            if (videoHeightIn < videoWidthIn) {
                if (videoHeightIn > 480) {
                    videoWidthOut = (int) ((videoWidthIn) / round((videoHeightIn / 480f), 2));
                    videoHeightOut = 480;
                } else {
                    videoHeightOut = (int) ((videoHeightIn) / round((videoWidthIn / 480f), 2));
                    videoWidthOut = 480;
                }
            } else if (videoWidthIn > 480) {
                videoHeightOut = (int) ((videoHeightIn) / round((videoWidthIn / 480f), 2));
                videoWidthOut = 480;
            } else {
                videoWidthOut = (int) ((videoWidthIn) / round((videoHeightIn / 480f), 2));
                videoHeightOut = 480;
            }
        }

        if (outQuality == 4) {
            if (videoHeightIn < videoWidthIn) {
                if (videoHeightIn > 720) {
                    videoWidthOut = (int) ((videoWidthIn) / round((videoHeightIn / 720f), 2));
                    videoHeightOut = 720;
                } else {
                    videoHeightOut = (int) ((videoHeightIn) / round((videoWidthIn / 720f), 2));
                    videoWidthOut = 720;
                }
            } else if (videoWidthIn > 720) {
                videoHeightOut = (int) ((videoHeightIn) / round((videoWidthIn / 720f), 2));
                videoWidthOut = 720;
            } else {
                videoWidthOut = (int) ((videoWidthIn) / round((videoHeightIn / 720f), 2));
                videoHeightOut = 720;
            }
        }
        // Log.e("TAG", "setResolutionAndQuality: " + "videoHeightOut=" + videoHeightOut + " videoWidthOut=" + videoWidthOut + " videoBitRateInKBytes=" + videoBitRateInKBytes);
        // Log.e("TAG", "setResolutionAndQuality:2in " + "videoHeightIn=" + videoHeightIn + " videoWidthIn=" + videoWidthIn);
    }

    public static float round(float d, int decimalPlace) {
        return BigDecimal.valueOf(d).setScale(decimalPlace, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    public static void getDefaultFileInfo(Context context, Uri mediaUri, String mediaPath) {
        try {
            mediaFileInfo = new org.m4m.MediaFileInfo(new AndroidMediaObjectFactory(context));
            mediaUri1 = new org.m4m.Uri(mediaUri.toString());
            mediaFileInfo.setUri(mediaUri1);

            dstMediaPath = mediaPath;

            duration = mediaFileInfo.getDurationInMicroSec();

            audioFormat = (org.m4m.AudioFormat) mediaFileInfo.getAudioFormat();
            if (audioFormat == null) {
               /* showMessageBox("Audio format info unavailable", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });*/
            }

            videoFormat = (org.m4m.VideoFormat) mediaFileInfo.getVideoFormat();
            if (videoFormat == null) {
               /* showMessageBox("Video format info unavailable", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });*/
            } else {
                videoWidthIn = videoFormat.getVideoFrameSize().width();
                videoHeightIn = videoFormat.getVideoFrameSize().height();
                if (videoFormat.getVideoBitRateInKBytes() > 100)
                    videoBitRateInKBytes = videoFormat.getVideoBitRateInKBytes();
            }
        } catch (Exception e) {
            // String message = (e.getMessage() != null) ? e.getMessage() : e.toString();

           /* showMessageBox(message, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });*/
        }
    }

    protected static void configureVideoEncoder(org.m4m.MediaComposer mediaComposer, int width, int height) {

        VideoFormatAndroid videoFormat = new VideoFormatAndroid(videoMimeType, width, height);

        videoFormat.setVideoBitRateInKBytes(videoBitRateInKBytes);
        videoFormat.setVideoFrameRate(videoFrameRate);
        videoFormat.setVideoIFrameInterval(videoIFrameInterval);

        mediaComposer.setTargetVideoFormat(videoFormat);
    }

    protected static void configureAudioEncoder(org.m4m.MediaComposer mediaComposer) {

        /**
         * TODO: Audio resampling is unsupported by current m4m release
         * Output sample rate and channel count are the same as for input.
         */
        AudioFormatAndroid aFormat = new AudioFormatAndroid(audioMimeType, audioFormat.getAudioSampleRateInHz(), audioFormat.getAudioChannelCount());

        aFormat.setAudioBitrateInBytes(audioBitRate);
        aFormat.setAudioProfile(MediaCodecInfo.CodecProfileLevel.AACObjectLC);

        mediaComposer.setTargetAudioFormat(aFormat);
    }

    protected static void setTranscodeParameters(org.m4m.MediaComposer mediaComposer) throws IOException {

        mediaComposer.addSourceFile(mediaUri1);
        int orientation = mediaFileInfo.getRotation();
        mediaComposer.setTargetFile(dstMediaPath, orientation);

        configureVideoEncoder(mediaComposer, videoWidthOut, videoHeightOut);
        configureAudioEncoder(mediaComposer);
    }

    protected static void transcode(Context context, org.m4m.IProgressListener progressListener) throws Exception {

        factory = new AndroidMediaObjectFactory(context);
        mediaComposer = new org.m4m.MediaComposer(factory, progressListener);
        setTranscodeParameters(mediaComposer);
        mediaComposer.start();
    }

    public static void startTranscode(Context context, org.m4m.IProgressListener progressListener) {
        try {
            transcode(context, progressListener);
        } catch (Exception ignore) {
            progressListener.onError(ignore);
        }
    }

    public static void stopTranscode() {
        mediaComposer.stop();
    }

    private static void reportTranscodeDone() {
        String message = "Transcoding finished.";
    }


}
