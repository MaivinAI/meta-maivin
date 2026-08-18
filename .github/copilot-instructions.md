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

## Conventions / guardrails

- **Sign every commit and tag with `-s`.** The author is the engineer, never
  the tool.
- Never fabricate git SHAs — resolve with `git rev-parse` / `git rev-list -n1`.
- Layer compatibility is gated by `LAYERSERIES_COMPAT_meta-maivin` in
  `conf/layer.conf`; it must include the active Yocto series (`scarthgap`, and
  `walnascar` per the dual-compat requirement in `CLAUDE.md`).
- `maivin/default.xml` is the only manifest `repo init` should target for this
  product.
