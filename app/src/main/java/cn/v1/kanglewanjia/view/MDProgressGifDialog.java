package cn.v1.kanglewanjia.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import cn.v1.kanglewanjia.R;


/**
 * Created by lawrence on 17/10/12.
 */
public class MDProgressGifDialog extends Dialog {

    private Context context;

    public MDProgressGifDialog(Context context) {
        super(context, R.style.loading_gif_dialog);
        this.context = context;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progressgifdialog_layout);
        ImageView gifView = (ImageView) findViewById(R.id.img_gif);
        Glide.with(context).load(R.drawable.loading_dialog).asGif().into(gifView);
    }


}
