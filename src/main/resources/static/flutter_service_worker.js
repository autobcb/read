'use strict';
const MANIFEST = 'flutter-app-manifest';
const TEMP = 'flutter-temp-cache';
const CACHE_NAME = 'flutter-app-cache';

const RESOURCES = {"flutter_bootstrap.js": "192bb4037ef88d1095b87d83f5ab9560",
"main.dart.js_79.part.js": "8bdfde6f7ab642a6e6ca6d24de67a60a",
"main.dart.js_69.part.js": "312d2652905b40c5d8a64d6b31fcdaac",
"main.dart.js_101.part.js": "247849b6f0e878019d395665251cb2bd",
"main.dart.js_111.part.js": "204a46e0db6df0d94d7f49f1bf735efd",
"main.dart.js_34.part.js": "c61985e60baeaeaeca2b46d00e980305",
"main.dart.js_46.part.js": "4019cd7967fda13b15394bf9304e25e1",
"main.dart.js_163.part.js": "2689b7463ae087cd63af1749182c319b",
"version.json": "40f54b67347e5e196788af5d4967b765",
"main.dart.js_7.part.js": "29ddab93551b32a9f4bc3158224d023e",
"main.dart.js_60.part.js": "eb12ddeb6b2eb925858b982ee822b3ce",
"main.dart.js_70.part.js": "dd721e834e67d6a119974e72589fdbcb",
"main.dart.js_12.part.js": "36a17165d2fb53331cb9b96b2f561592",
"main.dart.js_137.part.js": "d5306d63be6b67d2e583bdea1bb67c8d",
"main.dart.js_84.part.js": "c93ef830bfdd406fb438d30be142b8f9",
"main.dart.js_94.part.js": "5b132cc1e36effb163153b04920b996c",
"main.dart.js_108.part.js": "c05e642b4f0b6bc6c1bdc9fab22b59c7",
"favicon.ico": "568a1ed53b5fb9ba8cb13938b4c1b23a",
"main.dart.js_154.part.js": "6dcfa91985149fc9e9bebb9d169ca4f3",
"main.dart.js_144.part.js": "4be42003192075b4b58c0a53733cf128",
"main.dart.js_71.part.js": "6d32a5603db717715560da01ebce0a27",
"main.dart.js_61.part.js": "6dbb891a0c48969bf3de8f9c633a8a30",
"main.dart.js_6.part.js": "ac1a8d27d1ef34bf3430692539fa8cb2",
"index.html": "f0fb83eef421024a56d50022cd18118f",
"/": "f0fb83eef421024a56d50022cd18118f",
"main.dart.js_136.part.js": "15eeb93d381ed1b8dd076a8d6547aa60",
"main.dart.js_126.part.js": "2e42f2f11029432ed9660050594a4a4d",
"main.dart.js_95.part.js": "634b2797a1d0b8a69f54bfbe29a68dd5",
"main.dart.js_196.part.js": "3cd24cec58158ce9533244e09d5c2b32",
"main.dart.js_78.part.js": "b30bfb1a65c86cb9c966667c805d7c37",
"main.dart.js_100.part.js": "193e345abe4557c0e1316f877929841f",
"main.dart.js_35.part.js": "4469c5328e260a7870f7792d4c76a188",
"main.dart.js_25.part.js": "cff4fdaf54fec2e78208ead9aaa74600",
"main.dart.js_47.part.js": "b3e4e47bc7cc8b81e4d383d5cf73a7e5",
"main.dart.js_172.part.js": "1247a03dd8187413274d946771846132",
"main.dart.js_134.part.js": "54b133fc86dc2696fea5712aa0f62eec",
"main.dart.js_63.part.js": "3a6e6dec2ecb60d1f5f9d056bef302f3",
"main.dart.js_4.part.js": "b1fddbecdd90fd6c69c59d7eb0a8b85f",
"main.dart.js_73.part.js": "fe177f7a197d38c60f847c77a9cecb04",
"main.dart.js": "14173ea622c5cf8c8b8e416105ffe61d",
"main.dart.js_97.part.js": "194bd9bd2a067a8c058cb700bb73e48a",
"main.dart.js_184.part.js": "8e6a7ace628ba4d1ca73b0bc05426b7b",
"main.dart.js_194.part.js": "3559df3f55affc3ece3615f9554ff666",
"main.dart.js_18.part.js": "a8f47ae799a08a0d9b41033c1bad9dc5",
"main.dart.js_170.part.js": "10ba75f5691fd5d95fef1aded207ae03",
"main.dart.js_45.part.js": "f17bc986f8596a460aa62f672c25b49a",
"main.dart.js_27.part.js": "56df1224cbc9500fc1ff39e4f0949ee5",
"main.dart.js_112.part.js": "e0ccb1d3717aeac3ab95b811cb37134e",
"flutter.js": "888483df48293866f9f41d3d9274a779",
"main.dart.js_195.part.js": "5da0207c80fbb925ee0b3e4853c5cdb9",
"main.dart.js_185.part.js": "e54a00ddb983f8c5de261104abcba9f0",
"main.dart.js_171.part.js": "6e0506195e7fcfc427f2b5b5865b46a7",
"main.dart.js_161.part.js": "f63ad5d967a4a0f81676754f244aef88",
"main.dart.js_54.part.js": "8d8e7d5a74fa7947e6088dda45d7f413",
"main.dart.js_36.part.js": "10a7b7ddfa8eafb0a4e2e74fac80ffcf",
"main.dart.js_26.part.js": "095b79d9552b5bc9494c7e5c420e2891",
"main.dart.js_113.part.js": "1b438ddcb260c93674bb54bd78c95083",
"main.dart.js_103.part.js": "4455cb48c0e58e1f54153f09600f696e",
"main.dart.js_135.part.js": "b460636fcca9e1bf5acb7c54687f00e8",
"main.dart.js_125.part.js": "03ef54ca1d34a01a9cb38cb5f43ceafb",
"main.dart.js_10.part.js": "f3d34d93df77b049ce5bf333d41d9b7c",
"main.dart.js_72.part.js": "1a20d2762b9f0684b4fe1ead89d793e9",
"main.dart.js_5.part.js": "ffbb9fd1385380e1072cc9f20c59c0e8",
"main.dart.js_62.part.js": "925d9bfbb47b49a0925a558a26afa756",
"main.dart.js_157.part.js": "4bc6ba9ed41601cd1ae584cd19315caa",
"main.dart.js_96.part.js": "87777cb89a20ff7a63e7e048dc06de10",
"main.dart.js_86.part.js": "fc71e26f05920059bf098f9850b24e4e",
"main.dart.js_168.part.js": "44a0c91c981795c3ef0d7b0e3e65e979",
"main.dart.js_178.part.js": "c9023f9adf9a4f42e5cc208872dc28a2",
"main.dart.js_83.part.js": "cbe81320f6c20844f094c6965805e701",
"main.dart.js_15.part.js": "0b6679e2e8b6379d8abe7d8505630121",
"main.dart.js_130.part.js": "a6410a9d44d105c80b14393094359ed4",
"main.dart.js_199.part.js": "07df9b7f7c47777abbc25daa0d2c22ef",
"main.dart.js_189.part.js": "95fd03553bc6a2b52142891b7b099781",
"main.dart.js_142.part.js": "771119132e1bb6ae2e6a13660756d761",
"main.dart.js_77.part.js": "47d649600561b0fcc31f4cece25b9f09",
"favicon.png": "0bb582c2d7ecab35d61c52f918b3a57a",
"main.dart.js_51.part.js": "a91a0d91d2654a65cc9c2478673e2c6e",
"main.dart.js_174.part.js": "c9ad1b467b2aceaaa65cf27fdf735270",
"main.dart.js_106.part.js": "11f015a70e6aaa94a5d06141736d8e13",
"main.dart.js_116.part.js": "78704a4e91ba870b8184c1b95fabe1c4",
"main.dart.js_23.part.js": "2da4998ec5a44a5f1207eb9105333a49",
"main.dart.js_33.part.js": "2c92068123444a71387ec65bd6376935",
"main.dart.js_180.part.js": "85a8e016aec1392aa2bcda2cc346bfab",
"main.dart.js_190.part.js": "0e1aba93bed4cf97496770a4f12b14dc",
"main.dart.js_40.part.js": "ceefe7cfbf271eff446a2ab76788423c",
"main.dart.js_175.part.js": "086e77bf6c892bfb25dc5de47028293e",
"main.dart.js_117.part.js": "08f53da0cfdd6a5bef8c28b91a2f5684",
"main.dart.js_107.part.js": "4f872f5566c4343c5692f15be9a7af0d",
"main.dart.js_32.part.js": "14dd265d5aca65a4c923da644dcb5ba5",
"main.dart.js_22.part.js": "9fb024001602af4efe51895d8ec9e739",
"icons/app_icon.png": "a5795b892c8ca52b83f62a72cb9c56af",
"main.dart.js_191.part.js": "f1270709ba18b58151d727127dc9dc7d",
"main.dart.js_181.part.js": "99fa51b348f60dde918acbc468be57b9",
"main.dart.js_128.part.js": "f3b55d130b5cc9c349eaf26aaa70ff9d",
"main.dart.js_138.part.js": "662533a3bb705f83ec73e2b24e0b7b18",
"manifest.json": "ce255632f156b3abd6f7816522c4ff1a",
"main.dart.js_82.part.js": "70e2df11260dad7490cda030908caf1d",
"main.dart.js_49.part.js": "2811d76d168045ad933e371f10ec25a3",
"main.dart.js_59.part.js": "b9d0a4fa8e88e926c6dbe5ca26b69656",
"main.dart.js_188.part.js": "57fa17f21587cd40fabce302bfa84b11",
"main.dart.js_198.part.js": "593368b8ff6f33ed1f90f0997da72d5c",
"main.dart.js_131.part.js": "7f486a2256b4c7ec429b9d3bc502e353",
"main.dart.js_143.part.js": "cae75fa2a43b32d7cc9570cac460e231",
"main.dart.js_76.part.js": "baea313334e15a016e663ff148d06c75",
"main.dart.js_1.part.js": "9db81c073b44a889821603f889871dca",
"main.dart.js_20.part.js": "622dc69234db709afc10163331bf0375",
"main.dart.js_30.part.js": "e4beda1212b557ab047da7b48671a17e",
"main.dart.js_105.part.js": "aea28e95fe1aa97769c096caa0a6491d",
"main.dart.js_115.part.js": "a09bb862898969a38a253cdb15dd0006",
"main.dart.js_99.part.js": "128346dce2fc44c498188e9f5af24e95",
"main.dart.js_89.part.js": "6a105eefe7240645eed277dfbc921988",
"main.dart.js_177.part.js": "f05c4b03ed8b3d1b9bd221118dcb3c7d",
"main.dart.js_42.part.js": "7e06c6d0620c747f0a34aeb057a0c322",
"main.dart.js_52.part.js": "17e3b4da0b2e9a97fb4738aa2f641419",
"main.dart.js_158.part.js": "c983e992c0dcb6cd48dc6f1cb12d2717",
"main.dart.js_148.part.js": "79dfd7983daae8a166324d223a6d9f85",
"main.dart.js_80.part.js": "42a5027bb908b24ccf1b352400c31d26",
"main.dart.js_90.part.js": "b1568efcdc19feb7f60cd53ef5689bb9",
"main.dart.js_64.part.js": "ec8edf25836f488641358061f92506c1",
"main.dart.js_141.part.js": "d567b15327e268c69d34398392038926",
"main.dart.js_133.part.js": "3bc9fa803e92a8f748f2a3edcb549e79",
"main.dart.js_16.part.js": "d1ef0520f859ab4551d93912a71950b1",
"assets/AssetManifest.json": "c879ffcdf178ecc7ea8a2bb75f1ffe5e",
"assets/NOTICES": "4c76d5c4ebe74e4d846441fa06113cf3",
"assets/FontManifest.json": "bed231d7b112623f57dc119fc603f653",
"assets/AssetManifest.bin.json": "81651bfbfb35cb9547f03dec4369a7d8",
"assets/packages/cupertino_icons/assets/CupertinoIcons.ttf": "33b7d9392238c04c131b6ce224e13711",
"assets/packages/flutter_inappwebview_web/assets/web/web_support.js": "509ae636cfdd93e49b5a6eaf0f06d79f",
"assets/packages/fluttertoast/assets/toastify.js": "56e2c9cedd97f10e7e5f1cebd85d53e3",
"assets/packages/fluttertoast/assets/toastify.css": "a85675050054f179444bc5ad70ffc635",
"assets/packages/flutter_inappwebview/assets/t_rex_runner/t-rex.css": "5a8d0222407e388155d7d1395a75d5b9",
"assets/packages/flutter_inappwebview/assets/t_rex_runner/t-rex.html": "16911fcc170c8af1c5457940bd0bf055",
"assets/packages/wakelock_plus/assets/no_sleep.js": "7748a45cd593f33280669b29c2c8919a",
"assets/shaders/ink_sparkle.frag": "ecc85a2e95f5e9f53123dcaf8cb9b6ce",
"assets/AssetManifest.bin": "a3fb8b8fdfddfaf78cb829b04ef6a45b",
"assets/fonts/boldface/AlimamaShuHeiTi-Bold.ttf": "9c8d7f88c39c3d90cd41ea9db70a9fa7",
"assets/fonts/circle/AlimamaFangYuanTiVF-Thin.ttf": "3be809e80815a8bc0feba3b241231c88",
"assets/fonts/MaterialIcons-Regular.otf": "16a85f2e437554cf4c1ae46c520a5c0b",
"assets/assets/images/no_cover.jpeg": "7e48f3134478ff6a02bdaae12702e698",
"assets/assets/images/fq.png": "6e5136dee28ad0cb3a351ed047fac21c",
"assets/assets/images/package.txt": "d41d8cd98f00b204e9800998ecf8427e",
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
"main.dart.js_91.part.js": "4c561607264f74ca5faa188a089817fa",
"main.dart.js_81.part.js": "470443b7572859261266868afde263d6",
"main.dart.js_28.part.js": "eb23bc9696d914520b1f2454c2a6d652",
"main.dart.js_75.part.js": "840699a9d21517d12c9be132625f9bef",
"main.dart.js_2.part.js": "34b4c9f83d6ccbe6d675a78312a383a7",
"main.dart.js_65.part.js": "a2fce355531004c6b13c94c2e80888f1",
"main.dart.js_140.part.js": "c3a8d2758ed04c33c9e34e79fab95e0a",
"main.dart.js_122.part.js": "d5da27e4ee2c3b24db5c00579ff2c503",
"main.dart.js_17.part.js": "85a949a6bcb1c0bd260c79dca90d4f6c",
"main.dart.js_88.part.js": "992149c96a616e16c6ca2fae56daf628",
"main.dart.js_98.part.js": "4094c1f6ca715c525430931c59691fd6",
"main.dart.js_31.part.js": "3afca22141811ea4fb10255ae9f624f3",
"main.dart.js_114.part.js": "f084aea7154dd144290f15efa9cec7ff",
"main.dart.js_176.part.js": "8e12f9e51cbd332434dee7148e839844",
"main.dart.js_166.part.js": "8fbcfac2560f1a845003f3ce2c07ea48",
"main.dart.js_53.part.js": "6c80a643428d7db116a136778547f38f",
"main.dart.js_200.part.js": "b57271c8ce530e6abbee03610836acb5",
"main.dart.js_43.part.js": "e85d6daff150b36bc3f94496b5196e5d",
"main.dart.js_192.part.js": "8c410d14f8bf80b9af1cbcafe013dbe9",
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
"canvaskit/skwasm_heavy.wasm": "8034ad26ba2485dab2fd49bdd786837b",
"main.dart.js_159.part.js": "e9b7650a7a318b84c12415b7dc84d1e0"};
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
