'use strict';
const MANIFEST = 'flutter-app-manifest';
const TEMP = 'flutter-temp-cache';
const CACHE_NAME = 'flutter-app-cache';

const RESOURCES = {"main.dart.js_187.part.js": "bf25036c17e4ddd432d9c09f71c51b38",
"main.dart.js_197.part.js": "947735fae61cfb0aff7ed1e3910ede27",
"flutter_bootstrap.js": "b04fbd163919b8b6a31d344790ed9fb0",
"main.dart.js_79.part.js": "e850e862ce6b1b43b1056f9969caedc7",
"main.dart.js_69.part.js": "ebffa21b10f7025ec2ffa554853c9192",
"main.dart.js_101.part.js": "1890b0d925e6c1db4b566d400fb52c67",
"main.dart.js_111.part.js": "5af83a1563f3b7f67ae94830cdf58378",
"main.dart.js_34.part.js": "997ce7e1a7ad5a1d22c31f233810393f",
"main.dart.js_46.part.js": "11196ae1877b66c19af4c3a00a1ba1d0",
"main.dart.js_56.part.js": "42904718551ca9c691e096871f9fd0e1",
"main.dart.js_163.part.js": "e22b8450e4b469c44d50b1fa5fa3bdfd",
"main.dart.js_173.part.js": "d2ea6dfa0e0677092d941e1fab22d500",
"main.dart.js_145.part.js": "4e5941a073626d72fb7c915da451bff0",
"main.dart.js_155.part.js": "c2098ba05a68285026a576340a4a570f",
"version.json": "de024844ca9a1ef2b9a1ec20bbc66c8f",
"main.dart.js_7.part.js": "bec17316497091797d50ddadc28a361d",
"main.dart.js_60.part.js": "12a77a9791ee7ba3ee15ce3174509624",
"main.dart.js_12.part.js": "850d85196c5ae5d4cf0407ab65780e42",
"main.dart.js_127.part.js": "386a9eb486ac0db297751786609d86a5",
"main.dart.js_137.part.js": "b054c037a508bda28efe528d29882c45",
"main.dart.js_84.part.js": "865a4742af98669b5219fd9fb6bc7116",
"main.dart.js_94.part.js": "178009286d1a308fa149485d32c85a7e",
"main.dart.js_118.part.js": "92788077f3a2c035733235aab3c1e185",
"main.dart.js_108.part.js": "2565de3286e1256b5ddf1361fa0d1263",
"main.dart.js_144.part.js": "82896402f3e2c1421bfa99c995dc7e9e",
"main.dart.js_61.part.js": "4f12b7dc6440fc9eb1517f4ecf6be16f",
"main.dart.js_6.part.js": "41846c2e0bbe84c8cdc484ebcf7a5c43",
"index.html": "f0fb83eef421024a56d50022cd18118f",
"/": "f0fb83eef421024a56d50022cd18118f",
"main.dart.js_136.part.js": "ec00fc773a4ef3d3a34103e54a31cfed",
"main.dart.js_126.part.js": "b2e64a654d3b245e94df30deccc7cbcf",
"main.dart.js_95.part.js": "df19b746fef2732b81f2eb162f6a60d0",
"main.dart.js_85.part.js": "1b578328f2757e961747e0baf0535dc4",
"main.dart.js_196.part.js": "8c89675f4822454bced2456ed2ec0d8d",
"main.dart.js_186.part.js": "522bbef6291480b92c1a8d92581a8c2f",
"main.dart.js_68.part.js": "263ce45d29db5e5533dbac1698af78c0",
"main.dart.js_78.part.js": "cf79495c556d0062bb6b35ce34a8b759",
"main.dart.js_35.part.js": "406890689354b21f5e7e7e7e5fe5e626",
"main.dart.js_25.part.js": "d2f4f43a14dc54aa86df5daccc0508fb",
"main.dart.js_47.part.js": "7db86b0cd6079b204b08277535697560",
"main.dart.js_162.part.js": "5f467a90d53d148ef1236d95652c6488",
"main.dart.js_124.part.js": "4420faa4a25ee18a3c3bd53ec3fb9245",
"main.dart.js_63.part.js": "70f4cd6c1d10d55c66c1180dcd7c7c51",
"main.dart.js_4.part.js": "334ed98c567dadfe0c9cc30071232939",
"main.dart.js_73.part.js": "0b29f3c47d8ca159be27b108bb4c49f1",
"main.dart.js_146.part.js": "1f0c8851185da482507729957ea70e05",
"main.dart.js_156.part.js": "631819744b33449e6ca089bf9716550a",
"main.dart.js": "776a2c3e23fe73e0ac599455b92e5394",
"main.dart.js_87.part.js": "6b64d6798c5ebe1974e09c6284908675",
"main.dart.js_97.part.js": "8306e9115af3f11a64685fb9ec9b8eb6",
"main.dart.js_179.part.js": "b040c0940c510880674e9b178e846340",
"main.dart.js_169.part.js": "0dc6222d3403310f3e8b9cd686f6f4e5",
"main.dart.js_194.part.js": "1f6542a2649e7bb18d80f0f7404a6e27",
"main.dart.js_18.part.js": "ff5d5d085b1ba066a0b221df92d732a8",
"main.dart.js_160.part.js": "b10752e211573bff60000c6857cab02d",
"main.dart.js_170.part.js": "0cf2a788dff9a19da95a2a616a6f654d",
"main.dart.js_45.part.js": "c0682985496ddcd2cfeaac921c0a2cfc",
"main.dart.js_55.part.js": "0b3e40b43e824f512e63959dd6792296",
"main.dart.js_19.part.js": "4dc1f182da7e89097c371bc2e156dd5f",
"flutter.js": "f31737fb005cd3a3c6bd9355efd33061",
"main.dart.js_195.part.js": "85a02132c11e582839c21f37fdf9556f",
"main.dart.js_171.part.js": "401cd554dee94214aef3a128c6ab2d04",
"main.dart.js_54.part.js": "5baa1798b5877cdcfdb51355d9c0b834",
"main.dart.js_44.part.js": "2e89abd04af6991c70949cfed38e64c2",
"main.dart.js_36.part.js": "01704fc94656b4b2b95fcbd000e122e1",
"main.dart.js_26.part.js": "55e172faf6a79cba44b3236e6c8e0047",
"main.dart.js_103.part.js": "aebdec7eecf06a79a3fc31e76160ddf2",
"main.dart.js_135.part.js": "593279161a2168873c8e83a0831c00f7",
"main.dart.js_125.part.js": "8048ff4c027623cadd8d9eea80e7fff9",
"main.dart.js_72.part.js": "62f8e6e9a845eba741c093a0e746bafc",
"main.dart.js_5.part.js": "bda8f0ce9effa69616cd937694e51d32",
"main.dart.js_157.part.js": "2893b16691b029c505d6972d20a8557b",
"main.dart.js_147.part.js": "40d5fc7cb2e1a9c921f4d5764eceed4d",
"main.dart.js_96.part.js": "0dc7d3989af771aa89b217ed1d0f732f",
"main.dart.js_86.part.js": "e805036ac128357199b5c75707f3a0f4",
"main.dart.js_83.part.js": "9ea7a609877c1a5cb2ef9c0b746c1266",
"main.dart.js_93.part.js": "9830e30073978edc2de08a4747d28f8a",
"main.dart.js_58.part.js": "7924f9cc733db53dbf175a350b614baa",
"main.dart.js_48.part.js": "d7c3ed53f991b46657bbea185e1655a5",
"main.dart.js_15.part.js": "ab664f7b2c7fde3aaa752035cf5fe0a4",
"main.dart.js_199.part.js": "eed741a1eda6503b3abd868c90dde316",
"main.dart.js_189.part.js": "51e714e4d79d445713193c29834d2203",
"main.dart.js_142.part.js": "4ba6b0897641aba23a1a70a47888d8cb",
"main.dart.js_67.part.js": "c76cc10e8178c820321eefcd3543c43d",
"main.dart.js_77.part.js": "12e6503d5e1a348a1e4d03855466e312",
"main.dart.js_202.part.js": "35fc413a66611d3b0f3ebd1ed8913b2b",
"main.dart.js_41.part.js": "9b0def0a36fdc6bb9801242e6118d1ac",
"main.dart.js_51.part.js": "a593994ac190aff5635cdbd3ad704a77",
"main.dart.js_174.part.js": "b4aee414491ffb697faf4df1e4124536",
"main.dart.js_116.part.js": "f6c2172546dbd4e7a984c2ce4d186ed2",
"main.dart.js_23.part.js": "89392e39541f24c9d0ce300385f0645d",
"main.dart.js_33.part.js": "083bea8ecdd01eb93abdc319e4c110af",
"main.dart.js_139.part.js": "0b5a47631b8ecf8771625990ae954736",
"main.dart.js_129.part.js": "9b78b822b518139fa1b831b46ba94b19",
"main.dart.js_190.part.js": "50490cc7a1f98f9a2dcd538bec605a5d",
"main.dart.js_50.part.js": "ab32187bea3b98b60ff498f598f29905",
"main.dart.js_40.part.js": "02019cb54744d52807e621d0b4a20ca3",
"main.dart.js_165.part.js": "e4cf6e367d597a9dd041e8ef36801f43",
"main.dart.js_117.part.js": "549698d3db7f3b31a8f030405465b2b1",
"main.dart.js_22.part.js": "3fd100be3a7583cba57faa8e18ad72b0",
"icons/app_icon.png": "9f8a277dbc30308e6ea0de6c057c0a55",
"main.dart.js_138.part.js": "56222c1a4c090469395e782e60e72eb4",
"manifest.json": "ce255632f156b3abd6f7816522c4ff1a",
"main.dart.js_92.part.js": "361834b0b46d43ff7ba402e3e74f0f1f",
"main.dart.js_82.part.js": "b46cb1d19b97cb4e97559650e3ed1c56",
"main.dart.js_49.part.js": "464e86142235a1bb3ddb726752b744e1",
"main.dart.js_59.part.js": "80e0c5891a6eb4f95b34c6ea7f1098df",
"main.dart.js_131.part.js": "5dd59d5236008ab7691cf12208e6e65a",
"main.dart.js_121.part.js": "6e18779c9d7ace040790184c7d8e93f0",
"main.dart.js_153.part.js": "8ba3755cc17f2defa6abe842d7c19096",
"main.dart.js_76.part.js": "22f7a415ee25979609d5a3e444084f5c",
"main.dart.js_1.part.js": "9006b4640a694fa38821d3bc4bffc62a",
"main.dart.js_20.part.js": "a69a60d8881abc74228e976e86a6313e",
"main.dart.js_105.part.js": "bbdeb68b6c8fc27242cb3e4ed1a32ad3",
"main.dart.js_89.part.js": "bd162a5ed9e97951f2eaa989bfc6124f",
"main.dart.js_167.part.js": "762e8a032024e4b1a0bc8ac16781d083",
"main.dart.js_177.part.js": "cf35f3ba45a4c02db7ff176c6a809a05",
"main.dart.js_42.part.js": "954327fd0b1c02dd06dfeb9d9c13bb06",
"main.dart.js_201.part.js": "a80412a07f0d477943005d5e34d7e35e",
"main.dart.js_52.part.js": "f41e51e39ea5959b6203eb6b97ead362",
"main.dart.js_183.part.js": "5d227c5f5ff70288ee6ca74850ad7829",
"main.dart.js_193.part.js": "32c2f589f1e653881ca638d417b123e3",
"main.dart.js_158.part.js": "6ea8ffc76cb01dc70504bd7d103017bf",
"main.dart.js_39.part.js": "2829ea959b661a7330bdc65ffa62ca1f",
"main.dart.js_29.part.js": "2740f6afb69e0cb210d2fd2bbec5ed3a",
"main.dart.js_90.part.js": "1a5b0540571f701d4239aaf70a553c47",
"main.dart.js_64.part.js": "7a983b2bd293a966f6fdd3ae1bd3f890",
"main.dart.js_3.part.js": "9c533fe91369606df2241d2bc3d9ad86",
"main.dart.js_141.part.js": "56b8f6abea315c326c3ad73cac70f787",
"main.dart.js_151.part.js": "4f566606186e8fd8dab5ce7de498d1f6",
"main.dart.js_133.part.js": "c46930e65b162b4f25512daa73a3cff2",
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
"main.dart.js_28.part.js": "dc63d5774110f01edc8235eb59c4c848",
"main.dart.js_75.part.js": "0288eae10d1a4731027730e126739b89",
"main.dart.js_65.part.js": "f8c8b559534c58163f06dbb44257a42a",
"main.dart.js_122.part.js": "daef3368ef9c73a425762d7b8f022a31",
"main.dart.js_88.part.js": "5c93a9a942e726a70cec2896e17d098c",
"main.dart.js_98.part.js": "1c85e8cf70e0cff3e2f1dae38a2c0cd0",
"main.dart.js_31.part.js": "d306da91ec89498c53fb3bc221fe85a0",
"main.dart.js_21.part.js": "308d1be8ef0ed147c48fecffeaf39ced",
"main.dart.js_114.part.js": "3afcdaf6f2eda32366843e9b6f995241",
"main.dart.js_176.part.js": "3043d3cc09c44bec7d02c7278512135e",
"main.dart.js_200.part.js": "e0517b1c7489c6132805b7a7311fa798",
"main.dart.js_192.part.js": "1d85c5a633a25fc952358c0ecb55543e",
"main.dart.js_182.part.js": "0cd3c79ce71b44b53ea10921c0f4aba4",
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
