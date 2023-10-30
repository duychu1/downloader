package com.duycomp.downloader.feature.download

import android.content.ClipboardManager
import android.content.Context
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.duycomp.downloader.core.designsystem.icon.DownloaderIcon

@Composable
fun DownloadRoute() {

}

@Composable
fun DownloadScreen() {

}

@Composable
fun DownloadScreenContent(
    modifier: Modifier,
    clipboard: ClipboardManager,
    status: String,
    textField: String,
    isDownloadBtnOnTop: Boolean,
) {

    ConstraintLayout(modifier = modifier
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
        ){
            TutorialBtn()
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(324.dp)
                .clip(MaterialTheme.shapes.extraSmall)
                .background(Color.Gray.copy(alpha = 0.2f))
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
//                clipboard = clipboard
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

@Composable
fun ColumnScope.DownloadMainContent(
    status: String,
    isDownloadBtnOnTop: Boolean,
//    context: Context,
    textField: String,
//    clipboard: ClipboardManager
) {

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(36.dp)
    ) {
        OutlinedButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.Center)
        ) {
            Text(
                text = "How to download!",
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
    }

    Spacer(modifier = Modifier.height(18.dp))

    Box(modifier = Modifier
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

    Spacer(modifier = Modifier.height(10.dp))

    //top button
    ButtonCustom(
        name = if (isDownloadBtnOnTop) {
            stringResource(id = R.string.Download)
        } else
            stringResource(id = R.string.Open_AppName),
        onClick = { },
        isShowSwapIconOnTop = false,
        onSwapIconClick = { /*TODO*/ },
    )

    Spacer(modifier = Modifier.height(6.dp))

    TextPaste(
        textField = textField,
        onTextFieldChanged = {},
        onPasteClick = {}
    )

    Spacer(modifier = Modifier.height(6.dp))

    //bot button
    ButtonCustom(
        name = if (isDownloadBtnOnTop) {
            stringResource(id = R.string.Download)
        } else
            stringResource(id = R.string.Open_AppName),
        onClick = { },
        isShowSwapIconOnTop = false,
        onSwapIconClick = { /*TODO*/ },
    )
}


@Composable
fun TutorialBtn() {

}

@Composable
fun ButtonCustom(
    name: String,
    isShowSwapIconOnTop: Boolean = false,
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
            if (isShowSwapIconOnTop) {
                Icon(
                    imageVector = DownloaderIcon.swapVert,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .clickable(onClick = onSwapIconClick),
                    contentDescription = "swap",
                    tint = MaterialTheme.colorScheme.onPrimary.copy(0.5f)
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
            shape = MaterialTheme.shapes.small,
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
            shape = MaterialTheme.shapes.small
        )
        {
            Text(text = "Paste", fontSize = 16.sp)
        }

    }
}


@Preview
@Composable
fun PrevDownloadMainContent() {
    Surface() {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            DownloadMainContent(
                status = "Let start download",
                isDownloadBtnOnTop = true,
                textField = "",
            )
        }
    }
}
