package com.nytimes.task.glide

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.facebook.shimmer.ShimmerFrameLayout
import com.nytimes.task.R
import com.nytimes.task.network.data.ImageFormatType
import com.nytimes.task.network.data.Media
import com.nytimes.task.network.data.MediaType

@GlideModule
class SpokGlide : AppGlideModule()

class BindingAdapter

@BindingAdapter(value =  ["app:image", "app:placeholder"])
fun image(imageView: ImageView, url: String?, placeholder : Int) {
    Glide.with(imageView.context)
        .load(url)
        .circleCrop()
        .placeholder(placeholder)
        .into(imageView)
}

@BindingAdapter("app:images")
fun images(imageView: ImageView, mediaList : List<Media>?) {
    mediaList?.find { it.type ==  MediaType.image}.apply {
        image(imageView, this?.metadataList?.find { it.format == ImageFormatType.ImageThumbnail.type }?.url, R.drawable.bg_image_placeholder)
    }?: image(imageView, null, R.drawable.bg_image_placeholder)
}


@BindingAdapter("app:shimmerState")
fun shimmerState(shimmerLayout: ShimmerFrameLayout, state : Boolean) {
    if(state) {
        shimmerLayout.visibility = View.VISIBLE
        shimmerLayout.startShimmer()
    } else {
        shimmerLayout.visibility = View.GONE
        shimmerLayout.stopShimmer()
    }
}

@BindingAdapter("app:refreshState")
fun refreshState(refreshLayout: SwipeRefreshLayout, state : Boolean) {
    if(!state && refreshLayout.isRefreshing) {
       refreshLayout.isRefreshing = false
    }
}