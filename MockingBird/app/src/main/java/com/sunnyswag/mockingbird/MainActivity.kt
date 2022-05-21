package com.sunnyswag.mockingbird

import android.media.MediaRecorder
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sunnyswag.mockingbird.databinding.ActivityMainBinding
import com.sunnyswag.mockingbird.fragment.TransformFragment
import java.io.IOException

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mMediaRecorder: MediaRecorder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecordBtn()
    }

    override fun onResume() {
        super.onResume()
        initMediaRecorder()
        binding.startRecord.visibility = View.VISIBLE
    }

    private fun initMediaRecorder() {
        mMediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(FileUtils.getOutputMediaPath(applicationContext.getExternalFilesDir("audio_file")))

            try {
                prepare()
            } catch (e: IllegalStateException) {
                Log.d(TAG, "IllegalStateException preparing MediaRecorder: " + e.message)
                releaseMediaRecorder()
            } catch (e: IOException) {
                Log.d(TAG, "IOException preparing MediaRecorder: " + e.message)
                releaseMediaRecorder()
            }
        }
    }

    private fun releaseMediaRecorder() {
        // clear recorder configuration
        mMediaRecorder.reset()
        // release the recorder object
        mMediaRecorder.release()
    }

    private fun initRecordBtn() {
        binding.startRecord.setOnClickListener {
            mMediaRecorder.start()
            it.visibility = View.GONE
            it.postDelayed({
                mMediaRecorder.stop()
                releaseMediaRecorder()

                val transaction = supportFragmentManager.beginTransaction()
                transaction.add(binding.fragmentAddContainer.id, TransformFragment())
                transaction.commit()
            }, 5000)
        }
    }
}