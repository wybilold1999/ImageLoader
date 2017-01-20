加载图片的工具类，基于LruCache和DiskLruCache封装

使用方式：

异步加载方式：

//uri:图片的url地址   imageView:图片控件 reqWidth/reqHeight:要求的宽度/高度
ImageLoader.getInstance(mContext).displayImageAsync(uri, imageView, reqWidth, reqHeight);

同步加载方式：

//参数同上
ImageLoader.getInstance(mContext)。displayImageSync(uri, imageView, reqWidth, reqHeight) {