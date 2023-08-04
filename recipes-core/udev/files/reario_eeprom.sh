#!/usr/bin/env sh
#
# Called from udev for Maivin rear connector EEPROM.
#

if [ "$ACTION" = "add" ]; then
    ln -s "/sys$DEVPATH/eeprom" /dev/reario_eeprom
elif [ "$ACTION" = "remove" ]; then
    rm -f /dev/reario_eeprom
fi
