'use strict';
const MANIFEST = 'flutter-app-manifest';
const TEMP = 'flutter-temp-cache';
const CACHE_NAME = 'flutter-app-cache';

const RESOURCES = {"flutter_bootstrap.js": "ce800a9854e61d93a229482f6e994800",
"main.dart.js_69.part.js": "af24ed7039d856fd5bdbade7325c9514",
"main.dart.js_24.part.js": "4c20f884e2d65787f412f9394f5e8359",
"main.dart.js_34.part.js": "2cd7ae1447bd705027b8d918fe07d1ff",
"main.dart.js_46.part.js": "edcf54731adc29b493b1bee80d12d223",
"main.dart.js_163.part.js": "63fc7a662bacc7707aed4d0f67baef8f",
"main.dart.js_173.part.js": "eafc3a002ef1f2b7fc89f52381c68819",
"main.dart.js_145.part.js": "cbb65fac5b1c51f06502bbde83846985",
"main.dart.js_155.part.js": "603e31a7292a9d69736bc66bfee338d4",
"version.json": "85cab4e56c620d0c8a0841ab6e9b7578",
"main.dart.js_7.part.js": "7ffb60c3dae83aa67899b93310ffb46c",
"main.dart.js_137.part.js": "f5dd9b303d65b47b79ec21420443c1c0",
"main.dart.js_84.part.js": "d3adc974bbf7d1c48d194741a48ff319",
"main.dart.js_94.part.js": "9e11f6aa1465e3d1d74c94c542f214f2",
"main.dart.js_154.part.js": "093305f061c7bad124e4a332ee73b8b7",
"main.dart.js_144.part.js": "18e27fd095935fb6fbd1cbac56264666",
"main.dart.js_61.part.js": "8cea68bfafdb50c68c7cce6ea537b7ba",
"main.dart.js_6.part.js": "26a2fc675ad5ec5855a06c17dc2b243e",
"index.html": "f0fb83eef421024a56d50022cd18118f",
"/": "f0fb83eef421024a56d50022cd18118f",
"main.dart.js_136.part.js": "02f764ee9a89b53ae8dd727d4c2a1132",
"main.dart.js_126.part.js": "7dd37436b7e420a681d3d7b2c1442a7c",
"main.dart.js_95.part.js": "529d11d48cc4bfb813ebea83c1f883f3",
"main.dart.js_85.part.js": "4a9f9749cb699fd6b9f89d129e896106",
"main.dart.js_196.part.js": "8e71e1af65b4275645117c73aa0455ce",
"main.dart.js_186.part.js": "35ed8dfa922eb67d8809506c2214c1c8",
"main.dart.js_68.part.js": "82ed0fee7d512f2a75b044cf17a9b360",
"main.dart.js_78.part.js": "14e91b233e5b81854e31147d217ab044",
"main.dart.js_110.part.js": "7f4d25a4d377c48fcdf26077398cf161",
"main.dart.js_100.part.js": "dd083f9ee71c49fcd9449338ea3b0600",
"main.dart.js_35.part.js": "7c0976f156b42eff510e3f92abf7724a",
"main.dart.js_25.part.js": "1c18351f3f4ca415129e7812298768be",
"main.dart.js_57.part.js": "012a2293574786768bfd76c97c23a682",
"main.dart.js_47.part.js": "3caf32de687f2e9b570123bd1f2458fc",
"main.dart.js_124.part.js": "7aea7d8cbc7dc1aafeb2718e38dfc97b",
"main.dart.js_134.part.js": "34e1dc7081ab3c01aa51743d6962cc63",
"main.dart.js_63.part.js": "8fb427864ca06db07ee21ea46e7c4bc2",
"main.dart.js_4.part.js": "3dd5c1bf29de2cfb42fd4276001705e7",
"main.dart.js_146.part.js": "cdef3a4ac74432af159cd085e480328a",
"main.dart.js_156.part.js": "8a8c7ac65edbd772c3d99964b734e88c",
"main.dart.js": "c02cc9febdba13c76255db258a69de26",
"main.dart.js_87.part.js": "0c1d29efb565697bef6024f2a844b77e",
"main.dart.js_97.part.js": "fabc299a10f4a7510762cf7dfdb53f2f",
"main.dart.js_169.part.js": "a2169dbda1850c13eaf9579cf5a88889",
"main.dart.js_194.part.js": "556155d3ddfa9d6e7f34c21b65ab2164",
"main.dart.js_18.part.js": "d7919163b076130716410dbd1776d9a1",
"main.dart.js_160.part.js": "ab1d6743638fd0b76e8a73feec61d528",
"main.dart.js_170.part.js": "4b836e5d6dcce6cabd5b7fad284920bb",
"main.dart.js_45.part.js": "8b4a9965bc501acd067eab7005158626",
"main.dart.js_55.part.js": "a5b45ca960eddbb59412329bf069810f",
"main.dart.js_27.part.js": "b0e4799cd3851e4ca7b2afd9f02d1582",
"main.dart.js_102.part.js": "bf7c3ccad24df44b0055aa122435e3ca",
"main.dart.js_19.part.js": "d5e2345cea2503c887af2fb5b86f7e53",
"flutter.js": "83d881c1dbb6d6bcd6b42e274605b69c",
"main.dart.js_195.part.js": "ac9356219d838ed1964b135eaf7aae3f",
"main.dart.js_185.part.js": "28f4e266f2eb12ef98d2065b8a259ebe",
"main.dart.js_171.part.js": "3e09e29cd19fe6f0aa7014fafabf9a0d",
"main.dart.js_161.part.js": "0fdbc558b7595bb790b093f2624e749c",
"main.dart.js_54.part.js": "e138c029b56fee0c95091df6d2366f7e",
"main.dart.js_44.part.js": "530b207624141a430accf0064f790d77",
"main.dart.js_113.part.js": "980c7c69e4bd568b4f34155c7e653e85",
"main.dart.js_135.part.js": "8402fb15b522f5fe6f4938fa68c27b83",
"main.dart.js_125.part.js": "ff6d28d0e18b91797419b2a890318d9d",
"main.dart.js_10.part.js": "2b4a17870edc425b26c28dadd0820774",
"main.dart.js_72.part.js": "4d2906c9cba46390be5de17beb7b9263",
"main.dart.js_5.part.js": "fb82caf79b64b7e5f1f054d3d6dcf5fa",
"main.dart.js_62.part.js": "f1bd9aed950521acde65c2dd4a5c7dec",
"main.dart.js_157.part.js": "52481dad9e765b86b39bd2662b9e6699",
"main.dart.js_96.part.js": "08f8137f447597adae511af4970abb9f",
"main.dart.js_86.part.js": "cfb010719b7e1ccfcef3bcc466d8ddda",
"main.dart.js_178.part.js": "9dc9116e0336e8bda02b9bdaa9a22652",
"main.dart.js_83.part.js": "686aaec41cf61eb2e6ea784afd4de88f",
"main.dart.js_93.part.js": "daaaa3cec826361cfef18f1770e8571a",
"main.dart.js_58.part.js": "a6bb6af9b154b7a8366db65175945160",
"main.dart.js_48.part.js": "da6035e2846cd98f011a723ba0b9b2a5",
"main.dart.js_120.part.js": "ce89602500f3d42b6169f38cf2eb183d",
"main.dart.js_199.part.js": "729ceedcb55c59a88f16a96788ad6b64",
"main.dart.js_189.part.js": "33521df20327291d5e80189b527a1b72",
"main.dart.js_152.part.js": "39737c21caff3b7b8a0c9b26d349260c",
"main.dart.js_67.part.js": "5a6ed225e4f431a14df332ea9f034e70",
"main.dart.js_77.part.js": "7aff360546f43f1067f035d2cee4bc43",
"main.dart.js_41.part.js": "ddbf25245aaa2d33abef7b5975ca383f",
"main.dart.js_51.part.js": "cee9063951c3e6d9c38c58ec90d032de",
"main.dart.js_116.part.js": "764a2e8faf94f9c28de9d36c49c315dd",
"main.dart.js_23.part.js": "263ec6dd86840bfb83cc9932063f8f32",
"main.dart.js_33.part.js": "dc3b5d3523ba60392c26cd834adf537d",
"main.dart.js_129.part.js": "97d980c9410849cdadc7bf0da8a4a78b",
"main.dart.js_180.part.js": "9ad067ed3b2b3bdd43614d4f5ed95a39",
"main.dart.js_50.part.js": "af2bd8322fb36dfd014cfa90a98069ab",
"main.dart.js_40.part.js": "dbfb16a377bd45a4a6e8110bd0d81f15",
"main.dart.js_175.part.js": "d4f7562aa72cfc9bd7e3fc9353fca4d0",
"main.dart.js_165.part.js": "686d99b511f5da98d10c0008ab90da25",
"main.dart.js_117.part.js": "0a61dc19a36653be7ba1a5576fbc288a",
"main.dart.js_107.part.js": "4c87f4b08df175ed8fba488394f72bd8",
"main.dart.js_32.part.js": "dbc31437f696f58f16aeaaf57b85d056",
"icons/app_icon.png": "9f8a277dbc30308e6ea0de6c057c0a55",
"main.dart.js_191.part.js": "ef7172e18927add2ccb6099bd11d3858",
"main.dart.js_181.part.js": "2c59706a5e146629ac08a62d70ddbb70",
"main.dart.js_138.part.js": "ad4b0db4e03288ffd52713504ade99d2",
"manifest.json": "ce255632f156b3abd6f7816522c4ff1a",
"main.dart.js_92.part.js": "555d8fabb331d0a5305c74d0a1c4242d",
"main.dart.js_82.part.js": "6eb3531c1a4209f63a873b18e814244f",
"main.dart.js_49.part.js": "55dcf5adad0463058bbb4f9d967977ec",
"main.dart.js_59.part.js": "92d9f1a1e6c0324382d8c87e0c7cdf89",
"main.dart.js_188.part.js": "fcf3614e77521b6302a6fc3365bddaae",
"main.dart.js_198.part.js": "92f0117f3d1fadc7568b5544c18d3bbb",
"main.dart.js_14.part.js": "3a0b269cb49cc55f08bd4ff2c68fb77a",
"main.dart.js_131.part.js": "8b837b9de5469174ede222479283405f",
"main.dart.js_121.part.js": "5e293ec46926f6cc8335a8530238f8a7",
"main.dart.js_143.part.js": "d3488ccfd6eed71a50b177b77b2f567f",
"main.dart.js_76.part.js": "963a3d9d77d14d791433f90ea39bb466",
"main.dart.js_1.part.js": "4433c784fa8f10878cb37d434cf9fbf5",
"main.dart.js_20.part.js": "6a87403fec0e68e4ecde4f73a3c6d5a4",
"main.dart.js_30.part.js": "d4c81d3f8eb31c5236c7e21adfa73b08",
"main.dart.js_115.part.js": "711fea4755bd89c0fd00124ab0705d2e",
"main.dart.js_89.part.js": "c33c4eaa9c18f7f946872fc951335f54",
"main.dart.js_167.part.js": "2ee98ae581a60fb5039f66f32e19d80a",
"main.dart.js_201.part.js": "a39ce8ae68bdd986b8c50f33ef59e312",
"main.dart.js_193.part.js": "0ad5dc10ca847db43fc5f93b2927e137",
"main.dart.js_39.part.js": "2c0a310c22bc245db96e6ee191f4b142",
"main.dart.js_90.part.js": "6c2340750e30ef0897f02835d34010cd",
"main.dart.js_64.part.js": "5b9c8638c274abc885cde647465a4509",
"main.dart.js_3.part.js": "bf8d6c3e65cfadf789c4b980a25d79da",
"main.dart.js_74.part.js": "262776c2c469ade912eba87c717167a1",
"main.dart.js_141.part.js": "eb460e21dfeb6d8997bc1cdd2202591e",
"main.dart.js_123.part.js": "05d61daee09718a15e1dfbc9d0a898f9",
"main.dart.js_16.part.js": "c4ac2b16ff6602f0e0748e4801eb4b7f",
"assets/AssetManifest.json": "3eb09be2ac2ecbd14a6ea6229a1da724",
"assets/NOTICES": "c637fed3e62a5a18269658cb26c2b44b",
"assets/FontManifest.json": "bed231d7b112623f57dc119fc603f653",
"assets/AssetManifest.bin.json": "24c267e3ce25db2a67d84a9ca69767b5",
"assets/packages/cupertino_icons/assets/CupertinoIcons.ttf": "33b7d9392238c04c131b6ce224e13711",
"assets/packages/flutter_inappwebview_web/assets/web/web_support.js": "509ae636cfdd93e49b5a6eaf0f06d79f",
"assets/packages/fluttertoast/assets/toastify.js": "56e2c9cedd97f10e7e5f1cebd85d53e3",
"assets/packages/fluttertoast/assets/toastify.css": "a85675050054f179444bc5ad70ffc635",
"assets/packages/flutter_inappwebview/assets/t_rex_runner/t-rex.css": "5a8d0222407e388155d7d1395a75d5b9",
"assets/packages/flutter_inappwebview/assets/t_rex_runner/t-rex.html": "16911fcc170c8af1c5457940bd0bf055",
"assets/packages/wakelock_plus/assets/no_sleep.js": "7748a45cd593f33280669b29c2c8919a",
"assets/shaders/ink_sparkle.frag": "ecc85a2e95f5e9f53123dcaf8cb9b6ce",
"assets/AssetManifest.bin": "3e0799ee356749c201caf5a5829e90fd",
"assets/fonts/boldface/AlimamaShuHeiTi-Bold.ttf": "9c8d7f88c39c3d90cd41ea9db70a9fa7",
"assets/fonts/circle/AlimamaFangYuanTiVF-Thin.ttf": "3be809e80815a8bc0feba3b241231c88",
"assets/fonts/MaterialIcons-Regular.otf": "06992bebcf9923179018862fa457b6bd",
"assets/assets/images/no_cover.jpeg": "7e48f3134478ff6a02bdaae12702e698",
"assets/assets/images/fq.png": "6e5136dee28ad0cb3a351ed047fac21c",
"assets/assets/images/splash.png": "4371e4db6d28c3d27973f5a840fa69ee",
"assets/assets/images/logo.png": "4371e4db6d28c3d27973f5a840fa69ee",
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
"main.dart.js_91.part.js": "25e70bdbe471005750c9f4471cb0cb6d",
"main.dart.js_81.part.js": "aef770d4a0646b341de6a63cc2fa3e57",
"main.dart.js_28.part.js": "94112e6cb2572d73b784af61d01a9e93",
"main.dart.js_75.part.js": "f4db2ca7aeaa11a89899f7da9063313f",
"main.dart.js_65.part.js": "33abf0958f96571565934a3adfe84b30",
"main.dart.js_150.part.js": "9189f18c6d45b2846d807b832950873d",
"main.dart.js_140.part.js": "bca535d5e37904b860b9dbced7971be7",
"main.dart.js_132.part.js": "129a032806cce912354ccac017c52e70",
"main.dart.js_17.part.js": "91c57e9ce1a8e941c46912f5103e74d8",
"main.dart.js_88.part.js": "de44c3d49f252bbf45dd1d19ba4fbf91",
"main.dart.js_21.part.js": "bbe97686cd0d14a2dc52a22ef43ce81f",
"main.dart.js_104.part.js": "9f8a1475fcfa9324777c90809f292266",
"main.dart.js_176.part.js": "f504a474bfc920b6741fc85ae341ccc8",
"main.dart.js_53.part.js": "11177b2118c67f59d1f17e5328625742",
"main.dart.js_200.part.js": "b66646cc4ffd6688e74922d9092c625e",
"main.dart.js_43.part.js": "89d45ab1fe86e0bdee487048f6767893",
"main.dart.js_192.part.js": "4fb0947754459cc9a321fb9570fdc048",
"canvaskit/skwasm.js": "ea559890a088fe28b4ddf70e17e60052",
"canvaskit/skwasm.js.symbols": "e72c79950c8a8483d826a7f0560573a1",
"canvaskit/canvaskit.js.symbols": "bdcd3835edf8586b6d6edfce8749fb77",
"canvaskit/skwasm.wasm": "39dd80367a4e71582d234948adc521c0",
"canvaskit/chromium/canvaskit.js.symbols": "b61b5f4673c9698029fa0a746a9ad581",
"canvaskit/chromium/canvaskit.js": "8191e843020c832c9cf8852a4b909d4c",
"canvaskit/chromium/canvaskit.wasm": "f504de372e31c8031018a9ec0a9ef5f0",
"canvaskit/canvaskit.js": "728b2d477d9b8c14593d4f9b82b484f3",
"canvaskit/canvaskit.wasm": "7a3f4ae7d65fc1de6a6e7ddd3224bc93",
"main.dart.js_159.part.js": "76ca791bc8120ee1d7ef4aa237cac695"};
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
