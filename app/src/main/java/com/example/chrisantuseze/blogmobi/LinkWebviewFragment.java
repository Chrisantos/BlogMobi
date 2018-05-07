package com.example.chrisantuseze.blogmobi;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class LinkWebviewFragment extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";
    ProgressBar progressBar;

    public LinkWebviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_link_webview, container, false);

//        View parentView = getActivity().findViewById(R.id.progressBar);
//        if (parentView instanceof ProgressBar){
//            progressBar = (ProgressBar) parentView;
//        }
        progressBar = getActivity().findViewById(R.id.progressBar);

        String url = getArguments().getString(DetailArticle.URL_KEY);

        ((WebView) view.findViewById(R.id.website_detail)).loadUrl(url);     //Just what I added
        WebView webView = view.findViewById(R.id.website_detail);              //
        webView.setWebViewClient(new WebViewClient(){                                       //
            @Override                                                                       //
            public boolean shouldOverrideUrlLoading(WebView view, String url) {             //
                return super.shouldOverrideUrlLoading(view, url);                           //
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setVisibility(View.VISIBLE);
                if (newProgress >= 92){
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);                                   //
        webView.loadUrl(url);





        return view;
    }

}
