'use strict';
const MANIFEST = 'flutter-app-manifest';
const TEMP = 'flutter-temp-cache';
const CACHE_NAME = 'flutter-app-cache';

const RESOURCES = {"main.dart.js_187.part.js": "93d2baeffe3083559c7180ed8bd68d20",
"main.dart.js_197.part.js": "8210e78a2bba1721c6dfd5ecb92595c6",
"flutter_bootstrap.js": "0f1f1192550bf266d0194de60d976908",
"main.dart.js_79.part.js": "b6ea756e829299415fd860028b450e0f",
"main.dart.js_69.part.js": "6708e6c057816457ac3f2dd0e4c6e3bd",
"main.dart.js_101.part.js": "9a7b586311897f4d5f708443a48411aa",
"main.dart.js_111.part.js": "114ef4147ad14f2921ee9d4f79a0d04e",
"main.dart.js_34.part.js": "a8575fccd275ed68c86215b42975c0d7",
"main.dart.js_46.part.js": "cbe0645eb4ea063cb161a74682ca7208",
"main.dart.js_56.part.js": "b2dfd00e8fd4fd810233f5cfcbd6fef6",
"main.dart.js_163.part.js": "2a5b11f70b7ccb629c61203df44cf8a6",
"main.dart.js_173.part.js": "df7fa7d23dbce21a6db86a729ca6d195",
"main.dart.js_145.part.js": "9b1c7313153fe4a4548fe7cd20291e69",
"main.dart.js_155.part.js": "d2b293b6d5631a9b529cc3dba0be3988",
"version.json": "de024844ca9a1ef2b9a1ec20bbc66c8f",
"main.dart.js_7.part.js": "39deb98f43c574a9b4542189523c5700",
"main.dart.js_60.part.js": "82954a3be2d485f0f94b2bf87a991278",
"main.dart.js_12.part.js": "b2ce530c8515395d84ebfdba41ec4dae",
"main.dart.js_127.part.js": "ce3a0364bc8caf73c1bb5d4aa2608f69",
"main.dart.js_137.part.js": "0ed47324bb2dd68c214fa20b2a74e912",
"main.dart.js_84.part.js": "40ef06b295f6ec44531d3238c1958cb1",
"main.dart.js_94.part.js": "50e62d6fd1e775ffcc100ddeaedf8f0a",
"main.dart.js_118.part.js": "3fd08d509455f6303a90d9474707a8c3",
"main.dart.js_108.part.js": "9064527d728a1e1f7cc49bc3f2c7a762",
"main.dart.js_144.part.js": "2a872ead59dc96eddc4e29f86b295073",
"main.dart.js_61.part.js": "849d9568c84e4a950251a650c6e0da89",
"main.dart.js_6.part.js": "180a7544d2c17b74a2935836545aee9c",
"index.html": "f0fb83eef421024a56d50022cd18118f",
"/": "f0fb83eef421024a56d50022cd18118f",
"main.dart.js_136.part.js": "cae88202dfa3901da2109a1613a71703",
"main.dart.js_126.part.js": "1178ffc6cfce8aa4af8542de18d7894d",
"main.dart.js_95.part.js": "74175520b34968c9af9c63ebb705ded5",
"main.dart.js_85.part.js": "180ba811646dc846cb4915e6c59185c6",
"main.dart.js_196.part.js": "c0d07904fbd78b62981c9b3c31b07be7",
"main.dart.js_186.part.js": "ed14decc862835f46e9c54856654c561",
"main.dart.js_68.part.js": "1a0527b04afae421ea382d2930528a92",
"main.dart.js_78.part.js": "b63fe3623640bc85859a6e40324532e6",
"main.dart.js_35.part.js": "e51d7222d1be9a1ce444003921ac4b0d",
"main.dart.js_25.part.js": "6a15f192387073c4cd7fbffda9c81083",
"main.dart.js_47.part.js": "172a5b3fd0d95fdcd11b3f4e219451aa",
"main.dart.js_162.part.js": "7482902aa6c8c6e0707f3842eeb15a47",
"main.dart.js_124.part.js": "c9370c2068d1f88fd1bbe13974727476",
"main.dart.js_63.part.js": "3b5f9dc6f5db07632fed0763bb4ae1b0",
"main.dart.js_4.part.js": "b93ed7cee87870a5adee145d46db96da",
"main.dart.js_73.part.js": "c8621ea2eb3de1fd7a84143998e6c4ea",
"main.dart.js_146.part.js": "5d63f0666c1c94948eeec721e54f0456",
"main.dart.js_156.part.js": "2325c0a68e285ceecc5da8aef0f6a693",
"main.dart.js": "e243696484b39a93ece9b547566963e4",
"main.dart.js_87.part.js": "67aa7a5cc66f5895ba0f662fe9f81b1f",
"main.dart.js_97.part.js": "a5cf91c01ed3621d8ae173309d231ea8",
"main.dart.js_179.part.js": "89db49e74790729af299100f325b0182",
"main.dart.js_169.part.js": "a81f9b28a1ed7b15e9e422fb2540e9fc",
"main.dart.js_194.part.js": "ef35b41709573c33effc5a7de6d7c5cc",
"main.dart.js_18.part.js": "ab77c27fc1b739564cb37961c4be969a",
"main.dart.js_160.part.js": "7072aa6a9df88d2d1f77c080d25f6e22",
"main.dart.js_170.part.js": "512d9ff04724bf826daee4f3dd512117",
"main.dart.js_45.part.js": "ba31fdafebe2f0afc5ea18fb04690680",
"main.dart.js_55.part.js": "5e9f1ac3ebf0d6d67e8f2127fb5a2da6",
"main.dart.js_19.part.js": "dc12944abca6710417b3d0b1068a305b",
"flutter.js": "f31737fb005cd3a3c6bd9355efd33061",
"main.dart.js_195.part.js": "d34b871a97cc347580dcd50900b09d55",
"main.dart.js_171.part.js": "16b153b8fee91bf4b8858ab195707520",
"main.dart.js_54.part.js": "f8f4f863bd2fb60f4e3b161a8a4fea2c",
"main.dart.js_44.part.js": "8da7f2a4fa7b384ab9a2c7a5282f22be",
"main.dart.js_36.part.js": "0fe4101ce1e6192a36099129c2256db6",
"main.dart.js_26.part.js": "2d3297b4dcb6a91b3810d72b2dbe0cf5",
"main.dart.js_103.part.js": "6b23bef320fe34922f7805acd7c4d697",
"main.dart.js_135.part.js": "89399ebee4a692068e9a35a1c2552f9f",
"main.dart.js_125.part.js": "1b9298467f454023c0ce354d9cde1ded",
"main.dart.js_72.part.js": "90c3ca13f2470853bf66d6dad13437b1",
"main.dart.js_5.part.js": "7548fcb49ad10378c2cd8cea193b7f0f",
"main.dart.js_157.part.js": "cfcc69f54454b7b75a866e17d3355167",
"main.dart.js_147.part.js": "7ce060469f9f74ebeeaab92c2af802e8",
"main.dart.js_96.part.js": "f5fc9b4384985371564ea921d5e0da46",
"main.dart.js_86.part.js": "798626113e2a4972752f5555a0657b0c",
"main.dart.js_83.part.js": "0f9faedf95b346be9ae0cde85f72d1b1",
"main.dart.js_93.part.js": "4a822d4e1d5feb42e25f144b39463e0b",
"main.dart.js_58.part.js": "d14f9c9ab29c36e2efdb918a33fdae50",
"main.dart.js_48.part.js": "64d986b0a6f9c1e5909c97241481dd25",
"main.dart.js_15.part.js": "5a1188073fa0f7af947f1ee946254087",
"main.dart.js_199.part.js": "d7e1bc02da89cfb9717cb840b6d2d868",
"main.dart.js_189.part.js": "467798dbaf099457cf3b669566f0bf55",
"main.dart.js_142.part.js": "732ab6b4f15a163a4cf6002280112c93",
"main.dart.js_67.part.js": "b5b1d8b26e60ca7c6efa9b50568539f0",
"main.dart.js_77.part.js": "594662d7781a724fd877b4e13e9d745e",
"main.dart.js_202.part.js": "328492dd65c5c1cf211d608c6c52b7f3",
"main.dart.js_41.part.js": "3054b0eaf85da9951b08322e12377a49",
"main.dart.js_51.part.js": "fd5ee2b7145f86b25336da628bf9cb69",
"main.dart.js_174.part.js": "cc98abdbf97188af7f332fbc1faa1284",
"main.dart.js_116.part.js": "0d1cb6e60607f456dede482a441cfec8",
"main.dart.js_23.part.js": "f9ed4eeb8654debe20223033ee2c0347",
"main.dart.js_33.part.js": "fea610dcd0c07ed7b37292e7cd540fb3",
"main.dart.js_139.part.js": "2a92230b7eb839a7be2860b85d2df5ef",
"main.dart.js_129.part.js": "467da7e5753114e8833810b4c266eba8",
"main.dart.js_190.part.js": "7d71845ab45f16db9dd98a080c58e834",
"main.dart.js_50.part.js": "3503d8b7910269d5c3b71d575740689d",
"main.dart.js_40.part.js": "c6dc516de83fd9b8bdca3b61d88e805e",
"main.dart.js_165.part.js": "114fa35007d04a2967f1153dbbee8b9d",
"main.dart.js_117.part.js": "8b20aab232105f5e3dbbb33fa244d6fb",
"main.dart.js_22.part.js": "ff17236063b3357472b3fb4065e2cf96",
"icons/app_icon.png": "9f8a277dbc30308e6ea0de6c057c0a55",
"main.dart.js_138.part.js": "2e2385342348e3cca94d55306d95899e",
"manifest.json": "ce255632f156b3abd6f7816522c4ff1a",
"main.dart.js_92.part.js": "cd620054a0165c698a4a1daa683f1fec",
"main.dart.js_82.part.js": "211581f95ddc8e8354969da3d76b63d6",
"main.dart.js_49.part.js": "44802620622c3d4c79da13c0b99bf2bf",
"main.dart.js_59.part.js": "1cb86eac2044082123976d5adf4f829f",
"main.dart.js_131.part.js": "025652b0308c112a6de1a48760769c65",
"main.dart.js_121.part.js": "74e2b6d0e4519caf82c97fee2440d944",
"main.dart.js_153.part.js": "f9bf4859a4d8d0bd67c4edaecc3afbf9",
"main.dart.js_76.part.js": "a8dd792c99f43eab3f3ec2a3324eb96e",
"main.dart.js_1.part.js": "483c1b9589a6107bafdbc9e21d738556",
"main.dart.js_20.part.js": "11cc2992d0f0f34b3fd5623197a70d43",
"main.dart.js_105.part.js": "d16de16a468d45e9cdcc846d93d0e818",
"main.dart.js_89.part.js": "b4a1c18970072d077cc0f0da38a17852",
"main.dart.js_167.part.js": "11e0e22c5e0e77cab2f082c15aa72786",
"main.dart.js_177.part.js": "fe9286e6ea8fd003e58df54ec4a401db",
"main.dart.js_42.part.js": "70e282756f5d0be48b3e699b75a1c7ee",
"main.dart.js_201.part.js": "4f72c83c2265ad82aa36956621373d41",
"main.dart.js_52.part.js": "38449678ef0c0d87433e137ec49be754",
"main.dart.js_183.part.js": "f16105bde23ff29c97356f057c0f5d88",
"main.dart.js_193.part.js": "e5b80f760c5f6ae5e34dae94747d590f",
"main.dart.js_158.part.js": "4eb7bf41b081cdb195a3d7f311c71e2c",
"main.dart.js_39.part.js": "9e782a3a88e40f2284c307ab86c9c844",
"main.dart.js_29.part.js": "39b8b0c52e320c7d4efee01824ffe5ce",
"main.dart.js_90.part.js": "23a1a771f485c5fa5e646af57d88038d",
"main.dart.js_64.part.js": "5daa8508948da84d3f38632f60a4b50d",
"main.dart.js_3.part.js": "c610e2d535d8f563e4d580adea9ce11b",
"main.dart.js_141.part.js": "bbd315ac8d7743afd27683257dc37a88",
"main.dart.js_151.part.js": "8d13cbf5b0193f4b89f7cc96c2f9727d",
"main.dart.js_133.part.js": "bd6dfc6e5794e5db4f1a461dea26b9fe",
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
"main.dart.js_91.part.js": "31bbb4683fca43167cd28757fca37680",
"main.dart.js_28.part.js": "508d7be484537bb19cd2fac09ffa9c0f",
"main.dart.js_75.part.js": "0288eae10d1a4731027730e126739b89",
"main.dart.js_65.part.js": "fe05562fea64af183bc8126f59326f3f",
"main.dart.js_122.part.js": "d3d5cf00749dd1b2715cf1ebc849fa01",
"main.dart.js_88.part.js": "14e21b16fe62892133121509390bb9bb",
"main.dart.js_98.part.js": "adbf094799bc38d14f852cbe1f37ef78",
"main.dart.js_31.part.js": "af7fed0df02c9b919db78f1b63166eb1",
"main.dart.js_21.part.js": "8d25cf8134f34fdc74418e9de34b836b",
"main.dart.js_114.part.js": "0e237219ae4e85d992328b58fe37dbf8",
"main.dart.js_176.part.js": "5890019ad00c636bc534e251d46ee6f9",
"main.dart.js_200.part.js": "1901f5a4a7a40d3199a6dfce6f0f3857",
"main.dart.js_192.part.js": "749ed3fdc27584830eed6e444620248d",
"main.dart.js_182.part.js": "b8833acee857670cfdae420fafea9382",
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
