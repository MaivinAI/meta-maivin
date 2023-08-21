#!/usr/bin/env sh
#
# Called from udev for Maivin 2 carrier humidity and temperature sensor.
#

if [ "$ACTION" = "add" ]; then
    ln -s "/sys$DEVPATH/in_humidityrelative_input" /dev/carrier_humidity
    ln -s "/sys$DEVPATH/in_temp_input" /dev/carrier_humidity_temp
elif [ "$ACTION" = "remove" ]; then
    rm -f /dev/carrier_humidity
    rm -f /dev/carrier_humidity_temp
fi
