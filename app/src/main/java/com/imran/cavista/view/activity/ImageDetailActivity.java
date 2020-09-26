package com.imran.cavista.view.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.imran.cavista.R;
import com.imran.cavista.db.CommentDatabase;
import com.imran.cavista.db.table.CommentModel;
import com.imran.cavista.factory.ImageDetailsViewModelFactory;
import com.imran.cavista.repository.CommentRepository;
import com.imran.cavista.util.ConstantUtil;
import com.imran.cavista.viewmodel.ImageDetailsViewModel;

import java.util.List;


/**
 * Created by imran on 2020-09-25.
 */
public class ImageDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private String wrapperId;
    private AppCompatButton btnSubmit;
    private AppCompatEditText etComment;
    private TextView prevComment;

    private ImageDetailsViewModel mViewModel = null;
    private ImageDetailsViewModelFactory mFactory = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra(ConstantUtil.KEY_IMAGE_TITLE));
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        etComment = findViewById(R.id.etComment);
        prevComment = findViewById(R.id.prevComment);
        ImageView imageView = findViewById(R.id.imageView);
        String imageUrl = getIntent().getStringExtra(ConstantUtil.KEY_IMAGE_LINK);
        if (imageUrl != null) {
            Glide.with(this)
                    .load(imageUrl)
                    .centerCrop()
                    .into(imageView);
        }
        wrapperId = getIntent().getStringExtra(ConstantUtil.KEY_IMAGE_WRAPPER_ID);
        CommentDatabase database = CommentDatabase.Companion.getDatabase(this);
        mFactory = new ImageDetailsViewModelFactory(new CommentRepository(database));
        mViewModel = new ViewModelProvider(this, mFactory).get(ImageDetailsViewModel.class);
        initialiseObserver();

    }

    private void initialiseObserver() {
        mViewModel.getErrorLiveDat().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String message) {
                Toast.makeText(ImageDetailActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
        mViewModel.getCommentsLiveData().observe(this, new Observer<List<CommentModel>>() {
            @Override
            public void onChanged(List<CommentModel> commentModels) {
                if (commentModels != null && commentModels.size() > 0) {
                    prevComment.setVisibility(View.VISIBLE);
                    StringBuilder builder = new StringBuilder();
                    builder.append("Previous comments:- \n");
                    for (int i = 0; i < commentModels.size(); i++) {
                        CommentModel model = commentModels.get(i);
                        builder.append(model.getComment()).append("\n");
                    }
                    prevComment.setText(builder.toString());
                } else {
                    prevComment.setVisibility(View.GONE);
                }
            }
        });


        mViewModel.getInsertResultLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean == true) {
                    mViewModel.getCommentAsync(wrapperId);
                }
            }
        });
        mViewModel.getCommentAsync(wrapperId);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSubmit) {
            saveComment();
        }
    }

    private void saveComment() {
        final String commentMessage = etComment.getText().toString();
        if (commentMessage.length() > 0) {
            mViewModel.insertCommentAsy(commentMessage, wrapperId);
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
