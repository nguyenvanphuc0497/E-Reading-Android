package com.dtu.capstone2.ereading.ui.account;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.ui.utils.RxBusTransport;
import com.dtu.capstone2.ereading.ui.utils.Transport;
import com.dtu.capstone2.ereading.ui.utils.TypeTransportBus;

/**
 * Create by Nguyen Van Phuc on 3/22/19
 */
public class PageAccountFragment extends Fragment {
    private TextView tvTitle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_page_account, container, false);
        tvTitle = view.findViewById(R.id.tvAccountTitle);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getContext(), ManagerAccountContainerActivity.class));
//                Only Test
                RxBusTransport.INSTANCE.publish(new Transport(TypeTransportBus.DIALOG_LOADING, "phuc"));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        RxBusTransport.INSTANCE.publish(new Transport(TypeTransportBus.DIALOG_SUCCESS, "phuc"));
                        RxBusTransport.INSTANCE.publish(new Transport(TypeTransportBus.DISMISS_DIALOG_LOADING, "phuc"));
                        RxBusTransport.INSTANCE.publish(new Transport(TypeTransportBus.DISMISS_DIALOG_LOADING, "phuc"));
                        RxBusTransport.INSTANCE.publish(new Transport(TypeTransportBus.DISMISS_DIALOG_LOADING, "phuc"));
                    }
                }, 3000);

            }
        });
    }
}
