FILESEXTRAPATHS:append := "${THISDIR}/files:"

GARAGE_SIGN_PV = "0.7.7"

SRC_URI:remove = "https://tuf-cli-releases.ota.here.com/cli-${GARAGE_SIGN_PV}.tgz;unpack=0;name=garagesign"
SRC_URI:append = "https://garage-sign.s3.eu-west-1.amazonaws.com/cli-${GARAGE_SIGN_PV}.tgz;unpack=0;name=garagesign"

SRC_URI[garagesign.md5sum] = "138fc97c7130258efa80865a83290ad1"
SRC_URI[garagesign.sha256sum] = "16d9eef5a3144fbddf74ec206714ce2c526f4b68d8259da7fb5004f284848d59"
