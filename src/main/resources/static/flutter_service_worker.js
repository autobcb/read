'use strict';
const MANIFEST = 'flutter-app-manifest';
const TEMP = 'flutter-temp-cache';
const CACHE_NAME = 'flutter-app-cache';

const RESOURCES = {"main.dart.js_187.part.js": "7ed3c65a76e0c997cac216d72ecd5f1f",
"main.dart.js_197.part.js": "afe8f98c066a5b797bb508bde0796b49",
"flutter_bootstrap.js": "da2d5e0fb282fa2d4f30c2410f7b915d",
"main.dart.js_79.part.js": "f1d0597f2c538420b973eb53421173e1",
"main.dart.js_69.part.js": "04b753f621d0db040242baa01f1cbf8d",
"main.dart.js_101.part.js": "13491dee4dc4dcc10e893bba4f54d070",
"main.dart.js_24.part.js": "fbb1e09e489b991ea3d883ceb3f17acb",
"main.dart.js_34.part.js": "6e349c279811fac7a64611055b611a8a",
"main.dart.js_46.part.js": "3fa96e0239e539d97ea2d93b05c546d1",
"main.dart.js_56.part.js": "c5d7f4dba76e19909fb17b564ae9f3cd",
"main.dart.js_163.part.js": "66d4b8b60f792f36731ca4ed841d8535",
"main.dart.js_173.part.js": "e5c4ba9c0c5d8632a9ab9c5034b96509",
"main.dart.js_145.part.js": "74f21cf2f5da3af0eb62284645071d3d",
"main.dart.js_155.part.js": "d56a5e669fe21191f51a4975b855ec15",
"version.json": "7e6e5d2cb9cb7c829dd5332f5c5f548a",
"main.dart.js_7.part.js": "852904a003bd18560cf6443955dd9e09",
"main.dart.js_60.part.js": "f0e7c29afa754baef73fa8a356b43798",
"main.dart.js_70.part.js": "37533f1a16a8c91de97e9782f5fb3601",
"main.dart.js_127.part.js": "a9517329b3aa24faf88b673b4b0f7363",
"main.dart.js_84.part.js": "930571d2897e28bee0376208078546fa",
"main.dart.js_94.part.js": "cf22e285a3f621af669773acd37c76c1",
"main.dart.js_118.part.js": "d10dd6e07f7ef9f87d6d6fd7991c2906",
"main.dart.js_154.part.js": "26467363434354ffb436bacb020e4628",
"main.dart.js_144.part.js": "5a7e2ce08550eec9d1d96d147664af6c",
"main.dart.js_71.part.js": "875ff84a1279101a5680a1abf59e6c50",
"main.dart.js_61.part.js": "ae6fcdad3ab530b1ccbda281287b9ca7",
"main.dart.js_6.part.js": "6e1b9eca6c64624c6032e82f518fb93a",
"index.html": "f0fb83eef421024a56d50022cd18118f",
"/": "f0fb83eef421024a56d50022cd18118f",
"main.dart.js_126.part.js": "4733b76e4b3a061f3d6c488b3d59df8a",
"main.dart.js_109.part.js": "245734a62c91df9e1ec6a2ef29d2064e",
"main.dart.js_119.part.js": "29ea1734cf7301a4b4fe3936e05d4978",
"main.dart.js_95.part.js": "3524023f97313580f8f56d525f83e55b",
"main.dart.js_85.part.js": "ae800c8bff75d76e3fbd95d5c3963cf6",
"main.dart.js_196.part.js": "7eee0857e4e0ed65055951210b171bee",
"main.dart.js_186.part.js": "b399a4114bc86ebef32a49c2f5cea365",
"main.dart.js_68.part.js": "bd46af197fe7b4706317bb7fbf62ee68",
"main.dart.js_78.part.js": "4aab2cd24ec3a13dd241a6d95eebdea6",
"main.dart.js_110.part.js": "4385d9335df2b41e431c5e749954832c",
"main.dart.js_100.part.js": "5ec5057da72a8e2180a98ec5d2f8edc6",
"main.dart.js_25.part.js": "59152b714099e8271d50b02ba4dc9789",
"main.dart.js_57.part.js": "e83dc85e887993dcd34acf8f52b6660a",
"main.dart.js_47.part.js": "341ccea41b5b5ec8981cb91ce973e22d",
"main.dart.js_172.part.js": "379d010322d0f092b3d8110e8d54b5a9",
"main.dart.js_162.part.js": "b6fb9a697e9be98451803825a37137f5",
"main.dart.js_124.part.js": "98ac6362799d8256842f48c975736683",
"main.dart.js_134.part.js": "76ac0d20556ef9468fb3e4eb1d275d08",
"main.dart.js_11.part.js": "75b38e6d6a9ee56ffcc4f6f292556999",
"main.dart.js_63.part.js": "56da44454df0a34ebaea60171dbd202e",
"main.dart.js_4.part.js": "261f403a61e7569c89d5df7d0570626f",
"main.dart.js_73.part.js": "91f0d19cf88b42ce180f83098027187e",
"main.dart.js_146.part.js": "cfd311b86ba9e30314eb6a9e996bfd2f",
"main.dart.js_156.part.js": "34ab0a15f2ba54d5f867196efa9e55f5",
"main.dart.js": "1f9a6bdb873ec2a22aef380224da1871",
"main.dart.js_87.part.js": "48033a3235bd99edaf46e05ab0c4c014",
"main.dart.js_97.part.js": "c2718e3147bce2709a77a0b860434b0d",
"main.dart.js_179.part.js": "c2e73392a3f4b5a1d7e568b1ee2a41f4",
"main.dart.js_169.part.js": "b44b048830cdf1c054dbd0a460eb2e44",
"main.dart.js_184.part.js": "8764b495d8308fc175562a84d59b0505",
"main.dart.js_194.part.js": "3888698a2b305011b8e6adae00e5ff2d",
"main.dart.js_18.part.js": "ca7c8ff7e9c0ab7ab618b6a04c19f342",
"main.dart.js_160.part.js": "28073a0f23ea046128da5da98b6b971c",
"main.dart.js_170.part.js": "dd17e539b81876d8504c5ed41d019921",
"main.dart.js_45.part.js": "f219a4bad2bc7d552a19206abd5f6c35",
"main.dart.js_55.part.js": "c72bbffaa0c0fe988bcb7bd259dcb481",
"main.dart.js_27.part.js": "c6355809b2d549e907200718339b4989",
"main.dart.js_37.part.js": "769f5af6938d9c1b4505a87fa22163b8",
"main.dart.js_102.part.js": "0a9f2e64e92b6158d4c949f92cdabcd7",
"main.dart.js_112.part.js": "002c03d8f08bacfd385956f01655b59c",
"main.dart.js_19.part.js": "fb3406e6e2cccc9944d2faff4f666e8a",
"flutter.js": "83d881c1dbb6d6bcd6b42e274605b69c",
"main.dart.js_195.part.js": "5a48629a3b9b2538ed8e9d55fd1a1410",
"main.dart.js_185.part.js": "d85a8dec4f80c36fbd3f38ecd207e120",
"main.dart.js_171.part.js": "7c7e5aea15ed2c5572dd55a102e4cbc3",
"main.dart.js_161.part.js": "036143a75c8e1e134733d4c859299a1d",
"main.dart.js_54.part.js": "a81b019f9f179cc075c59e2e49cbf292",
"main.dart.js_44.part.js": "c7cc903bfc7643338f57c484ef50609b",
"main.dart.js_36.part.js": "d2469a6325187d9baaa48340aa38d80f",
"main.dart.js_26.part.js": "a96fbdbe774ae18e4ea38c4d3cc05a66",
"main.dart.js_113.part.js": "32db361930c1a4b025843ddbc7872db0",
"main.dart.js_103.part.js": "8afd94f59c06301db1554fda1d94bfe2",
"main.dart.js_135.part.js": "ae18a7bec5556853d2d251da9d5673f6",
"main.dart.js_72.part.js": "97c06299dd6c947c7d4457b18a6432ba",
"main.dart.js_5.part.js": "a3d1305967a8ce0cc7ccd084b2c17ca9",
"main.dart.js_62.part.js": "4188f6130252bea2984874526ca236f0",
"main.dart.js_157.part.js": "b93d930e014515f773a4dbb5c2d44529",
"main.dart.js_96.part.js": "0e6dc3e016f5a200150f149840a62338",
"main.dart.js_86.part.js": "faa1fae208898835a483f4715adbffa4",
"main.dart.js_178.part.js": "7c8e94f0ace2df46d073283395974bef",
"main.dart.js_83.part.js": "a737e1a41d22fe393a0c8de889b93d53",
"main.dart.js_93.part.js": "c26d2796d4f570bfd388f19dc82b1f8c",
"main.dart.js_58.part.js": "0acf7085c97ac71e1e543e247908b17a",
"main.dart.js_48.part.js": "e795a6aaeafea0aeb3b7137226129b39",
"main.dart.js_120.part.js": "d1629921dc4ac5513caf808cbd50abf7",
"main.dart.js_130.part.js": "3ed767f32f3df86f0768e86986117110",
"main.dart.js_199.part.js": "20ba00c71373e224c886320a6964f06e",
"main.dart.js_189.part.js": "482b29468e4989034af6cc5f1aedab80",
"main.dart.js_142.part.js": "cb8014833e851e8381e0dcef0c17754c",
"main.dart.js_152.part.js": "4a0419c48d99173f3c09d0066252224f",
"main.dart.js_67.part.js": "f83647c19b5443eee16ccd1ce9608500",
"main.dart.js_202.part.js": "e8fb1ff0ad9e92ed582a6fda6b6a4616",
"main.dart.js_41.part.js": "2edc5c7e16c313b4443d0f72400a7d98",
"main.dart.js_51.part.js": "a71e77fa6861d94b11798d25470d065a",
"main.dart.js_164.part.js": "250abdf06badf05dc34954e4f6b5288c",
"main.dart.js_116.part.js": "1569c6d023bd43893db419fe0acc72e9",
"main.dart.js_23.part.js": "423245e84a661b3fc6c075721c5c7323",
"main.dart.js_33.part.js": "a583f1d0c2e182bacd7467f64713a405",
"main.dart.js_9.part.js": "a0bc2c9266603d2e0f28379cbbdec1c4",
"main.dart.js_139.part.js": "090e64e47afff3ee9090d72853f564d8",
"main.dart.js_129.part.js": "e56fe5cd356c8d40d93e0d10237899b2",
"main.dart.js_180.part.js": "42eb9072559156e88b5aff9891478e92",
"main.dart.js_190.part.js": "79f452ccccf16630c786a2772b76bcdc",
"main.dart.js_50.part.js": "1758b6f4484c8990f996d70c9a9f77c7",
"main.dart.js_40.part.js": "a5610f036e2277606172b51fbe49066e",
"main.dart.js_175.part.js": "56aa6ff8468998cd1db0ac71ab00842a",
"main.dart.js_165.part.js": "8ac9c87288d972855e421c6ab3045d31",
"main.dart.js_117.part.js": "b66fb951705aa7abf2cac396e331198b",
"main.dart.js_107.part.js": "725e16c9de82a2ea5ad23218398016f4",
"main.dart.js_32.part.js": "4f4b4f16cb472ea3c9248328ae03dfe1",
"main.dart.js_22.part.js": "723f0a9ed4b9b7175104c3d7f95f7041",
"icons/app_icon.png": "9f8a277dbc30308e6ea0de6c057c0a55",
"main.dart.js_128.part.js": "7e93fdc83714388229da8908eb8e7269",
"main.dart.js_138.part.js": "d1a03ab75a496fefe150a40763ba1219",
"manifest.json": "ce255632f156b3abd6f7816522c4ff1a",
"main.dart.js_92.part.js": "0ed3efa55af41bb18fc21a9c52eee45c",
"main.dart.js_82.part.js": "8e5f6efec7cc83f2d4b3c007718d1b8f",
"main.dart.js_49.part.js": "36886b20623ea1b35171d2065faa3fba",
"main.dart.js_14.part.js": "23a561d2279c0f7729e7ba83d51bd916",
"main.dart.js_121.part.js": "558697d27f82c5786de3b39d867f56fd",
"main.dart.js_143.part.js": "1f9fed5f6f870bc30ca6fca44ab85722",
"main.dart.js_76.part.js": "8891419572ee738146ccf21170681b89",
"main.dart.js_66.part.js": "4a3e417415f751b1328d478ca262f608",
"main.dart.js_1.part.js": "aab0c0cef7f08597162f617b30f69a20",
"main.dart.js_20.part.js": "0c7294040d55ed09aa2a9b5c50503447",
"main.dart.js_105.part.js": "31d72fac31f15f9d2fbfb63e4d7eefc9",
"main.dart.js_115.part.js": "c49a98c405bf29411aea7b605dfc6afd",
"main.dart.js_99.part.js": "c29d5341db8d41dc86fbf5a439ac11ed",
"main.dart.js_89.part.js": "14510b897dd7d3ee293f0c96d2465507",
"main.dart.js_167.part.js": "dc3a4281842cdd2c0d1250e15358c6f5",
"main.dart.js_177.part.js": "1ac77c81afb3de0512b78ca82acfd334",
"main.dart.js_201.part.js": "bcd45d2ff5a489f6f31172d8e41ab4ef",
"main.dart.js_52.part.js": "83fd7375c25612536f1cbe4a6c40e5a2",
"main.dart.js_183.part.js": "6aa1fa64b7d7dcd0e7c105d7a41d598d",
"main.dart.js_193.part.js": "f90faf025dd85f596f9ed75b745aa8d9",
"main.dart.js_158.part.js": "d4524819218e6e3416ac8c04c14d13b2",
"main.dart.js_148.part.js": "206d1f3a5e16c3e26076b5909a6042a9",
"main.dart.js_39.part.js": "7965502b3c8af0f97deb5732899480f7",
"main.dart.js_29.part.js": "4653f4a4240cc7e42a1ad8e232b5080e",
"main.dart.js_80.part.js": "903e8bc2ab0da4ed01e9ced6e01c6bd4",
"main.dart.js_90.part.js": "cbadb2ef0850c33605bfe0a179a4ca2a",
"main.dart.js_64.part.js": "c6d977b1c6dea4b5f74cb96ef1a3d9a1",
"main.dart.js_3.part.js": "dbb949ef2c6c8d328cb16ae9d154128b",
"main.dart.js_74.part.js": "b8f2551a1814e60b85a179600de71874",
"main.dart.js_141.part.js": "78239c29fc1aa6f5139240f284bd89d0",
"main.dart.js_123.part.js": "754c3b0b1ee48f68d1ede6f1290ad63f",
"main.dart.js_133.part.js": "94e060f5bc17253619cb642a80e74d9b",
"main.dart.js_16.part.js": "524b10c7dc1d6d62fd28404abbbfd5d1",
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
"main.dart.js_91.part.js": "0c88b104547bd8c4357bdb8e8c424ee1",
"main.dart.js_81.part.js": "2cc93b4ec879778a265530150f4eb76b",
"main.dart.js_28.part.js": "d8be58d69f3cbabc1f76aebac7b3ac50",
"main.dart.js_38.part.js": "769766f34afe8cd36b9581cea7cb563b",
"main.dart.js_75.part.js": "c52f72f48ae39adf34ebbb1151e52a38",
"main.dart.js_65.part.js": "889d6db27f2db62a7b1caaa47b56bd0d",
"main.dart.js_150.part.js": "a5b7cbbe9e944f18c2b2e3bde0f8a894",
"main.dart.js_122.part.js": "cdcba1c18bbbd6db118fde37dce88141",
"main.dart.js_17.part.js": "5126dac441a52d14ea973e5d04ba19e2",
"main.dart.js_88.part.js": "79071139ed7001347a74ae3b46af7ce8",
"main.dart.js_98.part.js": "6f777d8136ae5f9f216ebfa1709f4b08",
"main.dart.js_31.part.js": "6e7d26524770ec009454482830542cb8",
"main.dart.js_21.part.js": "1b40d249e2024b6e01262394f6f8297f",
"main.dart.js_104.part.js": "682d08312939a2a8eee8a21206a3956c",
"main.dart.js_176.part.js": "0bc41e4d11bc82876254986d0bf8d99e",
"main.dart.js_166.part.js": "83e051340e18e91415357af9701133d5",
"main.dart.js_53.part.js": "95bba71181b739cb50ad6f9af7008cbb",
"main.dart.js_200.part.js": "5375076708809cda540b45c6279b0bef",
"main.dart.js_43.part.js": "b07b3b980af0960c843277458a2abb26",
"main.dart.js_192.part.js": "5d309d8e895d999e95420679d60293e5",
"main.dart.js_182.part.js": "102f6d1aa2b13bcb2a7d11c671d481a8",
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
