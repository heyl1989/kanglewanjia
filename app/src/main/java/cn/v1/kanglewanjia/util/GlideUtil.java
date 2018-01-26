package cn.v1.kanglewanjia.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by qy on 2018/1/12.
 */

public class GlideUtil {

    /**
     * 设置普通照片带占位图
     * @param context
     * @param imageUrl
     * @param placeRes
     * @param errRes
     * @param imageView
     */
    public static void setImagWithPlaceAndError(Context context, String imageUrl, int placeRes, int errRes,
                               ImageView imageView) {
        Glide.with(context)
                .load(imageUrl)
                .placeholder(placeRes)
                .error(errRes)
                .into(imageView);

    }

    /**
     * 设置圆角照片不带占位图
     * @param context
     * @param imageUrl
     * @param radius
     * @param imageView
     */
    public static void setImagRadius(Context context, String imageUrl,
                                         int radius, ImageView imageView) {
        Glide.with(context)
                .load(imageUrl)
                .transform(new GlideUtil.GlideRoundTransform(context, radius))
                .into(imageView);

    }

    /**
     * 设置圆角照片带占位图
     * @param context
     * @param imageUrl
     * @param placeRes
     * @param errRes
     * @param radius
     * @param imageView
     */
    public static void setImagWithRadius(Context context, String imageUrl, int placeRes, int errRes,
                               int radius, ImageView imageView) {
        Glide.with(context)
                .load(imageUrl)
                .placeholder(placeRes)
                .error(errRes)
                .transform(new GlideUtil.GlideRoundTransform(context, radius))
                .into(imageView);

    }

    //圆角图片
    public static class GlideRoundTransform extends BitmapTransformation {

        private float radius = 0f;

        public GlideRoundTransform(Context context) {
            this(context, 0);
        }

        public GlideRoundTransform(Context context, int dp) {
            super(context);
            this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return roundCrop(pool, toTransform);
        }

        private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName() + Math.round(radius);
        }
    }

    //圆形图片
    private static class GlideCircleTransform extends BitmapTransformation {

        public GlideCircleTransform(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            // TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }
}
