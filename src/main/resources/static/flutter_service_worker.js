'use strict';
const MANIFEST = 'flutter-app-manifest';
const TEMP = 'flutter-temp-cache';
const CACHE_NAME = 'flutter-app-cache';

const RESOURCES = {"flutter_bootstrap.js": "59c81448b8b45c506716b0c4ca4b3cdf",
"main.dart.js_34.part.js": "5becb02b0e0b84a31db27dfaedd10b70",
"version.json": "b09ca504ea08f480a63d25f8eaf65317",
"main.dart.js_7.part.js": "abb2b5189d3ef4d6647a3f8463743335",
"flutter-canvaskit/1e9a811bf8e70466596bcf0ea3a8b5adb5f17f7f/chromium/canvaskit.js": "5e27aae346eee469027c80af0751d53d",
"flutter-canvaskit/1e9a811bf8e70466596bcf0ea3a8b5adb5f17f7f/chromium/canvaskit.wasm": "24c77e750a7fa6d474198905249ff506",
"flutter-canvaskit/1e9a811bf8e70466596bcf0ea3a8b5adb5f17f7f/canvaskit.js": "140ccb7d34d0a55065fbd422b843add6",
"flutter-canvaskit/1e9a811bf8e70466596bcf0ea3a8b5adb5f17f7f/canvaskit.wasm": "07b9f5853202304d3b0749d9306573cc",
"favicon.ico": "568a1ed53b5fb9ba8cb13938b4c1b23a",
"index.html": "f0fb83eef421024a56d50022cd18118f",
"/": "f0fb83eef421024a56d50022cd18118f",
"main.dart.js_35.part.js": "20111372b4e2da1b6cbcdce1f7c4d76b",
"main.dart.js_25.part.js": "15a4921e989236dd62952a3f72f0c428",
"main.dart.js_47.part.js": "35f0571e44f4c4b202229c5d867bfcd8",
"main.dart.js_4.part.js": "1fa7bde5a2d1df2b1f04b7e1f3150e2e",
"main.dart.js": "a50fdfb2c98acf9159104c71937bd8d3",
"main.dart.js_27.part.js": "6b8364881838e74fb1a5569969eec146",
"flutter.js": "24bc71911b75b5f8135c949e27a2984e",
"main.dart.js_44.part.js": "68791fa08f9b737e61e19bf93d80013a",
"main.dart.js_36.part.js": "cbc7961919f5f26104f196ee93dd9dad",
"main.dart.js_26.part.js": "08ef35db0de0ae3c30f8c737bcc927ab",
"main.dart.js_5.part.js": "590c746fd51dfbe360eaf08918fabc1e",
"main.dart.js_48.part.js": "92024dee8b1a85948f957951a7a1365f",
"main.dart.js_15.part.js": "569fd351ff7f4c855281297385ab9f99",
"favicon.png": "0bb582c2d7ecab35d61c52f918b3a57a",
"main.dart.js_51.part.js": "31bfd10c6df5d8bea275508b649c5ea3",
"main.dart.js_23.part.js": "50f38d287258dc561056e846a288bab1",
"main.dart.js_33.part.js": "7380e781d28cb9ad9a6298fb59d3b684",
"main.dart.js_9.part.js": "3775cf73785d20acb9406ee615bdbf21",
"main.dart.js_50.part.js": "e8e644f29796c0418a5dd6d41a6ae111",
"main.dart.js_40.part.js": "0e26b50d8c893856ab59e6915d579b2d",
"main.dart.js_32.part.js": "2d45b2a3c1a809bf4ab52ad23411fc38",
"main.dart.js_22.part.js": "621c8e27720004c2f20178920fec9196",
"main.dart.js_8.part.js": "98da0dd10a4ce7a61c898ac9872226da",
"icons/app_icon.png": "a5795b892c8ca52b83f62a72cb9c56af",
"manifest.json": "ce255632f156b3abd6f7816522c4ff1a",
"main.dart.js_49.part.js": "46cb86e8c068d2e13212c95ee0e769b3",
"main.dart.js_1.part.js": "3ff26687c7599c97bac9282576fa7914",
"main.dart.js_20.part.js": "9e38ff290ea214bde7e809657327d8c5",
"main.dart.js_30.part.js": "b911ba3b76baf3dfc5ef07d61f8b970a",
"main.dart.js_42.part.js": "dee9ad4e91b28f03ddbe2b0a3db38d33",
"main.dart.js_39.part.js": "4e064cc20ddccf7e091edfe482ed19a5",
"main.dart.js_29.part.js": "24c40e814851b57ace8350a2bc41ccbd",
"assets/NOTICES": "575831ce7507f458acae09d9d8c97b05",
"assets/FontManifest.json": "bed231d7b112623f57dc119fc603f653",
"assets/AssetManifest.bin.json": "6e67706e55af7f6c136acc6c44bb0984",
"assets/packages/cupertino_icons/assets/CupertinoIcons.ttf": "33b7d9392238c04c131b6ce224e13711",
"assets/packages/flutter_inappwebview_web/assets/web/web_support.js": "509ae636cfdd93e49b5a6eaf0f06d79f",
"assets/packages/fluttertoast/assets/toastify.js": "56e2c9cedd97f10e7e5f1cebd85d53e3",
"assets/packages/fluttertoast/assets/toastify.css": "a85675050054f179444bc5ad70ffc635",
"assets/packages/flutter_inappwebview/assets/t_rex_runner/t-rex.css": "5a8d0222407e388155d7d1395a75d5b9",
"assets/packages/flutter_inappwebview/assets/t_rex_runner/t-rex.html": "16911fcc170c8af1c5457940bd0bf055",
"assets/packages/wakelock_plus/assets/no_sleep.js": "7748a45cd593f33280669b29c2c8919a",
"assets/shaders/ink_sparkle.frag": "ecc85a2e95f5e9f53123dcaf8cb9b6ce",
"assets/shaders/stretch_effect.frag": "40d68efbbf360632f614c731219e95f0",
"assets/AssetManifest.bin": "314f55840145913200674ee58d98c755",
"assets/fonts/boldface/AlimamaShuHeiTi-Bold.ttf": "9c8d7f88c39c3d90cd41ea9db70a9fa7",
"assets/fonts/circle/AlimamaFangYuanTiVF-Thin.ttf": "3be809e80815a8bc0feba3b241231c88",
"assets/fonts/MaterialIcons-Regular.otf": "ff33c3e19d2c5079f7330716ad8ac0ca",
"assets/assets/images/no_cover.jpeg": "7e48f3134478ff6a02bdaae12702e698",
"assets/assets/images/fq.png": "6e5136dee28ad0cb3a351ed047fac21c",
"assets/assets/images/package.txt": "d41d8cd98f00b204e9800998ecf8427e",
"assets/assets/images/splash.png": "0e3867382361707242862a08af1ccd4a",
"assets/assets/images/logo.png": "0e3867382361707242862a08af1ccd4a",
"assets/assets/images/qq.png": "491c32aad179267434096dc7f2b16214",
"assets/assets/images/qd.png": "7a81e826d519521b044abcf143a4bbcc",
"assets/assets/images/ic_launcher_round.webp": "a5b7d4275a1106f28902462be6eb7d9b",
"assets/assets/images/default_avatar.jpeg": "3cfd6cec07546522179cab14e9375c10",
"assets/assets/bg/8.jpg": "8fbc91e94bf5d0a50825840e228f9000",
"assets/assets/bg/9.jpg": "0092d2edcce59d898890d67d70782a42",
"assets/assets/bg/14.jpg": "7127ce518e454e811fbd4a6aa100ee85",
"assets/assets/bg/12.jpg": "3d39267e17bd07c35310c1c5aee6d373",
"assets/assets/bg/13.jpg": "36d67fb6aa9f88225c3de8724a4ee936",
"assets/assets/bg/11.jpg": "6efea1c466b21b16ae47840176119076",
"assets/assets/bg/10.jpg": "48b9f025bc1bcf4e604a885eb64c1573",
"assets/assets/bg/4.jpg": "3358e92098b1750ffb4cb139697fc40d",
"assets/assets/bg/5.jpg": "9d83e37c90acb62f65ef7516a203ed57",
"assets/assets/bg/7.jpg": "87e353a5aeb7db7ca04a50dd46c3b200",
"assets/assets/bg/6.jpg": "d062fef5a96ad859963bc7e1110fb025",
"assets/assets/bg/2.jpg": "1b3318fbc6dfc92d14c03e42565a0edc",
"assets/assets/bg/3.jpg": "98c18f5b62d40ca8b18a7241a91db4d1",
"assets/assets/bg/1.jpg": "bad1907a1b54e710ca483ba47d23c671",
"main.dart.js_28.part.js": "64794128cf8f22cfbf58db315a8f4b20",
"main.dart.js_38.part.js": "674c3aae7cf3500bd550869a215da181",
"main.dart.js_2.part.js": "bab62bd84d38df9d9f15ba57e5bcfa20",
"main.dart.js_31.part.js": "29733d2b0127199542ccd3281a3945f4",
"main.dart.js_21.part.js": "34517d9f907d7a3fca1258684dd7f998",
"main.dart.js_43.part.js": "9320fef0080b03f6efe1046e960c850a",
"canvaskit/skwasm.js": "8060d46e9a4901ca9991edd3a26be4f0",
"canvaskit/skwasm_heavy.js": "740d43a6b8240ef9e23eed8c48840da4",
"canvaskit/skwasm.js.symbols": "3a4aadf4e8141f284bd524976b1d6bdc",
"canvaskit/canvaskit.js.symbols": "a3c9f77715b642d0437d9c275caba91e",
"canvaskit/skwasm_heavy.js.symbols": "0755b4fb399918388d71b59ad390b055",
"canvaskit/skwasm.wasm": "7e5f3afdd3b0747a1fd4517cea239898",
"canvaskit/chromium/canvaskit.js.symbols": "e2d09f0e434bc118bf67dae526737d07",
"canvaskit/chromium/canvaskit.js": "a80c765aaa8af8645c9fb1aae53f9abf",
"canvaskit/chromium/canvaskit.wasm": "a726e3f75a84fcdf495a15817c63a35d",
"canvaskit/canvaskit.js": "8331fe38e66b3a898c4f37648aaf7ee2",
"canvaskit/canvaskit.wasm": "9b6a7830bf26959b200594729d73538e",
"canvaskit/skwasm_heavy.wasm": "b0be7910760d205ea4e011458df6ee01"};
// The application shell files that are downloaded before a service worker can
// start.
const CORE = ["main.dart.js",
"index.html",
"flutter_bootstrap.js",
"assets/AssetManifest.bin.json",
"assets/FontManifest.json"];

// During install, the TEMP cache is populated with the application shell files.
self.addEventListener("install", (event) => {
  self.skipWaiting();
  return event.waitUntil(
    caches.open(TEMP).then((cache) => {
      return cache.addAll(
        CORE.map((value) => new Request(value, {'cache': 'reload'})));
    })
  );
});
// During activate, the cache is populated with the temp files downloaded in
// install. If this service worker is upgrading from one with a saved
// MANIFEST, then use this to retain unchanged resource files.
self.addEventListener("activate", function(event) {
  return event.waitUntil(async function() {
    try {
      var contentCache = await caches.open(CACHE_NAME);
      var tempCache = await caches.open(TEMP);
      var manifestCache = await caches.open(MANIFEST);
      var manifest = await manifestCache.match('manifest');
      // When there is no prior manifest, clear the entire cache.
      if (!manifest) {
        await caches.delete(CACHE_NAME);
        contentCache = await caches.open(CACHE_NAME);
        for (var request of await tempCache.keys()) {
          var response = await tempCache.match(request);
          await contentCache.put(request, response);
        }
        await caches.delete(TEMP);
        // Save the manifest to make future upgrades efficient.
        await manifestCache.put('manifest', new Response(JSON.stringify(RESOURCES)));
        // Claim client to enable caching on first launch
        self.clients.claim();
        return;
      }
      var oldManifest = await manifest.json();
      var origin = self.location.origin;
      for (var request of await contentCache.keys()) {
        var key = request.url.substring(origin.length + 1);
        if (key == "") {
          key = "/";
        }
        // If a resource from the old manifest is not in the new cache, or if
        // the MD5 sum has changed, delete it. Otherwise the resource is left
        // in the cache and can be reused by the new service worker.
        if (!RESOURCES[key] || RESOURCES[key] != oldManifest[key]) {
          await contentCache.delete(request);
        }
      }
      // Populate the cache with the app shell TEMP files, potentially overwriting
      // cache files preserved above.
      for (var request of await tempCache.keys()) {
        var response = await tempCache.match(request);
        await contentCache.put(request, response);
      }
      await caches.delete(TEMP);
      // Save the manifest to make future upgrades efficient.
      await manifestCache.put('manifest', new Response(JSON.stringify(RESOURCES)));
      // Claim client to enable caching on first launch
      self.clients.claim();
      return;
    } catch (err) {
      // On an unhandled exception the state of the cache cannot be guaranteed.
      console.error('Failed to upgrade service worker: ' + err);
      await caches.delete(CACHE_NAME);
      await caches.delete(TEMP);
      await caches.delete(MANIFEST);
    }
  }());
});
// The fetch handler redirects requests for RESOURCE files to the service
// worker cache.
self.addEventListener("fetch", (event) => {
  if (event.request.method !== 'GET') {
    return;
  }
  var origin = self.location.origin;
  var key = event.request.url.substring(origin.length + 1);
  // Redirect URLs to the index.html
  if (key.indexOf('?v=') != -1) {
    key = key.split('?v=')[0];
  }
  if (event.request.url == origin || event.request.url.startsWith(origin + '/#') || key == '') {
    key = '/';
  }
  // If the URL is not the RESOURCE list then return to signal that the
  // browser should take over.
  if (!RESOURCES[key]) {
    return;
  }
  // If the URL is the index.html, perform an online-first request.
  if (key == '/') {
    return onlineFirst(event);
  }
  event.respondWith(caches.open(CACHE_NAME)
    .then((cache) =>  {
      return cache.match(event.request).then((response) => {
        // Either respond with the cached resource, or perform a fetch and
        // lazily populate the cache only if the resource was successfully fetched.
        return response || fetch(event.request).then((response) => {
          if (response && Boolean(response.ok)) {
            cache.put(event.request, response.clone());
          }
          return response;
        });
      })
    })
  );
});
self.addEventListener('message', (event) => {
  // SkipWaiting can be used to immediately activate a waiting service worker.
  // This will also require a page refresh triggered by the main worker.
  if (event.data === 'skipWaiting') {
    self.skipWaiting();
    return;
  }
  if (event.data === 'downloadOffline') {
    downloadOffline();
    return;
  }
});
// Download offline will check the RESOURCES for all files not in the cache
// and populate them.
async function downloadOffline() {
  var resources = [];
  var contentCache = await caches.open(CACHE_NAME);
  var currentContent = {};
  for (var request of await contentCache.keys()) {
    var key = request.url.substring(origin.length + 1);
    if (key == "") {
      key = "/";
    }
    currentContent[key] = true;
  }
  for (var resourceKey of Object.keys(RESOURCES)) {
    if (!currentContent[resourceKey]) {
      resources.push(resourceKey);
    }
  }
  return contentCache.addAll(resources);
}
// Attempt to download the resource online before falling back to
// the offline cache.
function onlineFirst(event) {
  return event.respondWith(
    fetch(event.request).then((response) => {
      return caches.open(CACHE_NAME).then((cache) => {
        cache.put(event.request, response.clone());
        return response;
      });
    }).catch((error) => {
      return caches.open(CACHE_NAME).then((cache) => {
        return cache.match(event.request).then((response) => {
          if (response != null) {
            return response;
          }
          throw error;
        });
      });
    })
  );
}
