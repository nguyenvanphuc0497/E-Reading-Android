package com.dtu.capstone2.ereading.ui.newfeed.translate

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.dtu.capstone2.ereading.R
import com.dtu.capstone2.ereading.datasource.repository.EReadingRepository
import com.dtu.capstone2.ereading.datasource.repository.LocalRepository
import com.dtu.capstone2.ereading.ui.model.LineContentNewFeed
import com.dtu.capstone2.ereading.ui.model.VocabularyLocation
import com.dtu.capstone2.ereading.ui.utils.*
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_translate_result.*

/**
 * Create by Nguyen Van Phuc on 4/9/19
 */
class TranslateNewFeedFragment : BaseFragment(), View.OnClickListener, DialogInterface.OnMultiChoiceClickListener, DialogInterface.OnClickListener {
    companion object {
        const val TITLE_DIALOG_FAVORITE = "favorite"
        const val TITLE_DIALOG_REFRESH = "refresh"
    }

    private var mImgBack: ImageView? = null
    private var mImgHighLight: ImageView? = null
    private var mImgRefresh: ImageView? = null
    private var mImgAddFavoriteReview: ImageView? = null
    private var mTvGuideFavorite: TextView? = null
    private var mTvGuideRefresh: TextView? = null
    private lateinit var viewModel: TranslateNewFeedViewModel
    private var mProgress: ProgressBar? = null
    private var mAlertDialogBuilder: AlertDialog.Builder? = null
    private var tvFavoriteCount: TextView? = null
    private var tvRefreshCount: TextView? = null
    private var recyclerViewContent: RecyclerView? = null
    private lateinit var adapter: TranslateNewFeedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = TranslateNewFeedViewModel(EReadingRepository(), LocalRepository(context))
        if (arguments != null) {
            viewModel!!.urlNewFeed = arguments!!.getString(Constants.KEY_URL_NEW_FEED)
        }

        RxBusTransport.listen()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : Observer<Transport> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(transport: Transport) {
                        if (transport.typeTransport === TypeTransportBus.SPAN_ON_CLICK && transport.sender == DefaultWordClickableSpan::class.java.simpleName) {
                            viewModel!!.addOrRemoveVocabularyToListRefresh((transport.message as VocabularyLocation?)!!)
                            reloadIconRefresh()
                        }
                        if (transport.typeTransport === TypeTransportBus.SPAN_ON_CLICK && transport.sender == FavoriteWordClickableSpan::class.java.simpleName) {
                            viewModel!!.addOrRemoveVocabularyToListAddFavoriteByLocationVocabulary((transport.message as VocabularyLocation?)!!)
                            reloadIconFavorite()
                        }
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })

        mAlertDialogBuilder = AlertDialog.Builder(context!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.fragment_translate_result, container, false)
        mImgBack = view.findViewById(R.id.imgTranslateNewFeedBack)
        mProgress = view.findViewById(R.id.progressTranslateNewFeed)
        mImgHighLight = view.findViewById(R.id.imgTranslateNewFeedHighLight)
        mImgRefresh = view.findViewById(R.id.imgTranslateNewFeedRefresh)
        mImgAddFavoriteReview = view.findViewById(R.id.imgTranslateNewFeedFavoriteReview)
        mTvGuideFavorite = view.findViewById(R.id.tv_new_feed_translate_guide_favorite)
        mTvGuideRefresh = view.findViewById(R.id.tv_new_feed_translate_guide_refresh)
        tvFavoriteCount = view.findViewById(R.id.tvTranslateNewFeedFavoriteReviewCount)
        tvRefreshCount = view.findViewById(R.id.tvTranslateNewFeedRefreshCount)
        recyclerViewContent = view.findViewById(R.id.recyclerViewTranslateNewFeed)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initEventsView()
        viewModel!!.getDataFromHTMLAndOnNextDetectWord().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<LineContentNewFeed> {
                    override fun onSubscribe(d: Disposable) {
                        managerSubscribe.add(d)
                    }

                    override fun onNext(s: LineContentNewFeed) {
//                        if (mProgress!!.visibility != View.VISIBLE) {
//                            mProgress!!.visibility = View.VISIBLE
//                        }
//                        if (s.typeContent === TypeContent.TITLE) {
//                            viewModel!!.getmTextSpannableResultsTitle().append(setSpannableClick(s, ""))
//                            mTvWordsResultTitle.setText(viewModel!!.getmTextSpannableResultsTitle())
//                        } else if (s.typeContent === TypeContent.INTRODUCTION) {
//                            mTvWordsResultIntroduction.setText(setSpannableClick(s, ""))
//                        } else if (s.typeContent === TypeContent.TEXT) {
//                            viewModel!!.getmTextSpannableResultsContent().append(setSpannableClick(s, "\n\n"))
//                            mTvWordsResultContent!!.text = viewModel!!.getmTextSpannableResultsContent()
//                        }
                        adapter.notifyItemInserted(viewModel.getPositionItemInsertedOfRV())
                    }

                    override fun onError(e: Throwable) {
                        Log.w("Translate", e.toString())
//                        mProgress!!.visibility = View.GONE
                        Toast.makeText(context, "Quá trình dịch gián đoạn! Kiểm tra kết nối Internet.", Toast.LENGTH_LONG).show()
                    }

                    override fun onComplete() {
//                        mProgress!!.visibility = View.GONE
                        Toast.makeText(context, "Dịch hoàn tất.", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.imgTranslateNewFeedBack -> {
                activity!!.onBackPressed()
            }
            R.id.imgTranslateNewFeedHighLight -> {
            }//                mTextSpannableResults.removeSpan(new);
            //                mTvWordsResultTitle.setText(mTextSpannableResults);
            R.id.imgTranslateNewFeedRefresh -> {
                viewModel!!.setNameListDialogShowing(TITLE_DIALOG_REFRESH)
                showDialog("Danh sách các từ đã chọn.", viewModel!!.getArrayWordRefresh(), viewModel!!.getArraySelectedRefresh())
            }
            R.id.imgTranslateNewFeedFavoriteReview -> {
                viewModel!!.setNameListDialogShowing(TITLE_DIALOG_FAVORITE)
                showDialog("Danh sách các từ yêu thích đã chọn.", viewModel!!.getArrayWordAddFavorite(), viewModel!!.getArraySelectedAddFavorite())
            }
        }
    }

    override fun onClick(dialog: DialogInterface, which: Int, isChecked: Boolean) {

    }

    override fun onClick(dialog: DialogInterface, which: Int) {
        when (which) {
            DialogInterface.BUTTON_POSITIVE -> {
                Log.e("xxx", "BUTTON_POSITIVE")
            }
            DialogInterface.BUTTON_NEGATIVE -> {
            }
            DialogInterface.BUTTON_NEUTRAL -> {
                when (viewModel!!.getNameListDialogShowing()) {
                    TITLE_DIALOG_REFRESH -> {
                        deleteListRefresh()
                    }
                    TITLE_DIALOG_FAVORITE -> {
                        deleteListFavorite()
                    }
                }
            }
        }
    }

    private fun initView() {
        adapter = TranslateNewFeedAdapter(viewModel.dataRecyclerView)
        recyclerViewTranslateNewFeed?.layoutManager = LinearLayoutManager(context)
        recyclerViewTranslateNewFeed?.adapter = adapter
    }

    private fun initEventsView() {
        mImgBack!!.setOnClickListener(this)
        mImgHighLight!!.setOnClickListener(this)
        mImgRefresh!!.setOnClickListener(this)
        mImgAddFavoriteReview!!.setOnClickListener(this)
    }

    private fun showDialog(title: String, arrayVocabulary: Array<String>, arraySelect: BooleanArray) {
        mAlertDialogBuilder!!.setTitle(title)
        mAlertDialogBuilder!!.setMultiChoiceItems(arrayVocabulary, arraySelect, this)
        mAlertDialogBuilder!!.setPositiveButton("Xác nhận", this)
        mAlertDialogBuilder!!.setNegativeButton("Quay lại", this)
        mAlertDialogBuilder!!.setNeutralButton("Xoá tất cả", this)
        mAlertDialogBuilder!!.setCancelable(false)
        mAlertDialogBuilder!!.show()
    }

    private fun reloadIconFavorite() {
        if (viewModel!!.getSizeListAddFavorite() > 0) {
            mImgAddFavoriteReview!!.visibility = View.VISIBLE
            mTvGuideFavorite!!.visibility = View.VISIBLE
            tvFavoriteCount!!.visibility = View.VISIBLE
            var count = viewModel!!.getSizeListAddFavorite().toString()
            if (viewModel!!.getSizeListAddFavorite() > 99) {
                count = "99+"
            }
            tvFavoriteCount!!.text = count
        } else {
            mImgAddFavoriteReview!!.visibility = View.GONE
            mTvGuideFavorite!!.visibility = View.GONE
            tvFavoriteCount!!.visibility = View.GONE
        }
    }

    private fun reloadIconRefresh() {
        if (viewModel!!.getSizeListRefresh() > 0) {
            mImgRefresh!!.visibility = View.VISIBLE
            mTvGuideRefresh!!.visibility = View.VISIBLE
            tvRefreshCount!!.visibility = View.VISIBLE
            var count = viewModel!!.getSizeListRefresh().toString()
            if (viewModel!!.getSizeListRefresh() > 99) {
                count = "99+"
            }
            tvRefreshCount!!.text = count
        } else {
            mImgRefresh!!.visibility = View.GONE
            mTvGuideRefresh!!.visibility = View.GONE
            tvRefreshCount!!.visibility = View.GONE
        }
    }

    private fun deleteListFavorite() {
        viewModel!!.resetListVocabularyAddFavorite()
        reloadIconFavorite()
    }

    private fun deleteListRefresh() {
        viewModel!!.resetListVocabularyRefresh()
        reloadIconRefresh()
    }
}
