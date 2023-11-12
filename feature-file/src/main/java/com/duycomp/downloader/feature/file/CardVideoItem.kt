package com.duycomp.downloader.feature.file

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardVideoItem(
    modifier: Modifier = Modifier,
    title: String,
    uri: String,
    duration: String,
    shareIcon: ImageVector = Icons.Rounded.Share,
    onImageClick: () -> Unit = {},
    onShareIconClick: () -> Unit = {},
    onDeleteIconClick: () -> Unit = {},
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colorScheme.primary)
            .padding(1.dp)


    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(MaterialTheme.shapes.small)
                .background(Color.DarkGray)
                .clickable(onClick = onImageClick),
            contentAlignment = Alignment.Center
        ) {

            DynamicAsyncImage(
                uri = uri,
                contentDescription = "uriLocal",
                modifier = Modifier.fillMaxSize()
            )
            
            DeleteIconButton(onDeleteIconClick)

            TextDuration(duration)
        }

        TitleAndShareIcon(title, shareIcon, onShareIconClick)
    }
}

@Composable
private fun TitleAndShareIcon(
    title: String,
    shareIcon: ImageVector,
    onShareIconClick: () -> Unit
) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(start = 3.dp),
    ) {
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .align(Alignment.CenterStart),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Icon(
            imageVector = shareIcon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(vertical = 2.dp)
                .clip(MaterialTheme.shapes.small)
                .clickable(onClick = onShareIconClick)
        )
//            }
    }
}

@Composable
private fun BoxScope.TextDuration(duration: String) {
    Text(
        text = duration,
        modifier = Modifier
            .padding(top = 4.dp, end = 8.dp)
            .align(Alignment.TopEnd),
        color = MaterialTheme.colorScheme.onPrimary,
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun BoxScope.DeleteIconButton(onDeleteIconClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize(0.25f)
            .clip(MaterialTheme.shapes.small)
            .clickable(onClick = onDeleteIconClick)
            .align(Alignment.TopStart),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES,)
@Preview(uiMode = UI_MODE_NIGHT_NO)
@Composable
fun CardVideoItemPrev() {

    Surface(Modifier.width(140.dp)) {
        CardVideoItem(
            title = "Long video titlelllllllllllll",
            uri = "aa",
            duration = "00:32",
        )
    }
}