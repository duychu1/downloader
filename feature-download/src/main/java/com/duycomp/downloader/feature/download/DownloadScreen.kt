package com.duycomp.downloader.feature.download

import android.content.ClipboardManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.duycomp.downloader.core.designsystem.icon.DownloaderIcons

@Composable
fun DownloadRoute(
    modifier: Modifier = Modifier,
    clipboard: ClipboardManager,
    textClipboard: String,
    handleDrawer: () -> Unit = { },
    viewModel: DownloadViewModel = hiltViewModel(),
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    val textField by viewModel.textField.collectAsStateWithLifecycle()
    val isDownloadBtnOnTop by viewModel.isDownloadBtnOnTop.collectAsStateWithLifecycle()

    val context = LocalContext.current

    DownloadScreen(
        clipboard = clipboard,
        status = status,
        textField = textField,
        isDownloadBtnOnTop = isDownloadBtnOnTop,
        onTextFieldChange = viewModel::onTextFieldChange,
        onTutorialClick = viewModel::onTutorialClick,
        onPasteClick = viewModel::onPatesClick,
        onSwapIconClick = viewModel::onSwapIconClick,
        onDownloadClick = { viewModel.onDownloadClick(context) },
        onOpenAppClick = { viewModel.onOpenAppClick(context) },
        handleDrawer = handleDrawer,
    )

    LaunchedEffect(key1 = textClipboard) {
        viewModel.onTextClipboardChange(textClipboard, context)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadScreen(
    modifier: Modifier = Modifier,
    clipboard: ClipboardManager,
    status: String,
    textField: String,
    isDownloadBtnOnTop: Boolean,
    onTutorialClick: () -> Unit,
    onPasteClick: (ClipboardManager) -> Unit,
    onSwapIconClick: (Boolean) -> Unit,
    onDownloadClick: () -> Unit,
    onOpenAppClick: () -> Unit,
    onTextFieldChange: (String) -> Unit,
    handleDrawer: () -> Unit = { },
) {
    DownloadScreenContent(
        status = status,
        isDownloadBtnOnTop = isDownloadBtnOnTop,
        textField = textField,
        onTutorialClick = onTutorialClick,
        onTextFieldChange = onTextFieldChange,
        onPasteClick = { onPasteClick(clipboard) },
        onDownloadClick = onDownloadClick,
        onOpenAppClick = onOpenAppClick,
        onSwapIconClick = { onSwapIconClick(!isDownloadBtnOnTop) },
        handleDrawer = handleDrawer
    )


}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun DownloadScreenContent(
    modifier: Modifier = Modifier,
    status: String,
    isDownloadBtnOnTop: Boolean,
    textField: String,
    onTutorialClick: () -> Unit = {  },
    onTextFieldChange: (String) -> Unit = {  },
    onPasteClick: () -> Unit = {  },
    onDownloadClick: () -> Unit = {  },
    onOpenAppClick: () -> Unit = {  },
    onSwapIconClick: () -> Unit = {  },
    handleDrawer: () -> Unit = { },
) {
    Column() {
        Spacer(modifier = Modifier.height(6.dp))

        ConstraintLayout(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 6.dp)
        ) {

            val (tutorial, nativeAd, mainContent) = createRefs()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .constrainAs(tutorial) {
                        bottom.linkTo(mainContent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                contentAlignment = Alignment.Center
            ) {
                TutorialButton()
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(324.dp)
                    .clip(MaterialTheme.shapes.extraSmall)
                    .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f))
                    .constrainAs(nativeAd) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                contentAlignment = Alignment.TopCenter
            ) {
                Text(text = "NativeAd")
            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(mainContent) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                DownloadMainContent(
                    status = status,
                    isDownloadBtnOnTop = isDownloadBtnOnTop,
                    textField = textField,
                    onTextFieldChange = onTextFieldChange,
                    onPasteClick = onPasteClick,
                    onDownloadClick = onDownloadClick,
                    onOpenAppClick = onOpenAppClick,
                    onSwapIconClick = onSwapIconClick
                )

            }

//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .constrainAs(centerTutorial) {
//                    top.linkTo(topAd.bottom)
//                    bottom.linkTo(botFunction.top)
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                    height = Dimension.fillToConstraints
//                }
////                .background(Color.DarkGray)
//                .padding(top = 10.dp)
//        ) {
//            TutorialOld()
//        }


        }
    }
}

@Composable
fun ColumnScope.DownloadMainContent(
    status: String,
    isDownloadBtnOnTop: Boolean,
    textField: String,
    onTextFieldChange: (String) -> Unit = {},
    onPasteClick: () -> Unit = {},
    onSwapIconClick: () -> Unit = {},
    onDownloadClick: () -> Unit = {},
    onOpenAppClick: () -> Unit = {},

) {
    val context = LocalContext.current

    Spacer(modifier = Modifier.height(10.dp))

    TextStatus(status)

    Spacer(modifier = Modifier.height(10.dp))

    //top button
    ButtonCustom(
        name = if (isDownloadBtnOnTop) {
            stringResource(id = R.string.Download)
        } else
            stringResource(id = R.string.Open_AppName),
        onClick = {
            if (isDownloadBtnOnTop) onDownloadClick()
            else onOpenAppClick()
        },
        isShowSwapIcon = false,
        onSwapIconClick = onSwapIconClick,
    )

    Spacer(modifier = Modifier.height(6.dp))

    TextPaste(
        textField = textField,
        onTextFieldChanged = onTextFieldChange,
        onPasteClick = onPasteClick
    )

    Spacer(modifier = Modifier.height(6.dp))

    //bot button
    ButtonCustom(
        name = if (!isDownloadBtnOnTop) {
            stringResource(id = R.string.Download)
        } else
            stringResource(id = R.string.Open_AppName),
        onClick = {
            if (isDownloadBtnOnTop) onDownloadClick()
            else onOpenAppClick()
        },
        isShowSwapIcon = true,
        onSwapIconClick = onSwapIconClick,
    )

    Spacer(modifier = Modifier.height(4.dp))

    Text(
        text = "Directory: /Download/${stringResource(id = R.string.app_name)}/",
        modifier = Modifier
//            .fillMaxWidth()
//            .height(24.dp),
            .align(Alignment.CenterHorizontally),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onBackground.copy(0.4f),
    )

    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
private fun TextStatus(status: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(15.dp)
    ) {
        Text(
            text = status,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground.copy(0.6f),
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun TutorialButton(onClick: () -> Unit = { }) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val uriHandler = LocalUriHandler.current
        val tutorialLink = stringResource(id = R.string.link_tutorial)
        OutlinedButton(
            onClick = {
                uriHandler.openUri(tutorialLink)
            },
            modifier = Modifier
                .height(36.dp)
                .align(Alignment.Center)
        ) {
            Text(
                text = "How to download!",
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
    }
}


@Composable
fun ButtonCustom(
    name: String,
    isShowSwapIcon: Boolean = false,
    onSwapIconClick: () -> Unit,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = MaterialTheme.shapes.large,
        )
    {
        Box(Modifier.fillMaxSize()) {
            if (isShowSwapIcon) {
                Icon(
                    imageVector = DownloaderIcons.swapVert,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .clip(MaterialTheme.shapes.small)
                        .clickable(onClick = onSwapIconClick),
                    contentDescription = "swap",
                    tint = MaterialTheme.colorScheme.onPrimary.copy(0.4f)
                )
            }

            Text(
                name,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun TextPaste(
    textField: String,
    onTextFieldChanged: (String) -> Unit,
    onPasteClick: () -> Unit
) {

    Box(Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = textField,
            onValueChange = onTextFieldChanged,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 52.dp)
                .align(Alignment.CenterStart),
            shape = MaterialTheme.shapes.large,
//            colors = TextFieldDefaults.,
//            colors = TextFieldDefaults.outlinedTextFieldColors(
//                textColor = MaterialTheme.colorScheme.onSurface,
//                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
//            ),
            singleLine = true,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.ex_link),
                    color = MaterialTheme.colorScheme.onBackground.copy(0.3f)
                )
            }
        )
        Button(
            onClick = onPasteClick,
            modifier = Modifier
                .height(50.dp)
                .align(Alignment.CenterEnd),
            shape = MaterialTheme.shapes.large
        )
        {
            Text(text = "Paste", fontSize = 16.sp)
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
fun PreDownloadScreenContent() {

    Surface {
        DownloadScreenContent(
            modifier = Modifier.fillMaxSize(),
            onTutorialClick = { /*TODO*/ },
            status = "Let start download",
            isDownloadBtnOnTop = true,
            textField = "",
            onTextFieldChange = { },
            onPasteClick = {},
            onDownloadClick = { /*TODO*/ },
            onOpenAppClick = { /*TODO*/ },
            onSwapIconClick = { }
        )
    }
}

