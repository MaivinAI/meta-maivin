SUMMARY = "Torizon for Maivin"
DESCRIPTION = "Torizon for Maivin"

require recipes-images/images/torizon-core-container.inc

CORE_IMAGE_BASE_INSTALL_append = " \
    docker-ce \
    python3-docker-compose \
    docker-compose-up \
    docker-integrity-checker \
    docker-watchdog \
    docker-auto-prune \
"

IMAGE_VARIANT = "Maivin"

inherit extrausers

EXTRA_USERS_PARAMS += "\
usermod -a -G docker torizon; \
"
