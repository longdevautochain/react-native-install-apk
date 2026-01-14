package com.camnpr.react_native_install_apk;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import androidx.core.content.FileProvider;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.io.File;

public class InstallApkModule extends ReactContextBaseJavaModule {
  private final ReactApplicationContext _context;

  public InstallApkModule(ReactApplicationContext reactContext) {
    super(reactContext);
    _context = reactContext;
  }

  @Override
  public String getName() {
    return "InstallApk";
  }

  @ReactMethod
  public void install(String path) {
    if (path == null || path.isEmpty()) return;
    
    File file = new File(path);
    if (!file.exists()) return;

    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

    Uri uri;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      String authority = _context.getPackageName() + ".installapk.provider";
      uri = FileProvider.getUriForFile(_context, authority, file);
    } else {
      uri = Uri.fromFile(file);
    }

    intent.setDataAndType(uri, "application/vnd.android.package-archive");
    _context.startActivity(intent);
  }
}
