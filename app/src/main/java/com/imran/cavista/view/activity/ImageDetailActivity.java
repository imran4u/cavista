package com.imran.cavista.view.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.bumptech.glide.Glide;
import com.imran.cavista.R;
import com.imran.cavista.application.CavistaApplication;
import com.imran.cavista.factory.ImageDetailsViewModelFactory;
import com.imran.cavista.util.ConstantUtil;
import com.imran.cavista.viewmodel.ImageDetailsViewModel;

import org.jetbrains.annotations.NotNull;
import org.kodein.di.Kodein;
import org.kodein.di.KodeinAware;
import org.kodein.di.KodeinContext;
import org.kodein.di.KodeinTrigger;

/**
 * Created by imran on 2020-09-25.
 */
public class ImageDetailActivity extends AppCompatActivity implements View.OnClickListener, KodeinAware {
    private String wrapperId;
    private AppCompatButton btnSubmit;
    private AppCompatEditText etComment;

    private ImageDetailsViewModel mViewModel = null;
//    private  ImageDetailsViewModelFactory mFactory = null;
private val mFactory: ImageDetailsViewModelFactory by instance()


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra(ConstantUtil.KEY_IMAGE_TITLE));
        btnSubmit = findViewById(R.id.btnSubmit);
        etComment = findViewById(R.id.etComment);
        ImageView imageView = findViewById(R.id.imageView);
        String imageUrl = getIntent().getStringExtra(ConstantUtil.KEY_IMAGE_LINK);
        if (imageUrl != null) {
            Glide.with(this)
                    .load(imageUrl)
                    .centerCrop()
                    .into(imageView);
        }
        wrapperId = getIntent().getStringExtra(ConstantUtil.KEY_IMAGE_WRAPPER_ID);

       mViewModel =

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSubmit) {
            saveComment();
        }
    }

    private void saveComment() {
        if (etComment.getText().length() > 0) {
            // TODO: insert in database
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @NotNull
    @Override
    public Kodein getKodein() {
        return ((CavistaApplication) getApplication()).getKodein();
    }

    @NotNull
    @Override
    public KodeinContext<?> getKodeinContext() {
        return getKodein().getKodeinContext();
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public KodeinTrigger getKodeinTrigger() {
        return getKodein().getKodeinTrigger();
    }
}
