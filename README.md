# Maivin Yocto Layer

Yocto meta-maivin adds Maivin support to the Toradex Yocto build.  This branch is for kirkstone which is supported by Toradex BSP6.

## Build Instructions

Toradex provides instructions in [Build a Reference Image with Yocto Project](1) which describes how to build and flash the image using Toradex Easy Installer.  You will need to perform two steps to add meta-maivin to the build which are detailed below.

## Add meta-maivin

Go into the layers sub-directory of your configured repo checkout of the Toradex Yocto.  Then checkout meta-maivin.  This can also be integrated into repo though this is not covered by this guide.

```
git checkout https://github.com/MaivinAI/meta-maivin
```

## Add Layer Configuration

At the bottom of your conf/bblayers.conf you will need to add the meta-maivin layer from above.

```
${TOPDIR}/../layers/meta-maivin
```

## Add Machine Configuration

You will need to add to your conf/local.conf file the following line after the machine include to ensure the Maivin DTB files are copied to /boot.

```
include conf/machine/verdin-imx8mp-maivin.conf
```

[1]: https://developer.toradex.com/linux-bsp/os-development/build-yocto/build-a-reference-image-with-yocto-projectopenembedded/
