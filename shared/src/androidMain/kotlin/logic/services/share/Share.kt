package logic.services.share

import android.content.Context
import androidx.core.app.ShareCompat

class AndroidShareService(private val context: Context): ShareService {
    override fun shareText(text: String, subject: String) {
        ShareCompat
            .IntentBuilder(
                context
            )
            .setText(text)
            .setSubject(subject)
            .setType("text/plain")
            .startChooser()
    }
}