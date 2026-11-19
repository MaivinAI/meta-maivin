KERNEL_MODULE_AUTOLOAD += " vvcam-video "

# Au-Zone's fork, which carries a number of improvements to the ISP kernel
# driver. See github.com/EdgeFirstAI/isp-vvcam (branch edgefirst-lf-6.6.y_2.2.0) for details.
ISP_KERNEL_SRC = "git://github.com/EdgeFirstAI/isp-vvcam.git;protocol=https"
SRCBRANCH = "edgefirst-lf-6.6.y_2.2.0"
SRCREV = "fe4b15f7bd9939d25e0fb3c0a68b10cbddd02039"
