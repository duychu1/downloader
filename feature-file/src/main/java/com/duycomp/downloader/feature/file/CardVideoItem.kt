package com.duycomp.downloader.feature.file

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
                .background(Color.DarkGray),
            contentAlignment = Alignment.Center
        ) {

            DynamicAsyncImage(
                imageUrl = uri,
                contentDescription = "uriLocal",
                modifier = Modifier.fillMaxSize()
            )
            
            DeleteIconButton(onDeleteIconClick)

            Text(
                text = duration,
                modifier = Modifier
                    .padding(top = 2.dp, end = 5.dp)
                    .align(Alignment.TopEnd),
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }

        //description
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
                    .padding(vertical = 3.dp)
                    .clickable(onClick = onShareIconClick)
            )
//            }
        }
    }
}

@Composable
private fun BoxScope.DeleteIconButton(onDeleteIconClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize(0.2f)
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

@Preview
@Composable
fun CardVideoItemPrev() {

    Surface(Modifier.width(140.dp)) {
        CardVideoItem(
            title = "Long video title",
            uri = "aa",
            duration = "00:32",
        )
    }
}