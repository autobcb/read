'use strict';
const MANIFEST = 'flutter-app-manifest';
const TEMP = 'flutter-temp-cache';
const CACHE_NAME = 'flutter-app-cache';

const RESOURCES = {"flutter_bootstrap.js": "c134cf43fc5f6e2fee3f64524cb326ff",
"main.dart.js_69.part.js": "32a3d78000315325ba8fd6a379b273e2",
"main.dart.js_101.part.js": "a18833a6f3c74e42aedb997ac1574d52",
"main.dart.js_111.part.js": "8f5501551f68bf37c5ca1181f7d8eafc",
"main.dart.js_24.part.js": "cf341d7f46a3ac153578ab32b35118d2",
"main.dart.js_56.part.js": "91cf660daccabf6d9d6a49da45364963",
"main.dart.js_163.part.js": "8f5102fbe5ebb6300ffaf5593c51a345",
"main.dart.js_145.part.js": "7a9ab62c9e9c5ae0aa1ed25766f8e54d",
"version.json": "fb300151bd2ef06e6ca7de4b46200374",
"main.dart.js_7.part.js": "5d3e9d1ffb10eff814832bf32e3e6daa",
"main.dart.js_60.part.js": "37d640db08c194cfb137bbe654008e89",
"main.dart.js_70.part.js": "75da5add6b65d91715c929adf77adf8a",
"main.dart.js_84.part.js": "2fe3327b81f444323af50aa8b46f9089",
"main.dart.js_94.part.js": "0f817d15db60e9678ba60bd27afa65e9",
"main.dart.js_154.part.js": "ab578ae205fe37d9e2705467de013d75",
"main.dart.js_144.part.js": "e3fc32b06bc8992af4794825679c9a81",
"main.dart.js_71.part.js": "5006cdaf9e25fd5f99f160163d2be15c",
"main.dart.js_61.part.js": "8b0c8576fa9f4044f2d4ace37f1c2efb",
"main.dart.js_6.part.js": "5ab3b8dfdb4d564c85d55fac05a8778d",
"index.html": "f0fb83eef421024a56d50022cd18118f",
"/": "f0fb83eef421024a56d50022cd18118f",
"main.dart.js_126.part.js": "3fa59568a8a9002fb842a9160df51edd",
"main.dart.js_119.part.js": "04be8e44302a1988177d5e75a2a75f3e",
"main.dart.js_95.part.js": "b0bdf90b20d3b50b19f0cf2ac993932d",
"main.dart.js_85.part.js": "b4461e615fe884b79ad88c08a016c4c6",
"main.dart.js_186.part.js": "0250e3794606ed9ab0d0545fd3aaf100",
"main.dart.js_100.part.js": "e487f7d99bb5223737b8551dc511915a",
"main.dart.js_25.part.js": "355a478a9667ce01830ec4ee892dda77",
"main.dart.js_57.part.js": "28a676582963b9b3d7c4b3aab77d9ba6",
"main.dart.js_172.part.js": "139af5f2a1e1d98c51744da5a46a6d2a",
"main.dart.js_162.part.js": "9138d84c14724cc3238c325d1f2e643a",
"main.dart.js_124.part.js": "d4a864f60a9f3f36dec1295caa261bf7",
"main.dart.js_11.part.js": "3fa8f55d2cb45c36f44d3299c63edd73",
"main.dart.js_63.part.js": "0a1360c1259a98f202ab061292bca543",
"main.dart.js_4.part.js": "79845a3430adacea22b6b6df70d74799",
"main.dart.js_73.part.js": "7896d64e2585e7f2c3259f17fbabe2ec",
"main.dart.js_156.part.js": "d5f0e7ebbdcd8342096a286a2f7d0e0a",
"main.dart.js": "6e1191854bb40f1c05ba349c1bc626b0",
"main.dart.js_87.part.js": "32c84ab89d87f7e7c2442a6a79470b60",
"main.dart.js_97.part.js": "7c69631c9cbb3a149eb7cfb4e273a16d",
"main.dart.js_179.part.js": "df799f89582b640210f30491b9340fd6",
"main.dart.js_184.part.js": "f5b684c12cfbfb9612d4aa1ceb1e25f4",
"main.dart.js_18.part.js": "4e408642367c73a017c1da89b0995ba0",
"main.dart.js_160.part.js": "57675cbf164a1eaecc8fc60d153c6d45",
"main.dart.js_45.part.js": "ee2551565599a9201e6cb4dec16158c8",
"main.dart.js_55.part.js": "853ecca3298dbed2491ff2e69e6b74c6",
"main.dart.js_27.part.js": "598f2ff1f35ad09fc7eb64e68eb57307",
"main.dart.js_37.part.js": "83eae5bc293e6a04f543662a484c8f54",
"main.dart.js_102.part.js": "4cb056e738f41dba36614d87a4cf7ee5",
"main.dart.js_19.part.js": "116f42f3eb256176b26170301ba00a77",
"flutter.js": "83d881c1dbb6d6bcd6b42e274605b69c",
"main.dart.js_185.part.js": "336663e3a216eb8b62fbc3f0cf2d384f",
"main.dart.js_171.part.js": "1446b488474cf4c74556907d941288db",
"main.dart.js_44.part.js": "87407fb9e983d7077ed3e1e622640545",
"main.dart.js_36.part.js": "9843b6f9273d525b8d66b760ebb06878",
"main.dart.js_113.part.js": "3acf9792b1f5fdc3b3699b8a25504658",
"main.dart.js_103.part.js": "b2ec5931a138263b4e4b45812cc5e822",
"main.dart.js_135.part.js": "4b9df158554f0733b0a9eb60ff1fcc00",
"main.dart.js_125.part.js": "891b5970bd049f55728195bcb119869d",
"main.dart.js_72.part.js": "3b2ea1bebd427adabfd8dd9f21dca07e",
"main.dart.js_5.part.js": "0695608510c7913650b422f82687ddf0",
"main.dart.js_62.part.js": "25dd906f82cf68c3392d5a5ee92f8d70",
"main.dart.js_157.part.js": "7ce07a98240c1a09d20750645852c0b9",
"main.dart.js_147.part.js": "910282676a350f2fb6aa5abd9c478d6d",
"main.dart.js_86.part.js": "b1204708b6573fb34ded8c8eee6dc8cf",
"main.dart.js_168.part.js": "cf767ff00193c70e58f4c34294755180",
"main.dart.js_178.part.js": "49772f79dd4f0b33416734851c507bb6",
"main.dart.js_83.part.js": "c7d14445ae19cdd5be62b5c99d8dd9c5",
"main.dart.js_58.part.js": "df0d0c80891c1320a420902db720514b",
"main.dart.js_120.part.js": "8680296761ef9e14b062a234e9dcf348",
"main.dart.js_130.part.js": "b381ef9a461b2c09bd9d8003906f4826",
"main.dart.js_152.part.js": "8f855064ec0622fced041ed223acbf20",
"main.dart.js_67.part.js": "32037e944028c026a278ee0b880030ba",
"main.dart.js_41.part.js": "cd55e81cbdbb86ca635427df1d746176",
"main.dart.js_51.part.js": "bf77258f68b5c6dda56550408c07b49f",
"main.dart.js_174.part.js": "f7ea0857b03ff1a7a1076b4015f1b9e4",
"main.dart.js_33.part.js": "594929b412d49f6839a27dcde601b660",
"main.dart.js_9.part.js": "d0c5eccc179e14a225a7859b5a69e2f4",
"main.dart.js_129.part.js": "132a6b99a0687be6fb192e7532082e62",
"main.dart.js_180.part.js": "7b968b585e1fc506cf916d0da15e2e2f",
"main.dart.js_50.part.js": "5c582974d82d12c30237cd29460af0b3",
"main.dart.js_165.part.js": "f03020c9aeea37f62940c87419a4b7cd",
"main.dart.js_117.part.js": "223cef26cfa7794e5a9c3d96ecf4867d",
"main.dart.js_32.part.js": "e1c86338857633050ec6bd71a50d4a25",
"main.dart.js_22.part.js": "ec95fb142f81c511eb149d20cd22e3e3",
"icons/app_icon.png": "9f8a277dbc30308e6ea0de6c057c0a55",
"main.dart.js_181.part.js": "4fb7afd1431d7d158eb9ef644bb73625",
"main.dart.js_128.part.js": "ed27ac9d07527a0c87f73aaadebcc55c",
"manifest.json": "ce255632f156b3abd6f7816522c4ff1a",
"main.dart.js_92.part.js": "51258eeee5f9437d88d2afe3b9977835",
"main.dart.js_82.part.js": "9fc57793f1344642d242c14ed21e2c55",
"main.dart.js_14.part.js": "002a821324806f247a3312b22b6b4498",
"main.dart.js_131.part.js": "95ea1c17a97375434884a10c55888f42",
"main.dart.js_143.part.js": "7d80c52a02de3acd7d3e8018c6c2f7aa",
"main.dart.js_76.part.js": "9636d352e034c0f9b550294a727b86dd",
"main.dart.js_66.part.js": "fa9feec5b9c99366a8805d8e1c7fe058",
"main.dart.js_1.part.js": "3685ebf51fdcc22f408d53e054b5d008",
"main.dart.js_20.part.js": "394033cad116a6efed5ff420ea17fc47",
"main.dart.js_105.part.js": "836af1f5dabc6c971bb593c0f959a3eb",
"main.dart.js_99.part.js": "566880da5bb2b833203c164bb95d0d08",
"main.dart.js_167.part.js": "ee698b01c2dc178bf7941f9029ff67cd",
"main.dart.js_177.part.js": "b9490b4880600c542efb8cac9f156d63",
"main.dart.js_52.part.js": "9b16f070cd046045b2c47519815d7764",
"main.dart.js_183.part.js": "1cc4ea83a64187f2053cc043bb328bee",
"main.dart.js_158.part.js": "2f873c48b9b2cbb953b2257c6de492f9",
"main.dart.js_148.part.js": "0ee98a6ab472a60dd2b777f0d847f7f7",
"main.dart.js_39.part.js": "5e0136a3bfd6736448c812c60a1a09a8",
"main.dart.js_29.part.js": "b264fa926968072e3f5ba928e5b05dce",
"main.dart.js_80.part.js": "325d63c00befdc49134cc7834e7e80e6",
"main.dart.js_90.part.js": "00e50b94ca68dceb71fb0119cf777634",
"main.dart.js_64.part.js": "fa8de67dc39b083070a9d99dd866f77d",
"main.dart.js_3.part.js": "344452ca7a9e5f766caecbe0ba0c175b",
"main.dart.js_74.part.js": "6be20154e2242f68424fc7bd78904c91",
"main.dart.js_123.part.js": "e005872394e08066abcff26cb2e40898",
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
"main.dart.js_91.part.js": "97396106428551e9b92af7e096f673a7",
"main.dart.js_81.part.js": "846536de6bf2cb351f47a7b42e87656f",
"main.dart.js_28.part.js": "d461c6f781d93df882e0bd807f3caf60",
"main.dart.js_38.part.js": "0969f7cee565e3337d6f7b94a1f10d6c",
"main.dart.js_75.part.js": "71900a67ae5768bb53e3bdf4bd4fd876",
"main.dart.js_65.part.js": "933d4f5552a359e808874dfc815d91ca",
"main.dart.js_140.part.js": "f3aa7ec9e471e0ebb0c915f0f99de9fb",
"main.dart.js_132.part.js": "e33ec5bf784be5fdad67d64821ea61e8",
"main.dart.js_122.part.js": "9db3341a6986f3f1ebbf47f87f8fb8d4",
"main.dart.js_17.part.js": "cc7ae657ba0e0452c7a626ff987d6992",
"main.dart.js_88.part.js": "392051d2be53ced32ba3c7e5643b3e6f",
"main.dart.js_98.part.js": "253fbe26991a9cb9da5a227e97a016d7",
"main.dart.js_31.part.js": "c5b9ba3dc4e6e0c62cf631d6c8df30c3",
"main.dart.js_21.part.js": "49f12ad7ccf6a2f498044bcbc5983d5a",
"main.dart.js_114.part.js": "c1806dfaba400454dcdd548f41d31fa4",
"main.dart.js_104.part.js": "62957c4dbd7ed32130f5849359908535",
"main.dart.js_176.part.js": "a9a68b402572528ac8e149405d79405a",
"main.dart.js_53.part.js": "6b1d10a08db521ae79a5e097c3af0d8d",
"main.dart.js_43.part.js": "f97af05a12da8a5c37a86f12e21293a4",
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
