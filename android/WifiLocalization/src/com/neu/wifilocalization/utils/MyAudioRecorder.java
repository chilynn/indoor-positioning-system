package com.neu.wifilocalization.utils;

import java.io.File;
import java.io.IOException;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.neu.wifilocalization.R;

public class MyAudioRecorder {

    private Context context;

    private Dialog dialog;
    private AudioRecorder mr;
    private ImageView dialog_img;

    private Thread recordThread;

    private static int MAX_TIME = 0; // 最长录制时间，单位秒，0为无时间限制
    private static int MIX_TIME = 1; // 最短录制时间，单位秒，0为无时间限制，建议设为1
    private static int RECORD_NO = 0; // 不在录音
    private static int RECORD_ING = 1; // 正在录音
    private static int RECODE_ED = 2; // 完成录音
    private static int RECODE_STATE = 0; // 录音的状态
    private static float recodeTime = 0.0f; // 录音的时间
    private static double voiceValue = 0.0; // 麦克风获取的音量值
    private static boolean playState = false; // 播放状态

    public MyAudioRecorder(Context context) {
        this.context = context;
    }

    public void showRecording() {
        if (RECODE_STATE != RECORD_ING) {
            scanOldFile();
            System.out.println("扫描完毕");
            mr = new AudioRecorder("voice");
            RECODE_STATE = RECORD_ING;
            showVoiceDialog();
            try {
                mr.start();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            mythread();
        }
    }

    public interface Callback {
        void execute();
    }

    public void finishRecord(Callback callback) {
        if (RECODE_STATE == RECORD_ING) {
            RECODE_STATE = RECODE_ED;
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            try {
                mr.stop();
                voiceValue = 0.0;
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (recodeTime < MIX_TIME) {
                showWarnToast();
                // record.setText("按住开始录音");
                RECODE_STATE = RECORD_NO;
            } else {
                // record.setText("录音完成!点击重新录音");
                callback.execute();
            }
        }
    }

    // 删除老文件
    void scanOldFile() {
        try {
            String url = CommonUtils.audioFile;
            File file = new File(url);
            if (file.exists()) {
                System.out.println(url + "@文件存在");
                boolean isDelete = file.delete();
                System.gc();
                if (isDelete) {
                    System.out.println(url + "@成功删除");
                } else {
                    System.out.println(url + "@未删除");
                }
            } else {
                System.out.println(url + "@文件不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 录音时显示Dialog
    void showVoiceDialog() {
        dialog = new Dialog(context, R.style.DialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.social_audio_record_dialog);
        dialog_img = (ImageView) dialog.findViewById(R.id.dialog_img);
        dialog.show();
    }

    // 录音时间太短时Toast显示
    void showWarnToast() {
        Toast toast = new Toast(context);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(20, 20, 20, 20);

        // 定义一个ImageView
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(R.drawable.voice_to_short); // 图标

        TextView mTv = new TextView(context);
        mTv.setText("时间太短   录音失败");
        mTv.setTextSize(14);
        mTv.setTextColor(Color.WHITE);// 字体颜色
        // mTv.setPadding(0, 10, 0, 0);

        // 将ImageView和ToastView合并到Layout中
        linearLayout.addView(imageView);
        linearLayout.addView(mTv);
        linearLayout.setGravity(Gravity.CENTER);// 内容居中
        linearLayout.setBackgroundResource(R.drawable.record_bg);// 设置自定义toast的背景

        toast.setView(linearLayout);
        toast.setGravity(Gravity.CENTER, 0, 0);// 起点位置为中间 100为向下移100dp
        toast.show();
    }

    // 录音计时线程
    void mythread() {
        recordThread = new Thread(ImgThread);
        recordThread.start();
    }

    // 录音Dialog图片随声音大小切换
    void setDialogImage() {
        if (voiceValue < 200.0) {
            dialog_img.setImageResource(R.drawable.record_animate_01);
        } else if (voiceValue > 200.0 && voiceValue < 400) {
            dialog_img.setImageResource(R.drawable.record_animate_02);
        } else if (voiceValue > 400.0 && voiceValue < 800) {
            dialog_img.setImageResource(R.drawable.record_animate_03);
        } else if (voiceValue > 800.0 && voiceValue < 1600) {
            dialog_img.setImageResource(R.drawable.record_animate_04);
        } else if (voiceValue > 1600.0 && voiceValue < 3200) {
            dialog_img.setImageResource(R.drawable.record_animate_05);
        } else if (voiceValue > 3200.0 && voiceValue < 5000) {
            dialog_img.setImageResource(R.drawable.record_animate_06);
        } else if (voiceValue > 5000.0 && voiceValue < 7000) {
            dialog_img.setImageResource(R.drawable.record_animate_07);
        } else if (voiceValue > 7000.0 && voiceValue < 10000.0) {
            dialog_img.setImageResource(R.drawable.record_animate_08);
        } else if (voiceValue > 10000.0 && voiceValue < 14000.0) {
            dialog_img.setImageResource(R.drawable.record_animate_09);
        } else if (voiceValue > 14000.0 && voiceValue < 17000.0) {
            dialog_img.setImageResource(R.drawable.record_animate_10);
        } else if (voiceValue > 17000.0 && voiceValue < 20000.0) {
            dialog_img.setImageResource(R.drawable.record_animate_11);
        } else if (voiceValue > 20000.0 && voiceValue < 24000.0) {
            dialog_img.setImageResource(R.drawable.record_animate_12);
        } else if (voiceValue > 24000.0 && voiceValue < 28000.0) {
            dialog_img.setImageResource(R.drawable.record_animate_13);
        } else if (voiceValue > 28000.0) {
            dialog_img.setImageResource(R.drawable.record_animate_14);
        }
    }

    // 录音线程
    private Runnable ImgThread = new Runnable() {
        @Override
        public void run() {
            recodeTime = 0.0f;
            while (RECODE_STATE == RECORD_ING) {
                if (recodeTime >= MAX_TIME && MAX_TIME != 0) {
                    imgHandle.sendEmptyMessage(0);
                } else {
                    try {
                        Thread.sleep(200);
                        recodeTime += 0.2;
                        if (RECODE_STATE == RECORD_ING) {
                            voiceValue = mr.getAmplitude();
                            imgHandle.sendEmptyMessage(1);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        Handler imgHandle = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                switch (msg.what) {
                case 0:
                    // 录音超过15秒自动停止
                    if (RECODE_STATE == RECORD_ING) {
                        RECODE_STATE = RECODE_ED;
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        try {
                            mr.stop();
                            voiceValue = 0.0;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (recodeTime < 1.0) {
                            showWarnToast();
                            // record.setText("按住开始录音");
                            RECODE_STATE = RECORD_NO;
                        } else {

                        }
                    }
                    break;
                case 1:
                    setDialogImage();
                    break;
                default:
                    break;
                }
            }
        };
    };

    private class AudioRecorder {
        private static final int SAMPLE_RATE_IN_HZ = 8000;

        final MediaRecorder recorder = new MediaRecorder();
        final String path;

        public AudioRecorder(String path) {
            this.path = CommonUtils.audioFile;
        }

        public void start() throws IOException {
            String state = android.os.Environment.getExternalStorageState();
            if (!state.equals(android.os.Environment.MEDIA_MOUNTED)) {
                throw new IOException("SD Card is not mounted,It is  " + state + ".");
            }
            File directory = new File(path).getParentFile();
            if (!directory.exists() && !directory.mkdirs()) {
                throw new IOException("Path to file could not be created");
            }
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setAudioSamplingRate(SAMPLE_RATE_IN_HZ);
            recorder.setOutputFile(path);
            recorder.prepare();
            recorder.start();
        }

        public void stop() throws IOException {
            recorder.stop();
            recorder.release();
        }

        public double getAmplitude() {
            if (recorder != null) {
                return (recorder.getMaxAmplitude());
            } else
                return 0;
        }
    }

}
