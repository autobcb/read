'use strict';
const MANIFEST = 'flutter-app-manifest';
const TEMP = 'flutter-temp-cache';
const CACHE_NAME = 'flutter-app-cache';

const RESOURCES = {"main.dart.js_187.part.js": "01241d91e5d9de8b7b0dbf09af87165a",
"main.dart.js_197.part.js": "974ac2043fb3ff8e3f33d4af0365fd9c",
"flutter_bootstrap.js": "38dd7d81b863bf9b16044eb5f7e7be48",
"main.dart.js_79.part.js": "df758a1a236fe27ac8ffc32e2342b164",
"main.dart.js_69.part.js": "c6481602be66db3bde945f8e4578382c",
"main.dart.js_101.part.js": "419ac127ed031824d9b63fcc6483134c",
"main.dart.js_111.part.js": "1e2699a5f677e1b47b4179b9ee17dfa4",
"main.dart.js_34.part.js": "f8bd8bd684e7c90215648941a4e09c6b",
"main.dart.js_46.part.js": "ef9dea3890aff206b73cf8e1956bd597",
"main.dart.js_56.part.js": "a31d20be13cd4f874c68bcf5531bf7db",
"main.dart.js_163.part.js": "fb063c372050028396591b82b2a986ef",
"main.dart.js_173.part.js": "878e6628d7b4a50350d770555f891f97",
"main.dart.js_145.part.js": "469ec54052cd092245edb6c3d035a736",
"main.dart.js_155.part.js": "c2098ba05a68285026a576340a4a570f",
"version.json": "23bf3d082d994908e07c50fd39282654",
"main.dart.js_7.part.js": "bec17316497091797d50ddadc28a361d",
"main.dart.js_60.part.js": "cd17953764148467ab38f038283649a8",
"main.dart.js_12.part.js": "84fbaa69e60a35b8ba42e5b618a5d9bb",
"main.dart.js_127.part.js": "73e6d973c494fab8673d51420898c9bc",
"main.dart.js_137.part.js": "b054c037a508bda28efe528d29882c45",
"main.dart.js_84.part.js": "f21e43e812c0d123a16200851e735821",
"main.dart.js_94.part.js": "ecca867f8fbf99ef1bb0efccce7bcc3c",
"main.dart.js_118.part.js": "8df756ae71dd48917685064f7888e785",
"main.dart.js_108.part.js": "2565de3286e1256b5ddf1361fa0d1263",
"main.dart.js_144.part.js": "57fe412485b39b70d1c039f8932c1cfd",
"main.dart.js_61.part.js": "ce9bbe5316c2a85a1b7166b9d62ac016",
"main.dart.js_6.part.js": "c1862b8fd5fd503582caaf6ed18b7922",
"index.html": "f0fb83eef421024a56d50022cd18118f",
"/": "f0fb83eef421024a56d50022cd18118f",
"main.dart.js_136.part.js": "2e7ff0af673cd3620b4c76659d0fd296",
"main.dart.js_126.part.js": "b6e570a3efff1d1f9ffecb94a2505654",
"main.dart.js_95.part.js": "1c33aad650d9766bef19bf40004f61d6",
"main.dart.js_85.part.js": "27c25ad0092fe07e5c873faebb673e1a",
"main.dart.js_196.part.js": "96cf35b27602397a92cf522511ca4815",
"main.dart.js_186.part.js": "6d52e8192325fedbf4ab42544fbb1862",
"main.dart.js_68.part.js": "9e1828b0ca7c4eb200d373309d82f6ab",
"main.dart.js_78.part.js": "d394f0386fdf9cdeff5a24f46bb6be94",
"main.dart.js_35.part.js": "c5923c353e58e27d39893da4eadf9cac",
"main.dart.js_25.part.js": "59ed8d9748f8f2b65251dadb2af57769",
"main.dart.js_47.part.js": "6150feb6448f8219b1737f834809f7d8",
"main.dart.js_162.part.js": "5f467a90d53d148ef1236d95652c6488",
"main.dart.js_124.part.js": "3d886ee0e8f49a882126b714eab3c458",
"main.dart.js_63.part.js": "003ccf0217af3511ee690805127344c7",
"main.dart.js_4.part.js": "39ff324e3b0062eafcd1c7d43a182e2f",
"main.dart.js_73.part.js": "51c089bcffe6561952760425f2db26fb",
"main.dart.js_146.part.js": "74dc7e9f455032f96bbda222f82f86c3",
"main.dart.js_156.part.js": "5dce75261a8b7f5cfddc7cb99a16f5df",
"main.dart.js": "996a9524cbc83d5d6c9fc6d4be224bac",
"main.dart.js_87.part.js": "9b52e58f7499996384bb2a617f1b5d6a",
"main.dart.js_97.part.js": "8203125866fb6dba2d5f8f8eea4900b6",
"main.dart.js_179.part.js": "32caef6ad4a618d05cded051af3a8344",
"main.dart.js_169.part.js": "6f08dc6447b72634a4d191fa876b5045",
"main.dart.js_194.part.js": "f397844d3e2b5b33175c5a2b97be939d",
"main.dart.js_18.part.js": "8b5db0698c185640668998092b447e7c",
"main.dart.js_160.part.js": "b10752e211573bff60000c6857cab02d",
"main.dart.js_170.part.js": "10cb3ace90256b12d3c9cf6ddb896cbb",
"main.dart.js_45.part.js": "616ec0a2e7f7926e0d5e68ab4c791845",
"main.dart.js_55.part.js": "e906815154a86b2f484575bb0906f14a",
"main.dart.js_19.part.js": "6463f42002e02120bbfc4b05ada128ba",
"flutter.js": "f31737fb005cd3a3c6bd9355efd33061",
"main.dart.js_195.part.js": "85a02132c11e582839c21f37fdf9556f",
"main.dart.js_171.part.js": "dbf2f6c06c871b54d52ed09eea717602",
"main.dart.js_54.part.js": "d8449fad163222f9c8b651b816c49a0a",
"main.dart.js_44.part.js": "d41fe22144fe31645dad943b1f79f04f",
"main.dart.js_36.part.js": "87a65127e3c42577e1bd2d5ffe339c36",
"main.dart.js_26.part.js": "6433482fcef5e261bf1d362af082c3c4",
"main.dart.js_103.part.js": "a71593bfe235fa9bd3fecf1546740ffe",
"main.dart.js_135.part.js": "29ebbc7c80dfe074daededf4b71622b9",
"main.dart.js_125.part.js": "27e5d348abdee7b26fd0d1156b5dd7e0",
"main.dart.js_72.part.js": "52d5c63b67b4eb4b608719390d633f4c",
"main.dart.js_5.part.js": "c8909ccfcb12320b0d53711d6fd89ad3",
"main.dart.js_157.part.js": "4f2d4e1438df3432fce440a1c5852db2",
"main.dart.js_147.part.js": "8cdef26cb527a94ef3e234d55d6b3205",
"main.dart.js_96.part.js": "48be1e4dd12e8b116f255ca907091d02",
"main.dart.js_86.part.js": "490116bfa0c4a4cea2cf08201564a89e",
"main.dart.js_83.part.js": "317310f08b4fd4c4bc33d27d494f706f",
"main.dart.js_93.part.js": "f3cdd69b84e1439e2acf0ad922754f6f",
"main.dart.js_58.part.js": "48f88db34537e4280016994c8ef17812",
"main.dart.js_48.part.js": "b958d81700ad739903dd94276b822f92",
"main.dart.js_15.part.js": "7c4bae86f41fa5257ec3569f9b30c643",
"main.dart.js_199.part.js": "5a4218c7190b81a88c327e22754d31a2",
"main.dart.js_189.part.js": "13665f93ff7f620feefacec92180c596",
"main.dart.js_142.part.js": "0d4c5c58e4c9e1df127c725bf0671e09",
"main.dart.js_67.part.js": "b8d545fe5addaea362585ba1df47cd4d",
"main.dart.js_77.part.js": "cc3436dfa70ae9f1b6ebb5de82bcde5c",
"main.dart.js_202.part.js": "e2ca1b793466ae5c4699df1beec0582b",
"main.dart.js_41.part.js": "5f7a9f64246d1d3ae3c2a9fda41bbdd5",
"main.dart.js_51.part.js": "933c57aedcadf734c7c2afcf8a9c8468",
"main.dart.js_174.part.js": "83cbb1949061a6b3b1490cf144858079",
"main.dart.js_116.part.js": "f781f8d452c33f0d4da9c4c60e90b2bc",
"main.dart.js_23.part.js": "b16fcbff08b32ef294d1bb83af910d23",
"main.dart.js_33.part.js": "f825b4a3e2a09c52aa82c9dd2a32d27a",
"main.dart.js_139.part.js": "7555f712ccc3c0ef08a638233035e78e",
"main.dart.js_129.part.js": "9b78b822b518139fa1b831b46ba94b19",
"main.dart.js_190.part.js": "0e3777c5286faebf2a0e9d5a0aa2e934",
"main.dart.js_50.part.js": "bf6927e09a6bbf509412bd12607f61ac",
"main.dart.js_40.part.js": "484ac170a6865762703d8d099d9c3119",
"main.dart.js_165.part.js": "d687016ca216b883154d8b38aaa8f500",
"main.dart.js_117.part.js": "4fb186f684cda916484e9e14af0dd7fb",
"main.dart.js_22.part.js": "3949fa145b68e32fc7e1a1e022aae86d",
"icons/app_icon.png": "9f8a277dbc30308e6ea0de6c057c0a55",
"main.dart.js_138.part.js": "aabf4b2accdfa348d8d15d914eaa1889",
"manifest.json": "ce255632f156b3abd6f7816522c4ff1a",
"main.dart.js_92.part.js": "3c45605b8b507fa164ab6f9f1697c289",
"main.dart.js_82.part.js": "9b5ed783deb12b976aa7ca2df192c128",
"main.dart.js_49.part.js": "49a0dc7b2de67e7b1fb1feabb02ecfb1",
"main.dart.js_59.part.js": "11dc467749e845c96ef020a990a8e5af",
"main.dart.js_131.part.js": "5dd59d5236008ab7691cf12208e6e65a",
"main.dart.js_121.part.js": "2ce49c21527749dbd401edb57233d66e",
"main.dart.js_153.part.js": "ccab66e2560fbc060c2da96192e89585",
"main.dart.js_76.part.js": "63ddace5b4397ce31735d4df8ff50bd1",
"main.dart.js_1.part.js": "51ea4533baefc3034b335384829e87a7",
"main.dart.js_20.part.js": "c9fcfa84fe45ac3e8d83bbe63fdaa15f",
"main.dart.js_105.part.js": "620aff20e7174e4749a2ebe8fd1763e2",
"main.dart.js_89.part.js": "bd162a5ed9e97951f2eaa989bfc6124f",
"main.dart.js_167.part.js": "762e8a032024e4b1a0bc8ac16781d083",
"main.dart.js_177.part.js": "3aab1658f72ebb1d061e64b338c16d85",
"main.dart.js_42.part.js": "ff5ef2089abf589fc71aa470b16b0a47",
"main.dart.js_201.part.js": "e9f06825e353ce5623ec10bb3808a167",
"main.dart.js_52.part.js": "2c3ac37889799c93244b325d9aec080b",
"main.dart.js_183.part.js": "a8ff8c24cb4a65669a9fa2f95bfe1e91",
"main.dart.js_193.part.js": "76eff5ba2fe32656db932cad18c9756b",
"main.dart.js_158.part.js": "6ea8ffc76cb01dc70504bd7d103017bf",
"main.dart.js_39.part.js": "ca53ebe06e6fd8b910b797e33cd9f669",
"main.dart.js_29.part.js": "2bdbcacc1ab35ac04030516a5286e5d2",
"main.dart.js_90.part.js": "2bcf5e3cb8624cff42eb16ccd1b39403",
"main.dart.js_64.part.js": "c55f1ec58768183800f39c7faa315910",
"main.dart.js_3.part.js": "c87a04b36307c2cdb7c2b5a5a6b474d2",
"main.dart.js_141.part.js": "cc696c8f0511ebd6c7ee09007c89ab37",
"main.dart.js_151.part.js": "68bc6630ab65c155f592e4599aa9c463",
"main.dart.js_133.part.js": "d0c00701f45287700ef71ced81a3deb4",
"assets/AssetManifest.json": "3eb09be2ac2ecbd14a6ea6229a1da724",
"assets/NOTICES": "db6c43844e633b953bc27997d60ac800",
"assets/FontManifest.json": "bed231d7b112623f57dc119fc603f653",
"assets/AssetManifest.bin.json": "24c267e3ce25db2a67d84a9ca69767b5",
"assets/packages/cupertino_icons/assets/CupertinoIcons.ttf": "e986ebe42ef785b27164c36a9abc7818",
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
"assets/fonts/MaterialIcons-Regular.otf": "baf5d82ef90e863cee548c2a88f0a625",
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
"main.dart.js_91.part.js": "2c6d7094c4b2510efc86a26e4a8ab1c9",
"main.dart.js_28.part.js": "2f20bea96bae549e8073978e1de89b6a",
"main.dart.js_75.part.js": "0288eae10d1a4731027730e126739b89",
"main.dart.js_65.part.js": "208ed4e60fe34cebfb39b667fbad84e1",
"main.dart.js_122.part.js": "cdc325d9e7b64fb3ef90f54f80aa831e",
"main.dart.js_88.part.js": "b55459a811cd3efa853cb5021ce2e446",
"main.dart.js_98.part.js": "f727020b7af75a504d557e092096bc10",
"main.dart.js_31.part.js": "631fe2df4a55c4d024c74c5d36807cdc",
"main.dart.js_21.part.js": "00ef96727ab7c1d7addb91f99d8c7d5b",
"main.dart.js_114.part.js": "3afcdaf6f2eda32366843e9b6f995241",
"main.dart.js_176.part.js": "0d888b456690e0fe8dc46becdf4f6b95",
"main.dart.js_200.part.js": "7d1603de21da91b15f9dda675063e87a",
"main.dart.js_192.part.js": "afc485e1aa3ee4548f936ce086f4311e",
"main.dart.js_182.part.js": "1dc16c5e56b056e4d57c743ae7692989",
"canvaskit/skwasm.js": "9fa2ffe90a40d062dd2343c7b84caf01",
"canvaskit/skwasm.js.symbols": "262f4827a1317abb59d71d6c587a93e2",
"canvaskit/canvaskit.js.symbols": "48c83a2ce573d9692e8d970e288d75f7",
"canvaskit/skwasm.wasm": "9f0c0c02b82a910d12ce0543ec130e60",
"canvaskit/chromium/canvaskit.js.symbols": "a012ed99ccba193cf96bb2643003f6fc",
"canvaskit/chromium/canvaskit.js": "87325e67bf77a9b483250e1fb1b54677",
"canvaskit/chromium/canvaskit.wasm": "b1ac05b29c127d86df4bcfbf50dd902a",
"canvaskit/canvaskit.js": "5fda3f1af7d6433d53b24083e2219fa0",
"canvaskit/canvaskit.wasm": "1f237a213d7370cf95f443d896176460",
"canvaskit/skwasm.worker.js": "bfb704a6c714a75da9ef320991e88b03"};
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
