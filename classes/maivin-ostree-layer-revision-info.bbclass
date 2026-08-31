# Local copy of meta-toradex-torizon's ostree_layer_revision_info.bbclass
# (upstream commit 9daddac6, "get_layer_revision_information: reducing
# parsing time from 10min to 30s"). This project's manifest currently pins
# meta-toradex-torizon to a revision that predates that class
# (aacc45aa1567d81705746c21244ffe6676908bcf), so torizon-core-maivin.bb
# cannot `inherit ostree_layer_revision_info` from there -- BitBake parse
# fails with "Could not inherit file classes/ostree_layer_revision_info
# .bbclass". Providing the same OSTREE_LAYER_REVISION_INFO computation
# locally keeps EXTRA_OSTREE_COMMIT's oe.layers metadata working
# (torizon_base_image_type.inc references it unconditionally) without
# depending on an upstream revision we don't have pinned.
#
# Once the meta-toradex-torizon pin is bumped past 9daddac6, this file and
# its `inherit` in torizon-core-maivin.bb can be dropped in favor of the
# upstream class.

def get_layer_revision_information(d):
    import bb.process
    import subprocess
    try:
        layers = []
        paths = (d.getVar("BBLAYERS" or "")).split()

        for path in paths:
            # Use relative path from ${OEROOT}/layers/ as layer name
            name = os.path.relpath(path, os.path.join(d.getVar('OEROOT'), "layers"))
            rev, _ = bb.process.run('export PSEUDO_UNLOAD=1; git rev-parse HEAD', cwd=path)
            branch, _ = bb.process.run('export PSEUDO_UNLOAD=1; git rev-parse --abbrev-ref HEAD', cwd=path)
            try:
                subprocess.check_output("""cd %s; export PSEUDO_UNLOAD=1; set -e;
                                        git diff --quiet --no-ext-diff
                                        git diff --quiet --no-ext-diff --cached""" % path,
                                        shell=True,
                                        stderr=subprocess.STDOUT)
                modified = ""
            except subprocess.CalledProcessError as ex:
                modified = ":modified"
            # Key/value pair per layer
            layers.append("'{}': '{}:{}{}'".format(name, branch.strip(), rev.strip(), modified))

        # Create GLib dictionary
        return "{" + ",".join(layers) + "}"
    except:
        e = sys.exc_info()[0]
        bb.warn("Failed to get layers information. Caused by layer at {}. Exception: {}".format(path, e))

# Use immediate expansion here to avoid calling a somewhat costly function whenever
# EXTRA_OSTREE_COMMIT is expanded.
OSTREE_LAYER_REVISION_INFO := "${@get_layer_revision_information(d)}"
