package com.example.chap6top1.profile

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.chap6top1.BlurViewModel
import com.example.chap6top1.R
import com.example.chap6top1.profile.workerprofile.TAG_OUTPUT
import com.example.chap6top1.workers.BlurWorker
import com.example.chap6top1.workers.KEY_IMAGE_URI

class BlurProfileViewModel(application : Application): ViewModel()  {
    private val workManager = WorkManager.getInstance(application)
    private var imageUri: Uri? = null


    init {
        // This transformation makes sure that whenever the current work Id changes the WorkInfo
        // the UI is listening to changes
        imageUri = getImageUri(application.applicationContext)

    }
    internal fun applyBlur(blurLevel: Int) {
        val blurRequest = OneTimeWorkRequestBuilder<BlurWorker>()
            .setInputData(createInputDataForUri())
            .build()
        workManager.enqueue(blurRequest)
    }


    private fun createInputDataForUri(): Data {
        val builder = Data.Builder()
        imageUri?.let {
            builder.putString(KEY_IMAGE_URI, imageUri.toString())
        }
        return builder.build()
    }

    private fun getImageUri(context: Context): Uri {
        val resources = context.resources

        return Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(resources.getResourcePackageName(R.drawable.fruit))
            .appendPath(resources.getResourceTypeName(R.drawable.fruit))
            .appendPath(resources.getResourceEntryName(R.drawable.fruit))
            .build()
    }



}
class BlurViewModelFactoryProfile(private val application: Application) :
    ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(BlurProfileViewModel::class.java)) {
            BlurProfileViewModel(application) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
