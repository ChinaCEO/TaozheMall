export const getJumpBaseUrl = (toUrl, webUrl) => {
    let bundleUrl = weex.config.bundleUrl;
    bundleUrl = new String(bundleUrl);
    let nativeBase;
    let native;
    let toWebUrl; 
    if(webUrl) {
      toWebUrl = "?couponUrl=" + webUrl
    }else {
      toWebUrl = ''
    }
    
    if (WXEnvironment.platform === "android") {
      // nativeBase = 'file://assets/dist/';
      // native = nativeBase + toUrl + ".js"
      let isAndroidAssets = bundleUrl.indexOf('file://assets/') >= 0;
      if(isAndroidAssets){
        nativeBase = 'local://' + 'file://assets/dist/';
        native = nativeBase + toUrl + ".js" + toWebUrl;
      }else{
        nativeBase = bundleUrl.substring(0, bundleUrl.lastIndexOf("/") + 1);
        native = nativeBase + toUrl + ".js" + toWebUrl;
      }
      
    } else if (WXEnvironment.platform === "iOS") {
      nativeBase = bundleUrl.substring(0, bundleUrl.lastIndexOf("/") + 1);
      native = nativeBase + toUrl + ".js" + toWebUrl;
    } else {
      var host = "localhost:8081";
      var matches = /\/\/([^\/]+?)\//.exec(bundleUrl);
      if (matches && matches.length >= 2) {
        host = matches[1];
      }

      //此处需注意一下,tabbar 用的直接是jsbundle 的路径,但是navigator是直接跳转到新页面上的.
      if (typeof window === "object") {
        nativeBase = "http://" + host + "/";
      } else {
        nativeBase = "http://" + host + "/";
      }

      native = nativeBase + toUrl + ".html" + toWebUrl;
    }
    return native;
}