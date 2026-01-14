# React Native APK Install Module (Modernized)

This project has been modernized to support Android 14+ (API 34), fixing issues with `FileUriExposedException` on Android 7+ and permission handling on Android 8+.

## Features

- ✅ **Android 14+ Support**: Updated Gradle and Android SDK configurations.
- ✅ **FileProvider Integration**: Built-in FileProvider to securely share APK files (fixes Android 7.0+ crash).
- ✅ **Automatic Permissions**: Manifest includes `REQUEST_INSTALL_PACKAGES`.
- ✅ **React Native Autolinking**: No need for manual `react-native link`.
- ✅ **TypeScript Support**: Includes type definitions.

## Installation

### From GitHub (Recommended for Fork)

Since this is a custom fork, install it directly from the git repository:

```bash
# using npm
npm install github:longdevautochain/react-native-install-apk#master

# using yarn
yarn add github:longdevautochain/react-native-install-apk#master
```

## Usage

### 1. Basic Usage

```javascript
import InstallApk from "@isudaji/react-native-install-apk";

// The path must be an absolute path to the APK file on the device
const apkPath = "/storage/emulated/0/Download/update.apk";

InstallApk.install(apkPath);
```

### 2. Download and Install (with `react-native-fs`)

```javascript
import RNFS from "react-native-fs";
import InstallApk from "@isudaji/react-native-install-apk";
import { Platform } from "react-native";

const downloadAndInstall = async (url) => {
  // Save to ExternalCachesDirectoryPath to ensure it's accessible
  const filePath = RNFS.ExternalCachesDirectoryPath + "/update.apk";

  const options = {
    fromUrl: url,
    toFile: filePath,
    progress: (res) => {
      console.log((res.bytesWritten / res.contentLength).toFixed(2));
    },
  };

  RNFS.downloadFile(options).promise.then((res) => {
    if (res.statusCode === 200) {
      InstallApk.install(filePath);
    }
  });
};
```

本模組使用 `FileProvider` 安全地分享 APK 檔案給 Android 安裝程式：

- **Authority**: `${applicationId}.installapk.provider` (自動根據您的 App ID 生成，避免衝突)
- **Path**: 支援外部儲存 (external path)、快取 (cache) 及 檔案目錄 (files)。

## 修改記錄 (Modernization Changes)

此 Fork 版本已進行以下修復：

1. **Android Gradle Plugin**: 升級至現代版本。
2. **Dependencies**: 將 `compile` 替換為 `implementation`。
3. **Java Source**:
   - 移除不安全的 `chmod 777`。
   - 實作 `FileProvider.getUriForFile` 以支援 Android N+。
   - 加入 `FLAG_GRANT_READ_URI_PERMISSION`。
4. **Manifest**: 加入 Provider 定義及必要權限。

---

_Based on previous guide and modernization work._
