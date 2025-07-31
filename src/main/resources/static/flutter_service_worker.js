'use strict';
const MANIFEST = 'flutter-app-manifest';
const TEMP = 'flutter-temp-cache';
const CACHE_NAME = 'flutter-app-cache';

const RESOURCES = {"flutter_bootstrap.js": "d8c277efed1522b8a0678269b67377fd",
"main.dart.js_69.part.js": "85bda7d38f49132eb5749d7a156b59d8",
"main.dart.js_101.part.js": "a18833a6f3c74e42aedb997ac1574d52",
"main.dart.js_111.part.js": "f4e7f7a1c92d6e050d552f576731d924",
"main.dart.js_24.part.js": "a5cea5c440149e2f4932830c9bafdfb2",
"main.dart.js_56.part.js": "36378e2f3b8e55031051d037134df28f",
"main.dart.js_163.part.js": "dfc384268834d20c349f7805c6c15cb2",
"main.dart.js_145.part.js": "8a925ae0e830e7a66d956d700df8452e",
"version.json": "1d610b042f2ebd1f3c2dfbd7a142e7e9",
"main.dart.js_7.part.js": "4a6a7f8fda00fc54e1e3cacf4241f823",
"main.dart.js_60.part.js": "dc8a925c6bffea2f6d07547fdbde64ed",
"main.dart.js_70.part.js": "cd42344f96b7a3e455ce18f86e4aed4f",
"main.dart.js_84.part.js": "717b077e4b05c3a9395bcaea7b028ed5",
"main.dart.js_94.part.js": "0f817d15db60e9678ba60bd27afa65e9",
"main.dart.js_154.part.js": "ab578ae205fe37d9e2705467de013d75",
"main.dart.js_144.part.js": "c3c16f9498a7775d13dfb712645ae73a",
"main.dart.js_71.part.js": "422cfe3488dd7cbf8f8c22cefafef24e",
"main.dart.js_61.part.js": "26c9bcfbea127e982fdf3e613acd9d93",
"main.dart.js_6.part.js": "9703a35ec1289f9f42129bd10ebd74ed",
"index.html": "f0fb83eef421024a56d50022cd18118f",
"/": "f0fb83eef421024a56d50022cd18118f",
"main.dart.js_126.part.js": "3fa59568a8a9002fb842a9160df51edd",
"main.dart.js_119.part.js": "04be8e44302a1988177d5e75a2a75f3e",
"main.dart.js_95.part.js": "0a963b3b44e5d8304ba1a354e2089eef",
"main.dart.js_85.part.js": "a1955b388d5517ef7dc95d9ec52b01cc",
"main.dart.js_186.part.js": "307183dfd9abf104cde5ccdec7098e3b",
"main.dart.js_100.part.js": "dd158ceb1de19b14598f54dba0661bb5",
"main.dart.js_25.part.js": "21eaa6cad26279bcb44afc0b609d15d1",
"main.dart.js_57.part.js": "2f5b7a093e0c5fb5ef0ccafbdbcaa42a",
"main.dart.js_172.part.js": "7b3dbc3956891c5fe846c43213823f3b",
"main.dart.js_162.part.js": "7620244cf59e7bbcdf7cf639f64142ee",
"main.dart.js_124.part.js": "1b9c0ab008dd8890bb0a36e2e5b69ef0",
"main.dart.js_11.part.js": "70024b0ef981aa349ceee13034697e67",
"main.dart.js_63.part.js": "4744b2b933e73aeb73debd01ce5ef2e4",
"main.dart.js_4.part.js": "28ed14a5ab7f865ad1291c6c372d4087",
"main.dart.js_73.part.js": "9e0114e282eff1eee3fc3fcc9ccf7bdc",
"main.dart.js_156.part.js": "6a6973c517eeae2e589f7ae1b5e68caf",
"main.dart.js": "296b7a53525970d0519fe84f918a9aef",
"main.dart.js_87.part.js": "b488a1429fc501710df77f8a624700e3",
"main.dart.js_97.part.js": "c571d6a197568176d3f6420e615fdcba",
"main.dart.js_179.part.js": "df799f89582b640210f30491b9340fd6",
"main.dart.js_184.part.js": "0b7cfbccfca4c79a031e9d7e49a5f63b",
"main.dart.js_18.part.js": "4e408642367c73a017c1da89b0995ba0",
"main.dart.js_160.part.js": "cbd7163b50a1ccaded765c7392f50742",
"main.dart.js_45.part.js": "9b110aed6bda1ca98c41f0adc37d6634",
"main.dart.js_55.part.js": "f4e9828c3319e8f2033a0a87323471ed",
"main.dart.js_27.part.js": "8624e4de8eaeb1ef20a7569ea00292f2",
"main.dart.js_37.part.js": "83eae5bc293e6a04f543662a484c8f54",
"main.dart.js_102.part.js": "5a8b562ff8d2552d2f0c625992034510",
"main.dart.js_19.part.js": "7f94d7aee58e6bee96f999902fdc1018",
"flutter.js": "83d881c1dbb6d6bcd6b42e274605b69c",
"main.dart.js_185.part.js": "212dcba4565dfa2441ce675081639834",
"main.dart.js_171.part.js": "87f68e0aa43eaf1b3c8608e8fc5a9b4f",
"main.dart.js_44.part.js": "bee1cbe7d045be0e5bca6593c67d3b65",
"main.dart.js_36.part.js": "9843b6f9273d525b8d66b760ebb06878",
"main.dart.js_113.part.js": "455b5de394f33c66ece784de388ad050",
"main.dart.js_103.part.js": "403330af3d62b10c53c68ed12e003077",
"main.dart.js_135.part.js": "a3dbd8641c9839e83340d8f741d89551",
"main.dart.js_125.part.js": "891b5970bd049f55728195bcb119869d",
"main.dart.js_72.part.js": "a6093558f3ed0ebf7838f4d7204d16be",
"main.dart.js_5.part.js": "d8369e63cbda3c1412cab5e69315330f",
"main.dart.js_62.part.js": "cfb00cac5736ecc5d01ab81449dd17a8",
"main.dart.js_157.part.js": "7f606ab21184347179e47f3ac60a9e1c",
"main.dart.js_147.part.js": "27113123e89e3bbd234f9146862ea356",
"main.dart.js_86.part.js": "9345271abce645548495eeaf2b3054a6",
"main.dart.js_168.part.js": "fdcaaed1b09a0c83f5e0abbb0e4627da",
"main.dart.js_178.part.js": "8ca7ce003bd9277ed6d1d3e8b8099602",
"main.dart.js_83.part.js": "11f7cd887d5085c3a2c068942965299c",
"main.dart.js_58.part.js": "a9a9961195821863e08869734f4366de",
"main.dart.js_120.part.js": "d5bfb5c624ed26e58344f1186f51660f",
"main.dart.js_130.part.js": "f5943c0a6d74052ce86cd5c2ad145f0f",
"main.dart.js_152.part.js": "e9ad638d648980ebad915e49a8ffc2f7",
"main.dart.js_67.part.js": "9c45fa94a16a19a7cf9a4264cdbebb6f",
"main.dart.js_41.part.js": "9a373600dfcf9b78aac82859f003fd0d",
"main.dart.js_51.part.js": "750ad9f16ee4c6bf0d2ce65b864f80a8",
"main.dart.js_174.part.js": "6ea10c7aa9531301a62e8de3bf349a99",
"main.dart.js_33.part.js": "919ca8a72d7a5a531eaed4f526537def",
"main.dart.js_9.part.js": "64630224d3fe491ee7ecbc42fd4854c2",
"main.dart.js_129.part.js": "b49c4a4f2a1f99727173308464a1dd1b",
"main.dart.js_180.part.js": "4e89316916780d6c40d9f7b74dba2814",
"main.dart.js_50.part.js": "20802d2e8b9673d18328edeb58f45889",
"main.dart.js_165.part.js": "a0d0dc48d7e255d0e8a3b745906d5ada",
"main.dart.js_117.part.js": "e06034e226db1c87de57bc7a56f379c1",
"main.dart.js_32.part.js": "bae62d9647513a82493e736e723cc433",
"main.dart.js_22.part.js": "c57ea342f843b9eb50951bb534fe17a7",
"icons/app_icon.png": "9f8a277dbc30308e6ea0de6c057c0a55",
"main.dart.js_181.part.js": "4172f7ff2bdf568805179765f36c2bd4",
"main.dart.js_128.part.js": "ed27ac9d07527a0c87f73aaadebcc55c",
"manifest.json": "ce255632f156b3abd6f7816522c4ff1a",
"main.dart.js_92.part.js": "7587a388f83a11d0825d1af54f0bfd93",
"main.dart.js_82.part.js": "482a462d6e77db81e5ccfa87e606063c",
"main.dart.js_14.part.js": "dec7d36623e4c959fdc96348ee2851df",
"main.dart.js_131.part.js": "95ea1c17a97375434884a10c55888f42",
"main.dart.js_143.part.js": "26b35a7ba0ba10b21bc1187c7eaa135b",
"main.dart.js_76.part.js": "d9c03c38014c112d212dc083c0fe8703",
"main.dart.js_66.part.js": "2a2ac37c7bf63af59a19a93ded352ec7",
"main.dart.js_1.part.js": "028cc516878f284d2be2fa7ce1e52e84",
"main.dart.js_20.part.js": "a3269471e60a726f86cd22ca34c61860",
"main.dart.js_105.part.js": "836af1f5dabc6c971bb593c0f959a3eb",
"main.dart.js_99.part.js": "b0fdf212547ddc107573920e3eac0f83",
"main.dart.js_167.part.js": "2ae548436e6ea515b26295c0872b69f1",
"main.dart.js_177.part.js": "64394a1ae4e3c9f603579c5743a7faec",
"main.dart.js_52.part.js": "da98c333f18af88def5059cc049f78fb",
"main.dart.js_183.part.js": "811c8ce0ff426adfe57fb433a3b52f6c",
"main.dart.js_158.part.js": "fe18eab47a13209ce3b33688e683981e",
"main.dart.js_148.part.js": "bc57ba27c9a050f158800155aec0481e",
"main.dart.js_39.part.js": "ef86752c099f35b678342d2e8a78f46d",
"main.dart.js_29.part.js": "8722f8cc2b179872f1faa4a4f513f660",
"main.dart.js_80.part.js": "325d63c00befdc49134cc7834e7e80e6",
"main.dart.js_90.part.js": "c556545439e235131854d7bc48d2f845",
"main.dart.js_64.part.js": "131628f9d341c4dc43254e7e9439e9c9",
"main.dart.js_3.part.js": "e8d5c1c9a245a3c765e8dad2bfa1ff94",
"main.dart.js_74.part.js": "08d40a4c1e4753d05b9450e4f68dd832",
"main.dart.js_123.part.js": "1b25e5c66773b72db8b715401d77a76c",
"assets/AssetManifest.json": "3eb09be2ac2ecbd14a6ea6229a1da724",
"assets/NOTICES": "1b3bfdc2f99e292fd243ab32217e3fcd",
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
"assets/fonts/MaterialIcons-Regular.otf": "50152570b587fa0d9779ab576a0a2e72",
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
"main.dart.js_91.part.js": "baf01a4fa40acd6fb03f180e54fb3d6c",
"main.dart.js_81.part.js": "afd767b04ffe6d4d8ff69ae8e391a28b",
"main.dart.js_28.part.js": "c31443a89f30056f36fa81dcf62cc1f3",
"main.dart.js_38.part.js": "494e99bc102a87afa5337ad5495df1f7",
"main.dart.js_75.part.js": "a0aff0a1185a2ac12677306f02051a18",
"main.dart.js_65.part.js": "323128fbb9d60bf574eb8452374b5afb",
"main.dart.js_140.part.js": "93bc22929f41ac8d6c6d75ab87003659",
"main.dart.js_132.part.js": "e33ec5bf784be5fdad67d64821ea61e8",
"main.dart.js_122.part.js": "9db3341a6986f3f1ebbf47f87f8fb8d4",
"main.dart.js_17.part.js": "f47067eff859fc0afc68f9693254a68d",
"main.dart.js_88.part.js": "b557b7ef26e6f493674069ab5b51ac26",
"main.dart.js_98.part.js": "8975af6075b66152ea339091b232a359",
"main.dart.js_31.part.js": "e623718e3c4437802e7c9082831710fe",
"main.dart.js_21.part.js": "2466d4834a4fd901488e8a8152b38fb6",
"main.dart.js_114.part.js": "586356c6462cd5ac70ae0b10c1fffc1d",
"main.dart.js_104.part.js": "d857770feeb22944d9a4a69604eebd66",
"main.dart.js_176.part.js": "6f71860cb9ee00683bf1c84962cafa99",
"main.dart.js_53.part.js": "fbcf91df8e6bb31a1d9ae0753e3a2334",
"main.dart.js_43.part.js": "bd7d1c377e7c179010f0438caf9075d6",
"canvaskit/skwasm.js": "ea559890a088fe28b4ddf70e17e60052",
"canvaskit/skwasm.js.symbols": "e72c79950c8a8483d826a7f0560573a1",
"canvaskit/canvaskit.js.symbols": "bdcd3835edf8586b6d6edfce8749fb77",
"canvaskit/skwasm.wasm": "39dd80367a4e71582d234948adc521c0",
"canvaskit/chromium/canvaskit.js.symbols": "b61b5f4673c9698029fa0a746a9ad581",
"canvaskit/chromium/canvaskit.js": "8191e843020c832c9cf8852a4b909d4c",
"canvaskit/chromium/canvaskit.wasm": "f504de372e31c8031018a9ec0a9ef5f0",
"canvaskit/canvaskit.js": "728b2d477d9b8c14593d4f9b82b484f3",
"canvaskit/canvaskit.wasm": "7a3f4ae7d65fc1de6a6e7ddd3224bc93"};
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
