![screenshot](https://github.com/wybilold1999/ImageLoader/blob/master/screenshot/QQ%E5%9B%BE%E7%89%8720170124113237.jpg)


����ͼƬ�Ĺ����࣬����LruCache��DiskLruCache��װ

ʹ�÷�ʽ��

�첽���ط�ʽ��

//uri:ͼƬ��url��ַ   imageView:ͼƬ�ؼ� reqWidth/reqHeight:Ҫ��Ŀ��/�߶�
ImageLoader.getInstance(mContext).displayImageAsync(uri, imageView, reqWidth, reqHeight);

ͬ�����ط�ʽ��

//����ͬ��
ImageLoader.getInstance(mContext)��displayImageSync(uri, imageView, reqWidth, reqHeight) {