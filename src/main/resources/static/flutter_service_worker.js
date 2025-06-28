'use strict';
const MANIFEST = 'flutter-app-manifest';
const TEMP = 'flutter-temp-cache';
const CACHE_NAME = 'flutter-app-cache';

const RESOURCES = {"main.dart.js_187.part.js": "7ed3c65a76e0c997cac216d72ecd5f1f",
"main.dart.js_197.part.js": "afe8f98c066a5b797bb508bde0796b49",
"flutter_bootstrap.js": "630c0b5901868d70254743f79cb71d39",
"main.dart.js_79.part.js": "a43efb23b8dcb3766dc85af7143b0c91",
"main.dart.js_24.part.js": "9b4686a2dfd50cb95f42e6879545675c",
"main.dart.js_34.part.js": "6e349c279811fac7a64611055b611a8a",
"main.dart.js_46.part.js": "3fa96e0239e539d97ea2d93b05c546d1",
"main.dart.js_56.part.js": "e385de424466484737d60f26eea99e7d",
"main.dart.js_163.part.js": "b55184e5ae0bc9d486dac14845b1a8c9",
"main.dart.js_173.part.js": "4244495a611504eb8a996abf9ca360e4",
"main.dart.js_145.part.js": "64eb6c9cda86576bd98488f7080ae880",
"main.dart.js_155.part.js": "252682b62bb9c057069591c79fa48877",
"version.json": "ee69a93169ec86942d0442a2f1c3c8f9",
"main.dart.js_7.part.js": "87a3a073e561d504bd382a49580a71a5",
"main.dart.js_60.part.js": "47c27b90d17a8ec3269dbc4b2ef9b7ac",
"main.dart.js_70.part.js": "29976553cbaf9298742ba7eff66808d8",
"main.dart.js_127.part.js": "68caf2f1fb624e92253e865fa6149364",
"main.dart.js_84.part.js": "c997d6614f3409447025cf09afcd2eab",
"main.dart.js_94.part.js": "cf22e285a3f621af669773acd37c76c1",
"main.dart.js_118.part.js": "fccc3e36fd06816632feb60f3d696f9e",
"main.dart.js_144.part.js": "5a7e2ce08550eec9d1d96d147664af6c",
"main.dart.js_71.part.js": "120c9613f34237e9af0442f9cbe2a976",
"main.dart.js_61.part.js": "fbfe60f7fe5c4240400c2adab9fff4ea",
"main.dart.js_6.part.js": "557313d7999671944e6fe47556e6b172",
"index.html": "f0fb83eef421024a56d50022cd18118f",
"/": "f0fb83eef421024a56d50022cd18118f",
"main.dart.js_126.part.js": "3015f041144c2d90aef89d1998475154",
"main.dart.js_109.part.js": "245734a62c91df9e1ec6a2ef29d2064e",
"main.dart.js_119.part.js": "29ea1734cf7301a4b4fe3936e05d4978",
"main.dart.js_95.part.js": "31c3d8151ebadf17c2915964d8ad4ca4",
"main.dart.js_85.part.js": "b5853f278fa4c449d56db49f7e2f1923",
"main.dart.js_196.part.js": "7eee0857e4e0ed65055951210b171bee",
"main.dart.js_186.part.js": "b399a4114bc86ebef32a49c2f5cea365",
"main.dart.js_68.part.js": "bd46af197fe7b4706317bb7fbf62ee68",
"main.dart.js_78.part.js": "4aab2cd24ec3a13dd241a6d95eebdea6",
"main.dart.js_100.part.js": "8134b4c8a019f3668708c46301fcd010",
"main.dart.js_57.part.js": "ee9968dbd671346fdba11e3c1bd8676c",
"main.dart.js_47.part.js": "341ccea41b5b5ec8981cb91ce973e22d",
"main.dart.js_172.part.js": "1581db62991cc623187aafcefd3c3791",
"main.dart.js_162.part.js": "b6fb9a697e9be98451803825a37137f5",
"main.dart.js_124.part.js": "2292c6d5d7e279c9868b70a2f5cf4982",
"main.dart.js_134.part.js": "76ac0d20556ef9468fb3e4eb1d275d08",
"main.dart.js_11.part.js": "da880b98848fc581205612d3c5753897",
"main.dart.js_4.part.js": "04a78ce49fa69259a5fbcdff0ebd9208",
"main.dart.js_73.part.js": "a4de9322df2c8a94c0d96c0f266aefb2",
"main.dart.js_146.part.js": "cc17819f139bcd6eb97c56563cbeb4d5",
"main.dart.js": "985273b4f8d851598fd00a27c74b4ce9",
"main.dart.js_87.part.js": "d93e8e65a84cd9874f38d954c3a00e12",
"main.dart.js_97.part.js": "9eb7d09169afd17eb744fd254fba27a7",
"main.dart.js_169.part.js": "37fea28fe1b6ae29a19d6fa197046e65",
"main.dart.js_194.part.js": "3888698a2b305011b8e6adae00e5ff2d",
"main.dart.js_18.part.js": "32203fb0f2681bd7297b0d8936d33c73",
"main.dart.js_160.part.js": "d7045b561f2caeaf422ba891d0ba96a7",
"main.dart.js_45.part.js": "91b61d32eada050d020423167e8ee3c0",
"main.dart.js_27.part.js": "d49892a89acd5869637bb71122860556",
"main.dart.js_19.part.js": "e220025abb67ee92674c1266e11d8667",
"flutter.js": "83d881c1dbb6d6bcd6b42e274605b69c",
"main.dart.js_195.part.js": "5a48629a3b9b2538ed8e9d55fd1a1410",
"main.dart.js_171.part.js": "7c7e5aea15ed2c5572dd55a102e4cbc3",
"main.dart.js_54.part.js": "a81b019f9f179cc075c59e2e49cbf292",
"main.dart.js_36.part.js": "1b560ea04dea11568116a0b35266bb56",
"main.dart.js_26.part.js": "a96fbdbe774ae18e4ea38c4d3cc05a66",
"main.dart.js_113.part.js": "32db361930c1a4b025843ddbc7872db0",
"main.dart.js_103.part.js": "457fa2dfab858a4a9b06bcacb4cd927e",
"main.dart.js_135.part.js": "ae18a7bec5556853d2d251da9d5673f6",
"main.dart.js_72.part.js": "772a710aea1128c1fdccb436ccb5bbb9",
"main.dart.js_5.part.js": "4ff6714e4ccfd8c5613482b021dcd5ac",
"main.dart.js_62.part.js": "027237610145dc2d1ec6538a2370db89",
"main.dart.js_157.part.js": "b93d930e014515f773a4dbb5c2d44529",
"main.dart.js_96.part.js": "0e6dc3e016f5a200150f149840a62338",
"main.dart.js_86.part.js": "fc5df6508e8eb64074d5c2ab832dfca0",
"main.dart.js_178.part.js": "e45f6b9e70b62e37f954c14557c906f6",
"main.dart.js_83.part.js": "3847258921b5cf57b3b557859cdfee0d",
"main.dart.js_93.part.js": "10107bf5ab6ea5960683a552a376c6ea",
"main.dart.js_58.part.js": "11fb6f2fd51df3a5dda2022c93f5bc74",
"main.dart.js_48.part.js": "e795a6aaeafea0aeb3b7137226129b39",
"main.dart.js_120.part.js": "8eeb1f6b15c52c65904e7eb0e8de16b5",
"main.dart.js_199.part.js": "20ba00c71373e224c886320a6964f06e",
"main.dart.js_189.part.js": "482b29468e4989034af6cc5f1aedab80",
"main.dart.js_142.part.js": "12bf96237017efc73df4184df1cec351",
"main.dart.js_152.part.js": "d5180f437630d6566d365320cec327d6",
"main.dart.js_67.part.js": "324338af76e709325f28e6f1402a92ca",
"main.dart.js_202.part.js": "e8fb1ff0ad9e92ed582a6fda6b6a4616",
"main.dart.js_41.part.js": "a7d9ec824fb212212be610f7f8bbd352",
"main.dart.js_51.part.js": "c8e045224f829b36f31f738f05be7b98",
"main.dart.js_164.part.js": "250abdf06badf05dc34954e4f6b5288c",
"main.dart.js_116.part.js": "1569c6d023bd43893db419fe0acc72e9",
"main.dart.js_23.part.js": "423245e84a661b3fc6c075721c5c7323",
"main.dart.js_33.part.js": "f503c2510604111da3720efe448c0301",
"main.dart.js_9.part.js": "92426b3f1d770349c710103c41e5ac9b",
"main.dart.js_139.part.js": "090e64e47afff3ee9090d72853f564d8",
"main.dart.js_129.part.js": "41727c67f1054189cabc61fb26c23fe0",
"main.dart.js_180.part.js": "8746a747bac81a888a2e7ac7463f1545",
"main.dart.js_190.part.js": "79f452ccccf16630c786a2772b76bcdc",
"main.dart.js_40.part.js": "a5610f036e2277606172b51fbe49066e",
"main.dart.js_175.part.js": "1659afe0ed90f95f046ff47d5c4d3852",
"main.dart.js_165.part.js": "e138628cb19a56695285567f86aa50ce",
"main.dart.js_107.part.js": "725e16c9de82a2ea5ad23218398016f4",
"main.dart.js_32.part.js": "330dadd3c7857a30841c4c28eed2c5ca",
"icons/app_icon.png": "9f8a277dbc30308e6ea0de6c057c0a55",
"main.dart.js_128.part.js": "8c72cc97795f1bcae8cf56c95e343ebe",
"main.dart.js_138.part.js": "5dedb43843b00ca494b48d388ddfaa96",
"manifest.json": "ce255632f156b3abd6f7816522c4ff1a",
"main.dart.js_92.part.js": "7359833096772714d6d73822acdc396c",
"main.dart.js_49.part.js": "36886b20623ea1b35171d2065faa3fba",
"main.dart.js_14.part.js": "d4154eb0a541fe3bd8590c0a38cf8ca1",
"main.dart.js_143.part.js": "c98746e412e4aa18383ff4efabc15261",
"main.dart.js_76.part.js": "0fedbe63c09e330f9266bd3180010426",
"main.dart.js_66.part.js": "7cc410069e9e5372b1cb0a69a6e8c1b2",
"main.dart.js_1.part.js": "ff0d0b33997889c37d54ff3c5665a8df",
"main.dart.js_20.part.js": "0c146a9058016e5e82d913695cbb6e39",
"main.dart.js_105.part.js": "0aa6ba143b4b65b5ceab5df1a6c8fcb3",
"main.dart.js_99.part.js": "993681294fada313b71865786706c9b8",
"main.dart.js_89.part.js": "14510b897dd7d3ee293f0c96d2465507",
"main.dart.js_167.part.js": "dc3a4281842cdd2c0d1250e15358c6f5",
"main.dart.js_177.part.js": "9ec254269d4a3ccb709ec5304a93457b",
"main.dart.js_201.part.js": "bcd45d2ff5a489f6f31172d8e41ab4ef",
"main.dart.js_52.part.js": "cba1cdb1a88ee560ce64ceabe4785903",
"main.dart.js_183.part.js": "2d59596ec51727755369ed0551645cf8",
"main.dart.js_193.part.js": "f90faf025dd85f596f9ed75b745aa8d9",
"main.dart.js_158.part.js": "217d972dd6f6b79790088324d961205e",
"main.dart.js_148.part.js": "206d1f3a5e16c3e26076b5909a6042a9",
"main.dart.js_39.part.js": "6406ffd151df7be071fdeec4c63d6a8f",
"main.dart.js_29.part.js": "9f277934586fe67d340ef6b626ea581b",
"main.dart.js_80.part.js": "2cf946eb6177695343890b3fabfb0875",
"main.dart.js_90.part.js": "13dfa15a95817d2dc797c842c45c49eb",
"main.dart.js_64.part.js": "8a0ecceafef25894eb4f1f87a42102a7",
"main.dart.js_3.part.js": "c8044883e5f358d2ef0777e07e5dfcde",
"main.dart.js_141.part.js": "4239a893b4d7fb8ea7dab3bbbcd22438",
"main.dart.js_123.part.js": "b8e5b16fbff2b9a325c8e651cacc2191",
"main.dart.js_133.part.js": "b8aa3269100809059ed5e7f64c791346",
"main.dart.js_16.part.js": "524b10c7dc1d6d62fd28404abbbfd5d1",
"assets/AssetManifest.json": "3eb09be2ac2ecbd14a6ea6229a1da724",
"assets/NOTICES": "070359d662bcaf37b62b608a5424ab65",
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
"main.dart.js_91.part.js": "6e57ed314b1209c547ddebbc806d92dc",
"main.dart.js_28.part.js": "54256b05b411e07862d7537cb71e6b3d",
"main.dart.js_38.part.js": "8ecaa81a87b923e777dca1ef0280e712",
"main.dart.js_65.part.js": "31a06948c65252ed0a16859d0d74478d",
"main.dart.js_150.part.js": "cfc14cb8a2299777606f907eb91b043c",
"main.dart.js_17.part.js": "87c4aff003763cc17931c5c5d8ba8b08",
"main.dart.js_88.part.js": "79071139ed7001347a74ae3b46af7ce8",
"main.dart.js_98.part.js": "6c7abd63bd39b6595781303a10186786",
"main.dart.js_31.part.js": "c4155119203f37bb55385bc9217424cc",
"main.dart.js_21.part.js": "8da76ba5eb4046dc6b55b1ff8b79ca4a",
"main.dart.js_53.part.js": "68f9fac88f8f840fa35bba73f9d51e6a",
"main.dart.js_200.part.js": "5375076708809cda540b45c6279b0bef",
"main.dart.js_192.part.js": "5d309d8e895d999e95420679d60293e5",
"main.dart.js_182.part.js": "2acc209f266f59ca9cff7f5e67e3bbba",
"canvaskit/skwasm.js": "ea559890a088fe28b4ddf70e17e60052",
"canvaskit/skwasm.js.symbols": "e72c79950c8a8483d826a7f0560573a1",
"canvaskit/canvaskit.js.symbols": "bdcd3835edf8586b6d6edfce8749fb77",
"canvaskit/skwasm.wasm": "39dd80367a4e71582d234948adc521c0",
"canvaskit/chromium/canvaskit.js.symbols": "b61b5f4673c9698029fa0a746a9ad581",
"canvaskit/chromium/canvaskit.js": "8191e843020c832c9cf8852a4b909d4c",
"canvaskit/chromium/canvaskit.wasm": "f504de372e31c8031018a9ec0a9ef5f0",
"canvaskit/canvaskit.js": "728b2d477d9b8c14593d4f9b82b484f3",
"canvaskit/canvaskit.wasm": "7a3f4ae7d65fc1de6a6e7ddd3224bc93",
"main.dart.js_149.part.js": "78c45f79cf5bf18f598e78876734d7cc",
"main.dart.js_159.part.js": "fd98af8a7f11061815ecd5cbc6a96e7e"};
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
