package com.duycomp.downloader.ui

import android.content.ClipboardManager
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.duycomp.downloader.core.designsystem.R.string
import com.duycomp.downloader.core.designsystem.component.ShareAppDialog
import com.duycomp.downloader.core.designsystem.icon.DownloaderIcons
import com.duycomp.downloader.feature.setting.SettingsDialog

//import com.google.android.play.core.review.ReviewManagerFactory

@Composable
fun DownloaderDrawer(
    clipboard: ClipboardManager,
    handelDrawer: () -> Unit,
) {
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    Column(
        Modifier
            .fillMaxHeight()
            .width(360.dp)
            .clip(
                MaterialTheme.shapes.large.copy(
                    topStart = CornerSize(0),
                    bottomStart = CornerSize(0)
                )
            )
//            .statusBarsPadding()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
            .padding(12.dp)
            .verticalScroll(rememberScrollState())
    ) {

//        headerLogo(handelDrawer)
        
        DrawerTutorial(
            onClick =  {
                uriHandler.openUri(context.resources.getString(string.link_tutorial))
            }
        )

        DrawerDirectory()

        DrawerTheme()
        
        DrawerShare(clipboard = clipboard)

        DrawerRate(
            onClick = {
//                showRatting(context)
            }
        )

    }
}

//@Composable
//private fun headerLogo(onCloseDrawer: () -> Unit) {
//    Box(
//        Modifier
//            .fillMaxWidth()
//            .height(180.dp)
//    ) {
//
//        Icon(
//            imageVector = Icons.Default.Close,
//            contentDescription = null,
//            modifier = Modifier
//                .align(Alignment.TopEnd)
//                .clickable(onClick = onCloseDrawer)
//        )
//
//        Row(
//            Modifier
////                    .fillMaxHeight()
//                .fillMaxSize(),
////                    .padding(start = 24.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
////                Box(modifier = Modifier.height(100.dp).width(100.dp)){}
//            Image(
//                painter = painterResource(id = drawable.ic_app_icon),
//                contentDescription = "App icon",
//                modifier = Modifier
//                    .clip(MaterialTheme.shapes.extraLarge)
//                    .background(bkg_icon)
//            )
//
//            Spacer(modifier = Modifier.width(24.dp))
//
//            Column() {
//                Text(
//                    text = stringResource(id = string.app_name),
//                    style = MaterialTheme.typography.headlineSmall,
//                    fontWeight = FontWeight.Bold,
//                )
//
//                Text(
//                    text = stringResource(id = string.header_description),
//                    style = MaterialTheme.typography.bodySmall,
//                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
//                )
//            }
//        }
//    }
//}


@Composable
fun DrawerItem(
    icon: ImageVector,
    title: String,
    isExpand: MutableState<Boolean> = remember {
        mutableStateOf(false)
    },
    onClick: (MutableState<Boolean>) -> Unit = { it.value = !it.value },
    expanded: @Composable (MutableState<Boolean>) -> Unit = {  },
) {

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(MaterialTheme.shapes.extraLarge)
                .clickable { onClick(isExpand) },
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Icon(imageVector = icon, contentDescription = null)
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = title, fontWeight = FontWeight.Medium)
        }
        expanded(isExpand)
    }
}

@Composable
fun ExpandedItem(
    text: String,
    isExpand: MutableState<Boolean>,
    onClick: () -> Unit,
    content: @Composable () -> Unit = {
        TextCustom(text = text)
    }
) {
    AnimatedVisibility(
        visible = isExpand.value,
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp)
                .clip(MaterialTheme.shapes.extraLarge)
                .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(72.dp))

            content()
        }
    }
}

@Composable
private fun TextCustom(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
        style = MaterialTheme.typography.bodyMedium
    )
}

@Preview
@Composable
fun DrawerTutorial(onClick: () -> Unit = {  }) {
    val isExpand: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }
    DrawerItem(
        icon = DownloaderIcons.setting,
        title = stringResource(id = string.how_to_use),
        isExpand = isExpand,
        expanded = {
            ExpandedItem(
                text = stringResource(id = string.video_tutorial),
                isExpand = it,
                onClick = onClick,
            )
        }
    )
}

@Composable
fun NewDirectory(onClick: () -> Unit = {  }) {
    DrawerItem(
        icon = DownloaderIcons.folder,
        title = stringResource(id = string.directory),
        expanded = {
            ExpandedItem(
                text = "/Download/${stringResource(id = string.app_name)}/",
                isExpand = it,
                onClick = onClick
            )
        }
    )
}

@Preview
@Composable
fun DrawerDirectory(onClick: () -> Unit = {  }) {
    DrawerItem(
        icon = DownloaderIcons.folder,
        title = stringResource(id = string.directory),
        expanded = {
            ExpandedItem(
                text = "/Download/${stringResource(id = string.app_name)}/",
                isExpand = it,
                onClick = onClick,
            )
        }
    )
}


@Composable
fun DrawerTheme() {
    DrawerItem(
        icon = DownloaderIcons.darkTheme,
        title = stringResource(id = string.theme),
        expanded = {
            if (it.value) {
                SettingsDialog(onDismiss = { it.value = !it.value })
            }
        }
    )

}

@Composable
fun DrawerShare(clipboard: ClipboardManager) {
    DrawerItem(
        icon = DownloaderIcons.share,
        title = stringResource(id = string.share),
        expanded = {
            if (it.value) {
                ShareAppDialog(onDismiss = { it.value = !it.value } , clipboard = clipboard)
            }
        }
    )
}

@Composable
fun DrawerRate(onClick: (MutableState<Boolean>) -> Unit = {  }) {
    DrawerItem(
        icon = DownloaderIcons.star,
        title = stringResource(id = string.rate),
        onClick = onClick
    )
}

//fun showRatting(context: Context) {
//    try {
//        val manager = ReviewManagerFactory.create(context)
////            val manager = FakeReviewManager(context)
//        val request = manager.requestReviewFlow()
//        request.addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                // We got the ReviewInfo object
//                val reviewInfo = task.result
//                val flow = manager.launchReviewFlow(context as Activity, reviewInfo!!)
//                flow.addOnCompleteListener { _ ->
//                    // The flow has finished. The API does not indicate whether the user
//                    // reviewed or not, or even whether the review dialog was shown. Thus, no
//                    // matter the result, we continue our app flow.
////                    dlog("review complete")
//                    MyDatastore.saveDatastore(context, "isRate", true)
//                }
//            } else {
//                // There was some problem, log or handle the error code.
////                    @ReviewErrorCode val reviewErrorCode = (task.exception as TaskException).errorCode
//                val appLink = "https://play.google.com/store/apps/details?id=${context.packageName}"
//                val intentRate = Intent(Intent.ACTION_VIEW, Uri.parse(appLink))
//                try {
//                    context.startActivity(intentRate)
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//            }
//        }
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
//
//}

@Preview
@Composable
fun item() {
    Column(Modifier.fillMaxWidth()) {
        TextButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.height(56.dp),
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Icon(imageVector = Icons.Rounded.Star, contentDescription = "null")
            Text(text = "Rate")
        }
    }
}

