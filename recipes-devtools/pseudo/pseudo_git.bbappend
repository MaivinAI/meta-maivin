# Bisection experiment only (this Torizon-7.0.0-targeted branch): the
# 7.0.0-pinned openembedded-core ships pseudo 1.9.0, which predates
# upstream's "openat2, exec and linkat fixes" and glibc-2.43 compat fixes.
# On this (much newer than validated) host, do_package for several
# unrelated recipes -- base-files, ca-certificates, kernel headers --
# fails with pseudo errors ("got *at() syscall for unknown directory",
# "couldn't allocate absolute path for ..."), which are exactly what
# those upstream fixes address.
#
# Bump to 1.9.11, the latest coordinated version bump openembedded-core
# itself later moved to (well past the relevant fixes). SRC_URI is
# overridden rather than just SRCREV/PV: 1.9.11 no longer needs
# 0001-configure-Prune-PIE-flags.patch or glibc238.patch (upstreamed/
# obsoleted as pseudo's own source moved on) and reverts to a plain
# tag-based fetch, so keeping the 1.9.0-era SRC_URI would try to apply
# patches that no longer match this newer source and fail do_patch.
#
# pseudo is a native build-host tool only, never deployed to the target,
# so this has no effect on the actual 7.0.0 target image or the
# kernel/camera investigation this branch is for.
#
# older-glibc-symbols.patch (still applied via the base recipe's
# SRC_URI:append:class-native/nativesdk, unchanged here) also needed
# updating to match: it no longer applied cleanly against 1.9.11's source
# (a pseudo_client_scanf.o split was added upstream between these
# versions) -- the version below is lifted from the same later
# openembedded-core commit as the SRCREV/PV bump.
FILESEXTRAPATHS:prepend := "${THISDIR}/pseudo_git:"

SRC_URI = "git://git.yoctoproject.org/pseudo;branch=master;protocol=https \
           file://fallback-passwd \
           file://fallback-group \
           "
SRCREV = "ba8887e5f1e922f866681ec7dec1a00b602a9328"
PV = "1.9.11"
