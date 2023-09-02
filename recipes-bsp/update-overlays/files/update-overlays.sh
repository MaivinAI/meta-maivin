#!/bin/sh

UPDATED=0

for boot in /boot/ostree/torizon-*
do
    DIFF=$(diff -q /etc/overlays.txt $boot/dtb/overlays.txt)

    if [ "$DIFF" != "" ]
    then
        echo updating $boot/dtb/overlays.txt
        cp /etc/overlays.txt $boot/dtb/overlays.txt
        UPDATED=1
    fi
done

if [ $UPDATED = 1 ]
then
    echo rebooting to load updated overlays
    reboot
fi
