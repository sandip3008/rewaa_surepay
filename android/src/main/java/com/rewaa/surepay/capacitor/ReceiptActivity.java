package com.rewaa.surepay.capacitor;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import CTOS.CtPrint;


public class ReceiptActivity extends Activity {

  WebView webView;
  ProgressBar progressBar;
  Bitmap webviewBitmp = null;
  private CtPrint print;

  String url = "file:///android_asset/img/test.html";
  String TAG = "ReceiptActivity";

//    final String filename= URLUtil.guessFileName(URLUtil.guessUrl(url));

  @SuppressLint("SetJavaScriptEnabled")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    String package_name = getApplication().getPackageName();
    if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
      WebView.enableSlowWholeDocumentDraw();
    }
    setContentView(getApplication().getResources().getIdentifier("activity_main", "layout", package_name));

//        getSupportActionBar().hide();
    //INIT PRINT
    try {
            print = new CtPrint();
    } catch (Exception e) {
      e.printStackTrace();
    }


    webView = findViewById(R.id.web);
    progressBar = findViewById(R.id.progress);
    progressBar.setVisibility(View.GONE);
    webView.getSettings().setJavaScriptEnabled(true);
    webView.getSettings().setSupportZoom(false);
    webView.getSettings().setDomStorageEnabled(true);
    webView.setWebChromeClient(new WebChromeClient());
    webView.setWebViewClient(new myWebViewclient());
    String html = "";
    try {
      html = URLEncoder.encode(getIntent().getStringExtra("data"), "utf-8").replaceAll("\\+", " ");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    webView.loadData(html, "text/html; charset=utf-8", "UTF-8");


    //  ==================== START HERE: THIS CODE BLOCK IS TO ENABLE FILE DOWNLOAD FROM THE WEB. YOU CAN COMMENT IT OUT IF YOUR APPLICATION DOES NOT REQUIRE FILE DOWNLOAD. IT WAS ADDED ON REQUEST ======//

//        webView.setDownloadListener(new DownloadListener() {
//            String fileName = MimeTypeMap.getFileExtensionFromUrl(url);
//            @Override
//            public void onDownloadStart(String url, String userAgent,
//                                        String contentDisposition, String mimetype,
//                                        long contentLength) {
//
//                DownloadManager.Request request = new DownloadManager.Request(
//                        Uri.parse(url));
//
//                request.allowScanningByMediaScanner();
//                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
//                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
//                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
//                dm.enqueue(request);
//                Toast.makeText(getApplicationContext(), "Downloading File", //To notify the Client that the file is being downloaded
//                        Toast.LENGTH_LONG).show();
//
//            }
//        });
    //  ==================== END HERE: THIS CODE BLOCK IS TO ENABLE FILE DOWNLOAD FROM THE WEB. YOU CAN COMMENT IT OUT IF YOUR APPLICATION DOES NOT REQUIRE FILE DOWNLOAD. IT WAS ADDED ON REQUEST ======//


  }


  public class myWebViewclient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
      view.loadUrl(url);
      return true;
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
      Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
//      webView.loadUrl("file:///android_asset/lost.html");
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
      super.onReceivedSslError(view, handler, error);
      handler.cancel();
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
      super.onPageStarted(view, url, favicon);
      progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
      super.onPageFinished(view, url);
      Log.e(TAG,"onPageFinished");

      new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
        @Override
        public void run() {
          runOnUiThread(new Runnable() {
            @Override
            public void run() {

              try {
                progressBar.setVisibility(View.GONE);
                            doPrinting(view);
//                createWebPrintJob(view);

              }catch (Exception e){
                e.printStackTrace();
              }

            }
          });
        }
      }, 2000);
    }
  }


  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {

    if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
      webView.goBack();
      return true;
    }
    return super.onKeyDown(keyCode, event);
  }

  public static class PrintDocumentAdapterWrapper extends PrintDocumentAdapter {
    String TAG = "PrintDocumentAdapterWrapper";
    private final PrintDocumentAdapter delegate;
    private final Activity activity;

    PrintDocumentAdapterWrapper(Activity activity, PrintDocumentAdapter adapter) {
      super();
      this.activity = activity;
      this.delegate = adapter;
    }

    @Override
    public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras) {
      delegate.onLayout(oldAttributes, newAttributes, cancellationSignal, callback, extras);
      Log.d(TAG, "onLayout");
    }

    @Override
    public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {
      delegate.onWrite(pages, destination, cancellationSignal, callback);
      Log.d(TAG, "onWrite");
    }

    @Override
    public void onFinish() {
      delegate.onFinish();
      activity.finish();
      Log.d(TAG, "onFinish");
    }

  }

  public static Bitmap getBitmapFromView(View view) {
    view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
    Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
            Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
    view.draw(canvas);
    return bitmap;
  }

  private static Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
    if (maxHeight > 0 && maxWidth > 0) {
//            Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);
      int width = image.getWidth();
      int height = image.getHeight();
      float ratioBitmap = (float) width / (float) height;
      float ratioMax = (float) maxWidth / (float) maxHeight;

      int finalWidth = maxWidth;
      int finalHeight = maxHeight;
      if (ratioMax > ratioBitmap) {
        finalWidth = (int) ((float)maxHeight * ratioBitmap);
      } else {
        finalHeight = (int) ((float)maxWidth / ratioBitmap);
      }
      image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, false);
      return image;
    } else {
      return image;
    }
  }

  //CTPrinter
  private void doPrinting(View view) {
    Bitmap temp = getBitmapFromView(view);
    Bitmap finalPrint = resize(temp,800,2600);
    Log.e("doPrinting", "doPrinting: ");
        print.initPage(finalPrint.getHeight());
        print.drawImage(finalPrint, 0, 0);
        print.printPage();
        print.roll(100);
  }

  //create a function to create the print job
  private void createWebPrintJob(WebView webView) {

    //create object of print manager in your device
    PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);

    //create object of print adapter
    PrintDocumentAdapterWrapper printAdapter = new PrintDocumentAdapterWrapper(ReceiptActivity.this, webView.createPrintDocumentAdapter());

    //provide name to your newly generated pdf file
    String jobName = "Print Invoice";

    // Create a print job with name and adapter instance
    PrintJob printJob = printManager.print(jobName, printAdapter,
            new PrintAttributes.Builder().build());

    // Save the job object for later status checking
//    mPrintJobs.add(printJob);

    //open print dialog
    if (printManager != null) {
      printManager.print(jobName, printAdapter, new PrintAttributes.Builder().setMinMargins(new PrintAttributes.Margins(0, 0, 0, 0)).build());
    } else {
      Log.e("createWebPrintJob", "printManager null");
//            webView.loadData(String.format(Locale.US, htmlHead, fontSize, printFontSize) + "PrintManager is null" + htmlFooter, "text/html; charset=utf-8", "utf-8");

    }
  }


}
