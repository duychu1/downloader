package com.duycomp.downloader.core.designsystem.component

import android.content.ClipboardManager
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.duycomp.downloader.core.designsystem.R.string
import com.duycomp.downloader.core.designsystem.icon.DownloaderIcon
import com.duycomp.downloader.core.model.DarkThemeConfig
import com.duycomp.downloader.core.model.UserData

//import com.google.android.play.core.review.ReviewManagerFactory

@Composable
fun TdDrawer(
    clipboard: ClipboardManager,
    userData: UserData,
    setDarkThemeConFig: (DarkThemeConfig) -> Unit,
    setDisableDynamicColor: (Boolean) -> Unit,
    handelDrawer: () -> Unit,
) {
    val context = LocalContext.current
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
            .padding(12.dp)
            .verticalScroll(rememberScrollState())
    ) {

//        headerLogo(handelDrawer)
        
        DrawerTutorial(
            onClick =  {
                val link = context.resources.getString(string.link_tutorial)
                val intentTutorial = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                try {
                    context.startActivity(intentTutorial)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        )

        DrawerDirectory()
        
        DrawerTheme(
            userData = userData,
            setDarkThemeConFig = setDarkThemeConFig,
            setDisableDynamicColor = setDisableDynamicColor
        )
        
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
    isExpand: MutableState<Boolean> = mutableStateOf(false),
    onClick: () -> Unit = { isExpand.value = !isExpand.value },
    expanded: @Composable AnimatedVisibilityScope.() -> Unit = {  },
) {

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(ShapeDefaults.Medium)
                .clickable { onClick },
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Icon(imageVector = icon, contentDescription = null)
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = title, fontWeight = FontWeight.Medium)
        }

        AnimatedVisibility(
            visible = isExpand.value,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            expanded()
        }
    }



}

@Composable
fun ExpandedItem(
    text: String,
    onClick: () -> Unit,
    content: @Composable () -> Unit = {
        TextCustom(text = text)
    }
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(42.dp)
            .clip(ShapeDefaults.Medium)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(72.dp))
        
        content()
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
    DrawerItem(
        icon = DownloaderIcon.setting,
        title = stringResource(id = string.how_to_use),
        expanded = {
            ExpandedItem(
                text = stringResource(id = string.video_tutorial),
                onClick = onClick
            )
        }
    )
}

@Composable
fun DrawerDirectory(onClick: () -> Unit = {  }) {
    DrawerItem(
        icon = DownloaderIcon.folder,
        title = stringResource(id = string.directory),
        expanded = {
            ExpandedItem(
                text = "/Download/${stringResource(id = string.app_name)}/",
                onClick = onClick
            )
        }
    )
}


@Composable
fun DrawerTheme(
    userData: UserData,
    setDarkThemeConFig: (DarkThemeConfig) -> Unit,
    setDisableDynamicColor: (Boolean) -> Unit,
) {

    var isShowDialog by remember { mutableStateOf(false) }

    DrawerItem(
        icon = DownloaderIcon.folder,
        title = stringResource(id = string.directory),
        expanded = {
//            ShareAppDialog(onDismiss = { isShowDialog = false })
        }
    )

//    DrawerItem(
//        icon = DownloaderIcon.darkTheme,
//        title = stringResource(id = R.string.theme)
//    ) {
//        Column(modifier = Modifier.fillMaxWidth()) {
//            ExpandedItem(
//                text = stringResource(id = R.string.dark),
//                onClick = {
//
//                }
//            ) {
//
//            }
//            ExpandedItem(
//                text = stringResource(id = R.string.light),
//                onClick = {
//
//                }
//            ) {
//
//            }
//            ExpandedItem(
//                text = stringResource(id = R.string.follow_system),
//                onClick = {
//
//                }
//            ) {
//
//            }
//
//            when(userData.darkThemeConfig) {
//                DarkThemeConfig.DARK -> {
//
//                }
//                DarkThemeConfig.LIGHT -> {
//
//                }
//                DarkThemeConfig.FOLLOW_SYSTEM -> {
//
//                }
//            }
//        }
//
//    }
}

@Composable
fun DrawerShare(clipboard: ClipboardManager) {

    var isShowDialog by remember { mutableStateOf(false) }

    DrawerItem(
        icon = DownloaderIcon.folder,
        title = stringResource(id = string.share),
        expanded = {
            ShareAppDialog(onDismiss = { isShowDialog = false }, clipboard = clipboard)
        }
    )
}

@Composable
fun DrawerRate(onClick: () -> Unit) {
    DrawerItem(
        icon = DownloaderIcon.star,
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
            shape = ShapeDefaults.Medium
        ) {
            Icon(imageVector = Icons.Rounded.Star, contentDescription = "null")
            Text(text = "Rate")
        }
    }
}

