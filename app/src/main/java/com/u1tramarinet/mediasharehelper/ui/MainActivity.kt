package com.u1tramarinet.mediasharehelper.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.u1tramarinet.mediasharehelper.ui.theme.MediaShareHelperTheme
import com.u1tramarinet.mediasharehelper.util.LogUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val bundle: MutableState<Bundle?> = mutableStateOf(null)
    private val uri: MutableState<Uri?> = mutableStateOf(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MediaShareHelperTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MediaShareHelperApp(
                        onShare = { text, imageUri -> share(text, imageUri) },
                    )
                }
            }
        }
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            handleIntent(intent)
        }
    }

    private fun handleIntent(intent: Intent) {
        val action = intent.action
        val type = intent.type

        if (Intent.ACTION_SEND != action || type == null) {
            return
        }

        if (TYPE_TEXT_PLANE == type) {
            bundle.value = intent.extras
        } else if (type.startsWith(TYPE_IMAGE_PREFIX)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                uri.value = intent.getParcelableExtra(Intent.EXTRA_STREAM, Uri::class.java)
            } else {
                uri.value = intent.getParcelableExtra(Intent.EXTRA_STREAM)
            }
        }
    }

    private fun share(text: String?, imageUri: Uri?) {
        LogUtil.methodIn(TAG, "text=$$text, imageUri=$imageUri")
        if (text == null && imageUri == null) {
            hoge()
            LogUtil.methodOut(TAG, "both null.")
            return
        }
        val extras: Bundle = Bundle().apply {
            if (text != null) {
                putString(Intent.EXTRA_TEXT, text)
            }
            if (imageUri != null) {
                putParcelable(Intent.EXTRA_STREAM, imageUri)
            }
        }
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = TYPE_TEXT_PLANE
            putExtras(extras)
        }
        startActivity(intent)
        LogUtil.methodOut(TAG)
    }

    private fun hoge() {
        LogUtil.methodIn(TAG, "test")
        LogUtil.methodOut(TAG, "")
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private const val TYPE_TEXT_PLANE = "text/plain"
        private const val TYPE_IMAGE_PREFIX = "image/"
    }
}