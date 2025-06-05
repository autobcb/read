'use strict';
const MANIFEST = 'flutter-app-manifest';
const TEMP = 'flutter-temp-cache';
const CACHE_NAME = 'flutter-app-cache';

const RESOURCES = {"main.dart.js_197.part.js": "e74f1931102cb31c2ccbb4e228829950",
"flutter_bootstrap.js": "10070042244999bb69eba4fe05457496",
"main.dart.js_69.part.js": "8076dae54aab00cc933a558cc0722425",
"main.dart.js_101.part.js": "ac8419d76a3b46e29befcd14c658449f",
"main.dart.js_111.part.js": "ac71ee1a60a4276b98ec810682f68945",
"main.dart.js_34.part.js": "bb371513087a7d03dd0f25323f1dfb53",
"main.dart.js_205.part.js": "cc87f8ee92fcc71116cfbf05dca5709c",
"main.dart.js_46.part.js": "b2a84157d9110a481d937372d971c2a8",
"main.dart.js_56.part.js": "269e6aec6c4b58fc697982b96aecae3c",
"main.dart.js_163.part.js": "81827a06695c75b9648fe4ebb6d9856e",
"main.dart.js_145.part.js": "d9b4b7a403e8c120f4aad049d903d123",
"main.dart.js_155.part.js": "f883a2918641ceb399a0ce8a38ff24b8",
"version.json": "2c9f05f34bef386e2cd95bb1d4005f91",
"main.dart.js_7.part.js": "25d40de482746e9902d4aaee9edce157",
"main.dart.js_60.part.js": "3966a5073c9493b3a2ee415a6bef2447",
"main.dart.js_70.part.js": "9c1c58b31e647694b4153ea8b949d333",
"main.dart.js_12.part.js": "9104f37ac5a40b2967428fc3d96b8f54",
"main.dart.js_127.part.js": "fed5e9171d34d0549d646651ea9fbeff",
"main.dart.js_137.part.js": "532bb5b4b15573e5646b86dc1db8ac11",
"main.dart.js_94.part.js": "567a431c19353aec36f575d928f9d758",
"main.dart.js_118.part.js": "ad1c5411111d12fb8de257c29e59cc53",
"main.dart.js_108.part.js": "755ba5fbce94353ccd1f12f93678e488",
"main.dart.js_154.part.js": "d930d6f9bc98bf9323605eaeb1f58c07",
"main.dart.js_6.part.js": "f26829b290476058544dcef08a43bd19",
"index.html": "f5c63c3c2bcf52234963567aca19a5a3",
"/": "f5c63c3c2bcf52234963567aca19a5a3",
"main.dart.js_136.part.js": "e37c975a5bafcf96abb1f37a568a8c61",
"main.dart.js_95.part.js": "cea37ad8f6f2bf9bafac860d94343fb0",
"main.dart.js_85.part.js": "481230dcba558aa060fd829cdd4a4ff5",
"main.dart.js_186.part.js": "6bfe3278ea2915017847284298b44afa",
"main.dart.js_68.part.js": "a2d35162d4bf3995819e99e473024d8f",
"main.dart.js_78.part.js": "84f608dffb2ae5a11832df66efaf62a7",
"main.dart.js_100.part.js": "ee7d521f2dec84490247578a87b86c9f",
"main.dart.js_35.part.js": "25f09d16576f435d8f7c1c4f2c8852b5",
"main.dart.js_25.part.js": "a5f053c87d4a9063b72e020906fb0ed3",
"main.dart.js_47.part.js": "3716cf3515d927a05f43f5030c71928f",
"main.dart.js_204.part.js": "5a03442021b377e6d8523050b48434b6",
"main.dart.js_172.part.js": "55f553fb58e8d36fda525695e38f0b29",
"main.dart.js_162.part.js": "041db018da621ada60412e3930ff7e91",
"main.dart.js_63.part.js": "b36a3b5cecaa19948d35110a3ca7fecd",
"main.dart.js_4.part.js": "fc7bf81a81e8f333e8173e39a5ea8c44",
"main.dart.js_73.part.js": "9437b152ae2850a710b19bf888ed2736",
"main.dart.js_156.part.js": "12b0393d38eac5f05c4d850bbc22511a",
"main.dart.js": "357844e6ad1229bcfda28880698412ca",
"main.dart.js_87.part.js": "c42bdf03f9a6723feb9259ae5e6b20ea",
"main.dart.js_97.part.js": "6c7a0594573f1c9032e3d6565e67cc8c",
"main.dart.js_179.part.js": "07022991cd5c6db899389c94dad9a336",
"main.dart.js_194.part.js": "b9e60585f399601b4f7717bdb68c31d0",
"main.dart.js_18.part.js": "a5e87247100d5a0bbf8101722574348d",
"main.dart.js_170.part.js": "234f3918245f63d1f30c3910a1f963a9",
"main.dart.js_45.part.js": "62cd098888c68b6fefa22d150579e647",
"main.dart.js_102.part.js": "75ee7bb34a17e611c2e7cbcb1ffe4167",
"main.dart.js_19.part.js": "b30a19c090d79a79fa24f9a0efb5840a",
"flutter.js": "f31737fb005cd3a3c6bd9355efd33061",
"main.dart.js_195.part.js": "4d53d56ddbb673fbfaa138037de2939c",
"main.dart.js_185.part.js": "685c4b4e87c518b55d6488e83440e594",
"main.dart.js_171.part.js": "78fca572cefd2c17f13137b2b41def0c",
"main.dart.js_54.part.js": "16444b489c0cea5fd36908ced30a18b0",
"main.dart.js_207.part.js": "facf69cc2f72f55892e92be154b68525",
"main.dart.js_44.part.js": "b2aa2523113dd07dbd0e0fe790919e17",
"main.dart.js_36.part.js": "595f6fb37b903dbba22352605edb8cd8",
"main.dart.js_26.part.js": "e5a3fc3e81575195a0c4d381dff440f4",
"main.dart.js_113.part.js": "8d0af02d5752facc9dac5f773efdfa94",
"main.dart.js_103.part.js": "c9a281d2fde42fdbff359d651395f4a2",
"main.dart.js_135.part.js": "7430e8a6affb3fc738eed634f0399102",
"main.dart.js_125.part.js": "de48b69d96aa45a1ea323b2cb314caf4",
"main.dart.js_72.part.js": "b5823b4d15c9b96d1cd020d448b7aedb",
"main.dart.js_5.part.js": "14b02bc57d011a1b851d40e379dc8213",
"main.dart.js_62.part.js": "84a69a987cb90864b4dcd7253ca38131",
"main.dart.js_157.part.js": "56d7c165de4b5570eb206753392135d7",
"main.dart.js_147.part.js": "5193bfddc18cfa4a897623030e8ba1bb",
"main.dart.js_96.part.js": "32e6ab3034bf5fb3170859ef43a6c0b4",
"main.dart.js_168.part.js": "97909b10aaea163cccf842593a2ace27",
"main.dart.js_178.part.js": "fcfb7119caddbba1118a1bf15550ef81",
"main.dart.js_83.part.js": "eb66bf35b558ddeb2960c08c694a0ede",
"main.dart.js_93.part.js": "40a192927fcd030536f29da48620f5b0",
"main.dart.js_48.part.js": "9177b29c5687c7b0212943d84674179a",
"main.dart.js_15.part.js": "14e4fd48a6ab3ab57913099eea039c94",
"main.dart.js_142.part.js": "8c9c88b63ba5e337771a8e795356f601",
"main.dart.js_152.part.js": "05c7767ecc4170807f7a146c71cefc25",
"main.dart.js_67.part.js": "460d4249697830b752412295ee8bed2c",
"main.dart.js_77.part.js": "9a8a217a6e19530d1cf5e9ddddae9d9b",
"main.dart.js_202.part.js": "f7b887344aa23ea78cabbc2211f47049",
"main.dart.js_41.part.js": "6ed3dd9a96fddb0b9efd1fa953f07830",
"main.dart.js_51.part.js": "146e7e345a1e12fbd2c71b4a985d33d8",
"main.dart.js_174.part.js": "18353a9074c039abdbb72986f0c126da",
"main.dart.js_106.part.js": "bd0991848145e952690ed499dc31f9b2",
"main.dart.js_23.part.js": "f4f37413f962a90d38992ffb872d6f6e",
"main.dart.js_33.part.js": "309e762d303cb488d90681536646d5dd",
"main.dart.js_129.part.js": "8551c772d9158c204c64c70aa16dff69",
"main.dart.js_180.part.js": "c9dd1096710266b9286efc4c99721d31",
"main.dart.js_190.part.js": "5fab085061a2d9f712df56b479a521ac",
"main.dart.js_50.part.js": "b512a7b43255988faf043233d7396783",
"main.dart.js_40.part.js": "e3b20f2f8c147f5b39a5fe014ec4d90b",
"main.dart.js_203.part.js": "c2db7957c60a3c32f12284f6baa6b95f",
"main.dart.js_165.part.js": "6c84cc041162c03c13acca9a6b260d44",
"main.dart.js_107.part.js": "7cf09b499c11c2ec130d047f682cbc2a",
"main.dart.js_22.part.js": "3e74be2e634ab8f814cda5d736cb7650",
"icons/Icon-192.png": "d41d8cd98f00b204e9800998ecf8427e",
"icons/Icon-maskable-192.png": "d41d8cd98f00b204e9800998ecf8427e",
"icons/Icon-maskable-512.png": "d41d8cd98f00b204e9800998ecf8427e",
"icons/Icon-512.png": "d41d8cd98f00b204e9800998ecf8427e",
"main.dart.js_191.part.js": "5adbca226251be8fa439f9b72d18cc58",
"main.dart.js_128.part.js": "9af433e276d95c6ba012cd9587bb9b00",
"main.dart.js_138.part.js": "fa7f3597a57822d3ae394daabd43185c",
"manifest.json": "ce255632f156b3abd6f7816522c4ff1a",
"main.dart.js_92.part.js": "bcad49cc4f176139745a9d7a78a7505a",
"main.dart.js_82.part.js": "8d2f45c5ccdad7b42b37fc1af855f72a",
"main.dart.js_49.part.js": "dd91ed27552dd0af326853f314b3a43f",
"main.dart.js_59.part.js": "601006969180cda52ce68b4665976bcd",
"main.dart.js_188.part.js": "01b7af5c8bc47af6007bf744760c9ff6",
"main.dart.js_198.part.js": "f60c2f3c953a9f9bf417f0087ba81d38",
"main.dart.js_121.part.js": "7e0c7c72959cda07d9abefe4b72522ad",
"main.dart.js_76.part.js": "ad7889da010a768d29f7fbf547f18203",
"main.dart.js_1.part.js": "a7c8ad43629e9290c7b144ecf87fe887",
"main.dart.js_20.part.js": "2bfd977f32d7ee3d95caeb0f4c589c32",
"main.dart.js_105.part.js": "53d7355939eb7201f7fb1421fe1e7c3f",
"main.dart.js_115.part.js": "1334a32bf5c7dba6eaa320a437d5aa32",
"main.dart.js_99.part.js": "a6b273e31aa236035be254893e021155",
"main.dart.js_89.part.js": "8abb931ab89a71457f525bc9420077f2",
"main.dart.js_167.part.js": "d4e99e92adda1d58a475ced87a6e411b",
"main.dart.js_42.part.js": "21818ce3e72617117fec1cd111f1856b",
"main.dart.js_201.part.js": "e9dff2105670e1a53c7b98119b7879fe",
"main.dart.js_52.part.js": "9dc834df62af230f724c8e0c3e681d30",
"main.dart.js_183.part.js": "71c98fa9d59011d685b16ae0653c6864",
"main.dart.js_148.part.js": "591f6c1a4b71d1695a97291bcdfc4b93",
"main.dart.js_208.part.js": "aaf2cb5e477d16345713b6d23a257642",
"main.dart.js_39.part.js": "223922dc5c6d79cfb4d616e331b316c6",
"main.dart.js_29.part.js": "7834a99c3d4072b051b0578fa4cf28c0",
"main.dart.js_64.part.js": "ce70c856735599cfcb1ea24606340bb2",
"main.dart.js_3.part.js": "78857de9d597595a1ff5f4d3253d6ba0",
"main.dart.js_74.part.js": "0a254a27fcd78608a2dd55a4a6b6341c",
"main.dart.js_151.part.js": "c59003603baebf23e16972f4640a4833",
"main.dart.js_133.part.js": "6794262bf6b884f4e229d6598f4c9650",
"assets/AssetManifest.json": "3eb09be2ac2ecbd14a6ea6229a1da724",
"assets/NOTICES": "81dc42fde28d7f63a33a960016dde587",
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
"assets/fonts/MaterialIcons-Regular.otf": "784f835db26891bc65ec57063fa95fc8",
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
"main.dart.js_209.part.js": "99ac39f6b850f1dff1c64294deeddd83",
"main.dart.js_81.part.js": "9f6ac6d79a6d38241f4c3f647a80ee4f",
"main.dart.js_28.part.js": "1043116d483a77933dd84715e3aa5914",
"main.dart.js_65.part.js": "c3946ab0212b3dab825ab9b5fce060ec",
"main.dart.js_150.part.js": "9e2447407a8d7fcd14d7635929e721f2",
"main.dart.js_140.part.js": "1330fb156ed15ef9f64b606efdfa40eb",
"main.dart.js_132.part.js": "75ae055688be737cc7133c1e4a324510",
"main.dart.js_88.part.js": "c6adf57148378ea8c7e66977b8be089a",
"main.dart.js_98.part.js": "83b2d98bbbf7ebee84fe14516d76eb71",
"main.dart.js_31.part.js": "d80fcb200b5a353c77bec2f42a8cb07e",
"main.dart.js_21.part.js": "b4f85f0be3ca34d309e07534693a0942",
"main.dart.js_104.part.js": "a388a164841c7e7d48c5fe54c2da9a85",
"main.dart.js_176.part.js": "920f2e03ec7bbaf696cbb0c0108652ac",
"main.dart.js_166.part.js": "498c1f96d030113a6430cc4506b456d3",
"main.dart.js_53.part.js": "8906052a51e67fee1ee6882ffacf3148",
"main.dart.js_200.part.js": "a88fa4aa03bc6f14469fa0184977df7e",
"main.dart.js_192.part.js": "d2bcb4b71de2feb71e5238baa4b001fe",
"main.dart.js_182.part.js": "4dbe6f53e2295c93fe0a6fa4f366557f",
"canvaskit/skwasm.js": "9fa2ffe90a40d062dd2343c7b84caf01",
"canvaskit/skwasm.js.symbols": "262f4827a1317abb59d71d6c587a93e2",
"canvaskit/canvaskit.js.symbols": "48c83a2ce573d9692e8d970e288d75f7",
"canvaskit/skwasm.wasm": "9f0c0c02b82a910d12ce0543ec130e60",
"canvaskit/chromium/canvaskit.js.symbols": "a012ed99ccba193cf96bb2643003f6fc",
"canvaskit/chromium/canvaskit.js": "87325e67bf77a9b483250e1fb1b54677",
"canvaskit/chromium/canvaskit.wasm": "b1ac05b29c127d86df4bcfbf50dd902a",
"canvaskit/canvaskit.js": "5fda3f1af7d6433d53b24083e2219fa0",
"canvaskit/canvaskit.wasm": "1f237a213d7370cf95f443d896176460",
"canvaskit/skwasm.worker.js": "bfb704a6c714a75da9ef320991e88b03",
"main.dart.js_149.part.js": "bac22242a8f746886d310ca743fbd7ed"};
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
