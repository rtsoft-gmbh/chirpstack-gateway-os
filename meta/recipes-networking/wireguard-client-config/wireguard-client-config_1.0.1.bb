DESCRIPTION = "Wireguard VPN client configuration"
HOMEPAGE = "https://www.chirpstack.io/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# procps is needed to work around the following wireguard error:
# sysctl: invalid option -- 'r'
# iproute2 is needed as busybox ip command does not implement all needed features.
RDEPENDS_${PN} = "wireguard-tools procps iproute2"

SRC_URI = " \
    file://wireguard.init \
    file://wireguard.default \
    file://wg0.conf \
"
PR = "r1"

inherit update-rc.d

INITSCRIPT_NAME = "wireguard"
INITSCRIPT_PARAMS = "defaults"

do_install() {
    install -d ${D}${sysconfdir}/init.d
    install -m 0750 ${WORKDIR}/wireguard.init ${D}${sysconfdir}/init.d/wireguard

    install -d ${D}${sysconfdir}/default
    install -m 0644 ${WORKDIR}/wireguard.default ${D}${sysconfdir}/default/wireguard

    install -d ${D}${sysconfdir}/wireguard
    install -m 0640 ${WORKDIR}/wg0.conf ${D}${sysconfdir}/wireguard/wg0.conf
}

FILES_${PN} = "${sysconfdir}"
