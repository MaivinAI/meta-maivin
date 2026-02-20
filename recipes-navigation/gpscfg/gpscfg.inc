# please note if you have entries that do not begin with crate://
# you must change them to how that package can be fetched
SRC_URI += " \
    crate://crates.io/CoreFoundation-sys/0.1.4 \
    crate://crates.io/IOKit-sys/0.1.5 \
    crate://crates.io/aho-corasick/1.0.5 \
    crate://crates.io/android-tzdata/0.1.1 \
    crate://crates.io/android_system_properties/0.1.5 \
    crate://crates.io/ansi_term/0.12.1 \
    crate://crates.io/atty/0.2.14 \
    crate://crates.io/autocfg/1.1.0 \
    crate://crates.io/bitflags/1.3.2 \
    crate://crates.io/bitflags/2.4.0 \
    crate://crates.io/bumpalo/3.13.0 \
    crate://crates.io/cc/1.0.83 \
    crate://crates.io/cfg-if/0.1.10 \
    crate://crates.io/cfg-if/1.0.0 \
    crate://crates.io/chrono/0.4.28 \
    crate://crates.io/clap/2.34.0 \
    crate://crates.io/core-foundation-sys/0.8.4 \
    crate://crates.io/hermit-abi/0.1.19 \
    crate://crates.io/iana-time-zone-haiku/0.1.2 \
    crate://crates.io/iana-time-zone/0.1.57 \
    crate://crates.io/itoa/1.0.9 \
    crate://crates.io/js-sys/0.3.64 \
    crate://crates.io/libc/0.2.147 \
    crate://crates.io/libudev-sys/0.1.4 \
    crate://crates.io/libudev/0.2.0 \
    crate://crates.io/log/0.4.20 \
    crate://crates.io/mach/0.1.2 \
    crate://crates.io/mach/0.2.3 \
    crate://crates.io/memchr/2.6.3 \
    crate://crates.io/nix/0.14.1 \
    crate://crates.io/num-traits/0.2.16 \
    crate://crates.io/once_cell/1.18.0 \
    crate://crates.io/pkg-config/0.3.27 \
    crate://crates.io/proc-macro2/1.0.66 \
    crate://crates.io/quote/1.0.33 \
    crate://crates.io/regex-automata/0.3.8 \
    crate://crates.io/regex-syntax/0.7.5 \
    crate://crates.io/regex/1.9.5 \
    crate://crates.io/ryu/1.0.15 \
    crate://crates.io/serde/1.0.188 \
    crate://crates.io/serde_derive/1.0.188 \
    crate://crates.io/serde_json/1.0.105 \
    crate://crates.io/serialport/3.3.0 \
    crate://crates.io/strsim/0.8.0 \
    crate://crates.io/syn/1.0.109 \
    crate://crates.io/syn/2.0.30 \
    crate://crates.io/textwrap/0.11.0 \
    crate://crates.io/time/0.1.45 \
    crate://crates.io/unicode-ident/1.0.11 \
    crate://crates.io/unicode-width/0.1.10 \
    crate://crates.io/vec_map/0.8.2 \
    crate://crates.io/void/1.0.2 \
    crate://crates.io/wasi/0.10.0+wasi-snapshot-preview1 \
    crate://crates.io/wasm-bindgen-backend/0.2.87 \
    crate://crates.io/wasm-bindgen-macro-support/0.2.87 \
    crate://crates.io/wasm-bindgen-macro/0.2.87 \
    crate://crates.io/wasm-bindgen-shared/0.2.87 \
    crate://crates.io/wasm-bindgen/0.2.87 \
    crate://crates.io/winapi-i686-pc-windows-gnu/0.4.0 \
    crate://crates.io/winapi-x86_64-pc-windows-gnu/0.4.0 \
    crate://crates.io/winapi/0.3.9 \
    crate://crates.io/windows-targets/0.48.5 \
    crate://crates.io/windows/0.48.0 \
    crate://crates.io/windows_aarch64_gnullvm/0.48.5 \
    crate://crates.io/windows_aarch64_msvc/0.48.5 \
    crate://crates.io/windows_i686_gnu/0.48.5 \
    crate://crates.io/windows_i686_msvc/0.48.5 \
    crate://crates.io/windows_x86_64_gnu/0.48.5 \
    crate://crates.io/windows_x86_64_gnullvm/0.48.5 \
    crate://crates.io/windows_x86_64_msvc/0.48.5 \
"

SRC_URI[CoreFoundation-sys-0.1.4.sha256sum] = "d0e9889e6db118d49d88d84728d0e964d973a5680befb5f85f55141beea5c20b"
SRC_URI[IOKit-sys-0.1.5.sha256sum] = "99696c398cbaf669d2368076bdb3d627fb0ce51a26899d7c61228c5c0af3bf4a"
SRC_URI[aho-corasick-1.0.5.sha256sum] = "0c378d78423fdad8089616f827526ee33c19f2fddbd5de1629152c9593ba4783"
SRC_URI[android-tzdata-0.1.1.sha256sum] = "e999941b234f3131b00bc13c22d06e8c5ff726d1b6318ac7eb276997bbb4fef0"
SRC_URI[android_system_properties-0.1.5.sha256sum] = "819e7219dbd41043ac279b19830f2efc897156490d7fd6ea916720117ee66311"
SRC_URI[ansi_term-0.12.1.sha256sum] = "d52a9bb7ec0cf484c551830a7ce27bd20d67eac647e1befb56b0be4ee39a55d2"
SRC_URI[atty-0.2.14.sha256sum] = "d9b39be18770d11421cdb1b9947a45dd3f37e93092cbf377614828a319d5fee8"
SRC_URI[autocfg-1.1.0.sha256sum] = "d468802bab17cbc0cc575e9b053f41e72aa36bfa6b7f55e3529ffa43161b97fa"
SRC_URI[bitflags-1.3.2.sha256sum] = "bef38d45163c2f1dde094a7dfd33ccf595c92905c8f8f4fdc18d06fb1037718a"
SRC_URI[bitflags-2.4.0.sha256sum] = "b4682ae6287fcf752ecaabbfcc7b6f9b72aa33933dc23a554d853aea8eea8635"
SRC_URI[bumpalo-3.13.0.sha256sum] = "a3e2c3daef883ecc1b5d58c15adae93470a91d425f3532ba1695849656af3fc1"
SRC_URI[cc-1.0.83.sha256sum] = "f1174fb0b6ec23863f8b971027804a42614e347eafb0a95bf0b12cdae21fc4d0"
SRC_URI[cfg-if-0.1.10.sha256sum] = "4785bdd1c96b2a846b2bd7cc02e86b6b3dbf14e7e53446c4f54c92a361040822"
SRC_URI[cfg-if-1.0.0.sha256sum] = "baf1de4339761588bc0619e3cbc0120ee582ebb74b53b4efbf79117bd2da40fd"
SRC_URI[chrono-0.4.28.sha256sum] = "95ed24df0632f708f5f6d8082675bef2596f7084dee3dd55f632290bf35bfe0f"
SRC_URI[clap-2.34.0.sha256sum] = "a0610544180c38b88101fecf2dd634b174a62eef6946f84dfc6a7127512b381c"
SRC_URI[core-foundation-sys-0.8.4.sha256sum] = "e496a50fda8aacccc86d7529e2c1e0892dbd0f898a6b5645b5561b89c3210efa"
SRC_URI[hermit-abi-0.1.19.sha256sum] = "62b467343b94ba476dcb2500d242dadbb39557df889310ac77c5d99100aaac33"
SRC_URI[iana-time-zone-haiku-0.1.2.sha256sum] = "f31827a206f56af32e590ba56d5d2d085f558508192593743f16b2306495269f"
SRC_URI[iana-time-zone-0.1.57.sha256sum] = "2fad5b825842d2b38bd206f3e81d6957625fd7f0a361e345c30e01a0ae2dd613"
SRC_URI[itoa-1.0.9.sha256sum] = "af150ab688ff2122fcef229be89cb50dd66af9e01a4ff320cc137eecc9bacc38"
SRC_URI[js-sys-0.3.64.sha256sum] = "c5f195fe497f702db0f318b07fdd68edb16955aed830df8363d837542f8f935a"
SRC_URI[libc-0.2.147.sha256sum] = "b4668fb0ea861c1df094127ac5f1da3409a82116a4ba74fca2e58ef927159bb3"
SRC_URI[libudev-sys-0.1.4.sha256sum] = "3c8469b4a23b962c1396b9b451dda50ef5b283e8dd309d69033475fa9b334324"
SRC_URI[libudev-0.2.0.sha256sum] = "ea626d3bdf40a1c5aee3bcd4f40826970cae8d80a8fec934c82a63840094dcfe"
SRC_URI[log-0.4.20.sha256sum] = "b5e6163cb8c49088c2c36f57875e58ccd8c87c7427f7fbd50ea6710b2f3f2e8f"
SRC_URI[mach-0.1.2.sha256sum] = "2fd13ee2dd61cc82833ba05ade5a30bb3d63f7ced605ef827063c63078302de9"
SRC_URI[mach-0.2.3.sha256sum] = "86dd2487cdfea56def77b88438a2c915fb45113c5319bfe7e14306ca4cd0b0e1"
SRC_URI[memchr-2.6.3.sha256sum] = "8f232d6ef707e1956a43342693d2a31e72989554d58299d7a88738cc95b0d35c"
SRC_URI[nix-0.14.1.sha256sum] = "6c722bee1037d430d0f8e687bbdbf222f27cc6e4e68d5caf630857bb2b6dbdce"
SRC_URI[num-traits-0.2.16.sha256sum] = "f30b0abd723be7e2ffca1272140fac1a2f084c77ec3e123c192b66af1ee9e6c2"
SRC_URI[once_cell-1.18.0.sha256sum] = "dd8b5dd2ae5ed71462c540258bedcb51965123ad7e7ccf4b9a8cafaa4a63576d"
SRC_URI[pkg-config-0.3.27.sha256sum] = "26072860ba924cbfa98ea39c8c19b4dd6a4a25423dbdf219c1eca91aa0cf6964"
SRC_URI[proc-macro2-1.0.66.sha256sum] = "18fb31db3f9bddb2ea821cde30a9f70117e3f119938b5ee630b7403aa6e2ead9"
SRC_URI[quote-1.0.33.sha256sum] = "5267fca4496028628a95160fc423a33e8b2e6af8a5302579e322e4b520293cae"
SRC_URI[regex-automata-0.3.8.sha256sum] = "c2f401f4955220693b56f8ec66ee9c78abffd8d1c4f23dc41a23839eb88f0795"
SRC_URI[regex-syntax-0.7.5.sha256sum] = "dbb5fb1acd8a1a18b3dd5be62d25485eb770e05afb408a9627d14d451bae12da"
SRC_URI[regex-1.9.5.sha256sum] = "697061221ea1b4a94a624f67d0ae2bfe4e22b8a17b6a192afb11046542cc8c47"
SRC_URI[ryu-1.0.15.sha256sum] = "1ad4cc8da4ef723ed60bced201181d83791ad433213d8c24efffda1eec85d741"
SRC_URI[serde-1.0.188.sha256sum] = "cf9e0fcba69a370eed61bcf2b728575f726b50b55cba78064753d708ddc7549e"
SRC_URI[serde_derive-1.0.188.sha256sum] = "4eca7ac642d82aa35b60049a6eccb4be6be75e599bd2e9adb5f875a737654af2"
SRC_URI[serde_json-1.0.105.sha256sum] = "693151e1ac27563d6dbcec9dee9fbd5da8539b20fa14ad3752b2e6d363ace360"
SRC_URI[serialport-3.3.0.sha256sum] = "5b8d3ecaf58010bedccae17be55d4ed6f2ecde5646fc48ce8c66ea2d35a1419c"
SRC_URI[strsim-0.8.0.sha256sum] = "8ea5119cdb4c55b55d432abb513a0429384878c15dde60cc77b1c99de1a95a6a"
SRC_URI[syn-1.0.109.sha256sum] = "72b64191b275b66ffe2469e8af2c1cfe3bafa67b529ead792a6d0160888b4237"
SRC_URI[syn-2.0.30.sha256sum] = "0ddc1f908d32ec46858c2d3b3daa00cc35bf4b6841ce4355c7bb3eedf2283a68"
SRC_URI[textwrap-0.11.0.sha256sum] = "d326610f408c7a4eb6f51c37c330e496b08506c9457c9d34287ecc38809fb060"
SRC_URI[time-0.1.45.sha256sum] = "1b797afad3f312d1c66a56d11d0316f916356d11bd158fbc6ca6389ff6bf805a"
SRC_URI[unicode-ident-1.0.11.sha256sum] = "301abaae475aa91687eb82514b328ab47a211a533026cb25fc3e519b86adfc3c"
SRC_URI[unicode-width-0.1.10.sha256sum] = "c0edd1e5b14653f783770bce4a4dabb4a5108a5370a5f5d8cfe8710c361f6c8b"
SRC_URI[vec_map-0.8.2.sha256sum] = "f1bddf1187be692e79c5ffeab891132dfb0f236ed36a43c7ed39f1165ee20191"
SRC_URI[void-1.0.2.sha256sum] = "6a02e4885ed3bc0f2de90ea6dd45ebcbb66dacffe03547fadbb0eeae2770887d"
SRC_URI[wasi-0.10.0+wasi-snapshot-preview1.sha256sum] = "1a143597ca7c7793eff794def352d41792a93c481eb1042423ff7ff72ba2c31f"
SRC_URI[wasm-bindgen-backend-0.2.87.sha256sum] = "5ef2b6d3c510e9625e5fe6f509ab07d66a760f0885d858736483c32ed7809abd"
SRC_URI[wasm-bindgen-macro-support-0.2.87.sha256sum] = "54681b18a46765f095758388f2d0cf16eb8d4169b639ab575a8f5693af210c7b"
SRC_URI[wasm-bindgen-macro-0.2.87.sha256sum] = "dee495e55982a3bd48105a7b947fd2a9b4a8ae3010041b9e0faab3f9cd028f1d"
SRC_URI[wasm-bindgen-shared-0.2.87.sha256sum] = "ca6ad05a4870b2bf5fe995117d3728437bd27d7cd5f06f13c17443ef369775a1"
SRC_URI[wasm-bindgen-0.2.87.sha256sum] = "7706a72ab36d8cb1f80ffbf0e071533974a60d0a308d01a5d0375bf60499a342"
SRC_URI[winapi-i686-pc-windows-gnu-0.4.0.sha256sum] = "ac3b87c63620426dd9b991e5ce0329eff545bccbbb34f3be09ff6fb6ab51b7b6"
SRC_URI[winapi-x86_64-pc-windows-gnu-0.4.0.sha256sum] = "712e227841d057c1ee1cd2fb22fa7e5a5461ae8e48fa2ca79ec42cfc1931183f"
SRC_URI[winapi-0.3.9.sha256sum] = "5c839a674fcd7a98952e593242ea400abe93992746761e38641405d28b00f419"
SRC_URI[windows-targets-0.48.5.sha256sum] = "9a2fa6e2155d7247be68c096456083145c183cbbbc2764150dda45a87197940c"
SRC_URI[windows-0.48.0.sha256sum] = "e686886bc078bc1b0b600cac0147aadb815089b6e4da64016cbd754b6342700f"
SRC_URI[windows_aarch64_gnullvm-0.48.5.sha256sum] = "2b38e32f0abccf9987a4e3079dfb67dcd799fb61361e53e2882c3cbaf0d905d8"
SRC_URI[windows_aarch64_msvc-0.48.5.sha256sum] = "dc35310971f3b2dbbf3f0690a219f40e2d9afcf64f9ab7cc1be722937c26b4bc"
SRC_URI[windows_i686_gnu-0.48.5.sha256sum] = "a75915e7def60c94dcef72200b9a8e58e5091744960da64ec734a6c6e9b3743e"
SRC_URI[windows_i686_msvc-0.48.5.sha256sum] = "8f55c233f70c4b27f66c523580f78f1004e8b5a8b659e05a4eb49d4166cca406"
SRC_URI[windows_x86_64_gnu-0.48.5.sha256sum] = "53d40abd2583d23e4718fddf1ebec84dbff8381c07cae67ff7768bbf19c6718e"
SRC_URI[windows_x86_64_gnullvm-0.48.5.sha256sum] = "0b7b52767868a23d5bab768e390dc5f5c55825b6d30b86c844ff2dc7414044cc"
SRC_URI[windows_x86_64_msvc-0.48.5.sha256sum] = "ed94fce61571a4006852b7389a063ab983c02eb1bb37b47f8272ce92d06d9538"
