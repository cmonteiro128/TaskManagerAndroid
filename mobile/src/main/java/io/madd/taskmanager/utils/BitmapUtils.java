package io.madd.taskmanager.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

public class BitmapUtils extends AsyncTask<Integer, Void, Bitmap> {

    private final WeakReference<ImageView> mBitmapWorkerWeakReference;
    private Context mBitmapWorkerContext;
    private int mBitmapWorkerImage;
    private int mBitmapWorkerWidth;
    private int mBitmapWorkerHeight;
    private boolean mBitmapWorkerAlreadyExecuted;

    public BitmapUtils(Context context, ImageView imageView, int image,
                       int width, int height) {

        mBitmapWorkerWeakReference = new WeakReference<>(imageView);
        mBitmapWorkerContext = context;
        mBitmapWorkerImage = image;
        mBitmapWorkerWidth = width;
        mBitmapWorkerHeight = height;
    }

    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Caalculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 1;
            }
        }
        return inSampleSize;
    }

    private static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                          int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    // Decode image in background.
    @Override
    protected Bitmap doInBackground(Integer... params) {
        Bitmap bitmap = decodeSampledBitmapFromResource(mBitmapWorkerContext.getResources(),
                mBitmapWorkerImage, mBitmapWorkerWidth, mBitmapWorkerHeight);

        return Bitmap.createScaledBitmap(bitmap, mBitmapWorkerWidth,
                mBitmapWorkerHeight, true);
    }

    @Override
    protected void onPostExecute(final Bitmap bitmap) {
        if (bitmap != null) {
            if (mBitmapWorkerImage != 0) {
                final ImageView imageView = mBitmapWorkerWeakReference.get();
                if (imageView != null) {
                    Log.d("DEBUG", "Setting Bitmap Image");
                    imageView.startAnimation(AnimationUtils.loadAnimation(mBitmapWorkerContext,
                            android.R.anim.fade_in));
                    imageView.setImageBitmap(bitmap);
                    setExecutionTrue();
                }
            }
        }
    }

    public void setExecutionFalse() {
        mBitmapWorkerAlreadyExecuted = false;
    }

    public void setExecutionTrue() {
        mBitmapWorkerAlreadyExecuted = true;
    }

    public boolean checkIfExecuted() {
        return mBitmapWorkerAlreadyExecuted;
    }
}
