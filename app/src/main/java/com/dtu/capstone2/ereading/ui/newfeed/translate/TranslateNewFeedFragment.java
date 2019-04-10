package com.dtu.capstone2.ereading.ui.newfeed.translate;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.BulletSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.datasource.repository.EReadingRepository;
import com.dtu.capstone2.ereading.ui.model.LineContentNewFeed;
import com.dtu.capstone2.ereading.ui.model.TypeContent;
import com.dtu.capstone2.ereading.ui.utils.BaseFragment;
import com.dtu.capstone2.ereading.ui.utils.Constants;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Create by Nguyen Van Phuc on 4/9/19
 */
public class TranslateNewFeedFragment extends BaseFragment {
    private ImageView mImgBack;
    private TextView mTvWordsResult;
    private TranslateNewFeedViewModel mViewModel;
    private SpannableStringBuilder mTextSpannableResults = new SpannableStringBuilder();

    @Override
    public void onCreate(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new TranslateNewFeedViewModel(new EReadingRepository());
        if (getArguments() != null) {
            mViewModel.setUrlNewFeed(getArguments().getString(Constants.KEY_URL_NEW_FEED));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_translate_result, container, false);
        mImgBack = view.findViewById(R.id.imgTranslateNewFeedBack);
        mTvWordsResult = view.findViewById(R.id.tvTranslateNewFeedWordResult);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initEventsView();
        mViewModel.getDataFromHTMLAndOnNextDetectWord().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LineContentNewFeed>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getManagerSubscribe().add(d);
                    }

                    @Override
                    public void onNext(LineContentNewFeed s) {
                        mTextSpannableResults.append(getStringStyleOfContent(s));
                        mTvWordsResult.setText(mTextSpannableResults);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.w("Translate", e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initEventsView() {
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    private SpannableString getStringStyleOfContent(LineContentNewFeed contentNewFeed) {
        SpannableString result = new SpannableString(contentNewFeed.getTextContent() + "\n\n");
        int sizeContent = contentNewFeed.getTextContent().length();
        if (contentNewFeed.getTypeContent() == TypeContent.TITLE) {
            result.setSpan(new ForegroundColorSpan(Color.BLACK), 0, sizeContent, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            result.setSpan(new StyleSpan(Typeface.BOLD), 0, sizeContent, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            result.setSpan(new RelativeSizeSpan(1.8F), 0, sizeContent, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if (contentNewFeed.getTypeContent() == TypeContent.INTRODUCTION) {
            result.setSpan(new ForegroundColorSpan(Color.BLACK), 0, sizeContent, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            result.setSpan(new StyleSpan(Typeface.BOLD), 0, sizeContent, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            result.setSpan(new RelativeSizeSpan(1.3F), 0, sizeContent, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if (contentNewFeed.getTypeContent() == TypeContent.HEADER) {
            result.setSpan(new ForegroundColorSpan(Color.BLACK), 0, sizeContent, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            result.setSpan(new StyleSpan(Typeface.BOLD), 0, sizeContent, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if (contentNewFeed.getTypeContent() == TypeContent.ITEM) {
            result.setSpan(new BulletSpan(20, Color.BLACK), 0, sizeContent, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            result.setSpan(new ForegroundColorSpan(Color.BLACK), 0, sizeContent, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return result;
    }
}
