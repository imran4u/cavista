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
import com.imran.cavista.util.ConstantUtil;

/**
 * Created by imran on 2020-09-25.
 */
public class ImageDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private String wrapperId;
    private AppCompatButton btnSubmit;
    private AppCompatEditText etComment;

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
}
