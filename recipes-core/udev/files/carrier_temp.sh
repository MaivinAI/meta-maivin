#!/usr/bin/env sh
#
# Called from udev for Maivin carrier temperature sensor.
#

if [ "$ACTION" = "add" ]; then
    ln -s "/sys$DEVPATH/temp1_input" /dev/carrier_temp
elif [ "$ACTION" = "remove" ]; then
    rm -f /dev/carrier_temp
fi
