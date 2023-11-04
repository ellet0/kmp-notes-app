package logic.services.share

import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.UIViewController
import platform.UIKit.UIWindow
import platform.UIKit.popoverPresentationController

// Everything here is a workaround
class IosShareService : ShareService {
    override fun shareText(text: String, subject: String) {
        val activityController = UIActivityViewController(
            activityItems = listOf(
                subject,
                text,
            ),
            applicationActivities = null,
        )
        val window = UIApplication.sharedApplication.windows().first() as UIWindow?
        activityController.popoverPresentationController()?.sourceView =
            window
        activityController.setTitle(subject)
        activityController.title = subject
        window?.rootViewController?.presentViewController(
            activityController as UIViewController,
            animated = true,
            completion = null,
        )
    }
}


/// For some reasons this doesn't work in KMP 1.9.20 but it was fine in 1.9.10
//@OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)
//private class TextItemSource(
//    val title: String,
//    val message: String,
//) : NSObject(), UIActivityItemSourceProtocol {
//    override fun activityViewController(
//        activityViewController: UIActivityViewController,
//        itemForActivityType: UIActivityType?
//    ): String {
//        return message
//    }
//
////    open override fun activityViewControllerLinkMetadata(activityViewController: UIActivityViewController): LPLinkMetadata? {
////        val metadata = LPLinkMetadata()
////        metadata.title = title
//////        metadata.iconProvider = NSItemProvider(
//////            `object` = UIImage()
//////        )
////        metadata.originalURL = NSURL(fileURLWithPath = message)
////        return metadata
////    }
//
//    override fun activityViewControllerPlaceholderItem(activityViewController: UIActivityViewController): Any {
//        return title
//    }
//
//}