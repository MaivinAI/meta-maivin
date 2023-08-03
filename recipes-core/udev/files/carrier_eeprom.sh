#!/usr/bin/env sh
#
# Called from udev for Maivin carrier EEPROM.
#

if [ "$ACTION" = "add" ]; then
    ln -s "/sys$DEVPATH/eeprom" /dev/carrier_eeprom
elif [ "$ACTION" = "remove" ]; then
    rm -f /dev/carrier_eeprom
fi
