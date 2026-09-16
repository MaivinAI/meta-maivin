# Copilot / Claude Instructions — Torizon for Maivin

This is the project-level guidance for **Torizon for Maivin**, the Yocto/OpenEmbedded
BSP for the **Maivin AI Vision platform** (Au-Zone Technologies, Toradex Verdin
i.MX8M Plus SoM on the Maivin carrier board). It is surfaced at the working-tree
root via a `repo` `linkfile` (it physically lives in
`layers/meta-maivin/.github/copilot-instructions.md`).

`meta-maivin` is the platform-owning layer: distro, image, kernel, device tree,
and the service wiring for the stock `edgefirst-*` middleware. **`torizon-adis`**
is a downstream product built on this same platform (see
[Relationship with torizon-adis](#relationship-with-torizon-adis) below) — changes
here ripple there.

- Target: Torizon OS **7.x** / Yocto **Scarthgap** (Walnascar dual-compat
  required — see root `CLAUDE.md`), `MACHINE=verdin-imx8mp`,
  `DISTRO=torizon-maivin`.
- Image: `torizon-core-maivin`.
- For **manifest / Toradex-upstream** work (upgrading the Torizon release, layer
  pin bumps), read the manifest-repo playbook at
  `.repo/manifests/.github/copilot-instructions.md` instead.
- For the full layer/hardware/middleware inventory, on-target dev workflow, and
  day-to-day BitBake commands, see the root `CLAUDE.md` — this file only covers
  what that one doesn't: cross-repo coordination and in-flight migration state.

## Repository layout

This is a Google `repo` workspace, not a single git repo — see `CLAUDE.md` for
the full tree. The layers relevant to this file:

```
torizon-maivin/
├── .repo/manifests/       ← the manifest git repo (au-zone/torizon-maivin)
│     └── maivin/default.xml    ← active manifest (repo init -m maivin/default.xml)
└── layers/
      ├── meta-maivin      ← Au-Zone (this layer; platform distro/image/kernel)
      ├── meta-edgefirst   ← EdgeFirstAI (Zenoh foundation + stock services)
      ├── meta-kinara      ← EdgeFirstAI (Kinara Ara-2 NPU, NDA-licensed)
      └── meta-toradex-* … ← upstream Toradex / OE / Freescale (pinned)
```

## meta-deepview: removed

`meta-deepview` (DeepView RT / VAAL / the old `videostream`) is **no longer a
layer dependency of torizon-maivin** — dropped from `maivin/default.xml`,
`meta-maivin/conf/bblayers.conf`, and `torizon-maivin.conf`. `videostream` is
provided by `meta-edgefirst`; `edgefirst-model` runs TFLite + the VX delegate
directly (no DeepView RT ModelRunner); the old `vpkui` recipe (the last
DeepView RT/VAAL consumer in `meta-maivin`, and already unused by any image or
packagegroup) was deleted outright rather than ported.

This was a direct removal, not a staged migration — nothing in
`packagegroup-maivin-middleware` or the image recipe depended on
`visionpack-base`/`visionpack-python`/`deepview-rt-modelrunner` at runtime, so
there was no blocking consumer to port first (contrast with `torizon-adis`
below, which had one). If a clean build surfaces a task that still expects
`deepview-rt`, `vaal`, `visionpack-base`, or `visionpack-python`, that's a
regression from this removal — check `bitbake -e <recipe> | grep DEPENDS` on
the failing recipe rather than re-adding the layer.

## Updating meta-edgefirst

Bump procedure lives in the manifest playbook (`Updating the Maivin layers`
section). One addition specific to this layer: **check torizon-adis's pin as a
freshness signal.** `torizon-adis` (`.repo/manifests/adis/default.xml`) pins
the same `meta-edgefirst` remote and often moves first — ADIS iterates on it
faster since its app layer depends directly on `videostream`/`edgefirst-schemas`
releases. If ADIS's pin is ahead, that's usually a validated newer SHA worth
picking up here too, not just a diff to ignore.

## Relationship with torizon-adis

`torizon-adis` reuses `meta-maivin` and `meta-edgefirst` wholesale and layers a
proprietary product overlay (`meta-adis`) on top, replacing the stock
`edgefirst-<service>` units with custom ADIS apps on the same Zenoh topics.
Two things drift silently if not kept in sync by hand:

- **The Scarthgap/meta-edgefirst integration block** in
  `meta-maivin/conf/distro/torizon-maivin.conf` (flatbuffers pin, BBMASK'd
  bbappends, nnstreamer `PACKAGECONFIG` fix) is hand-copied into
  `torizon-adis.conf`. If you touch that block here, port the same change
  there (and vice versa) — `torizon-adis.conf`'s copy is explicitly commented
  as mirroring this one.
- **meta-deepview status**: this layer has fully removed it (see above).
  `torizon-adis` is still mid-migration at the app layer — `adis-webvision`
  dropped VAAL/DeepView RT, but `adis-logger` still links both directly, so
  `meta-deepview` stays a required layer there until that's ported. Don't
  assume ADIS can drop the layer just because Maivin did.

## Local OSTree deployment for testing (skip the Jenkins round trip)

For testing a local build change on real hardware, deploy straight from the
build host over LAN instead of waiting on Jenkins → S3 → CloudFront → device
OTA. Verified working 2026-09-16 against `verdin-imx8mp-15141091`: a 3-package
`IMAGE_INSTALL` change built locally, deployed, and booted in well under the
time a single Jenkins round trip takes, with **zero impact on the device's
real `maivin` OTA remote** — aktualizr does not read the deployment's origin
refspec (see `ostree-channel-origin-model` project memory), so this never
collides with production updates.

Every local `bitbake <image>` run already produces a complete, self-contained
OSTree archive repo — no extra recipe/task needed:

```
build/deploy/images/verdin-imx8mp/ostree_repo
```

Steps:

1. **Build normally** (`DISTRO=torizon-maivin bitbake torizon-core-maivin`).
2. **Find the ref to deploy.** Refs follow Toradex's
   `0/<machine>/<distro>/<image-basename>/<purpose-lowercase>` scheme, e.g.
   `0/verdin-imx8mp/torizon-maivin/torizon-core-maivin/develop` (from
   `TDX_PURPOSE ?= "Develop"`). List them and confirm the commit you expect:
   ```shell
   ostree --repo=build/deploy/images/verdin-imx8mp/ostree_repo refs
   ostree --repo=build/deploy/images/verdin-imx8mp/ostree_repo log <ref>
   ```
3. **Serve the repo over HTTP** from the build host (needs no auth — this is
   why it must never be left running against anything but a trusted LAN):
   ```shell
   cd build/deploy/images/verdin-imx8mp/ostree_repo
   python3 -m http.server 8765 --bind 0.0.0.0
   ```
4. **On the target**, add a one-off remote and pull:
   ```shell
   sudo ostree remote add --no-gpg-verify --if-not-exists local-test http://<build-host-ip>:8765/
   sudo ostree pull local-test <ref>
   sudo ostree admin deploy --os=torizon local-test:<ref>
   sudo reboot
   ```
   `ostree admin deploy` stages the new commit as `pending` and demotes the
   previously-current deployment to `rollback` — it does **not** delete it.
   Confirm before and after with `sudo ostree admin status`.
5. **Roll back** if needed: `sudo ostree admin rollback`, or just re-deploy
   the production `maivin:torizon/maivin/<channel>` ref once a real OTA
   build lands. Do not run `ostree admin cleanup` while you still need the
   rollback slot.

Gotchas hit while validating this:

- If the device already has an in-progress `ostree admin unlock --hotfix`
  overlay (persistent, unlike a plain transient unlock), `deploy` leaves that
  deployment untouched and current — it only replaces the *other* slot. Check
  `ostree admin status` first so you know what you might be about to demote.
- `ostree pull` over a plain HTTP remote needs `--no-gpg-verify` (no signing
  keys involved for a local dev repo).
- A LAN pull of a small incremental change (~115 MB compressed / ~330 MB
  content, three added packages) took ~18 seconds — the actual bottleneck in
  this workflow is the local `bitbake` build, not the deploy step. A
  follow-up iteration changing only a single systemd unit file pulled ~92 KB
  in ~3 seconds — OSTree's content-addressed storage means each redeploy
  only ever costs what actually changed.
- Kill the `http.server` and remove the `local-test` remote when done if the
  device shouldn't keep trusting your build host indefinitely.

## Conventions / guardrails

- **Sign every commit and tag with `-s`.** The author is the engineer, never
  the tool.
- Never fabricate git SHAs — resolve with `git rev-parse` / `git rev-list -n1`.
- Layer compatibility is gated by `LAYERSERIES_COMPAT_meta-maivin` in
  `conf/layer.conf`; it must include the active Yocto series (`scarthgap`, and
  `walnascar` per the dual-compat requirement in `CLAUDE.md`).
- `maivin/default.xml` is the only manifest `repo init` should target for this
  product.
