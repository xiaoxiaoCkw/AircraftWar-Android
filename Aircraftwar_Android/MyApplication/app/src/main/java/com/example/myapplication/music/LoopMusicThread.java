package com.example.myapplication.music;
//
//import com.example.myapplication.music.MusicThread;
//
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//
//public class LoopMusicThread extends MusicThread {
//    /**
//     * true:跳出循环并停止播放
//     */
//    private boolean stop = false;
//
//    public LoopMusicThread(String filename){
//        super(filename);
//    }
//
//    public void setStop() {
//        this.stop = true;
//    }
//
//    @Override
//    public void run(){
//        while (!stop){
//            InputStream stream = new ByteArrayInputStream(super.getSamples());
//            play(stream);
//        }
//    }
//}
