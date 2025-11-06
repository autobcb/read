'use strict';
const MANIFEST = 'flutter-app-manifest';
const TEMP = 'flutter-temp-cache';
const CACHE_NAME = 'flutter-app-cache';

const RESOURCES = {"flutter_bootstrap.js": "c81de98d85589f7f9f4504b2d75da6ab",
"main.dart.js_24.part.js": "e9a0b2075d1a3fe38e90badbc8ed2511",
"main.dart.js_34.part.js": "8776d757097b72ee0da96b4f18541ad2",
"main.dart.js_46.part.js": "1307213aab5bdf360a470e0d822380e7",
"version.json": "29226a9741bf4c73b152885e7171c7c4",
"main.dart.js_7.part.js": "eef15911ac8ca7cae872cc471fb414a7",
"flutter-canvaskit/1e9a811bf8e70466596bcf0ea3a8b5adb5f17f7f/chromium/canvaskit.js": "5e27aae346eee469027c80af0751d53d",
"flutter-canvaskit/1e9a811bf8e70466596bcf0ea3a8b5adb5f17f7f/chromium/canvaskit.wasm": "24c77e750a7fa6d474198905249ff506",
"flutter-canvaskit/1e9a811bf8e70466596bcf0ea3a8b5adb5f17f7f/canvaskit.js": "140ccb7d34d0a55065fbd422b843add6",
"flutter-canvaskit/1e9a811bf8e70466596bcf0ea3a8b5adb5f17f7f/canvaskit.wasm": "07b9f5853202304d3b0749d9306573cc",
"favicon.ico": "568a1ed53b5fb9ba8cb13938b4c1b23a",
"index.html": "f0fb83eef421024a56d50022cd18118f",
"/": "f0fb83eef421024a56d50022cd18118f",
"main.dart.js_35.part.js": "cccd70870cae333144a73315a581a4a4",
"main.dart.js_25.part.js": "64d9554af56660369080c29c7b6904ef",
"main.dart.js_4.part.js": "db2c0af5f1d66605120f904bbff73b39",
"main.dart.js": "90475a0bed48397d2ba0bc7d0b460e1a",
"main.dart.js_45.part.js": "181a64616c63675f55af545cfdad1802",
"main.dart.js_27.part.js": "67e3598136784c584a3b79b4346fb6b6",
"main.dart.js_37.part.js": "9f27590ff69d41383150944df8efd63e",
"main.dart.js_19.part.js": "4faa221e26cb64ba30c62bad8cad92c2",
"flutter.js": "888483df48293866f9f41d3d9274a779",
"main.dart.js_44.part.js": "e6c76e7c616d7d26078bb51099080ef7",
"main.dart.js_36.part.js": "8e1d537733bb2f1d595e63aeb1e6f79d",
"main.dart.js_26.part.js": "99422bb79e3fb3902c0a5cfcfe4b51b2",
"main.dart.js_5.part.js": "9224906d4ac41cc4a7dcfef2ebb86da3",
"favicon.png": "0bb582c2d7ecab35d61c52f918b3a57a",
"main.dart.js_23.part.js": "f363604acedfad43c78ecd69f92961ff",
"main.dart.js_33.part.js": "6e847e2c0c52c4941669f45167bc0734",
"main.dart.js_9.part.js": "88963f4ea651fe901252c68e0209dd76",
"main.dart.js_40.part.js": "8fa1323ee3782bc6482789651eddb0b6",
"main.dart.js_32.part.js": "35cb5ab433eba23fc1733d32425f0090",
"main.dart.js_22.part.js": "ed0d4fef6c49c73c7233bc4075492288",
"main.dart.js_8.part.js": "59dd10090b39ccf872bf53d08c496d66",
"icons/app_icon.png": "a5795b892c8ca52b83f62a72cb9c56af",
"manifest.json": "ce255632f156b3abd6f7816522c4ff1a",
"main.dart.js_1.part.js": "d6c11731574d1f2709b24cf45743eccf",
"main.dart.js_20.part.js": "a087173c48b6741e116479ce927f2203",
"main.dart.js_30.part.js": "da44cdfab9d8a1c3ea996346928eec75",
"main.dart.js_39.part.js": "fd7e05ffb3cbafc957a62074d1f5d83f",
"main.dart.js_29.part.js": "fc5c8e3fbcc540d30555e17166569bc2",
"main.dart.js_16.part.js": "34e58888f7a23652409bbb8e0b2a73db",
"assets/AssetManifest.json": "84f1b24e3845e5ab78ec17296724779c",
"assets/NOTICES": "943ad1247daaddfaf0b4d3a584416747",
"assets/FontManifest.json": "bed231d7b112623f57dc119fc603f653",
"assets/AssetManifest.bin.json": "b0d1213b25f4d7a6b08305034ffd4903",
"assets/packages/cupertino_icons/assets/CupertinoIcons.ttf": "33b7d9392238c04c131b6ce224e13711",
"assets/packages/flutter_inappwebview_web/assets/web/web_support.js": "509ae636cfdd93e49b5a6eaf0f06d79f",
"assets/packages/fluttertoast/assets/toastify.js": "56e2c9cedd97f10e7e5f1cebd85d53e3",
"assets/packages/fluttertoast/assets/toastify.css": "a85675050054f179444bc5ad70ffc635",
"assets/packages/flutter_inappwebview/assets/t_rex_runner/t-rex.css": "5a8d0222407e388155d7d1395a75d5b9",
"assets/packages/flutter_inappwebview/assets/t_rex_runner/t-rex.html": "16911fcc170c8af1c5457940bd0bf055",
"assets/packages/flutter_js/assets/js/fetch.js": "277e0c5ec36810cbe57371a4b7e26be0",
"assets/packages/wakelock_plus/assets/no_sleep.js": "7748a45cd593f33280669b29c2c8919a",
"assets/shaders/ink_sparkle.frag": "ecc85a2e95f5e9f53123dcaf8cb9b6ce",
"assets/AssetManifest.bin": "a63ab93b39fa689256fdaa78780e1489",
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
"main.dart.js_28.part.js": "c7374fb33a1b4f1bf2ea465457fe72cf",
"main.dart.js_38.part.js": "3dd60807a4f2c5c5a89791ab7df5394d",
"main.dart.js_2.part.js": "a5d2c5b826e836c85da4544eff00bd39",
"main.dart.js_17.part.js": "7e8f871330e56409cafd210f6d5838c0",
"main.dart.js_31.part.js": "5f72c24bdd293cb66bc6f81eea3abb18",
"main.dart.js_21.part.js": "d5269164f75cc28715745be1e4d3ff2c",
"main.dart.js_43.part.js": "a894dd0e96735a1718476500d079d6c4",
"canvaskit/skwasm.js": "1ef3ea3a0fec4569e5d531da25f34095",
"canvaskit/skwasm_heavy.js": "413f5b2b2d9345f37de148e2544f584f",
"canvaskit/skwasm.js.symbols": "0088242d10d7e7d6d2649d1fe1bda7c1",
"canvaskit/canvaskit.js.symbols": "58832fbed59e00d2190aa295c4d70360",
"canvaskit/skwasm_heavy.js.symbols": "3c01ec03b5de6d62c34e17014d1decd3",
"canvaskit/skwasm.wasm": "264db41426307cfc7fa44b95a7772109",
"canvaskit/chromium/canvaskit.js.symbols": "193deaca1a1424049326d4a91ad1d88d",
"canvaskit/chromium/canvaskit.js": "5e27aae346eee469027c80af0751d53d",
"canvaskit/chromium/canvaskit.wasm": "24c77e750a7fa6d474198905249ff506",
"canvaskit/canvaskit.js": "140ccb7d34d0a55065fbd422b843add6",
"canvaskit/canvaskit.wasm": "07b9f5853202304d3b0749d9306573cc",
"canvaskit/skwasm_heavy.wasm": "8034ad26ba2485dab2fd49bdd786837b"};
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
