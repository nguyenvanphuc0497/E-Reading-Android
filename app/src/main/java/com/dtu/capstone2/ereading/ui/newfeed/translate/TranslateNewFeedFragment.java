package com.dtu.capstone2.ereading.ui.newfeed.translate;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.datasource.repository.EReadingRepository;
import com.dtu.capstone2.ereading.datasource.repository.LocalRepository;
import com.dtu.capstone2.ereading.network.request.Vocabulary;
import com.dtu.capstone2.ereading.ui.model.LineContentNewFeed;
import com.dtu.capstone2.ereading.ui.model.TypeContent;
import com.dtu.capstone2.ereading.ui.model.VocabularyLocation;
import com.dtu.capstone2.ereading.ui.utils.BaseFragment;
import com.dtu.capstone2.ereading.ui.utils.Constants;
import com.dtu.capstone2.ereading.ui.utils.DefaultWordClickableSpan;
import com.dtu.capstone2.ereading.ui.utils.FavoriteWordClickableSpan;
import com.dtu.capstone2.ereading.ui.utils.RxBusTransport;
import com.dtu.capstone2.ereading.ui.utils.Transport;
import com.dtu.capstone2.ereading.ui.utils.TypeTransportBus;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Create by Nguyen Van Phuc on 4/9/19
 */
public class TranslateNewFeedFragment extends BaseFragment implements View.OnClickListener, DialogInterface.OnMultiChoiceClickListener, DialogInterface.OnClickListener {
    private static final String TITLE_DIALOG_FAVORITE = "favorite";
    private static final String TITLE_DIALOG_REFRESH = "refresh";

    private ImageView mImgBack;
    private ImageView mImgHighLight;
    private ImageView mImgRefresh;
    private ImageView mImgAddFavoriteReview;
    private TextView mTvWordsResultTitle;
    private TextView mTvWordsResultIntroduction;
    private TextView mTvWordsResultContent;
    private TextView mTvGuideFavorite;
    private TextView mTvGuideRefresh;
    private TranslateNewFeedViewModel mViewModel;
    private ProgressBar mProgress;
    private AlertDialog.Builder mAlertDialogBuilder;
    private TextView tvFavoriteCount;
    private TextView tvRefreshCount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new TranslateNewFeedViewModel(new EReadingRepository(), new LocalRepository(getContext()));
        if (getArguments() != null) {
            mViewModel.setUrlNewFeed(getArguments().getString(Constants.KEY_URL_NEW_FEED));
        }

        RxBusTransport.INSTANCE.listen()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Transport>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Transport transport) {
                        if (transport.getTypeTransport() == TypeTransportBus.SPAN_ON_CLICK && transport.getSender().equals(DefaultWordClickableSpan.class.getSimpleName())) {
                            mViewModel.addOrRemoveVocabularyToListRefresh((VocabularyLocation) transport.getMessage());
                            reloadIconRefresh();
                        }
                        if (transport.getTypeTransport() == TypeTransportBus.SPAN_ON_CLICK && transport.getSender().equals(FavoriteWordClickableSpan.class.getSimpleName())) {
                            mViewModel.addOrRemoveVocabularyToListAddFavoriteByLocationVocabulary((VocabularyLocation) transport.getMessage());
                            reloadIconFavorite();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        mAlertDialogBuilder = new AlertDialog.Builder(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_translate_result, container, false);
        mImgBack = view.findViewById(R.id.imgTranslateNewFeedBack);
        mTvWordsResultTitle = view.findViewById(R.id.tvTranslateNewFeedWordResultTitle);
        mTvWordsResultIntroduction = view.findViewById(R.id.tvTranslateNewFeedWordResultIntroduction);
        mTvWordsResultContent = view.findViewById(R.id.tvTranslateNewFeedWordResultContent);
        mProgress = view.findViewById(R.id.progressTranslateNewFeed);
        mImgHighLight = view.findViewById(R.id.imgTranslateNewFeedHighLight);
        mImgRefresh = view.findViewById(R.id.imgTranslateNewFeedRefresh);
        mImgAddFavoriteReview = view.findViewById(R.id.imgTranslateNewFeedFavoriteReview);
        mTvGuideFavorite = view.findViewById(R.id.tv_new_feed_translate_guide_favorite);
        mTvGuideRefresh = view.findViewById(R.id.tv_new_feed_translate_guide_refresh);
        tvFavoriteCount = view.findViewById(R.id.tvTranslateNewFeedFavoriteReviewCount);
        tvRefreshCount = view.findViewById(R.id.tvTranslateNewFeedRefreshCount);
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
                        if (mProgress.getVisibility() != View.VISIBLE) {
                            mProgress.setVisibility(View.VISIBLE);
                        }
                        if (s.getTypeContent() == TypeContent.TITLE) {
                            mViewModel.getmTextSpannableResultsTitle().append(setSpannableClick(s, ""));
                            mTvWordsResultTitle.setText(mViewModel.getmTextSpannableResultsTitle());
                        } else if (s.getTypeContent() == TypeContent.INTRODUCTION) {
                            mTvWordsResultIntroduction.setText(setSpannableClick(s, ""));
                        } else if (s.getTypeContent() == TypeContent.TEXT) {
                            mViewModel.getmTextSpannableResultsContent().append(setSpannableClick(s, "\n\n"));
                            mTvWordsResultContent.setText(mViewModel.getmTextSpannableResultsContent());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.w("Translate", e.toString());
                        mProgress.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Quá trình dịch gián đoạn! Kiểm tra kết nối Internet.", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        mProgress.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Dịch hoàn tất.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgTranslateNewFeedBack: {
                getActivity().onBackPressed();
                break;
            }
            case R.id.imgTranslateNewFeedHighLight: {
//                mTextSpannableResults.removeSpan(new);
//                mTvWordsResultTitle.setText(mTextSpannableResults);
                break;
            }
            case R.id.imgTranslateNewFeedRefresh: {
                mViewModel.setNameListDialogShowing(TITLE_DIALOG_REFRESH);
                showDialog("Danh sách các từ đã chọn.", mViewModel.getArrayWordRefresh(), mViewModel.getArraySelectedRefresh());
                break;
            }
            case R.id.imgTranslateNewFeedFavoriteReview: {
                mViewModel.setNameListDialogShowing(TITLE_DIALOG_FAVORITE);
                showDialog("Danh sách các từ yêu thích đã chọn.", mViewModel.getArrayWordAddFavorite(), mViewModel.getArraySelectedAddFavorite());
                break;
            }

        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE: {
                Log.e("xxx", "BUTTON_POSITIVE");
                break;
            }
            case DialogInterface.BUTTON_NEGATIVE: {
                break;
            }
            case DialogInterface.BUTTON_NEUTRAL: {
                switch (mViewModel.getNameListDialogShowing()) {
                    case TITLE_DIALOG_REFRESH: {
                        deleteListRefresh();
                        break;
                    }
                    case TITLE_DIALOG_FAVORITE: {
                        deleteListFavorite();
                        break;
                    }
                }
                break;
            }
        }
    }

    private void initEventsView() {
        mImgBack.setOnClickListener(this);
        mImgHighLight.setOnClickListener(this);
        mImgRefresh.setOnClickListener(this);
        mImgAddFavoriteReview.setOnClickListener(this);
        mTvWordsResultContent.setMovementMethod(LinkMovementMethod.getInstance());
        mTvWordsResultIntroduction.setMovementMethod(LinkMovementMethod.getInstance());
        mTvWordsResultTitle.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private SpannableString setSpannableClick(LineContentNewFeed contentNewFeed, String breakLine) {
        SpannableString result = new SpannableString(contentNewFeed.getTextContent() + breakLine);
        if (contentNewFeed.getListVocabularies() != null && !contentNewFeed.getListVocabularies().isEmpty()) {
            for (Vocabulary vocabulary : contentNewFeed.getListVocabularies()) {
                result.setSpan(new FavoriteWordClickableSpan(), vocabulary.getStartIndex(), vocabulary.getEndIndex(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        if (contentNewFeed.getListVocabulariesNotTranslate() != null && !contentNewFeed.getListVocabulariesNotTranslate().isEmpty()) {
            for (Vocabulary vocabulary : contentNewFeed.getListVocabulariesNotTranslate()) {
                result.setSpan(new DefaultWordClickableSpan(), vocabulary.getStartIndex(), vocabulary.getEndIndex(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return result;
    }

    private void showDialog(String title, String[] arrayVocabulary, boolean[] arraySelect) {
        mAlertDialogBuilder.setTitle(title);
        mAlertDialogBuilder.setMultiChoiceItems(arrayVocabulary, arraySelect, this);
        mAlertDialogBuilder.setPositiveButton("Xác nhận", this);
        mAlertDialogBuilder.setNegativeButton("Quay lại", this);
        mAlertDialogBuilder.setNeutralButton("Xoá tất cả", this);
        mAlertDialogBuilder.setCancelable(false);
        mAlertDialogBuilder.show();
    }

    private void reloadIconFavorite() {
        if (mViewModel.getSizeListAddFavorite() > 0) {
            mImgAddFavoriteReview.setVisibility(View.VISIBLE);
            mTvGuideFavorite.setVisibility(View.VISIBLE);
            tvFavoriteCount.setVisibility(View.VISIBLE);
            String count = String.valueOf(mViewModel.getSizeListAddFavorite());
            if (mViewModel.getSizeListAddFavorite() > 99) {
                count = "99+";
            }
            tvFavoriteCount.setText(count);
        } else {
            mImgAddFavoriteReview.setVisibility(View.GONE);
            mTvGuideFavorite.setVisibility(View.GONE);
            tvFavoriteCount.setVisibility(View.GONE);
        }
    }

    private void reloadIconRefresh() {
        if (mViewModel.getSizeListRefresh() > 0) {
            mImgRefresh.setVisibility(View.VISIBLE);
            mTvGuideRefresh.setVisibility(View.VISIBLE);
            tvRefreshCount.setVisibility(View.VISIBLE);
            String count = String.valueOf(mViewModel.getSizeListRefresh());
            if (mViewModel.getSizeListRefresh() > 99) {
                count = "99+";
            }
            tvRefreshCount.setText(count);
        } else {
            mImgRefresh.setVisibility(View.GONE);
            mTvGuideRefresh.setVisibility(View.GONE);
            tvRefreshCount.setVisibility(View.GONE);
        }
    }

    private void deleteListFavorite() {
        mViewModel.resetListVocabularyAddFavorite();
        reloadIconFavorite();
    }

    private void deleteListRefresh() {
        mViewModel.resetListVocabularyRefresh();
        reloadIconRefresh();
    }
}
