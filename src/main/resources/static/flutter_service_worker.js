'use strict';
const MANIFEST = 'flutter-app-manifest';
const TEMP = 'flutter-temp-cache';
const CACHE_NAME = 'flutter-app-cache';

const RESOURCES = {"flutter_bootstrap.js": "0500fb6853c1e36c898efe7a646e7cb8",
"main.dart.js_79.part.js": "9a00c8edbec22b6200b44e2c3f71aed0",
"main.dart.js_69.part.js": "0da4369c0d73f2f8877d9baa064211c1",
"main.dart.js_101.part.js": "7512eb5dd12b2fc6a824b7de19a69c98",
"main.dart.js_111.part.js": "09429d8485cd424ceb90a3b26b955ca2",
"main.dart.js_34.part.js": "e9f5de933db385969633d05d9970176a",
"main.dart.js_46.part.js": "5306dc37288c26a7cbd5dd99f1b01d42",
"main.dart.js_163.part.js": "a80f9b0ffdb7de32ffdc5c4bbdbbf023",
"version.json": "3fce7e6a1049ad7f2d45b5f513751f65",
"main.dart.js_7.part.js": "54f715c22a72a0078d325d81177ddd9f",
"flutter-canvaskit/1e9a811bf8e70466596bcf0ea3a8b5adb5f17f7f/chromium/canvaskit.js": "5e27aae346eee469027c80af0751d53d",
"flutter-canvaskit/1e9a811bf8e70466596bcf0ea3a8b5adb5f17f7f/chromium/canvaskit.wasm": "24c77e750a7fa6d474198905249ff506",
"flutter-canvaskit/1e9a811bf8e70466596bcf0ea3a8b5adb5f17f7f/canvaskit.js": "140ccb7d34d0a55065fbd422b843add6",
"flutter-canvaskit/1e9a811bf8e70466596bcf0ea3a8b5adb5f17f7f/canvaskit.wasm": "07b9f5853202304d3b0749d9306573cc",
"main.dart.js_60.part.js": "107ea0915c72f63881d83b168c5246ee",
"main.dart.js_70.part.js": "bca51d7d66f37c388673c65665bb5091",
"main.dart.js_12.part.js": "741fa3374eb0013981e7b0348e3c4d14",
"main.dart.js_137.part.js": "254d59b7195f82fe54e97e053431f8ff",
"main.dart.js_84.part.js": "469f9b166f28c285155ebefea8bb8bbc",
"main.dart.js_94.part.js": "4adf03ecc24c9fff69b43eae5339083a",
"main.dart.js_108.part.js": "5186de127cb303283daf4fd6838443ab",
"favicon.ico": "568a1ed53b5fb9ba8cb13938b4c1b23a",
"main.dart.js_154.part.js": "bf7808a47c8811062dac5e26a78bf656",
"main.dart.js_144.part.js": "3cb73f657aa7048727a84991a857ec10",
"main.dart.js_71.part.js": "7907126ca86449c397dada126b27a097",
"main.dart.js_61.part.js": "43046d7a97eda89900ed77698f2d5bb7",
"main.dart.js_6.part.js": "cc5578dc70a030bdf30758e91eff0a2c",
"index.html": "f0fb83eef421024a56d50022cd18118f",
"/": "f0fb83eef421024a56d50022cd18118f",
"main.dart.js_136.part.js": "1331e90d49a998857c941b958bb47d44",
"main.dart.js_126.part.js": "2b3c60b273507edf17ca0f08f1c45731",
"main.dart.js_95.part.js": "16f7b6901c63ab0e8a422a05c0bb8d89",
"main.dart.js_196.part.js": "6e5fddc9a8776f985449f8d1e6f5e803",
"main.dart.js_78.part.js": "cfadaa04ed7a2bbf06d4c40c4ea72bc6",
"main.dart.js_100.part.js": "8d29a00bbf52164954f7ec9b1a9de3bf",
"main.dart.js_35.part.js": "5b61f23e4b7cb1a1030b10c789ed6ad8",
"main.dart.js_25.part.js": "654a7f6e8acc3413ab848ecec003b115",
"main.dart.js_47.part.js": "3ac66e76d5690aca9cb498626d4a6907",
"main.dart.js_172.part.js": "6e3458c03772a0c9a200415b4adcb76c",
"main.dart.js_134.part.js": "35b02592b16dcba06606bb6c2ccbceb0",
"main.dart.js_63.part.js": "ca202eb65eb2be8fbcd7d04f7c3963e0",
"main.dart.js_4.part.js": "0682bc4d5e3e2cdebfde2fce5387740a",
"main.dart.js_73.part.js": "d4f1aee54fe0b1edb0bf5e0bc77aa77c",
"main.dart.js": "654955e576cf3364ba70ca504d5d7685",
"main.dart.js_97.part.js": "97468f543351612ea1f84450b8782fc6",
"main.dart.js_184.part.js": "a160431576948b7b04c3fbf58d98532f",
"main.dart.js_194.part.js": "0babff1c707760f45051afa8c94cf7f1",
"main.dart.js_18.part.js": "aefd8a2924f7f511a2b6dbc941b9a362",
"main.dart.js_170.part.js": "21e48f4778495ad576d4ae92929bfd19",
"main.dart.js_45.part.js": "ef7a5061ac6042efdb567dcbc4e447b4",
"main.dart.js_27.part.js": "39bb686316ca6c5f8c51214911361494",
"main.dart.js_112.part.js": "b3a902d9cb841ffd7711535edb4f738e",
"flutter.js": "888483df48293866f9f41d3d9274a779",
"main.dart.js_195.part.js": "74feeb92770e675bd2a2424995d30e72",
"main.dart.js_185.part.js": "4cc99489ce392b36603b5a52af26160b",
"main.dart.js_171.part.js": "2b0cbcb999412e5efc5165e086e5357f",
"main.dart.js_161.part.js": "603168ec3ae407c2a26c47af691d1eab",
"main.dart.js_54.part.js": "225ade0b7e8d6f45ffafb4dba287ffd3",
"main.dart.js_36.part.js": "ea05670225e7f6cdc456492c8fb732cb",
"main.dart.js_26.part.js": "f597f1d4c46568078211099fd751ce33",
"main.dart.js_113.part.js": "87aac2e38db09cdb51120bbaeb2acb24",
"main.dart.js_103.part.js": "0f52e910b7611d8a4749433039309d05",
"main.dart.js_135.part.js": "ed39bc4b730fff180cd8f1288f750b7a",
"main.dart.js_125.part.js": "c4413d651e33be4426eb2ff9880f9a90",
"main.dart.js_10.part.js": "10b0c284ca2ee5c3fc7cfae29c3cd52f",
"main.dart.js_72.part.js": "c96bc014655bff528b2720e67e761a98",
"main.dart.js_5.part.js": "1a0ae2910aa5932fd02a025bfbff2fd9",
"main.dart.js_62.part.js": "1440dfa020b8423c4b3fd5bde5cedfc0",
"main.dart.js_157.part.js": "c548612c97a929f593384b9e83fe8871",
"main.dart.js_96.part.js": "64a14fa386f1649acb56e84513976af4",
"main.dart.js_86.part.js": "b54ce4dd66b08c142b3403f5df9c272c",
"main.dart.js_168.part.js": "69b9e14c58d0977bcd31088faba338e9",
"main.dart.js_178.part.js": "28cda0493c0b91310d78764b7d6a768b",
"main.dart.js_83.part.js": "1512518eb2df68520ca3f95dc8dce74b",
"main.dart.js_15.part.js": "b5e0e0b0db9fb6a665742cc7d43cb8b4",
"main.dart.js_130.part.js": "019735c4ef6f2063ead7f4874c7f7844",
"main.dart.js_199.part.js": "0c8be0ce4e9a5b8f8af6c2a19dd5dbf1",
"main.dart.js_189.part.js": "dcc3aaf89cde3956233c2a02fcc07752",
"main.dart.js_142.part.js": "08f3af316e84ad5d07143518b3c8ba34",
"main.dart.js_77.part.js": "915cffdda9706301d31bfaa467a0ab06",
"favicon.png": "0bb582c2d7ecab35d61c52f918b3a57a",
"main.dart.js_51.part.js": "6bd0bc8f9f7946f148bb48913151ee14",
"main.dart.js_174.part.js": "4247cd50d994287528e9efe2c3b61a88",
"main.dart.js_106.part.js": "0670baeff84959c9beb6fd7d38c370ec",
"main.dart.js_116.part.js": "b502da40a6725a165bc9e4f7bc70cb96",
"main.dart.js_23.part.js": "bcd1e9ecb4558c84801ac7b02abcba77",
"main.dart.js_33.part.js": "a78954d780eb2dfe014e29596329542a",
"main.dart.js_180.part.js": "47512b3cdb9b6c1fefb1118021b10b7d",
"main.dart.js_190.part.js": "cd14064341d7568ca473dcfd8a0288fb",
"main.dart.js_40.part.js": "df579c9971aef8120da69f5af6f9fe91",
"main.dart.js_175.part.js": "a94670448442bd0a9f24efb7846bed11",
"main.dart.js_117.part.js": "04d375eead6f841b1ceeae9b3c987625",
"main.dart.js_107.part.js": "37c7856974720521e7c745aa3d80224f",
"main.dart.js_32.part.js": "3cb55f8b414fb16e81a0902617ca8a3a",
"main.dart.js_22.part.js": "abfbf2a6e7a948a19b43b3f70386c2cb",
"icons/app_icon.png": "a5795b892c8ca52b83f62a72cb9c56af",
"main.dart.js_191.part.js": "ba70d91b3d74e8903755a3b51f99bd7a",
"main.dart.js_181.part.js": "bdeb5fcc2e79c2b278e6fd065013bb1d",
"main.dart.js_128.part.js": "879bb7c3f5259b0d65bb8a4c105b63b7",
"main.dart.js_138.part.js": "662533a3bb705f83ec73e2b24e0b7b18",
"manifest.json": "ce255632f156b3abd6f7816522c4ff1a",
"main.dart.js_82.part.js": "e53b0b38a3057b63d6f5a36632436c3c",
"main.dart.js_49.part.js": "e188b4764c2db6b76c49217139882480",
"main.dart.js_59.part.js": "bdd34cb1185b28d15c320b6335be7f17",
"main.dart.js_188.part.js": "d34f434a7d554aea6affdccda1ce081e",
"main.dart.js_198.part.js": "16098f35ada488f6d36120e8ed8f52e4",
"main.dart.js_131.part.js": "8d4828e71b73481fa263cf71d6fe2365",
"main.dart.js_143.part.js": "01ba4b34ee3e25a2c65d95fc1dafade6",
"main.dart.js_76.part.js": "e2fc50304b37e4dcb3d5f7473c319f06",
"main.dart.js_1.part.js": "303ecddac233700c80b8d9a9a258da6f",
"main.dart.js_20.part.js": "2cfdc980cb12b2783df4902ab994314f",
"main.dart.js_30.part.js": "ab590601c91af002f86794c47e868faa",
"main.dart.js_105.part.js": "70dd94a71b73961d324d3c0d1d8b41e4",
"main.dart.js_115.part.js": "34eba20a6e78c3fe9fb5c618c74dd62f",
"main.dart.js_99.part.js": "48781c3aea650c61f29b1e5f5e649219",
"main.dart.js_89.part.js": "03d907c348f5b95920e803cd96ad5bc1",
"main.dart.js_177.part.js": "df9de0f8484b4f5ed5abff3915535895",
"main.dart.js_42.part.js": "c3b3762ee8ace75452306d936dd4c143",
"main.dart.js_52.part.js": "0de44f61ed4302a56c9d0567ee5f0e2d",
"main.dart.js_158.part.js": "131111b97b83f862edfb0b6df28b3f9c",
"main.dart.js_148.part.js": "0c1f712b2ae88136a355f7473ce648f5",
"main.dart.js_80.part.js": "d46204e4917b57b2d01dcc15fb85a4a8",
"main.dart.js_90.part.js": "b1568efcdc19feb7f60cd53ef5689bb9",
"main.dart.js_64.part.js": "9eb707b8e5d4d5af5b16b45202e761c4",
"main.dart.js_141.part.js": "7148b7096e05224ae79ff15d11ce1a0f",
"main.dart.js_133.part.js": "deea7bed9ad6764c9365fc1f8ba752ac",
"main.dart.js_16.part.js": "3a63fb70f6bcca719a879c47d69a05cd",
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
"main.dart.js_91.part.js": "d97187f56ae4bb393c35414536174205",
"main.dart.js_81.part.js": "34ced8e5606304610e9049bb8caa432a",
"main.dart.js_28.part.js": "49e4650c0cb995274de67d627ea9acd9",
"main.dart.js_75.part.js": "d1c0e358c0341a41a220e41e8582935c",
"main.dart.js_2.part.js": "30bad0438100cccbc4c24507c7590e34",
"main.dart.js_65.part.js": "f9cb82cc0bf5885b483e1d9cc6a15d74",
"main.dart.js_140.part.js": "3c14da479917ab455f10587fa53f197e",
"main.dart.js_122.part.js": "156c5a3110a734abb1a1c705f8dced9d",
"main.dart.js_17.part.js": "1a61686cc1fe99083bf4c49e45a7daaf",
"main.dart.js_88.part.js": "9ddfe13ada4d6a20e88f5d560efe378c",
"main.dart.js_98.part.js": "60eb65111608392da6734a7e0c0479a1",
"main.dart.js_31.part.js": "6feb4018e1024dfdce55d56a6d38bc39",
"main.dart.js_114.part.js": "6b6c114fa4dbb324aec66f95016cc118",
"main.dart.js_176.part.js": "214bccea9920fd9a05a5295c6457d234",
"main.dart.js_166.part.js": "50a739adde4eee923f263fe63e650949",
"main.dart.js_53.part.js": "f186becaaa8bf9bdef33f960fb44e6bc",
"main.dart.js_200.part.js": "9a2a68c461c5f55b60d25ba3e43bce2e",
"main.dart.js_43.part.js": "a55d5111e751051706515f66101b73f6",
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
"main.dart.js_159.part.js": "c0c8c46a6ad838ed61f19f59ae354334"};
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
