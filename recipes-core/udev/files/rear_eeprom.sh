#!/usr/bin/env sh
#
# Called from udev for Maivin rear connector EEPROM.
#

if [ "$ACTION" = "add" ]; then
    ln -s "/sys$DEVPATH/eeprom" /dev/rear_eeprom
elif [ "$ACTION" = "remove" ]; then
    rm -f /dev/rear_eeprom
fi
